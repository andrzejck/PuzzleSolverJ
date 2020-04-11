package mensa;

import java.util.ArrayList;
import java.util.LinkedList;

public class PuzzleNode {
    private ArrayList<PuzzleNode> nodes;
    private PuzzleRot pr;
    private LinkedList<Puzzle> available;

    public PuzzleNode(){
        nodes = new ArrayList<>();
    }

    public PuzzleNode(PuzzleRot pr, LinkedList<Puzzle> available){
        nodes = new ArrayList<>();
        this.pr=pr;
        this.available = new LinkedList<Puzzle>(available);
    }

    public int getChildrenCount(){
        return nodes.size();
    }

    public PuzzleRot getNode(int i){
        return nodes.get(i).pr;
    }

    public void addNode(PuzzleRot pr, LinkedList<Puzzle> available){
        nodes.add(new PuzzleNode(pr, available));
    }
}
