package mensa;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Layout {
    public static float ANG_DELTA=5.0f/360.0f*2*3.1415f;
    public static float DIM_DELTA =5;
    public static float ENLARGED_BY =10;

    private Polygon envelope;
    private Polygon envelopeEnlarged;
    private ArrayList<PuzzleOnBoard> puzzlesOnBoard;
    private int iteration=0;
    private int parentIteration=-1;

    public int getIteration() {
        return iteration;
    }

    public int getParentIteration() {
        return parentIteration;
    }


    //a fit in b > a <= b
    public static boolean dimFitIn(float a, float b){
        return a-DIM_DELTA <= b;
    }

    public static boolean angFitIn(float a, float b) {
        return a-ANG_DELTA <= b;
    }

    public int compareAng(float a, float b){
        if (Math.abs(a-b) <= ANG_DELTA) return 0;
        if (a < b)
            return -1;
        else
            return 1;
    }

    public int compareDim(float a, float b){
        if (Math.abs(a-b) <= DIM_DELTA) return 0;
        if (a < b)
            return -1;
        else
            return 1;
    }

    public Layout(){
        envelope = new Polygon();
        envelopeEnlarged = new Polygon();
        puzzlesOnBoard =  new ArrayList<>();
    }

    public Layout(Layout l, int iteration){
        envelope = new Polygon(l.envelope);
        envelopeEnlarged = new Polygon(l.envelopeEnlarged);
        puzzlesOnBoard =  new ArrayList<>(l.puzzlesOnBoard);
        this.iteration=iteration;
        this.parentIteration=l.iteration;
    }

    public void modifyLayout(PuzzleOnBoard puzzle, int layoutPointId, int puzzlePointId  ){
        envelope  = envelope.mergeIn(puzzle, layoutPointId, puzzlePointId);
        envelopeEnlarged = envelope.expand(ENLARGED_BY);
        puzzlesOnBoard.add(puzzle);
    }

    public boolean doesPuzzleFit(PuzzleOnBoard puzzle){
        return envelopeEnlarged.doesPolygonFitIn(puzzle);
    }

    Point getPoint(int i){
        return envelope.getPoint(i);
    }

    void addPoint(Point p){
        envelope.addPoint(p);
        if(envelope.getPointsCount() >= 3) {
            envelopeEnlarged = envelope.expand(ENLARGED_BY);
            envelope.calculate();
        }
    }

    int getPointsCount(){
        return envelope.getPointsCount();
    }

    Point getPointEnlarged(int i){
        return envelopeEnlarged.getPoint(i);
    }

    public float getAngleToSegment(int pointId, int nextPointId, Segment segment) {
        return segment.angleBetween(new Segment(getPoint(pointId), getPoint(nextPointId)));
    }


    public void draw(GraphicsContext gc, Point centerPoint, float scale){
        envelope.setColor(Color.BLUE);
        envelope.draw(gc,centerPoint,scale);
        envelopeEnlarged.setColor(Color.LIGHTGRAY);
        envelopeEnlarged.draw(gc,centerPoint,scale);
        for(PuzzleOnBoard p: puzzlesOnBoard){
            p.setColor(Color.RED);
            p.draw(gc,centerPoint,scale);
        }
        gc.strokeText(" parIt "+getParentIteration()+" it "+getIteration(),600,600);
    }
//    public Layout(ArrayList<Puzzle> puzzleAvailable, BoardNode boardNodeRoot, ArrayList<PuzzleOnBoard> puzzles){
//        this.puzzleAvailable = new ArrayList<>(puzzleAvailable);
//        this.boardNodeRoot = new BoardNode(boardNodeRoot);
//        this.puzzles = new ArrayList<>(puzzles);
//        BoardNode bn=boardNodeRoot.getNext();
//        BoardNode tbn=boardNodeRoot;
//        while((bn !=  boardNodeRoot) && (bn != null)){
//            tbn.addNext(new BoardNode(bn));
//            tbn=tbn.getNext();
//            bn = bn.getNext();
//        }
//        tbn.addNext(this.boardNodeRoot);
//    }


}
