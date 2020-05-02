package mensa;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Objects;

public class Puzzle extends Polygon {
    private String id;
    private boolean twoSided=false;
    private int sidesIterable=0;
    public static float DEG180 = (float) Math.PI;

    public boolean isTwoSided() {
        return twoSided;
    }

    public void setTwoSided(boolean twoSided) {
        this.twoSided = twoSided;
    }

    public int getSidesIterable() {
        return sidesIterable;
    }

    public void setSidesIterable(int sidesIterable) {
        this.sidesIterable = sidesIterable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    Puzzle(){
        super();
    }

    Puzzle(String id){
        super();
        this.id = id;
    }

    Puzzle(Puzzle p){
        super((Polygon)p);
        this.id=p.id;
        this.sidesIterable=p.sidesIterable;
        this.twoSided=p.twoSided;
    }


}
