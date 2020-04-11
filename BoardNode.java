package mensa;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardNode {
    private ArrayList<BoardNode> nextList;
    private ArrayList<BoardNode> prevList;
    private Point point;
    private double angle;
    private double angleTo0;

    public BoardNode(BoardNode boardNode) {
        this.nextList=boardNode.nextList;
        this.prevList=boardNode.prevList;
        this.point=boardNode.point;
        this.angle=boardNode.angle;
        this.angleTo0 = boardNode.angleTo0;

    }
    public BoardNode(Point point) {
        this.point = point;
        nextList = new ArrayList<>();
        prevList = new ArrayList<>();
    }


    public double getAngle() {
        return angle;
    }

    public double getAngleTo0() {
        return angleTo0;
    }

    public void addNext(BoardNode next){
        nextList.add(next);
        next.addPrev(this);
        BoardNode prev=getPrev();
        angleTo0 = new Segment(point.getX(), point.getY(), point.getX(), 0.0).angleBetween(new Segment(point, next.getPoint()));
        if (prev != null){
            angle = new Segment(point,next.getPoint()).angleBetween(new Segment(point, prev.getPoint()));

        }

    }

    public void delNext(BoardNode next){
        next.delPrev(this);
        nextList.remove(next);
    }

    public void addPrev(BoardNode prev){
        prevList.add(prev);

    }

    public void delPrev(BoardNode prev){
        prevList.remove(prev);

    }

    public Iterator<BoardNode> getNextIterator(){
        return nextList.iterator();
    }

    public int getNextCount(){
        return nextList.size();

    }
    public BoardNode getNext(){
        if (nextList.size() == 1)
            return nextList.get(0);
        else
            return null;
    }

    public BoardNode getPrev(){
        if (prevList.size() == 1)
            return prevList.get(0);
        else
            return null;
    }


    public Point getPoint(){
        return point;
    }
    public Segment getNextSegment(){
        if (nextList.size() > 0)
            return new Segment(point, getNext().getPoint());
        else
            return null;
    }


}
