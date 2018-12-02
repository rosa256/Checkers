package checkers_swing;

import javax.swing.*;
import java.awt.*;

public class Pawn {

    ImageIcon image;
    Point point;
    int posindex;

    public Pawn(Point p, ImageIcon image) {
        this.point = p;
        this.image = image;
        posindex=0;
    }
    public Pawn() {
    }
    @Override
    public String toString() {
        return "Pawn{" +
                "point=" + this.point.x +", "+ this.point.y +
        '}';
    }

    public Point getPoint() {
        return point;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public void setP(int x, int y) {
        this.point.x = x;
        this.point.y = y;
    }


    public boolean equal(Pawn p) {
        boolean isEqual=false;
        if(p!=null && p instanceof Pawn) {
            isEqual=(this.point==p.point);
        }
        return isEqual;
    }

}
