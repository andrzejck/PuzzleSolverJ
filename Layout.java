package mensa;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.scene.*;

import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class Layout {
    public static float ANG_DELTA = 5.0f / 360.0f * 2 * 3.1415f;
    public static float DIM_DELTA = 5;
    public static float ENLARGED_BY = 10;

    private Polygon envelope;
    private Polygon envelopeEnlarged;
    //private ArrayList<PuzzleOnBoard> puzzlesOnBoard;
    private PuzzleRepository puzzlesOnBoard;
    private int iteration = 0;
    private int parentIteration = -1;

    public int getIteration() {
        return iteration;
    }

    public int getParentIteration() {
        return parentIteration;
    }


    //a fit in b > a <= b
    public static boolean dimFitIn(float a, float b) {
        return a - DIM_DELTA <= b;
    }

    public static boolean angFitIn(float a, float b) {
        return a - ANG_DELTA <= b;
    }

    public int compareAng(float a, float b) {
        if (Math.abs(a - b) <= ANG_DELTA) return 0;
        if (a < b)
            return -1;
        else
            return 1;
    }

    public int compareDim(float a, float b) {
        if (Math.abs(a - b) <= DIM_DELTA) return 0;
        if (a < b)
            return -1;
        else
            return 1;
    }

    public Layout() {
        envelope = new Polygon();
        envelopeEnlarged = new Polygon();
        puzzlesOnBoard = new PuzzleRepository();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        return puzzlesOnBoard.equals(((Layout) o).puzzlesOnBoard);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(super.hashCode(), puzzlesOnBoard.hashCode());
    }

    public Layout(Layout l, int iteration) {
        envelope = new Polygon(l.envelope);
        envelopeEnlarged = new Polygon(l.envelopeEnlarged);
        puzzlesOnBoard = new PuzzleRepository(l.puzzlesOnBoard);
        this.iteration = iteration;
        this.parentIteration = l.iteration;
    }

    public void modifyLayout(PuzzleOnBoard puzzle, int layoutPointId, int puzzlePointId) {
        envelope = envelope.mergeIn(puzzle, layoutPointId, puzzlePointId);
        envelopeEnlarged = envelope.expand(ENLARGED_BY);
        puzzlesOnBoard.add(puzzle);
    }

    public boolean doesPuzzleFit(PuzzleOnBoard puzzle) {
        return envelopeEnlarged.doesPolygonFitIn(puzzle);
    }

    Point getPoint(int i) {
        return envelope.getPoint(i);
    }

    void addPoint(Point p) {
        envelope.addPoint(p);
        if (envelope.getPointsCount() >= 3) {
            envelopeEnlarged = envelope.expand(ENLARGED_BY);
            envelope.calculate();
        }
    }

    int getPointsCount() {
        return envelope.getPointsCount();
    }

    Point getPointEnlarged(int i) {
        return envelopeEnlarged.getPoint(i);
    }

    public float getAngleToSegment(int pointId, int nextPointId, Segment segment) {
        return new Segment(getPoint(pointId), getPoint(nextPointId)).angleBetween(segment);
    }

    @Override
    public String toString() {
        return "Layout{" +
                "envelope=" + envelope +
                '}';
    }

    public void draw(GraphicsContext gc, Point centerPoint, float scale) {
        envelope.setColor(Color.BLUE);
        envelope.draw(gc, centerPoint, scale);
        envelopeEnlarged.setColor(Color.GRAY);
        envelopeEnlarged.draw(gc, centerPoint, scale);
        for (PuzzleOnBoard p : puzzlesOnBoard) {
            p.setColor(Color.RED);
            p.draw(gc, centerPoint, scale);
        }
        gc.strokeText(" parIt " + getParentIteration() + " it " + getIteration(), 600, 600);
    }

    public void drawSimplified(GraphicsContext gc, Point centerPoint, float scale) {
        envelope.setColor(Color.BLACK);
        envelope.drawSimplified(gc, centerPoint, scale);
        for (PuzzleOnBoard p : puzzlesOnBoard) {
            p.setColor(Color.BLACK);
            p.drawFilled(gc, centerPoint, scale);
        }
        //gc.strokeText(" parIt "+getParentIteration()+" it "+getIteration(),600,600);
    }

    public void drawSimplified(Graphics2D graphics2D, Point centerPoint, float scale) {
        graphics2D.setColor(java.awt.Color.WHITE);
        graphics2D.fillRect(0, 0, 50, 50);
        graphics2D.setColor(java.awt.Color.BLACK);
        envelope.drawSimplified(graphics2D, centerPoint, scale);
        for (PuzzleOnBoard p : puzzlesOnBoard) {
            p.drawFilled(graphics2D, centerPoint, scale);
        }


    }

/*    public Image snapshot(final Parent sourceNode){
        Region region = new Region();
        //region.set
        //region.s
        final Scene snapshotScene = new Scene(region);

        return sourceNode.snapshot(
                new SnapshotParameters(),
                null
        );
    }*/
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
