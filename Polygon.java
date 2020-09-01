package mensa;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


import java.awt.*;
import java.util.*;
//import java.util.Collection;

import static java.lang.Float.NaN;
import static mensa.Layout.ANG_DELTA;
import static mensa.Layout.DIM_DELTA;


public class Polygon {
    private ArrayList<Point> points;
    private ArrayList<Float> sides;
    private ArrayList<Float> angles;
    private Color color = Color.RED;

    private int pointsCount = 0;

    public int getPointsCount() {
        return pointsCount;
    }

    private float sideLength = 0;
    private float anglesSum = 0;

    public boolean isCalculated() {
        return calculated;
    }

    private boolean calculated = false;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Polygon() {
        points = new ArrayList<>();
        sides = new ArrayList<>();
        angles = new ArrayList<>();
    }

    public Polygon(Polygon p) {
        points = new ArrayList<>();
        sides = new ArrayList<>();
        angles = new ArrayList<>();
        pointsCount = p.pointsCount;
        sideLength = p.sideLength;
        anglesSum = p.anglesSum;

        for (int i = 0; i < p.pointsCount; i++) {
            points.add(new Point(p.points.get(i)));
            sides.add(p.sides.get(i));
            angles.add(p.angles.get(i));
        }
    }


    public void addPoint(Point p) {
        pointsCount++;
        points.add(p);
        angles.add(0f);
        sides.add(0f);
        calculated = false;
    }


    public boolean addPoint(int index, Point p) {
        if ((p.equals(getPoint(index))) ||
                (p.equals(getPoint(index - 1))) ||
                (p.equals(getPoint(index + 1)))) {
            //point already exists
            return false;
        }

        if (index > (pointsCount - 1)) {
            points.add(p);
            angles.add(0f);
            sides.add(0f);

        } else {
            points.add(cir(index), p);
            angles.add(cir(index), 0f);
            sides.add(cir(index), 0f);

        }

        pointsCount++;
        calculated = false;
        return true;
    }

    public void removePoint(int index) {
        points.remove(cir(index));
        angles.remove(cir(index));
        sides.remove(cir(index));
        pointsCount--;
        calculated = false;
    }


//    public void insertPoint(Point p, int i){
//        points.
//    }

    public Point getPoint(int i) {
        return points.get((((i % pointsCount) + pointsCount) % pointsCount));
    }

    public float getAngle(int i) {
        return angles.get((((i % pointsCount) + pointsCount) % pointsCount));
    }

    public float getSide(int i) {
        return sides.get((((i % pointsCount) + pointsCount) % pointsCount));
    }

    public int cir(int i) {
        return (((i % pointsCount) + pointsCount) % pointsCount);
    }

    public int inside(Point p) {
        int wn = 0;
        Segment tmpSeg = new Segment(0, 0, 0, 0);
        for (int i = 0; i < pointsCount; i++) {
            tmpSeg.setAB(getPoint(i), getPoint(i + 1));
            if (tmpSeg.pointOnSegment(p))
                return 1;
            if (getPoint(i).getY() <= p.getY()) {
                if (getPoint(i + 1).getY() > p.getY()) {
                    if (tmpSeg.isLeft(p) > 0) {
                        wn++;
                    }
                }
            } else {
                if (getPoint(i + 1).getY() <= p.getY()) {
                    if (tmpSeg.isLeft(p) < 0) {
                        wn--;
                    }
                }

            }
        }
        return wn;
    }

    public Polygon rotate(float angle, int pointId) {

        Point pivot = getPoint(pointId);

        float xp = pivot.getX();
        float yp = pivot.getY();

        float x_shifted, y_shifted;
        Point p;
        for (int i = 0; i < points.size(); i++) {
            p = getPoint(i);
            x_shifted = p.getX() - xp;
            y_shifted = p.getY() - yp;
            p.setX((float) (xp + x_shifted * Math.cos(angle) - y_shifted * Math.sin(angle)));
            p.setY((float) (yp + x_shifted * Math.sin(angle) + y_shifted * Math.cos(angle)));

        }

        return this;

    }

