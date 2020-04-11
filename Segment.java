package mensa;

public class Segment {
    private Point A;
    private Point B;

    public Segment(double xA, double yA, double xB, double yB){
        A=new Point(xA, yA);
        B=new Point(xB, yB);
    }


    public Segment(Point A, Point B){
        this.A=A;
        this.B=B;

    }

    public boolean inters(Segment other){
        double v1=vectorProduct(other.A,other.B,A);
        double v2=vectorProduct(other.A,other.B,B);
        double v3=vectorProduct(A,B,other.A);
        double v4=vectorProduct(A,B,other.B);

        if((v1>0&&v2<0||v1<0&&v2>0)&&(v3>0&&v4<0||v3<0&&v4>0))
            return true;
        else
            return false;
    }

    public boolean isParallel(Segment other, double precision){
        Segment x1=new Segment(new Point(0,0), new Point(B.getX()-A.getX(),
                                                                  B.getY()-A.getY()));
        Segment x2=new Segment(new Point(0,0), new Point(other.B.getX()-other.A.getX(),
                                                                other.B.getY()-other.A.getY()));

        double angleBetween=x1.angleBetween(x2);

        return (Math.abs(angleBetween) < precision) || (Math.abs(Math.abs(angleBetween)-Puzzle.DEG180) < precision);

    }

    public double distanceToParallel(Segment other){
        return 0;
    }

    // assume that this.A = other.A
    public double angleBetween(Segment other){
        double x1=B.getX()-A.getX();
        double x2=other.B.getX()-A.getX();
        double y1=B.getY()-A.getY();

        double y2=other.B.getY()-A.getY();
;
        double dot = x1*x2 + y1*y2;//      # dot product between [x1, y1] and [x2, y2]
        double det = x1*y2 - y1*x2;//      # determinant
        double alfa = Math.atan2(det, dot);
        return alfa;
    }

    private double vectorProduct(Point x, Point y, Point z){
        double x1=z.getX()-x.getX();
        double y1=z.getY()-x.getY();
        double x2=y.getX()-x.getX();
        double y2=y.getY()-x.getY();
        return x1*y2-x2*y1;
    }


}
