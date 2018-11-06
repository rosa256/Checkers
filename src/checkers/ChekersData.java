package checkers;

import java.util.ArrayList;

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
                {0, 1, 0, 4, 0, 1, 0, 1},
                {3, 0, 1, 0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0, 1, 0, 1},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0},
                {2, 0, 2, 0, 0, 0, 2, 0},
                {0, 2, 0, 2, 0, 2, 0, 2},
                {2, 0, 2, 0, 2, 0, 0, 0}};
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

    public void kingMove(CheckersMove move) {
        kingMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
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

    public boolean canMove(int player, int r1, int c1, int r2, int c2, int turn) {

        if (board[r2][c2] != EMPTY) {
            System.out.println("Niedozwolony ruch#1");
            return false;
        }
        if (player == WHITE && turn == 0) {
            if (board[r1][c1] == WHITE && r2 == r1 + 1 && (c2 == (c1 + 1) || c2 == (c1 - 1))) {
                return true;
            }
            System.out.println("Niedozwolony ruch#3");
            return false;
        }
        if (player == BLACK && turn == 1) {
            if (board[r1][c1] == BLACK && r2 == r1 - 1 && (c2 == (c1 + 1) || c2 == (c1 - 1))) {
                return true;
            }
            System.out.println("Niedozwolony ruch#4");
            return false;
        }
        if (player == WHITE_KING) {
            if (board[r1][c1] == WHITE_KING && board[r2][c2] == EMPTY) {
                if (r1 > r2 && c1 > c2) {
                    int row = r1;
                    int column = c1;
                    int truth_counter = 0;
                    while (row > r2 && column > c2) {
                        if (board[row][column] == EMPTY)
                            truth_counter++;
                        row--;
                        column--;
                    }
                    if (truth_counter == r1 - r2 - 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean canKingMoveJump(int player, int rowFrom, int rowTo, int colFrom, int colTo) {
        if (player == WHITE_KING || player == BLACK_KING) {
            int row_position, col_position;
            int count = 0;
            ArrayList<Integer> tab = new ArrayList<>();
            if (rowFrom > rowTo && colFrom > colTo) { // lewy_gorny
                col_position = colFrom;
                for (int i = rowFrom; i >= rowTo; i--) {
                    tab.add(board[i][col_position]);
                    col_position--;
                }
            } else if (rowFrom > rowTo && colFrom < colTo) { // prawy_gorny
                col_position = colFrom;
                for (int i = rowFrom; i >= rowTo; i--) {
                    tab.add(board[i][col_position]);
                    col_position++;
                }
            } else if (rowFrom < rowTo && colFrom < colTo) { // prawy_dolny
                col_position = colFrom;
                for (int i = rowFrom; i <= rowTo; i++) {
                    tab.add(board[i][col_position]);
                    col_position++;
                }
            } else if (rowFrom < rowTo && colFrom > colTo) { // lewy_dolny
                col_position = colFrom;
                for (int i = rowFrom; i <= rowTo; i++) {
                    tab.add(board[i][col_position]);
                    col_position--;
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

        for (int i = 1; i < tab.size(); i++) {
            if (player == WHITE_KING) {
                if (tab.get(i) == WHITE || tab.get(i) == WHITE_KING) {
                    flag = false;
                    break;
                } else if ((tab.get(i) == tab.get((i - 1) % size) && tab.get(i) != EMPTY && tab.get((i - 1) % size) != EMPTY) || (tab.get(i) == tab.get((i + 1) % size) && tab.get(i) != EMPTY && tab.get((i + 1) % size) != EMPTY)) {
                    flag = false;
                    break;
                } else if (tab.get(size - 1) != EMPTY) {
                    flag = false;
                    break;
                }
            } else if (player == BLACK_KING) {
                if (tab.get(i) == BLACK || tab.get(i) == BLACK_KING) {
                    flag = false;
                    break;
                } else if ((tab.get(i) == tab.get((i - 1) % size) && tab.get(i) != EMPTY && tab.get((i - 1) % size) != EMPTY) || (tab.get(i) == tab.get((i + 1) % size) && tab.get(i) != EMPTY && tab.get((i + 1) % size) != EMPTY)) {
                    flag = false;
                    break;
                } else if (tab.get(size - 1) != EMPTY) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }


    public boolean canJump(int player, int r1, int c1, int r2, int c2) {
        if (board[r2][c2] != EMPTY) {
            System.out.println("Niedozwolony skok#1");
            return false;
        }
        if (player == WHITE) {
            if (board[r1][c1] == WHITE && r2 == r1 + 2 && (c2 == (c1 + 2) || c2 == (c1 - 2)) && ((board[(r1 + r2) / 2][(c1 + c2) / 2] == BLACK) || (board[(r1 + r2) / 2][(c1 + c2) / 2] == BLACK_KING))) {
                return true;
            }
            System.out.println("Niedozwolony skok#3");
            return false;
        }
        else {
            if (board[r1][c1] == BLACK && r2 == r1 - 2 && (c2 == (c1 + 2) || c2 == (c1 - 2)) && ((board[(r1 + r2) / 2][(c1 + c2) / 2] == WHITE) || (board[(r1 + r2) / 2][(c1 + c2) / 2] == WHITE_KING))) {
                return true;
            }
            System.out.println("Niedozwolony skok#4");
            return false;
        }
    }

    public void kingMove(int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = EMPTY;
        int col_position;

        if (fromRow > toRow && fromCol > toCol) { // lewy_gorny
            col_position = fromCol;
            for (int i = fromRow; i > toRow; i--) {
                board[i][col_position] = EMPTY;
                col_position--;
            }
        } else if (fromRow > toRow && fromCol < toCol) { // prawy_gorny
            col_position = fromCol;
            for (int i = fromRow; i > toRow; i--) {
                board[i][col_position] = EMPTY;
                col_position++;
            }
        } else if (fromRow < toRow && fromCol < toCol) { // prawy_dolny
            col_position = fromCol;
            for (int i = fromRow; i < toRow; i++) {
                board[i][col_position] = EMPTY;
                col_position++;
            }
        } else if (fromRow < toRow && fromCol > toCol) { // lewy_dolny
            col_position = fromCol;
            for (int i = fromRow; i < toRow; i++) {
                board[i][col_position] = EMPTY;
                col_position--;
            }
        }

    }

    int isOver() {
        ArrayList<Integer> whole_board = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                whole_board.add(board[i][j]);
            }
        }
        if (!whole_board.contains(2) && !whole_board.contains(4)) {
            return 0;
        } else if (!whole_board.contains(1) && !whole_board.contains(3)) {
            return 1;
        } else {
            return -1;
        }
    }
}


