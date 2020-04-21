package mensa;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class PuzzleOnBoard extends Puzzle {
    private float angle;
    private Point vector;
    private int centerPointId=-1;
    private String puzzleId;
    private Puzzle puzzle;
    private String coordinates;

    //private

    private void reset(){

    }
    public PuzzleOnBoard takePuzzle(Puzzle puzzle){
        this.puzzle = puzzle;
        this.puzzleId = puzzle.getId();
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

    public String getCoordinates() {
        return coordinates;
    }

    public static String generateCoordinates(String puzzleId, float angle, Point vector, int centerPointId) {
        StringBuilder tmpCoordinates=new StringBuilder();
        tmpCoordinates.append(puzzleId)
                .append((int)(angle*60))
                .append(vector.toString())
                .append(centerPointId);
         return tmpCoordinates.toString();
    }

    public PuzzleOnBoard placeOnBoard(){
        if(puzzle == null)
            throw new IllegalStateException("puzzle is null");
        PuzzleOnBoard  puzzleOnBoard  = new PuzzleOnBoard(puzzle);
        puzzleOnBoard.puzzleId = puzzleId;
        puzzleOnBoard.angle = angle;
        puzzleOnBoard.vector = vector;
        puzzleOnBoard.centerPointId = centerPointId;
        puzzleOnBoard.puzzle = puzzle;
        puzzleOnBoard.coordinates = generateCoordinates(puzzleId, angle, vector, centerPointId);
        puzzleOnBoard.rotate(angle, centerPointId);
        puzzleOnBoard.translate(vector);
        return puzzleOnBoard;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        //if (!super.equals(o)) return false;

        PuzzleOnBoard that = (PuzzleOnBoard) o;

        if (Float.compare(that.angle, angle) != 0) return false;
        if (centerPointId != that.centerPointId) return false;
        if (vector != null ? !vector.equals(that.vector) : that.vector != null) return false;
        return puzzleId.equals(that.puzzleId);
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (angle != +0.0f ? Float.floatToIntBits(angle) : 0);
        result = 31 * result + (vector != null ? vector.hashCode() : 0);
        result = 31 * result + centerPointId;
        result = 31 * result + puzzleId.hashCode();
        return result;
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
