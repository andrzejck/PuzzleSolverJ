package mensa;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Solver {
    int iterations=0;
    private GraphicsContext gc;
    private Point centerPoint;
    float scale;
    private PriorityQueue<LayoutPuzzles> layoutPuzzles;

    public Solver(GraphicsContext graphicsContext, Point centerPoint, float scale) {
        this.gc = graphicsContext;
        this.centerPoint = centerPoint;
        this.scale = scale;
        layoutPuzzles = new PriorityQueue<>();
    }

    public class LayoutPuzzles implements Comparable<LayoutPuzzles>{
        private Layout layout;
        private PuzzleRepository puzzles;

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
        public int compareTo(LayoutPuzzles o){
            return layout.getPointsCount() - o.getLayout().getPointsCount();
            //return o.getLayout().getPointsCount()- layout.getPointsCount();
        }


    }


    public void add(LayoutPuzzles lp){
        layoutPuzzles.add(lp);

    }
    void solve() throws InterruptedException {

        while(! layoutPuzzles.isEmpty()){
            LayoutPuzzles lp = layoutPuzzles.poll();
            createNewLayouts(lp.getLayout(), lp.getPuzzles());
        }
    }
    void createNewLayouts(Layout layout, PuzzleRepository availablePuzzles) throws InterruptedException {
        // take all
        PuzzleOnBoard puzzleOnBoard = new PuzzleOnBoard();
        Context context = Context.getInstance();
        Layout newLayout;
        PuzzleRepository newAvailablePuzzles;
        PuzzleOnBoardRepository puzzleOnBoardRepository = PuzzleOnBoardRepository.getInstance();
        for(int i = 0; i < layout.getPointsCount(); i++){
            for(int j = 0; j <  availablePuzzles.size(); j++){
                for(int f=0; f <  (availablePuzzles.get(j).isTwoSided()?2:1); f++ ) {
                    for (int k = 0; k < availablePuzzles.get(j).getSidesIterable(); k++) {
                        float sideAngleToSegment0 = availablePuzzles.get(j).getAngleToSegment(k, k + 1,
                                context.getSegment0());
                        float envelopeAngleToSegment0 = layout.getAngleToSegment(i, i + 1, context.getSegment0());
                        float angle = envelopeAngleToSegment0 - sideAngleToSegment0;
                        if (puzzleOnBoardRepository.contains(availablePuzzles.get(j).getId(),
                                angle,
                                layout.getPoint(i),
                                k, f==1)) {
                            puzzleOnBoard = puzzleOnBoardRepository.getOrConstruct(availablePuzzles.get(j).getId(),
                                    angle,
                                    layout.getPoint(i),
                                    k, f==1);
                        } else {
                            puzzleOnBoard = puzzleOnBoard.takePuzzle(availablePuzzles.get(j))
                                    .flip(f==1)
                                    .rotate(angle)
                                    .aroundPoint(k)
                                    .move(layout.getPoint(i))
                                    .placeOnBoard();
                        }
                        if (layout.doesPuzzleFit(puzzleOnBoard)) {
                            iterations++;
                            //gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                            newLayout = new Layout(layout, iterations);
                            newLayout.modifyLayout(puzzleOnBoard, i, k);
                            newAvailablePuzzles = new PuzzleRepository(availablePuzzles);
                            newAvailablePuzzles.delete(availablePuzzles.get(j));
                            add(new LayoutPuzzles(newLayout, newAvailablePuzzles));
                            if (iterations % 20000 == 0) {
                                System.out.println("iteration " + iterations+
                                                    " queueSize " + layoutPuzzles.size());
                                //                            layout.draw(gc, centerPoint, scale);
                                //                            puzzleOnBoard.draw(gc, centerPoint, scale);
                                //                            Thread.sleep(4000);
                                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                                newLayout.draw(gc, centerPoint, scale);
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
