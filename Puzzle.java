package mensa;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Objects;

public class Puzzle {

    private double x0 = 0.0f;
    private double y0 = 0.0f;
    private double rotate = 0.0f;
    private int rotatePivot = 0;
    private Color color=Color.BLACK;
    private String id="n/n";
    private boolean twoSided=false;
    private boolean flip=false;
    final static double DEG180=3.1415;
    final static double DEG90=3.1415/2;

    public static int getCircularSideId(int side, int sidesCount){

        return ((side)%sidesCount+sidesCount)%sidesCount;

    }

    public double getAngle(int angle){
        return angles.get(angle);
    }

    public Point getPoint(int point){
        return points.get(point);
    }


    public boolean isTwoSided() {
        return twoSided;
    }

    public void setTwoSided(boolean twoSided) {
        this.twoSided = twoSided;
    }

    private ArrayList<Point> points;
    private ArrayList<Double> sides;
    private ArrayList<Double> angles;
    private ArrayList<Double> compAngles;

    public int getSidesCount() {
        return sidesCount;
    }

    public double getLastAngle(){
        return angles.get(angles.size()-1);
    }

    private int sidesCount=0;

    public int getSidesIterable() {
        return sidesIterable;
    }

    public void setSidesIterable(int sidesIterable) {
        this.sidesIterable = sidesIterable;
    }

    private int sidesIterable=0;


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public double getRotate() {
        return rotate;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    public int getRotatePivot() {
        return rotatePivot;
    }

    public Point getPivotPoint(){
        return new Point(points.get(rotatePivot).getX(),points.get(rotatePivot).getY());
    }
    public void setRotatePivot(int rotatePivot) {
        this.rotatePivot = rotatePivot;
    }



    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getY0() {
        return y0;
    }

    public void setY0(double y0) {
        this.y0 = y0;
    }

    public double getSide(int side) {
        return sides.get(side);
    }

    public double getLastSide() {
        return sides.get(sides.size()-1);
    }

    public double getLeftAngel(int side){
        if(side>0)
            return angles.get(side-1);
        else
            return angles.get(angles.size()-1);
    }
    public double getRightAngel(int side){
        return angles.get(side);
    }

    public Puzzle(String id) {
        this.points = new ArrayList<>();
        sides = new ArrayList<>();
        angles = new ArrayList<>();
        compAngles = new ArrayList<>();
        this.id=id;
    }

    @Override
    public String toString() {
        return "Puzzle{" +
                "id='" + id + '\'' +
                ", points=" + points +
                ", sides=" + sides +
                ", angles=" + angles +
                '}';
    }

    public void addPoint(Point point){
        points.add(point);
    }

    public void calculate(){
        sidesCount=points.size();
        Point prev=points.get(points.size()-1);
        for(Point p: points){
            if(prev != null) {
                sides.add(Math.sqrt( Math.pow(p.getX()-prev.getX(),2)+Math.pow(p.getY()-prev.getY(),2) ));
            }
            prev = p;
        }
        //sides.add(Math.sqrt( Math.pow(points.get(0).getX()-prev.getX(),2)+Math.pow(points.get(0).getY()-prev.getY(),2) ));
        Double a,b,c;
        Point A,B,C;
        for(int i=0; i<sidesCount; i++){
            C=points.get(i);
            if(i==0){
                B=points.get(points.size()-1);

            }else{
                B=points.get(i-1);

            }
            if(i==sidesCount-1){
                A=points.get(0);
                b=sides.get(0);
            }else{
                A=points.get(i+1);
                b=sides.get(i+1);
            }
            a=sides.get(i);
            c=Math.sqrt( Math.pow(A.getX()-B.getX(),2)+Math.pow(A.getY()-B.getY(),2) );
            angles.add(Math.acos( (Math.pow(a,2)+
                                  Math.pow(b,2)-
                                   Math.pow(c,2))/(2*a*b) ));
            compAngles.add(2*Math.PI-angles.get(i));
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Puzzle puzzle = (Puzzle) o;
        return Objects.equals(id, puzzle.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void draw(GraphicsContext gc){

        if(points.size() < 2)
            return;
        Point prev=null;
        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(2);

        for(Point p: points){
            if(prev != null) {

                gc.strokeLine(p.getX(this),
                                p.getY(this),
                                prev.getX(this),
                                prev.getY(this));

            }
            prev=p;
        }
        gc.strokeLine(prev.getX(this),
                      prev.getY(this),
                      points.get(0).getX(this),
                     points.get(0).getY(this));


    }

    private void flip(){
        double maxX=0;
        for(Point p: points){
            if (p.getX() > maxX) {
                maxX = p.getX();
            }
            p.setX(-p.getX());
        }
        for(Point p: points){
            p.setX(p.getX()+maxX);
            //System.out.println(p.getX());
            //System.out.println(p.getY());
        }


    }

    public void setPosition(double x0, double y0, int side, boolean flipped, int boxSide, double angle){
        //box sides: 3
        //         _____
        //       0 |   |
        //         |___| 2
        //           1

        setX0(0);
        setY0(0);
        double rotateAngle=angle;

        double yPivot=0.0;
        double xPivot=0.0;
        if(! flipped ) {
            if(boxSide == 0){
                rotateAngle += 0.0;
            }
            if(boxSide == 1){
                rotateAngle += 3.1415/2;
            }

            if(boxSide == 2){
                rotateAngle += 3.1415;
            }

            if(boxSide == 3){
                rotateAngle += 3.1415+3.1415/2;
            }

            rotateAngle = rotateAngle - DEG90 + getAngle(0);

            for (int i = 0; i < side; i++) {
                rotateAngle = rotateAngle + 3.1415 - getAngle(i);
            }

            Point pivotPoint = getPoint(side);
            setRotatePivot(0);
            setRotate(-rotateAngle);
            xPivot = x0 - pivotPoint.getX(this);
            yPivot = y0 - pivotPoint.getY(this);
        }else{
            if(boxSide == 0){
                rotateAngle += DEG180;
            }
            if(boxSide == 1){
                rotateAngle += DEG90;
            }

            if(boxSide == 2){
                rotateAngle += 0;
            }

            if(boxSide == 3){
                rotateAngle += -DEG90;
            }

            //rotateAngle = rotateAngle + DEG180 - (DEG90- getAngle(0));
            flip();
            //rotateAngle = rotateAngle + DEG180;
            rotateAngle = rotateAngle - DEG90 + getAngle(0);
            for (int i = 0; i < side; i++) {
                rotateAngle = rotateAngle + DEG180- getAngle(i);
            }
            //setRotatePivot(0);
            setRotate(rotateAngle);
            //setRotate(60);
            int n=getSidesCount();
            int pivotPointId = ((side-1)%n+n)%n;
            Point pivotPoint = getPoint(pivotPointId);
            xPivot = x0 - pivotPoint.getX(this);
            yPivot = y0 - pivotPoint.getY(this);

        //    xPivot = 500;
         //   yPivot = 500;
            /*System.out.println("***** pivot point "+pivotPointId);
            System.out.println("***** flipped x0 "+x0);
            System.out.println("***** flipped y0 "+y0);
            System.out.println("***** flipped xPivot "+xPivot);
            System.out.println("***** flipped yPivot "+yPivot);*/

        }
        setX0(xPivot);
        setY0(yPivot);






    }


    public void setPosition(double x0, double y0, int side, boolean flipped, int boxSide){
        //box sides: 3
        //         _____
        //       0 |   |
        //         |___| 2
        //           1
        setPosition(x0, y0, side, flipped, boxSide, 0.0);


    }
}
