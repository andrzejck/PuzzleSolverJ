package mensa;

import java.lang.UnsupportedOperationException;

import static java.lang.Float.NaN;


public class Segment {
    private Point A;
    private Point B;
    private float len = -1;

    public Segment(float xA, float yA, float xB, float yB) {
        A = new Point(xA, yA);
        B = new Point(xB, yB);
        float x = A.getX() - B.getX();
        float y = A.getY() - B.getY();
        len = (float) Math.sqrt(x * x + y * y);

    }


    public Segment(Point A, Point B) {
        this.A = A;
        this.B = B;
        float x = A.getX() - B.getX();
        float y = A.getY() - B.getY();
        len = (float) Math.sqrt(x * x + y * y);

    }

    public void setAB(Point A, Point B) {
        this.A = A;
        this.B = B;
        float x = A.getX() - B.getX();
        float y = A.getY() - B.getY();
        len = (float) Math.sqrt(x * x + y * y);

    }

    public boolean inters(Segment other) {
        float v1 = vectorProduct(other.A, other.B, A);
        float v2 = vectorProduct(other.A, other.B, B);
        float v3 = vectorProduct(A, B, other.A);
        float v4 = vectorProduct(A, B, other.B);

        if ((v1 > 0 && v2 < 0 || v1 < 0 && v2 > 0) && (v3 > 0 && v4 < 0 || v3 < 0 && v4 > 0))
            return true;
        else
            return false;
    }

    public boolean isParallel(Segment other, float precision) {
        Segment x1 = new Segment(new Point(0, 0), new Point(B.getX() - A.getX(),
                B.getY() - A.getY()));
        Segment x2 = new Segment(new Point(0, 0), new Point(other.B.getX() - other.A.getX(),
                other.B.getY() - other.A.getY()));

        float angleBetween = x1.angleBetween(x2);

        return (Math.abs(angleBetween) < precision) || (Math.abs(Math.abs(angleBetween) - Puzzle.DEG180) < precision);

    }

    public float distanceToParallel(Segment other) {
        return 0;
    }

    // assume that this.A = other.A
    public float angleBetween(Segment other) {
        float x1 = B.getX() - A.getX();
        float x2 = other.B.getX() - other.A.getX();
        float y1 = B.getY() - A.getY();

        float y2 = other.B.getY() - other.A.getY();

        float dot = x1 * x2 + y1 * y2;//      # dot product between [x1, y1] and [x2, y2]
        float det = x1 * y2 - y1 * x2;//      # determinant
        float alfa = (float) Math.atan2(det, dot);
        return alfa;
    }

    private float vectorProduct(Point x, Point y, Point z) {
        float x1 = z.getX() - x.getX();
        float y1 = z.getY() - x.getY();
        float x2 = y.getX() - x.getX();
        float y2 = y.getY() - x.getY();
        return x1 * y2 - x2 * y1;
    }

    public float length() {
        if (len < 0)
            throw new IllegalStateException("Length less than 0");
        else
            return len;
    }

    public boolean pointOnSegment(Point p) {
        if (((p.getY() - A.getY()) * (B.getX() - p.getX()) -
                (p.getX() - A.getX()) * (B.getY() - p.getY())) == 0) {
            if (p.getX() <= Math.max(A.getX(), B.getX()) && p.getX() >= Math.min(A.getX(), B.getX()) &&
                    p.getY() <= Math.max(A.getY(), B.getY()) && p.getY() >= Math.min(A.getY(), B.getY()))
                return true;
            else
                return false;
        } else
            return false;

    }

    public int isLeft(Point p) {
        return (int) ((B.getX() - A.getX()) * (p.getY() - A.getY())
                - (p.getX() - A.getX()) * (B.getY() - A.getY()));
    }


    public Point findIntersectionOfLines(Segment other) {
        float dx12 = B.getX() - A.getX();
        float dy12 = B.getY() - A.getY();
        float dx34 = other.B.getX() - other.A.getX();
        float dy34 = other.B.getY() - other.A.getY();
        float denominator = (dy12 * dx34 - dx12 * dy34);
        if (denominator == 0)
            return new Point(NaN, NaN);
        float t1 =
                ((A.getX() - other.A.getX()) * dy34 + (other.A.getY() - A.getY()) * dx34)
                        / denominator;

        return new Point(A.getX() + dx12 * t1, A.getY() + dy12 * t1);
    }
}
