package mensa;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class PuzzleOnBoard extends Puzzle {
    private float angle;
    private Point vector;
    private int centerPointId=-1;
    private Puzzle puzzle;

    private void reset(){

    }
    public PuzzleOnBoard takePuzzle(Puzzle puzzle){
        this.puzzle = puzzle;
        return this;
    }
    public PuzzleOnBoard rotate(float angle){
        this.angle = angle;
        return this;
    }

    public PuzzleOnBoard move(Point vector){
        this.vector = vector;
        return this;
    }

    public PuzzleOnBoard aroundPoint(int centerPointId){
        this.centerPointId = centerPointId;
        return this;
    }

    public PuzzleOnBoard placeOnBoard(){
        if(puzzle == null)
            throw new IllegalStateException("puzzle is null");
        PuzzleOnBoard  puzzleOnBoard  = new PuzzleOnBoard(puzzle);
        puzzleOnBoard.angle = angle;
        puzzleOnBoard.vector = vector;
        puzzleOnBoard.centerPointId = centerPointId;
        puzzleOnBoard.puzzle = puzzle;

        puzzleOnBoard.rotate(angle, centerPointId);
        puzzleOnBoard.translate(vector);
        return puzzleOnBoard;

    }

    public PuzzleOnBoard(){

    }

    public PuzzleOnBoard(Puzzle p){
        super(p);
    }

    @Override
    public void draw(GraphicsContext gc, Point centerPoint, float scale) {
        super.draw(gc, centerPoint, scale);
        Point cg = new Point(0,0);
        for(int i=0; i < getPointsCount(); i++){
            cg.move(getPoint(i));
        }
        gc.strokeText(getId(),
                cg.getX()/getPointsCount()*scale+centerPoint.getX(),
                cg.getY()/getPointsCount()*scale+centerPoint.getY());
    }
}