    public Polygon expand(float offset) {
        Polygon polygonExpanded = new Polygon();
        int i;
        int k;
        for (int j = 0; j < pointsCount; j++) {
//            int i=CIR(j+1, pointsCount());
//            int k=CIR(j-1, pointsCount());
            i = j + 1;
            k = j - 1;
            Vector v1 = new Vector(getPoint(j).getX() - getPoint(i).getX(),
                    getPoint(j).getY() - getPoint(i).getY());
            v1.normalize();
            v1.offset(offset);
            Vector n1 = new Vector(-v1.getY(), v1.getX());

            Point pij1 = new Point(
                    (getPoint(i).getX() + n1.getX()),
                    (getPoint(i).getY() + n1.getY()));
            Point pij2 = new Point(
                    (getPoint(j).getX() + n1.getX()),
                    (getPoint(j).getY() + n1.getY()));

            Vector v2 = new Vector(
                    getPoint(k).getX() - getPoint(j).getX(),
                    getPoint(k).getY() - getPoint(j).getY());
            v2.normalize();
            v2.offset(offset);
            Vector n2 = new Vector(-v2.getY(), v2.getX());

            Point pjk1 = new Point(
                    (getPoint(j).getX() + n2.getX()),
                    (getPoint(j).getY() + n2.getY()));
            Point pjk2 = new Point(
                    (getPoint(k).getX() + n2.getX()),
                    (getPoint(k).getY() + n2.getY()));

            // See where the shifted lines ij and jk intersect.
            //bool lines_intersect, segments_intersect;
            Point poi = (new Segment(pij1, pij2).findIntersectionOfLines(new Segment(pjk1, pjk2)));
            if (poi.getX() == NaN) {
                poi = getPoint(j);
            }
            polygonExpanded.addPoint(poi);
        }
        polygonExpanded.calculate();
        return polygonExpanded;
//        for (int j = 0; j < pointsCount; j++) {
//            envelopeEnlarged[j].calcAngle(envelopeEnlarged[CIR(j-1, pointsCount())],
//                    envelopeEnlarged[CIR(j+1, pointsCount())]);
//        }

        // throw new IllegalStateException("Not implemented");
    }

    public Polygon flipVertical() {
        float minX = 1000;
        for (Point p : points) {
            p.setX(-p.getX());
//            if (p.getX() < minX)
//                minX = p.getX();
        }
//        for(Point p: points){
//            p.setX(p.getX() - minX);
//        }
        Collections.reverse(points);
        Collections.reverse(angles);
        Collections.reverse(sides);
        calculate();
        return this;
    }

    public Polygon translate(Point vector) {
        for (Point p : points) {
            p.move(vector);
        }
        return this;
    }


    public void draw(GraphicsContext gc, Point centerPoint, float scale) {

        if (pointsCount < 3)
            throw new IllegalStateException("Less than 3 points in polygon.");
        Point prev = null;
        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(1);

        float cX = centerPoint.getX();
        float cY = centerPoint.getY();
        for (int i = 0; i < pointsCount; i++) {
            gc.strokeLine(getPoint(i).getX() * scale + cX,
                    getPoint(i).getY() * scale + cY,
                    getPoint(i + 1).getX() * scale + cX,
                    getPoint(i + 1).getY() * scale + cY);
            gc.strokeText(String.valueOf(i)
                            + " ("
                            + String.valueOf((int) getPoint(i).getX())
                            + ","
                            + String.valueOf((int) getPoint(i).getY())
                            + ")",
                    getPoint(i).getX() * scale + cX + 10,
                    getPoint(i).getY() * scale + cY + 10);


        }


    }

