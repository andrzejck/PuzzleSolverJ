package mensa;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;


public class Main extends Application {
    static ArrayList<Puzzle> puzzle;
    static SolverThread solverThread;
    static Solver solver;
    //static ArrayList<PuzzleRot> puzzleRot;
    static ArrayList<Polygon> testPolygons;
    static long iterations = 0;
    final static float DEG180 = 3.1415f;
    final static float DEG90 = 3.1415f / 2;
    final static Puzzle t = null;
    //static PuzzleNode root;
    private static Random generator;
    private static ArrayList<PuzzleOnBoard> puzzleLayout;

    private static BoardNode boardNodeRoot;

    private static Polygon testPolygon;
    private static PuzzleRepository puzzleRepository;
    private static PuzzleOnBoardRepository puzzleOnBoardRepository;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Mensa Puzzle");
        Group root = new Group();
        Canvas canvas = new Canvas(1040, 1040);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        solver = new Solver(gc, new Point(100, 100), 0.5f);
        PuzzleRepository pr = new PuzzleRepository();
        pr.generateSimplified1();
        Layout layout = new Layout();
        layout.addPoint(new Point(0, 0));
        layout.addPoint(new Point(1040, 0));
        layout.addPoint(new Point(1040, 1040));
        layout.addPoint(new Point(0, 1040));
        layout.getPoint(0).setTag(true);
//        float angle=0;
//        PuzzleOnBoard puzzleOnBoard =  new PuzzleOnBoard();
//        for(int i=0; i < 36; i++){
//
//            puzzleOnBoard = puzzleOnBoard.takePuzzle(pr.getById("6|7"))
//                    .rotate(-angle)
//                    .aroundPoint(0)
//                    .move(layout.getPoint(i))
//                    .placeOnBoard();
//            puzzleOnBoard.draw(gc, new Point(100+i*10, 100+i*10), 0.5f);
//            System.out.println("Rotate Angle "+String.valueOf(-angle));
//            System.out.println( " Angle between Segment0 and puzzle segment 0-1 "+Context.getInstance().getSegment0().angleBetween( new Segment(puzzleOnBoard.getPoint(0), puzzleOnBoard.getPoint(1)) ));
//            System.out.println( " Angle between Segment0 and puzzle segment 0-1 "+Context.getInstance().getSegment0().angleBetween( new Segment(puzzleOnBoard.getPoint(0), puzzleOnBoard.getPoint(1)) ));
//            System.out.println( " Angle between puzzle segment 0-1 and Segment0 "+ new Segment(puzzleOnBoard.getPoint(0), puzzleOnBoard.getPoint(1)).angleBetween(Context.getInstance().getSegment0()));
//            angle = angle + 0.174f;
//        }

        solver.add(solver.new LayoutPuzzles(layout, pr));
        solverThread = new SolverThread(solver);

        new Thread(solverThread).start();


        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void drawBoard(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(4);
        BoardNode boardNode = boardNodeRoot;
        while (boardNode.getNext() != boardNodeRoot) {
            gc.strokeLine(boardNode.getPoint().getX(),
                    boardNode.getPoint().getY(),
                    boardNode.getNext().getPoint().getX(),
                    boardNode.getNext().getPoint().getY());
            boardNode = boardNode.getNext();
            System.out.println("BoardNode angle " + boardNode.getAngle());
        }
        gc.strokeLine(boardNode.getPoint().getX(),
                boardNode.getPoint().getY(),
                boardNodeRoot.getPoint().getX(),
                boardNodeRoot.getPoint().getY());

    }


    //private stat
    private static void sectionTests() {
        Segment a = new Segment(0.0f, 0.0f, 0.0f, 1.0f);
        Segment b = new Segment(1.0f, 0.0f, 1.0f, -1.0f);

        Segment c = new Segment(0.0f, 0.0f, 1.0f, 1.0f);
        Segment d = new Segment(0.0f, 1.0f, 1.0f, 0.0f);

        Segment e = new Segment(0.0f, 0.0f, 0.0f, 1.0f);

        Segment f = new Segment(0.0f, 0.0f, -1.0f, 1.0f);


        System.out.println(a.inters(b));
        System.out.println(b.inters(a));
        System.out.println(c.inters(d));
        System.out.println(c.inters(a));

        System.out.println(a.isParallel(b, 0.001f));
        System.out.println(a.angleBetween(b));
        System.exit(0);

    }

    private static void polygonTest() {
        //generateSimplified1();;


//        testPolygon.calculate();System.out.println(testPolygon.toString());
//        testPolygon.addPoint(1, new Point (120,30));
//        testPolygon.calculate();
//        testPolygon.setColor(Color.BLUE);
//        testPolygons.add(testPolygon);
//        //testPolygon.removePoint(0);
//        //testPolygon.calculate();
//        System.out.println(testPolygon.toString());
//        Polygon p2 = testPolygon.expand(20);
//
//        p2.setColor(Color.BLACK);
//        testPolygons.add(p2);
////
//        Polygon p1 = new Polygon(testPolygon);
////        p1.flipVertical();
////        p1.move(new Point(350,350));
////        p1.rotateOneForward();
////        testPolygons.add(p2);
////        System.out.println(p1.toString());
////        testPolygons.add(p1);
//        p1.flipVertical();
//        p1.flipVertical();
//        System.out.println(testPolygon.equals(p1)); // why ???
////        System.out.println(testPolygon.equals(p2));
//
//        Polygon envelope = new Polygon();
//        envelope.addPoint(new Point(0,0));
//        envelope.addPoint(new Point(500,0));
//        envelope.addPoint(new Point(500,500));
//        envelope.addPoint(new Point(0,500));
//        envelope.calculate();
//        Polygon puzzle1 = new Polygon();
//        puzzle1.addPoint(new Point(0,0));
//        puzzle1.addPoint(new Point(200,0));
//        puzzle1.addPoint(new Point(0,200));
//        puzzle1.calculate();
//        puzzle1.flipVertical();
//        puzzle1.translate(new Point(500,000));
//
//        Polygon envelope1 = envelope.mergeIn(puzzle1, 1, 2);
//        envelope1.setColor(Color.GREEN);
////        testPolygons.add(envelope1);
//        System.out.println(envelope.doesPolygonFitIn(puzzle1));
//
//        puzzle1.translate(new Point(0,0));
//        System.out.println(envelope.doesPolygonFitIn(puzzle1));
//        //System.out.println(envelope.expand(10).doesPolygonFitIn(puzzle1));
//        testPolygons.add(envelope.expand(0));
//        testPolygons.add(puzzle1);
////


    }

    //public  modify

    public static void main(String[] args) {

        launch(args);

    }
}
