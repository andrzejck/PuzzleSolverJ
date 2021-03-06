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
    static long iterations=0;
    final static float DEG180= 3.1415f;
    final static float DEG90=3.1415f/2;
    final static Puzzle t=null;
    //static PuzzleNode root;
    private static Random generator;
    private static ArrayList<PuzzleOnBoard> puzzleLayout;

    private static BoardNode boardNodeRoot;

    private static Polygon testPolygon;
    private static PuzzleRepository puzzleRepository;
    private static PuzzleOnBoardRepository puzzleOnBoardRepository;

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Mensa Puzzle");
        Group root = new Group();
        Canvas canvas = new Canvas(1040, 1040);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        solver = new Solver(gc, new Point(100,100), 0.5f);
        PuzzleRepository pr = new PuzzleRepository();
        pr.generateSimplified1();
        Layout layout = new Layout();
        layout.addPoint(new Point(0,0));
        layout.addPoint(new Point(1040,0));
        layout.addPoint(new Point(1040,1040));
        layout.addPoint(new Point(0,1040));

        solver.add(solver.new LayoutPuzzles(layout, pr));
        solverThread = new SolverThread(solver);

        new Thread(solverThread).start();
        //drawMatching(gc);
        //testPolygon.draw(gc, new Point(100,100), 1f);
        /*for(Polygon poly: testPolygons){
            poly.draw(gc,new Point(100,100), 0.5f);
        }*/
//        drawBoard(gc);
///*        Puzzle p = puzzle.get(0);
//        p.setPosition(0,0,2,false, 0, 0.0);
        //p.draw(gc);*/

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /*public static void findAllMatching(){

    }*/

    /*public static  LinkedList<PuzzleRot>  findAllMatching(float convAngle,
                                                                      float length,
                                                                      int boxSide,
                                                                      LinkedList<PuzzleRot> leading,
                                                                      ArrayList<Puzzle> available)
    {
         iterations++;
         if(iterations%10 == 0){
             System.out.println("iterations "+iterations);
             System.out.println("availableSize "+available.size());
         }
        LinkedList<PuzzleRot> ret = new LinkedList<>();
        if((length) <= 30.0){
            if (boxSide == 3) {
                return ret;
            }

    }*/

    public static void drawBoard(GraphicsContext gc){
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(4);
        BoardNode boardNode = boardNodeRoot;
        while(boardNode.getNext() != boardNodeRoot){
            gc.strokeLine(boardNode.getPoint().getX(),
                          boardNode.getPoint().getY(),
                          boardNode.getNext().getPoint().getX(),
                          boardNode.getNext().getPoint().getY());
            boardNode=boardNode.getNext();
            System.out.println("BoardNode angle "+boardNode.getAngle());
        }
        gc.strokeLine(boardNode.getPoint().getX(),
                boardNode.getPoint().getY(),
                boardNodeRoot.getPoint().getX(),
                boardNodeRoot.getPoint().getY());

    }




  /*  public static void generatePuzzleRot(){
        puzzleRot = new ArrayList<>();
        PuzzleRot pR;
        for(Puzzle p: puzzle){
            for(int i=0; i<p.getSidesIterable(); i++){
                pR = new PuzzleRot(p,i,false);
                puzzleRot.add(pR);
                if(p.isTwoSided()){
                    pR = new PuzzleRot(p,i,true);
                    puzzleRot.add(pR);
                }
            }
        }
        //Collections.sort(puzzleRot);
    }*/






    //private stat
    private static void sectionTests(){
        Segment a=new Segment(0.0f, 0.0f, 0.0f, 1.0f);
        Segment b=new Segment(1.0f, 0.0f, 1.0f, -1.0f);

        Segment c=new Segment(0.0f, 0.0f, 1.0f, 1.0f);
        Segment d=new Segment(0.0f, 1.0f, 1.0f, 0.0f);

        Segment e=new Segment(0.0f, 0.0f, 0.0f, 1.0f);

        Segment f=new Segment(0.0f, 0.0f, -1.0f, 1.0f);


        System.out.println(a.inters(b));
        System.out.println(b.inters(a));
        System.out.println(c.inters(d));
        System.out.println(c.inters(a));

        System.out.println(a.isParallel(b, 0.001f));
        System.out.println(a.angleBetween(b));
        System.exit(0);

    }

    private static void polygonTest(){
        //generateSimplified1();;

/*        PuzzleOnBoard puzzleOnBoard = new PuzzleOnBoard();
        puzzleOnBoard = puzzleOnBoard.takePuzzle(puzzle.get(10))
                                      .rotate((float)Math.PI/5)
                                      .aroundPoint(0)
                                      .move(new Point(500, 100))
                                      .placeOnBoard();

        PuzzleOnBoard puzzleOnBoard1 = new PuzzleOnBoard();
        puzzleOnBoard1 = puzzleOnBoard1.takePuzzle(puzzle.get(10))
                .rotate(-(float)Math.PI/5)
                .aroundPoint(0)
                .move(new Point(800, 800))
                .placeOnBoard();
        testPolygons.add(puzzleOnBoard);
        testPolygons.add(puzzleOnBoard1);*/
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
