package mensa;

public class Vector {

    private float x;
    private  float y;

    Vector(Point p){
        x=p.getX();
        y=p.getY();
    }
    Vector(float _x, float _y){
        x=_x;
        y=_y;
    }
    public void normalize(){
        float len=(float)Math.sqrt(x*x+y*y);
        if(len != 0) {
            x = x*(1/len);
            y = y*(1/len);
        }
    }

    public void offset(float offset){
        x=x*offset;
        y=y*offset;
    }


    public float getX()  {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
       this.y = y;
    }



}
