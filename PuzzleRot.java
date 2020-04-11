package mensa;

import java.util.Objects;

public class PuzzleRot implements Comparable<PuzzleRot> {
    public Puzzle getPuzzle() {
        return puzzle;
    }

    public int getSide() {
        return sideId;
    }

    public double getSideLength(){
        return puzzle.getSide(sideId);
    }

    public boolean isFlipped() {
        return flipped;
    }

    public int getBoxSide() {
        return boxSide;
    }

    private Puzzle puzzle;
    private  int sideId=-1;
    private boolean flipped=false;
    private int boxSide=0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleRot puzzleRot = (PuzzleRot) o;
        return sideId == puzzleRot.sideId &&
                flipped == puzzleRot.flipped &&
                Objects.equals(puzzle, puzzleRot.puzzle);
    }

    @Override
    public int hashCode() {

        return Objects.hash(puzzle, sideId, flipped);
    }

    public PuzzleRot(Puzzle puzzle, int side, boolean flipped){
        this.puzzle = puzzle;
        this.sideId=side;
        this.flipped=flipped;
    }

    public PuzzleRot(Puzzle puzzle, int side, boolean flipped, int boxSide){
        this.puzzle = puzzle;
        this.sideId=side;
        this.flipped=flipped;
        this.boxSide=boxSide;
    }

    @Override
    public String toString() {
        return "PuzzleRot{" +
                "puzzle=" + puzzle.getId() +
                " side=" + sideId +
                " boxSide=" + boxSide +
                " sideLen=" + puzzle.getSide(sideId)+
                " flipped=" + flipped+
                " leftAngel=" + (int)(getLeftAngle()/2*Math.PI*36.0)+
                "}\n";
    }

    public double getLeftAngle(){
        if(! flipped)
            return puzzle.getLeftAngel(sideId);
        else
            return puzzle.getRightAngel(sideId);
    }

    public double getRightAngle(){
        if(! flipped)
            return puzzle.getRightAngel(sideId);
        else
            return puzzle.getLeftAngel(sideId);
    }

    public double getLeftSide(){
        ///
        if( ! flipped ){
            if (sideId > 0)
                return puzzle.getSide(sideId - 1);
            else
                return puzzle.getLastSide();
        }else{
            if (sideId == puzzle.getSidesCount()-1)
                return puzzle.getSide(0);
            else
                return puzzle.getSide(sideId+1);
        }
    }

    public double getRightSide(){
        ///
        if( ! flipped ){
            int rSide=(sideId+1)% puzzle.getSidesCount();
            return puzzle.getSide(rSide);
        }else{
            //int rSide=Math.abs((side-1)% puzzle.getSidesCount());
            //return puzzle.getSide(rSide);
            return puzzle.getSide(Puzzle.getCircularSideId(sideId-1, puzzle.getSidesCount()));

        }
    }

    public double getSideLen(){
        return puzzle.getSide(sideId);
    }

    public String getId() {
        return puzzle.getId();
    }

    public double getNextLeftAngel(){
        //!!!
        if( ! flipped ){
            //int angle=Math.abs((side-2)% puzzle.getSidesCount());
                return puzzle.getAngle(Puzzle.getCircularSideId(sideId-2,puzzle.getSidesCount()));
        }else{
            int angle=(sideId+2)% puzzle.getSidesCount();
            return puzzle.getAngle(angle);

        }

    }

    @Override
    public int compareTo(PuzzleRot o) {

            if (this.puzzle.getSide(this.sideId) < o.puzzle.getSide(o.sideId)){
                return -1;
            }else if (this.puzzle.getSide(this.sideId) > o.puzzle.getSide(o.sideId)){
                return 1;
            }else{
                if (this.getRightAngle() < o.getRightAngle()) {
                    return -1;
                } else if (this.getRightAngle() > o.getRightAngle()) {
                    return 1;
                } else {
                    return 0;
                }
            }


        }


    public void setPosition(double x0, double y0,  double angle){
        puzzle.setPosition(x0,y0,this.sideId, this.flipped, 0, angle);
    }

    /*@Override
    public int compareTo(PuzzleRot o) {
        if ((this.getLeftAngle() < o.getLeftAngle()))
            if ((this.puzzle.getSide(this.side) < o.puzzle.getSide(o.side))) {
                return 1;
            } else {
                return 0;
            }
        else {
            return 0;
        }


    }*/
}
