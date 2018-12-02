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

    public void setP(Point p) {
        this.point = p;
    }

}
