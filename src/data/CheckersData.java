package data;

import java.util.ArrayList;

public class CheckersData {
    // Klasa ktora przechowuje informacje o Białych i Czarnych pionkach.
    // Informacje o polozeniu pionkow na mapie itp. rozwinie się jeszcze

    // Białe Pionki to 1 a czarne to 2 i to będzie na tablicy intow
    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    public static final int WHITE_KING = 3;
    public static final int BLACK_KING = 4;


    private int[][] board;     // Mapa intow - tak naprawde to bedzie nasza plansza pomocnicza - board[r][c]

    public CheckersData() {       //Konstruktor
        board = new int[][]{
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 2, 0, 2, 0, 2, 0},
                {0, 2, 0, 2, 0, 2, 0, 2},
                {2, 0, 2, 0, 2, 0, 2, 0}};
    }

    public int[][] getBoard() {
        return board;
    }

    public void makeMove(CheckersMove move) {
        makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
    }

    public void kingMove(CheckersMove move) {
        kingMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
    }

    private void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
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

    public boolean canMove(int player, int fromRow, int fromCol, int toRow, int toCol, int turn) {
        if (board[toRow][toCol] != EMPTY) {
            return false;
        }
        boolean nextColumn = toCol == (fromCol + 1) || toCol == (fromCol - 1);
        int currentChecker = board[fromRow][fromCol];
        return (player == WHITE && turn == 0) && (currentChecker == WHITE && toRow == fromRow + 1 && nextColumn)
                || (player == BLACK && turn == 1) && (currentChecker == BLACK && toRow == fromRow - 1 && nextColumn);
    }

    public boolean canKingMoveJump(int player, int rowFrom, int rowTo, int colFrom, int colTo, int turn) {
        if ((player == WHITE_KING && turn == 0) || (player == BLACK_KING && turn == 1)) {
            int colPosition;
            ArrayList<Integer> tab = new ArrayList<>();
            if (rowFrom > rowTo && colFrom > colTo) { // lewy_gorny
                colPosition = colFrom;
                for (int i = rowFrom; i >= rowTo; i--) {
                    tab.add(board[i][colPosition]);
                    colPosition--;
                }
            } else if (rowFrom > rowTo && colFrom < colTo) { // prawy_gorny
                colPosition = colFrom;
                for (int i = rowFrom; i >= rowTo; i--) {
                    tab.add(board[i][colPosition]);
                    colPosition++;
                }
            } else if (rowFrom < rowTo && colFrom < colTo) { // prawy_dolny
                colPosition = colFrom;
                for (int i = rowFrom; i <= rowTo; i++) {
                    tab.add(board[i][colPosition]);
                    colPosition++;
                }
            } else if (rowFrom < rowTo && colFrom > colTo) { // lewy_dolny
                colPosition = colFrom;
                for (int i = rowFrom; i <= rowTo; i++) {
                    tab.add(board[i][colPosition]);
                    colPosition--;
                }
            } else
                return false;
            return checkKingMove(tab);
        }
        return false;
    }

    private boolean checkKingMove(ArrayList<Integer> tab) {
        int player = tab.get(0);
        int size = tab.size();
        boolean flag = true;
        boolean sameBefore;
        boolean sameAfter;
        boolean lastElementNotEmpty = tab.get(size - 1) != EMPTY;

        for (int i = 1; i < tab.size(); i++) {
            sameBefore = tab.get(i).equals(tab.get((i - 1) % size)) && tab.get(i) != EMPTY && tab.get((i - 1) % size) != EMPTY;
            sameAfter = tab.get(i).equals(tab.get((i + 1) % size)) && tab.get(i) != EMPTY && tab.get((i + 1) % size) != EMPTY;
            if ((player == WHITE_KING && ((tab.get(i) == WHITE || tab.get(i) == WHITE_KING) || (sameBefore || sameAfter) || (lastElementNotEmpty)))
                    || (player == BLACK_KING && ((tab.get(i) == BLACK || tab.get(i) == BLACK_KING) || (sameBefore || sameAfter) || (lastElementNotEmpty)))) {
                flag = false;
                break;
            }
        }
        return flag;
    }


    public boolean canJump(int player, int fromRow, int fromCol, int toRow, int toCol, int turn) {
        int jumpedChecker = board[(fromRow + toRow) / 2][(fromCol + toCol) / 2];
        boolean correctColumn = toCol == (fromCol + 2) || toCol == (fromCol - 2);
        boolean correctRowWhite = toRow == fromRow + 2;
        boolean correctRowBlack = toRow == fromRow - 2;
        if (board[toRow][toCol] != EMPTY) {
            return false;
        }
        if (player == WHITE && turn == 0) {
            return correctRowWhite && correctColumn && ((jumpedChecker == BLACK) || (jumpedChecker == BLACK_KING));
        } else if (player == BLACK && turn == 1) {
            return correctRowBlack && correctColumn && ((jumpedChecker == WHITE) || (jumpedChecker == WHITE_KING));
        }
        return false;
    }

    private void kingMove(int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = EMPTY;
        boolean movingRowUp = fromRow > toRow;
        boolean movingColLeft = fromCol > toCol;
        boolean movingRowDown = fromRow < toRow;
        boolean movingColRight = fromCol < toCol;
        int colPosition = fromCol;
        if (movingRowUp && movingColLeft) { // lewy_gorny
            for (int i = fromRow; i > toRow; i--) {
                colPosition = removeDown(colPosition, i);
            }
        }
        if (movingRowUp && movingColRight) { // prawy_gorny
            for (int i = fromRow; i > toRow; i--) {
                colPosition = removeUp(colPosition, i);
            }
        }
        if (movingRowDown && movingColRight) { // prawy_dolny
            for (int i = fromRow; i < toRow; i++) {
                colPosition = removeUp(colPosition, i);
            }
        }
        if (movingRowDown && movingColLeft) { // lewy_dolny
            for (int i = fromRow; i < toRow; i++) {
                colPosition = removeDown(colPosition, i);
            }
        }
    }

    private int removeDown(int colPosition, int i) {
        board[i][colPosition] = EMPTY;
        colPosition--;
        return colPosition;
    }

    private int removeUp(int colPosition, int i) {
        board[i][colPosition] = EMPTY;
        colPosition++;
        return colPosition;
    }

    public int isOver() {
        ArrayList<Integer> wholeBoard = new ArrayList<>();
        for (int[] row : board) {
            for (int element : row) {
                wholeBoard.add(element);
            }
        }
        if (!wholeBoard.contains(BLACK) && !wholeBoard.contains(BLACK_KING)) {
            return 0;
        } else if (!wholeBoard.contains(WHITE) && !wholeBoard.contains(WHITE_KING)) {
            return 1;
        } else {
            return -1;
        }
    }
}


