package mensa;

import java.util.Objects;

public class Point {
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public double distanceTo(Point other){
        return Math.sqrt((x-other.x)*(x-other.x)+(y-other.y)*(y-other.y));
    }
    //@Override
    @Override
    public String toString() {
        return toString("-");
    }

    public String toString(String id) {
        return "Point ["+id+"]{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public double getX(Puzzle p) {
        //if(this.equals(p.getPivotPoint()))
        //    return getX()+p.getX0();

        double alfa = p.getRotate();
        double x0=p.getX0();
        double y0=p.getY0();
        double xp=p.getPivotPoint().getX();
        double yp=p.getPivotPoint().getY();
        return (x-xp)*Math.cos(alfa)-(y-yp)*Math.sin(alfa)+xp+x0;
    }

    public double getY(Puzzle p) {
        //if(this.equals(p.getPivotPoint()))
        //    return getY()+p.getY0();
        double alfa = p.getRotate();
        double x0=p.getX0();
        double y0=p.getY0();
        double xp=p.getPivotPoint().getX();
        double yp=p.getPivotPoint().getY();
        return (x-xp)*Math.sin(alfa)+(y-yp)*Math.cos(alfa)+yp+y0;
    }

    public void setX(double x) {
        this.x = x;
    }

    public static Point substract(Point A, Point B){
        return new Point(A.getX()-B.getX(), A.getY()-B.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    private double x;
    private double y;


    public void setY(float y) {
        this.y = y;
    }
}