    public void drawSimplified(GraphicsContext gc, Point centerPoint, float scale) {

        if (pointsCount < 3)
            throw new IllegalStateException("Less than 3 points in polygon.");
        Point prev = null;
        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(1);

        float cX = centerPoint.getX();
        float cY = centerPoint.getY();
        for (int i = 0; i < pointsCount; i++) {
            gc.strokeLine(getPoint(i).getX() * scale + cX,
                    getPoint(i).getY() * scale + cY,
                    getPoint(i + 1).getX() * scale + cX,
                    getPoint(i + 1).getY() * scale + cY);

        }


    }

    public void drawSimplified(Graphics2D graphics2D, Point centerPoint, float scale) {
        if (pointsCount < 3)
            throw new IllegalStateException("Less than 3 points in polygon.");
        java.awt.Polygon awtPolygon = new java.awt.Polygon();

        float cX = centerPoint.getX();
        float cY = centerPoint.getY();
        for (int i = 0; i < pointsCount; i++) {
            awtPolygon.addPoint((int) ((getPoint(i).getX() * scale) + cX),
                    (int) ((getPoint(i).getY() * scale) + cY));

        }
        graphics2D.draw(awtPolygon);


    }


    public void drawFilled(GraphicsContext gc, Point centerPoint, float scale) {

        if (pointsCount < 3)
            throw new IllegalStateException("Less than 3 points in polygon.");
        Point prev = null;
        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(1);

        float cX = centerPoint.getX();
        float cY = centerPoint.getY();
        double pointsX[] = new double[pointsCount];
        double pointsY[] = new double[pointsCount];

        for (int i = 0; i < pointsCount; i++) {
            pointsX[i] = getPoint(i).getX() * scale + cX;
            pointsY[i] = getPoint(i).getY() * scale + cY;
        }
        gc.fillPolygon(pointsX, pointsY, pointsCount);
        gc.strokePolygon(pointsX, pointsY, pointsCount);
        //gc.strokePolygon(pointsX, pointsY, pointsCount);


    }

    public void drawFilled(Graphics2D graphics2D, Point centerPoint, float scale) {

        if (pointsCount < 3)
            throw new IllegalStateException("Less than 3 points in polygon.");
        java.awt.Polygon awtPolygon = new java.awt.Polygon();
        graphics2D.setColor(java.awt.Color.BLACK);
        float cX = centerPoint.getX();
        float cY = centerPoint.getY();

        for (int i = 0; i < pointsCount; i++) {
            awtPolygon.addPoint((int) (getPoint(i).getX() * scale + cX), (int) (getPoint(i).getY() * scale + cY));
        }
        graphics2D.fill(awtPolygon);
    }


    public void calculate() {
        if (pointsCount < 3)
            throw new IllegalStateException("Less than 3 points in polygon.");
        sides.clear();
        angles.clear();
        sideLength = 0;
        anglesSum = 0;
        float angle;
        for (int i = 0; i < pointsCount; i++) {
            Segment s = new Segment(getPoint(i), getPoint(i + 1));
            sides.add(s.length());
            angle = new Segment(getPoint(i), getPoint(i - 1)).angleBetween(s);
            if (angle < 0)
                angle = 2 * (float) Math.PI + angle;
            angles.add(angle);

            sideLength += sides.get(i);
            anglesSum += angles.get(i);
        }
    }

    public void rotateOneForward() {
        Point tmpP = points.get(0);
        float side = sides.get(0);
        float angle = angles.get(0);
        //int j=0;
        for (int i = 0; i < pointsCount - 1; i++) {
            points.set(i, points.get(i + 1));
            sides.set(i, sides.get(i + 1));
            angles.set(i, angles.get(i + 1));
        }
        points.set(pointsCount - 1, tmpP);
        sides.set(pointsCount - 1, side);
        angles.set(pointsCount - 1, angle);
    }

