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

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Mensa Puzzle");
        Group root = new Group();
        Canvas canvas = new Canvas(1040, 1040);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //drawMatching(gc);
        //testPolygon.draw(gc, new Point(100,100), 1f);
        for(Polygon poly: testPolygons){
            poly.draw(gc,new Point(100,100), 0.5f);
        }
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




    private static void generateAll(){
        puzzle = new ArrayList<>();
        Puzzle t = new Puzzle("0");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(0,363));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("1");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(0,363));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("2");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(0,290));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("3");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(0,290));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("4");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(435,0));
        t.addPoint(new Point(150,292));
        t.addPoint(new Point(0,292));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("5");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(50,404));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("6");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(555,0));
        t.addPoint(new Point(160,160));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("7");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(555,0));
        t.addPoint(new Point(160,160));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("8");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(200,0));
        t.addPoint(new Point(0,200));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("9");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(170,0));
        t.addPoint(new Point(210,360));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("10");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(405,0));
        t.addPoint(new Point(100,300));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("11");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(180,0));
        t.addPoint(new Point(180,180));
        t.addPoint(new Point(0,180));
        t.setSidesIterable(1);
        t.calculate();
        puzzle.add(t);


        t = new Puzzle("12");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(440,0));
        t.addPoint(new Point(340,270));
        t.addPoint(new Point(210,220));
        t.addPoint(new Point(155,350));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("13");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(365,0));
        t.addPoint(new Point(365,180));
        t.addPoint(new Point(180,180));
        t.addPoint(new Point(180,365));
        t.addPoint(new Point(0,365));
        t.setSidesIterable(4);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("14");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(570,0));
        t.addPoint(new Point(430,160));
        t.addPoint(new Point(220,160));
        t.addPoint(new Point(220,360));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("15");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(440,0));
        t.addPoint(new Point(360,160));
        t.addPoint(new Point(230,220));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("16");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(144,0));
        t.addPoint(new Point(0,144));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("17");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(290,150));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(2);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("18");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(145,0));
        t.addPoint(new Point(145,145));
        t.addPoint(new Point(0,145));
        t.setSidesIterable(1);
        t.calculate();
        puzzle.add(t);

        //generatePuzzleRot();

    }
    private static void generateSimplified1(){
        //13+11
        //12+16
        //14+8

        puzzle = new ArrayList<>();
        Puzzle t = new Puzzle("0");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(0,363));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("1");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(0,363));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("2");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(0,290));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("3");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(0,290));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("4");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(435,0));
        t.addPoint(new Point(150,292));
        t.addPoint(new Point(0,292));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("5");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(50,404));
        t.setSidesIterable(3);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("6");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(555,0));
        t.addPoint(new Point(160,160));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("7");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(555,0));
        t.addPoint(new Point(160,160));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);


        t = new Puzzle("9");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(170,0));
        t.addPoint(new Point(210,360));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("10");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(405,0));
        t.addPoint(new Point(100,300));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);




        t = new Puzzle("12+16");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(440,0));
        t.addPoint(new Point(340,270));

        t.addPoint(new Point(155,350));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        System.out.println(t);
        puzzle.add(t);

        t = new Puzzle("13+11");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(365,0));
        t.addPoint(new Point(365,365));
        t.addPoint(new Point(0,365));
        t.setSidesIterable(1);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("14+8");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(570,0));
        t.addPoint(new Point(220,360));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("15");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(440,0));
        t.addPoint(new Point(360,160));
        t.addPoint(new Point(230,220));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        puzzle.add(t);


        t = new Puzzle("17");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(290,150));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(2);
        t.calculate();
        puzzle.add(t);

        t = new Puzzle("18");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(145,0));
        t.addPoint(new Point(145,145));
        t.addPoint(new Point(0,145));
        t.setSidesIterable(1);
        t.calculate();
        puzzle.add(t);
    }
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
        generateSimplified1();;

        PuzzleOnBoard puzzleOnBoard = new PuzzleOnBoard();
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
        testPolygons.add(puzzleOnBoard1);
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
        testPolygons = new ArrayList<>();
        polygonTest();
        //System.exit(0);
        //return;
        //generateAll();

        //System.out.println(Math.abs(-5%3));
       // testIntersections();
       // sectionTests();
//        generator = new Random();
//
//        generateSimplified1();
//        //generatePuzzleRot();
//
//
//
//        ArrayList<PuzzleRot> prDeg90 = new ArrayList<>();
//
//
////        for(PuzzleRot pr: puzzleRot){
////            if ((pr.getLeftAngle() < DEG90 + Layout.ANG_DELTA)){
////                prDeg90.add(pr);
////            }
////        }
// //       PuzzleRot first  = prDeg90.get(generator.nextInt(prDeg90.size()));
//
//        ArrayList<Puzzle> available = new ArrayList<>(puzzle);
//        //available.remove(first.getPuzzle());
//        puzzleLayout = new ArrayList<>();
//        puzzleLayout.add(new PuzzleOnBoard(available.get(2),0.0f, new Point(0.0f,0.0f),0, false));
//        puzzleLayout.add(new PuzzleOnBoard(available.get(1),0.0f, new Point(0.0f,290f),2, false));
//        puzzleLayout.add(new PuzzleOnBoard(available.get(0),DEG90/2, new Point(0.0f,290f),0, false));
//
//        boardNodeRoot = new BoardNode(new Point(0.0f,0.0f));
//        boardNodeRoot.addNext(new BoardNode(new Point(1040, 0.0f)));
//        boardNodeRoot.getNext().addNext(new BoardNode(new Point(1040, 1040)));
//        boardNodeRoot.getNext().getNext().addNext(new BoardNode(new Point(0, 1040)));
//        boardNodeRoot.getNext().getNext().getNext().addNext(boardNodeRoot);
//
//        //matching = findAllMatching(DEG180 - first.getLeftAngle(), 1040- first.getSideLength(),0, available,first, first);
//        //matching.push(first);
//
//        System.out.println(matching);



        launch(args);

    }
}
