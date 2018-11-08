package checkers;

public class CheckersMove {
    // Klasa ktora przetrzymuje z ktorego miejsca, na ktore miejsce sie ruszyles
    int fromRow;
    int fromCol;
    int toRow;
    int toCol;

    CheckersMove(int r1, int c1, int r2, int c2) {
        fromRow = r1;
        fromCol = c1;
        toRow = r2;
        toCol = c2;
    }
}