    public boolean doesPolygonFitIn(Polygon o) {
        for (int j = 0; j < o.pointsCount; j++) {
            if (inside(o.getPoint(j)) == 0) {
                return false;
            }

        }

//     for (int j=0; j < pointsCount(); j++){
//         if ( puzzle->inside(get(j)) != 0){
//             //COUT("******************* fired D ************************************");
//             return false;
//         }
//
//     }

        for (int i = 0; i < pointsCount; i++) {
            Segment envelopeSegment = new Segment(getPoint(i), getPoint(i + 1));
            for (int j = 0; j < o.pointsCount; j++) {
                // don't compare adjacent sides

                Segment puzzleSegment = new Segment(o.getPoint(j), o.getPoint(j + 1));
                if (envelopeSegment.inters(puzzleSegment)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Polygon mergeIn(Polygon o, int extPointId, int intPointId) {
        TreeMap<Integer, Integer> extPointsToRemove = new TreeMap<>();
        TreeMap<Integer, Integer> intPointsToRemove = new TreeMap<>();
        ;
        Polygon res = new Polygon();
        extPointsToRemove.put(extPointId, 1);

        for (int i = 0; i < pointsCount; i++) {
            if ((Math.abs(getAngle(i + extPointId) - o.getAngle(i + intPointId)) < Layout.ANG_DELTA) &&
                    (getPoint(i + extPointId).distanceTo(o.getPoint(i + intPointId)) < DIM_DELTA)) {
                extPointsToRemove.put(i + extPointId, 1);
                intPointsToRemove.put(i + intPointId, 1);
            }
        }

        int lastBeforeRemoved = extPointsToRemove.firstKey() - 1;
        for (int i = 0; i < pointsCount; i++) {
            if (!extPointsToRemove.containsKey(i)) {
                res.addPoint(getPoint(i));
            } else {
                if (i <= lastBeforeRemoved)
                    lastBeforeRemoved--;
            }
        }
        int l = intPointId;
        //++lastBeforeRemoved;
        for (Point p : o.points) {
            if (!intPointsToRemove.containsKey(l)) {
                if (!res.addPoint(++lastBeforeRemoved, o.getPoint(l)))
                    --lastBeforeRemoved;
                //res.getPoint(lastBeforeRemoved).setTag(true);
            }
            l = o.cir(l - 1);
        }
        res.calculate();
        return res;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygon polygon = (Polygon) o;
        if (pointsCount == polygon.pointsCount &&
                Float.compare(polygon.sideLength, sideLength) <= DIM_DELTA * pointsCount * pointsCount &&
                Float.compare(polygon.anglesSum, anglesSum) <= ANG_DELTA * pointsCount) {
//            float angle= (Polygon o).getAngle(0);
//            angles.stream().filter(a -> (int)(a*60f) == (int)(angle*60f)).collect()
//            int j = ((Polygon) o).angles.indexOf(angles.get(0));
//            if(j == -1){
//                return false;
//            }
//            for(int i = 0; i < pointsCount; i++){
//                if(! angles.get(i).equals(((Polygon)o).angles.get((i+j)%pointsCount)) ||
//                        ! sides.get(i).equals(((Polygon)o).sides.get((i+j)%pointsCount))) {
//                    return false;
//                }
//
//            }
            return true;

        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(pointsCount, (int) sideLength, (int) (anglesSum * 60f));
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Polygon polygon = (Polygon) o;
//        if( pointsCount == polygon.pointsCount &&
//                Float.compare(polygon.sideLength, sideLength) <= DIM_DELTA*pointsCount &&
//                Float.compare(polygon.anglesSum, anglesSum) <= ANG_DELTA*pointsCount){
//            return true;
//
//        }else{
//            return false;
//        }
//    }
//@Override
//public int hashCode() {
//    return com.google.common.base.Objects.hashCode(pointsCount, (int)sideLength, (int)anglesSum*60) ;
//}

    public boolean hasNextPoint() {
        return points.iterator().hasNext();
    }

    public Point nextPoint() {
        return points.iterator().next();
    }

    public float getAngleToSegment(int pointId, int nextPointId, Segment segment) {
        return new Segment(getPoint(pointId), getPoint(nextPointId)).angleBetween(segment);
    }


    @Override
    public String toString() {
        return "Polygon{" +
                ", points=" + points +
                ", sides=" + sides +
                ", angles=" + angles +
                '}';
    }

}
