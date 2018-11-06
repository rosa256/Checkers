package checkers;

import java.util.Date;
import java.util.Vector;

public class ChekersData {
    // Klasa ktora przechowuje informacje o Białych i Czarnych pionkach.
    // Informacje o polozeniu pionkow na mapie itp. rozwinie się jeszcze

    // Białe Pionki to 1 a czarne to 2 i to będzie na tablicy intow
    public static final int EMPTY = 0, WHITE = 1, BLACK = 2, WHITE_KING = 3, BLACK_KING = 4;


    private static int board[][];     // Mapa intow - tak naprawde to bedzie nasza plansza pomocnicza - board[r][c]

    public ChekersData() {       //Konstruktor
        board = new int[8][8];  // Nasza plansza
        setUpGame();            //Funkcja ktora ustawi pionki na planszy
    }

    public void setUpGame() {
        board = new int[][]{
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 2, 0, 2, 0, 2, 0},
                {0, 2, 0, 2, 0, 2, 0, 2},
                {2, 0, 2, 0, 2, 0, 2, 0}};
    } //tworzy czystą planszę z poustawianymi pionkami

    int[][] getBoard() {
        return board;
    }

    public int getPiece(int r, int c) {
        return board[r][c];
    }

    public void setPiece(int r, int c, int val) {
        board[r][c] = val;
    }

    public void makeMove(CheckersMove move) {
        makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
    }

    public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = EMPTY;
        if (fromRow - toRow == 2 || fromRow - toRow == -2) {
            int jumpRow = (fromRow + toRow) / 2;
            int jumpCol = (fromCol + toCol) / 2;
            board[jumpRow][jumpCol] = EMPTY;
        }
        if (toRow == 0 && board[toRow][toCol] == BLACK)
            board[toRow][toCol] = BLACK_KING;
        if (toRow == 7 && board[toRow][toCol] == WHITE)
            board[toRow][toCol] = WHITE_KING;
    }

    public boolean canMove(int player, int r1, int c1, int r2, int c2) {

        if (board[r2][c2] != EMPTY) {
            System.out.println("Niedozwolony ruch#1");
            return false;
        }
        if (player == WHITE) {
            if (board[r1][c1] == WHITE && r2 == r1+1 && (c2==(c1+1) || c2==(c1-1))) {
                return true;
            }
            System.out.println("Niedozwolony ruch#3");
            return false;
        }
        else {
            if (board[r1][c1] == BLACK && r2 == r1-1 && (c2==(c1+1) || c2==(c1-1))) {
                return true;
            }
            System.out.println("Niedozwolony ruch#4");
            return false;
        }
    }

    public boolean canJump(int player, int r1, int c1, int r2, int c2) {
        if (board[r2][c2] != EMPTY) {
            System.out.println("Niedozwolony skok#1");
            return false;
        }
        if (player == WHITE) {
            if (board[r1][c1] == WHITE && r2 == r1+2 && (c2==(c1+2) || c2==(c1-2)) && ((board[r1+1][c1+1] == BLACK || board[r1+1][c1-1] == BLACK)
                    || (board[r1+1][c1+1] == BLACK_KING || board[r1+1][c1-1] == BLACK_KING))) {
                return true;
            }
            System.out.println("Niedozwolony skok#3");
            return false;
        }
        else {
            if (board[r1][c1] == BLACK && r2 == r1-2 && (c2==(c1+2) || c2==(c1-2)) && ((board[r1-1][c1+1] == WHITE || board[r1-1][c1-1] == WHITE)
                    || (board[r1-1][c1+1] == WHITE_KING) && board[r1-1][c1-1] == WHITE_KING)){
                return true;
            }
            System.out.println("Niedozwolony skok#4");
            return false;
        }
    }
}


