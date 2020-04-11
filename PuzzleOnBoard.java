package mensa;

import javafx.scene.canvas.GraphicsContext;

public class PuzzleOnBoard {
    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public PuzzleOnBoard(Puzzle puzzle, double angle, Point point, int pointId, boolean flipped) {
        this.puzzle = puzzle;
        this.angle = angle;
        this.point = point;
        this.pointId = pointId;
        this.flipped = flipped;
    }

    private Puzzle puzzle;
    private double angle;
    private Point point;
    private int pointId;
    private boolean flipped;

    public void draw(GraphicsContext gc) {
        puzzle.setPosition(point.getX(), point.getY(),pointId, flipped, 0, angle);
        puzzle.draw(gc);
    }


}
