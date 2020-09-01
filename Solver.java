package mensa;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.time.Duration;
import java.util.HashSet;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

//import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class Solver {
    private static final String FILE_FORMAT = "png";

    int iterations = 0;
    int predict = -1;
    private int cacheMiss = 0;
    private int cacheHit = 0;
    private GraphicsContext gc;
    private Point centerPoint;
    float scale;
    private PriorityQueue<LayoutPuzzles> layoutPuzzles;
    private HashSet<LayoutPuzzles> cache;
    private Scanner in;
    private LayoutPredictor layoutPredictor;
    private Instant start;
    // CODE HERE
    private Instant finish;


    private Canvas canvas;
    private GraphicsContext localGc;
    //private static final Logger LOGGER = LogManager.getLogger(Snapshot.class);


    public static String saveToFile(final Canvas canvas, String name) {
        File destination;
        final WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        final WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), writableImage);
        destination = new File("/tmp/" + name + ".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), FILE_FORMAT, destination);

        } catch (final IOException e) {
            System.out.println(e.getMessage());
        }
        //destination.close();
        return destination.getAbsolutePath();
    }

    public Solver(GraphicsContext graphicsContext, Point centerPoint, float scale) {
        this.gc = graphicsContext;
        this.centerPoint = centerPoint;
        this.scale = scale;
        layoutPredictor = new LayoutPredictor();
        layoutPuzzles = new PriorityQueue<>();
        in = new Scanner(System.in);
        cache = new HashSet<>();
        //this.destination =
    }

    public class LayoutPuzzles implements Comparable<LayoutPuzzles> {
        private Layout layout;
        private PuzzleRepository puzzles;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LayoutPuzzles that = (LayoutPuzzles) o;
            //return puzzles.equals(that.puzzles);
            if (layout != null ? !layout.equals(that.layout) : that.layout != null) return false;
            return puzzles != null ? puzzles.equals(that.puzzles) : that.puzzles == null;
        }

        @Override
        public int hashCode() {
            //int result = 0;
            int result = layout != null ? layout.hashCode() : 0;
            result = 31 * result + (puzzles != null ? puzzles.hashCode() : 0);
            return result;
        }

        public Layout getLayout() {
            return layout;
        }

        public void setLayout(Layout layout) {
            this.layout = layout;
        }

        public PuzzleRepository getPuzzles() {
            return puzzles;
        }

        public void setPuzzles(PuzzleRepository puzzles) {
            this.puzzles = puzzles;
        }

        public LayoutPuzzles(Layout layout, PuzzleRepository puzzles) {
            this.layout = layout;
            this.puzzles = puzzles;
        }

        @Override
        public int compareTo(LayoutPuzzles o) {
            //return getLayout().getIteration() - o.getLayout().getIteration();

            //*************************************************

            // return (int)((Math.random() - 1.1f) * 2.2f);

            //*************************************************
            if (o.getPuzzles().size() == puzzles.size()) {
                return layout.getPointsCount() - o.getLayout().getPointsCount();
            } else {
                return puzzles.size() - o.getPuzzles().size();
            }
            //*************************************************

            //           return o.getLayout().getPointsCount()- layout.getPointsCount();
        }


    }


    public void add(LayoutPuzzles lp) {
        layoutPuzzles.add(lp);

    }

    void solve() throws InterruptedException {
        //writableImage = new WritableImage(50,50);
        canvas = new Canvas(50, 50);
        localGc = canvas.getGraphicsContext2D();

        start = Instant.now();
        while (!layoutPuzzles.isEmpty()) {
            LayoutPuzzles lp = layoutPuzzles.poll();
            createNewLayouts(lp.getLayout(), lp.getPuzzles());
        }
    }

    void drawLeftPuzzles(GraphicsContext gc, PuzzleRepository availablePuzzles, Point centerPoint, float scale) {
        for (int i = 0; i < availablePuzzles.size(); i++) {
            availablePuzzles.get(i).drawFilled(gc, centerPoint.move(0, 70), scale);
        }
    }

    void createNewLayouts(Layout layout, PuzzleRepository availablePuzzles) throws InterruptedException {
        // take all
        String s;
        PuzzleOnBoard puzzleOnBoard = new PuzzleOnBoard();
        Context context = Context.getInstance();
        Layout newLayout;
        PuzzleRepository newAvailablePuzzles;
        PuzzleOnBoardRepository puzzleOnBoardRepository = PuzzleOnBoardRepository.getInstance();
        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_BYTE_GRAY);
        //int predict=0;
        for (int i = 0; i < layout.getPointsCount(); i++) {
            if (layout.getPoint(i).isTag()) {
                for (int j = 0; j < availablePuzzles.size(); j++) {
                    for (int f = 0; f < (availablePuzzles.get(j).isTwoSided() ? 2 : 1); f++) {
                        for (int k = 0; k < availablePuzzles.get(j).getSidesIterable(); k++) {

                            float envelopeAngleToSegment0 = layout.getAngleToSegment(i, i + 1, context.getSegment0());

                            float angle = 0;
                            if (f == 0)
                                angle = -layout.getAngleToSegment(i, i + 1, new Segment(availablePuzzles.get(j).getPoint(k), availablePuzzles.get(j).getPoint(k + 1)));
                            else
                                angle = -layout.getAngleToSegment(i, i + 1, new Segment(availablePuzzles.get(j).getMirrorRefl().getPoint(k), availablePuzzles.get(j).getMirrorRefl().getPoint(k + 1)));

                            if (puzzleOnBoardRepository.contains(availablePuzzles.get(j).getId(),
                                    angle,
                                    layout.getPoint(i),
                                    k, f == 1)) {
                                puzzleOnBoard = puzzleOnBoardRepository.getOrConstruct(availablePuzzles.get(j).getId(),
                                        angle,
                                        layout.getPoint(i),
                                        k, f == 1);
                            } else {
                                puzzleOnBoard = puzzleOnBoard.takePuzzle(availablePuzzles.get(j))
                                        .flip(f == 1)
                                        .rotate(angle)
                                        .aroundPoint(k)
                                        .move(layout.getPoint(i))
                                        .placeOnBoard();
                            }
                            if (layout.doesPuzzleFit(puzzleOnBoard)) {


                                finish = Instant.now();

                                long timeElapsed = Duration.between(start, finish).toMillis();
                                iterations++;
                                //gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

                                newLayout = new Layout(layout, iterations);
                                newLayout.modifyLayout(puzzleOnBoard, i, k);
                                newAvailablePuzzles = new PuzzleRepository(availablePuzzles);
                                newAvailablePuzzles.delete(availablePuzzles.get(j));
//                                localGc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
//                                newLayout.drawSimplified(localGc, new Point(0, 0), 0.05f);


                                LayoutPuzzles lp = new LayoutPuzzles(newLayout, newAvailablePuzzles);
                                if (!cache.contains(lp)) {
                                    cache.add(lp);
                                    cacheMiss++;
                                    Graphics2D graphics2D = image.createGraphics();
                                    graphics2D.setBackground(Color.WHITE);
                                    newLayout.drawSimplified(graphics2D, new Point(0, 0), 0.05f);
                                    predict = layoutPredictor.predict(image);

                                    if (predict == 0) {
                                        add(lp);
                                    }

                                } else {
                                    cacheHit++;
                                }
                                if (iterations % 100 == 0) {
                                    // if (iterations  >= 513) {
                                    System.out.println("iteration " + iterations
                                            + " queueSize " + layoutPuzzles.size()
                                            + " duration " + timeElapsed
                                            + " puzzles left " + newAvailablePuzzles.size()
                                            + " parentIteration " + layout.getParentIteration()
                                            + " predict " + predict
                                            + " cacheSize " + cache.size()
                                            + " cacheHitRation " + Float.toString((float) cacheHit / ((float) cacheMiss + (float) cacheHit))
                                    );


//                                     gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
//
//                                     layout.draw(gc, centerPoint, scale);
//                                     layout.drawSimplified(gc, new Point(850, 100), 0.1f);
//                                     s = in.nextLine();
                                    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

                                    newLayout.draw(gc, centerPoint, scale);
                                    newLayout.drawSimplified(gc, new Point(850, 100), 0.1f);
                                    drawLeftPuzzles(gc, newAvailablePuzzles, new Point(850, 200), 0.1f);
//                                    s = in.nextLine();
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            saveToFile(gc.getCanvas(), String.valueOf(iterations));
                                        }
                                    });
                                    File outputfile = new File("/tmp/" + String.valueOf(predict) +
                                            "_buff_" + String.valueOf(iterations) + "_.png");
                                    try {
                                        ImageIO.write(image, "png", outputfile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    //;SwingFXUtils.fromFXImage(writableImage, null);


                                    Thread.sleep(100);
                                }
                                if (newAvailablePuzzles.size() <= 1) {

                                    System.out.println("iteration " + iterations +
                                            " puzzles left " + newAvailablePuzzles.size());
                                    //                            layout.draw(gc, centerPoint, scale);
                                    //                            puzzleOnBoard.draw(gc, centerPoint, scale);
                                    //                            Thread.sleep(4000);
                                    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                                    newLayout.draw(gc, centerPoint, scale);
                                    newLayout.drawSimplified(gc, new Point(850, 100), 0.1f);
                                    drawLeftPuzzles(gc, newAvailablePuzzles, new Point(850, 200), 0.1f);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            saveToFile(gc.getCanvas(), String.valueOf(iterations));
                                        }
                                    });


                                    Thread.sleep(100);
                                }
                            } else {
                            }
                            //float x = layout.getPoint(i).getX() -
                            //puzzleOnBoardRepository.get()
                        }
                    }
                }
            }
        }

    }


}
