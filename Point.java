package mensa;

import java.util.Objects;

public class Point {

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
        tag = false;
    }

    public Point(float x, float y, boolean tag) {
        this.x = x;
        this.y = y;
        this.tag = tag;
    }

    public Point(Point p) {
        x = p.x;
        y = p.y;
        tag = p.tag;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float distanceTo(Point other) {
        return (float) Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
    }

    public Point move(float px, float py) {
        x = x + px;
        y = y + py;
        return this;
    }


    public void move(Point p) {
        x = x + p.getX();
        y = y + p.getY();
    }

    //@Override
    @Override
    public String toString() {
        return toString("");
    }

    public String toString(String id) {
        return "[" + id + "]{" +
                "x=" + (int) x +
                ", y=" + (int) y +
                '}';
    }

    public float getX(Puzzle p) {
        //if(this.equals(p.getPivotPoint()))
        //    return getX()+p.getX0();
        throw new UnsupportedOperationException("deprecated");
//        float alfa = p.getRotate();
//        float x0=p.getX0();
//        float y0=p.getY0();
//        float xp=p.getPivotPoint().getX();
//        float yp=p.getPivotPoint().getY();
//        return (x-xp)*Math.cos(alfa)-(y-yp)*Math.sin(alfa)+xp+x0;
    }

    public float getY(Puzzle p) {
        //if(this.equals(p.getPivotPoint()))
        //    return getY()+p.getY0();
        throw new UnsupportedOperationException("deprecated");
//        float alfa = p.getRotate();
//        float x0=p.getX0();
//        float y0=p.getY0();
//        float xp=p.getPivotPoint().getX();
//        float yp=p.getPivotPoint().getY();
//        return (x-xp)*Math.sin(alfa)+(y-yp)*Math.cos(alfa)+yp+y0;
    }

    public void setX(float x) {
        this.x = x;
    }

    public static Point substract(Point A, Point B) {
        return new Point(A.getX() - B.getX(), A.getY() - B.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Math.abs(point.x - x) < Layout.DIM_DELTA &&
                Math.abs(point.y - y) < Layout.DIM_DELTA;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    private float x;
    private float y;

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    private boolean tag;

    public void setY(float y) {
        this.y = y;
    }
}

