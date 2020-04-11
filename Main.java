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
    static ArrayList<PuzzleRot> puzzleRot;
    static HashMap<Integer, ArrayList<PuzzleRot>> puzzleRotShorter;
    static HashMap<Integer, ArrayList<PuzzleRot>> puzzleRotSmallerAngle;
    static long iterations=0;
    final static double DEG180=3.1415;
    final static double DEG90=3.1415/2;
    static LinkedList<PuzzleRot> matching;
    final static Puzzle t=null;
    static HashMap<Pair<Integer,Integer>, ArrayList<PuzzleRot>> possiblePuzzleRot;
    static PuzzleNode root;
    private static Random generator;
    private static ArrayList<PuzzleOnBoard> puzzleLayout;

    private static BoardNode boardNodeRoot;

    public void drawMatching(GraphicsContext gc){
        double x=0.0;
        double y=0.0;
        for(PuzzleRot p: matching){
            p.getPuzzle().setPosition(x,y, p.getSide(), p.isFlipped(), p.getBoxSide());
            p.getPuzzle().draw(gc);
            //if(p.getPuzzle().getSide(p.getSide()) > 1010){

            //}else{
            if(p.getBoxSide()==0){
                y=y+p.getPuzzle().getSide(p.getSide())+ PuzzleLayout.DIM_DELTA;
                if(y>1010){
                    if((p.getLeftAngle() > DEG90- PuzzleLayout.ANG_DELTA) &&
                            (p.getLeftAngle() < DEG90+ PuzzleLayout.ANG_DELTA)){
                        x=x+p.getLeftSide()+ PuzzleLayout.DIM_DELTA;
                        y=1040;
                    }
                }
            }
            if(p.getBoxSide()==1){
                x=x+p.getPuzzle().getSide(p.getSide())+ PuzzleLayout.DIM_DELTA;
                if(x>1010){
                    if((p.getLeftAngle() > DEG90- PuzzleLayout.ANG_DELTA) &&
                            (p.getLeftAngle() < DEG90+ PuzzleLayout.ANG_DELTA)){
                        y=y-p.getLeftSide()- PuzzleLayout.DIM_DELTA;
                        x=1040;
                    }
                }

            }
            if(p.getBoxSide()==2){
                y=y-p.getPuzzle().getSide(p.getSide())- PuzzleLayout.DIM_DELTA;

                if(y<30){
                    if((p.getLeftAngle() > DEG90- PuzzleLayout.ANG_DELTA) &&
                            (p.getLeftAngle() < DEG90+ PuzzleLayout.ANG_DELTA)){
                        x=x-p.getLeftSide()- PuzzleLayout.DIM_DELTA;
                        y=0;
                    }
                }

            }
            if(p.getBoxSide()==3){
                x=x-p.getPuzzle().getSide(p.getSide())- PuzzleLayout.DIM_DELTA;
            }



            //}

        }


    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Mensa Puzzle");
        Group root = new Group();
        Canvas canvas = new Canvas(1040, 1040);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //drawMatching(gc);
        for(PuzzleOnBoard pb: puzzleLayout){
            pb.draw(gc);
        }
        drawBoard(gc);
/*        Puzzle p = puzzle.get(0);
        p.setPosition(0,0,2,false, 0, 0.0);
        p.draw(gc);*/

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /*public static void findAllMatching(){

    }*/

    /*public static  LinkedList<PuzzleRot>  findAllMatching(double convAngle,
                                                                      double length,
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

    public static LinkedList<PuzzleRot> findAllMatching(double convRightAngle,
                                                        double length,
                                                        int boxSide,
                                                        ArrayList<Puzzle> available,
                                                        PuzzleRot first,
                                                        PuzzleRot prev

                                                                 ){
        iterations++;
        if(iterations%10 == 0){
            System.out.println("iterations "+iterations);
            System.out.println("availableSize "+available.size());
        }
        LinkedList<PuzzleRot> ret = new LinkedList<>();
        if (iterations > 1000000){
            System.out.println("Iterations overflow");
            return ret;
        }

        if((length) <= 30.0){
             if (boxSide == 3) {
                 if (first.getRightAngle() > DEG90  - PuzzleLayout.ANG_DELTA) {
                     System.out.println("iterations "+iterations+" available "+available.size());
                     return ret;
                 }else{
                     if ((DEG90 - first.getRightAngle()) > (prev.getLeftAngle()+ PuzzleLayout.ANG_DELTA)) {
                         System.out.println("iterations "+iterations+" available "+available.size());
                         return ret;
                     }
                     else
                         return null;
                 }

             }
             else{
                 if((prev.getLeftAngle() <= DEG90+ PuzzleLayout.ANG_DELTA) &&
                   ((prev.getLeftAngle() >= DEG90- PuzzleLayout.ANG_DELTA))){
                      return findAllMatching(DEG180-prev.getNextLeftAngel(),
                              (boxSide==2 && first.getRightAngle()>DEG90  - PuzzleLayout.ANG_DELTA)?1040- PuzzleLayout.DIM_DELTA -prev.getLeftSide()-first.getRightSide():1040-prev.getLeftSide(),
                              boxSide+1,
                                     available,
                                     first,
                                      prev);

                 }else{
                     return findAllMatching(DEG90-prev.getLeftAngle(),
                             (boxSide==2 && first.getRightAngle()>DEG90  - PuzzleLayout.ANG_DELTA)?1040-first.getRightSide()- PuzzleLayout.DIM_DELTA :1040,
                             boxSide+1,
                             available,
                             first,
                             prev);


                 }
             }
        }
        if(length <= 140)
            return null;
        if(available.isEmpty())
            return null;
        //PuzzleRot f=first;
        for(Puzzle x: available){
            Puzzle p = available.get(generator.nextInt(available.size()));
            ArrayList<Puzzle> newAvailable = new ArrayList<Puzzle>(available);
            newAvailable.remove(p);
            for(int y=0; y < p.getSidesIterable(); y++){
                //normal
                int s=generator.nextInt(p.getSidesIterable());

                if((p.getSide(s) <= length) && (p.getRightAngel(s) <= (convRightAngle+ PuzzleLayout.ANG_DELTA))){
                    ret=findAllMatching(DEG180-p.getLeftAngel(s),
                                        length-p.getSide(s)- PuzzleLayout.DIM_DELTA,
                                        boxSide,
                                        newAvailable,
                                        first,
                                        new PuzzleRot(p,s,false,boxSide)
                                        );
                    if(ret != null){
                        ret.push(new PuzzleRot(p,s,false,boxSide));
                        return ret;
                    }

                }
                //mirror
                if(p.isTwoSided()){
                    if((p.getSide(s) <= length) && (p.getLeftAngel(s) <= (convRightAngle+ PuzzleLayout.ANG_DELTA))){
                        ret=findAllMatching(DEG180-p.getRightAngel(s),
                                length-p.getSide(s)- PuzzleLayout.DIM_DELTA,
                                boxSide,
                                newAvailable,
                                first,
                                new PuzzleRot(p,s,true,boxSide));
                        if(ret != null){
                            ret.push(new PuzzleRot(p,s,true,boxSide));
                            return ret;
                        }

                    }


                }
            }
        }
        //System.out.println("******************************************");
        return null;
    }


    public static void generatePuzzleRot(){
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
    }
    public static void generatePossible(){
        possiblePuzzleRot=new HashMap<>();
    }


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
        Segment a=new Segment(0.0, 0.0, 0.0, 1.0);
        Segment b=new Segment(1.0, 0.0, 1.0, -1.0);

        Segment c=new Segment(0.0, 0.0, 1.0, 1.0);
        Segment d=new Segment(0.0, 1.0, 1.0, 0.0);

        Segment e=new Segment(0.0, 0.0, 0.0, 1.0);

        Segment f=new Segment(0.0, 0.0, -1.0, 1.0);


        System.out.println(a.inters(b));
        System.out.println(b.inters(a));
        System.out.println(c.inters(d));
        System.out.println(c.inters(a));

        System.out.println(a.isParallel(b, 0.001));
        System.out.println(a.angleBetween(b));
        System.exit(0);

    }

    //public  modify

    public static void main(String[] args) {
        //generateAll();

        //System.out.println(Math.abs(-5%3));
       // testIntersections();
       // sectionTests();
        generator = new Random();

        generateSimplified1();
        generatePuzzleRot();



        ArrayList<PuzzleRot> prDeg90 = new ArrayList<>();


        for(PuzzleRot pr: puzzleRot){
            if ((pr.getLeftAngle() < DEG90 + PuzzleLayout.ANG_DELTA)){
                prDeg90.add(pr);
            }
        }
        PuzzleRot first  = prDeg90.get(generator.nextInt(prDeg90.size()));

        ArrayList<Puzzle> available = new ArrayList<>(puzzle);
        available.remove(first.getPuzzle());
        puzzleLayout = new ArrayList<>();
        puzzleLayout.add(new PuzzleOnBoard(available.get(2),0.0, new Point(0.0,0.0),0, false));
        puzzleLayout.add(new PuzzleOnBoard(available.get(1),0.0, new Point(0.0,290),2, false));
        puzzleLayout.add(new PuzzleOnBoard(available.get(0),DEG90/2, new Point(0.0,290),0, false));

        boardNodeRoot = new BoardNode(new Point(0.0,0.0));
        boardNodeRoot.addNext(new BoardNode(new Point(1040, 0.0)));
        boardNodeRoot.getNext().addNext(new BoardNode(new Point(1040, 1040)));
        boardNodeRoot.getNext().getNext().addNext(new BoardNode(new Point(0, 1040)));
        boardNodeRoot.getNext().getNext().getNext().addNext(boardNodeRoot);

        /*matching = findAllMatching(DEG180 - first.getLeftAngle(), 1040- first.getSideLength(),0, available,first, first);
        matching.push(first);

        System.out.println(matching);*/



        launch(args);

    }
}
