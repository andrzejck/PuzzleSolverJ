package mensa;

import java.util.ArrayList;

public class PuzzleLayout {
    public static double ANG_DELTA=5.0/360.0*2*3.1415;
    public static double DIM_DELTA =5;
    private ArrayList<PuzzleOnBoard> puzzles;
    private BoardNode boardNodeRoot;
    private ArrayList<Puzzle> puzzleAvailable;
    //a fit in b > a <= b
    public static boolean dimFitIn(double a, double b){
        return a-DIM_DELTA <= b;
    }

    public static boolean angFitIn(double a, double b) {
        return a-ANG_DELTA <= b;
    }

    public int compareAng(double a, double b){
        if (Math.abs(a-b) <= ANG_DELTA) return 0;
        if (a < b)
            return -1;
        else
            return 1;
    }

    public int compareDim(double a, double b){
        if (Math.abs(a-b) <= DIM_DELTA) return 0;
        if (a < b)
            return -1;
        else
            return 1;
    }

    public boolean intersectsOthers(PuzzleRot puzzle){
        return false;
    }

    public boolean outOfBoundaries(PuzzleRot puzzle){
        return false;
    }


    public PuzzleLayout(ArrayList<Puzzle> puzzleAvailable, BoardNode boardNodeRoot, ArrayList<PuzzleOnBoard> puzzles){
        this.puzzleAvailable = new ArrayList<>(puzzleAvailable);
        this.boardNodeRoot = new BoardNode(boardNodeRoot);
        this.puzzles = new ArrayList<>(puzzles);
        BoardNode bn=boardNodeRoot.getNext();
        BoardNode tbn=boardNodeRoot;
        while((bn !=  boardNodeRoot) && (bn != null)){
            tbn.addNext(new BoardNode(bn));
            tbn=tbn.getNext();
            bn = bn.getNext();
        }
        tbn.addNext(this.boardNodeRoot);
    }
    public boolean checkFitting(BoardNode node, BoardNode neighbour, PuzzleRot puzzle){
        //conditions;
        // side length <= distance node-neighbour &&
        // if (node.next == neighbour)
        //    puzzle.leftAngle <= node.anglex
        //    puzzle.rightAngle <= neighbour.angle
        //  && ! intersect with other
        puzzle.setPosition(node.getPoint().getX(),node.getPoint().getY(),node.getAngleTo0());

        boolean res = ! outOfBoundaries(puzzle);
        if(! res)
            return res;
        double vertLen = node.getPoint().distanceTo(neighbour.getPoint());
        res = res && dimFitIn(puzzle.getSideLen(), vertLen);
        if(! res)
            return res;
        if(node.getNext() == neighbour){
            res =  res &&
                    angFitIn(puzzle.getLeftAngle(), node.getAngle())
                    //angFitIn(puzzle.getRightAngle(), neighbour.getAngle());
        }else{
            res =  res &&
                    //angFitIn(puzzle.getLeftAngle(),  neighbour.getAngle()) &&
                    angFitIn(puzzle.getRightAngle(), node.getAngle());
        }

        if(res){
            res = res && ! intersectsOthers(puzzle);
        }else{
            return false;
        }

        return res;

    }
    public BoardNode addPuzzle(BoardNode node, BoardNode neighbour, PuzzleRot puzzle){

    }

}
