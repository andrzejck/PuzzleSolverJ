package mensa;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class PuzzleOnBoard extends Puzzle {
    private float angle;
    private Point vector;
    private int centerPointId=-1;
    private boolean fliped = false;

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
        this.vector = new Point(vector);
        return this;
    }

    public PuzzleOnBoard aroundPoint(int centerPointId){
        this.centerPointId = centerPointId;
        return this;
    }

    public PuzzleOnBoard flip(boolean f){
        this.fliped=f;
        return this;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public static String generateCoordinates(String puzzleId, float angle, Point vector, int centerPointId, boolean fliped) {
        StringBuilder tmpCoordinates=new StringBuilder();
        tmpCoordinates.append(puzzleId)
                .append((int)(angle*60))
                .append(vector.toString())
                .append(centerPointId)
                .append(fliped);
         return tmpCoordinates.toString();
    }

    public PuzzleOnBoard placeOnBoard(){
        if(puzzle == null)
            throw new IllegalStateException("puzzle is null");
        PuzzleOnBoard  puzzleOnBoard  = new PuzzleOnBoard(puzzle);
        puzzleOnBoard.puzzleId = puzzleId;
        puzzleOnBoard.angle = angle;
        puzzleOnBoard.vector = new Point(vector);
        puzzleOnBoard.centerPointId = centerPointId;
        puzzleOnBoard.fliped = fliped;
        puzzleOnBoard.puzzle = puzzle;
        puzzleOnBoard.coordinates = generateCoordinates(puzzleId, angle, vector, centerPointId, fliped);
        if(fliped)
            puzzleOnBoard.flipVertical();
        puzzleOnBoard.rotate(angle, centerPointId);
        puzzleOnBoard.translate(vector.move(-puzzleOnBoard.getPoint(centerPointId).getX(),
                                            -puzzleOnBoard.getPoint(centerPointId).getY()));
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
        if (fliped != that.fliped) return false;
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
        result = 31 * result + (fliped?1:0);
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
        gc.strokeText(getId()+(fliped?" f ":""),
                cg.getX()/getPointsCount()*scale+centerPoint.getX(),
                cg.getY()/getPointsCount()*scale+centerPoint.getY());
    }

//    public void drawFilled(GraphicsContext gc, Point centerPoint, float scale) {
//        super.draw(gc, centerPoint, scale);
//        Point cg = new Point(0,0);
//        for(int i=0; i < getPointsCount(); i++){
//            cg.move(getPoint(i));
//        }
//        gc.strokeText(getId()+(fliped?" f ":""),
//                cg.getX()/getPointsCount()*scale+centerPoint.getX(),
//                cg.getY()/getPointsCount()*scale+centerPoint.getY());
//    }
}
