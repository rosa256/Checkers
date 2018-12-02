package checkers_swing;

import java.awt.*;
import java.util.HashMap;

public class CheckersMove {
    // Klasa ktora przetrzymuje z ktorego miejsca, na ktore miejsce sie ruszyles
    public int fromRow;
    public int fromCol;
    public int toRow;
    public int toCol;

    public CheckersMove(int r1, int c1, int r2, int c2) {
        fromRow = r1;
        fromCol = c1;
        toRow = r2;
        toCol = c2;
    }
}