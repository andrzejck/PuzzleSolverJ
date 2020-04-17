package mensa;

import java.util.ArrayList;

public class Layout {
    public static float ANG_DELTA=5.0f/360.0f*2*3.1415f;
    public static float DIM_DELTA =5;
    private ArrayList<PuzzleOnBoard> puzzles;
    private BoardNode boardNodeRoot;
    private ArrayList<Puzzle> puzzleAvailable;
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




    public Layout(ArrayList<Puzzle> puzzleAvailable, BoardNode boardNodeRoot, ArrayList<PuzzleOnBoard> puzzles){
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


}
