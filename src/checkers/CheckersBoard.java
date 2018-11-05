package checkers;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class CheckersBoard implements ActionListener, MouseListener {
    //Tu bym zrobil wyswietlanie calej planszy gry
    //i umozliwienie na reagowanie myszki - dlatego implementuje
    // dwa interfejsy ActionListener i MousListener
    //Bardzo możliwe, że coś się zmieni, po prostu teraz mam taka wizję
    //Uwaga: Czesc zmiennych tak implementuje bo moga sie przydac


    boolean gameInProgress;         // moze sie przydac
    int currentPlayer;              // czyj jest teraz ruch
    int selectedRow=-1, selectedCol=-1;   // sluzy do przechowania na ktore miejsce sie ruszyl gracz
    private static Screen screen;
    int rowCursor = 0, colCursor = 0;
    static int pom_row = 0, pom_col = 0, pom = 0;

//    static {
//        try {
//            screen = new DefaultTerminalFactory().createScreen();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public CheckersBoard(TextColor.ANSI uColor1,TextColor.ANSI uColor2) throws IOException, InterruptedException { // konstruktor

        screen = new DefaultTerminalFactory().createScreen();
        ChekersData board = new ChekersData(); // nasza plansza pomocnicza
        doNewGame(board);
        screen.startScreen();

        screen.doResizeIfNecessary();
        initBoard();
        printBoard(board.getBoard(),uColor1,uColor2, selectedCol, selectedRow);
        screen.refresh();

        boolean keepRunning = true;
        while (keepRunning) {
            KeyStroke keyPressed = screen.pollInput();

            if (keyPressed != null) {
                System.out.println(keyPressed.toString());
                if (keyPressed.getKeyType() == KeyType.Backspace) {
                    keepRunning = false;
                    screen.stopScreen();
                    break;
                }else if (keyPressed.getKeyType() == KeyType.ArrowRight) {
                        pom_col = pom_col + 1;
                        initBoard();
                        printBoard(board.board,uColor1,uColor2,selectedCol,selectedRow);
                    printCursor(pom_col * 6,pom_row * 3);
                        screen.refresh();

                }else if (keyPressed.getKeyType() == KeyType.ArrowLeft) {
                        pom_col = pom_col - 1;
                        initBoard();
                        printBoard(board.board,uColor1,uColor2, selectedCol, selectedRow);
                    printCursor(pom_col * 6,pom_row * 3);
                        screen.refresh();
                }else if (keyPressed.getKeyType() == KeyType.ArrowDown) {
                    pom_row = pom_row +1;
                    initBoard();
                    printBoard(board.board,uColor1,uColor2, selectedCol, selectedRow);
                    printCursor(pom_col * 6,pom_row * 3);
                    screen.refresh();
                }else if (keyPressed.getKeyType() == KeyType.ArrowUp) {
                    pom_row = pom_row -1;
                    initBoard();
                    printBoard(board.board,uColor1,uColor2, selectedCol, selectedRow);
                    printCursor(pom_col * 6,pom_row * 3);
                    screen.refresh();
                }else if (keyPressed.getKeyType() == KeyType.Enter) {
                    selectedRow = pom_row;
                    selectedCol = pom_col;
                    initBoard();
                    printBoard(board.board,uColor1,uColor2, selectedCol, selectedRow);
                    printCursor(pom_col * 6,pom_row * 3);
                    screen.refresh();
                } else if (keyPressed.getKeyType() == KeyType.Escape){
                    selectedRow=-1;
                    selectedCol=-1;
                    initBoard();
                    printBoard(board.board,uColor1,uColor2, selectedRow, selectedCol);
                    printCursor(pom_col * 6,pom_row * 3);
                }
            }
        }

        screen.refresh();
        screen.stopScreen();
    }

    public void closeGame() throws IOException {
        if (screen != null)
            screen.stopScreen();
    }

    public static void printBoard(int[][] board, TextColor.ANSI uColor1, TextColor.ANSI uColor2, int selectedCol, int selectedRow) throws IOException {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                //if ( row % 2 == col % 2 ) {
                if ( board[row][col] == 1) {

                    printFirstChecker(col * 6,row* 3,uColor1);
                }
                else if (board[row][col]==2){
                    printSecoundChecker(col * 6, row * 3,uColor2);
                }
            }
        }
        if(selectedCol != 1 && selectedRow != -1){
            printSelectedField(selectedCol * 6,selectedRow * 3);
        }
    screen.refresh();
    }

    public static void printSelectedField(int selectedRow, int selectedCol) {
        for (int i = selectedRow; i < selectedRow+ 6; i++) {
            for (int j = selectedCol; j < selectedCol + 3; j++) {
                if (i % 6 > 1 && i % 6 < 4) {
                    if (j % 3 == 1){
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(243,58,255), new TextColor.RGB(243,58,255)));
                    }
                }
            }
        }
    }

    //Ustawia poczatkowe parametry rozgrywki
    //glownie zmienne pomocnicze: czyj ruch
    // i pionki na mapie

    void doNewGame(ChekersData board){
        gameInProgress = true;
        selectedCol = -1;
        selectedRow = -1;

    }
    public static void initBoard(){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ( row % 2 == col % 2 ) {
                    printWhiteField(col * 6,row* 3);
                }
                else {
                    printBrownField(col * 6,row * 3);
                }
            }
        }
    }

    public static void printFirstChecker(int row, int col,TextColor.ANSI uCol1) {
        for (int i = row; i < row + 6; i++) {
            for (int j = col; j < col + 3; j++) {

                if (i % 6 < 2 || i % 6 > 3) {
                    if (j % 3 == 0){
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(151,102,59), new TextColor.RGB(151,102,59)));
                    }else if (j % 3 == 2)
           /*brazowy*/    screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(151,102,59), new TextColor.RGB(151,102,59)));
                    else
                        screen.setCharacter(i, j, new TextCharacter(' ', uCol1, uCol1));
                } else {
             /*szary*/ screen.setCharacter(i, j, new TextCharacter(' ', uCol1, uCol1));
                }
            }
        }
    }

    public static void printWhiteField(int r, int c){
        for (int i = r; i < r + 6; i++) {
            for (int j = c; j < c + 3; j++) {
  /*bialy*/    screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(255,255,255),new TextColor.RGB(255,255,255)));
            }
        }
    }


    public static void printCursor(int row, int col){
        for (int i = row; i < row + 6; i++) {
            for (int j = col; j < col + 3; j++) {
                if(j%3==0 || j%3==2 || ((i%6 == 0 || i%6 ==5) && j%3==1))
                    screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(243,58,255),new TextColor.RGB(243,58,255)));
            }
        }
    }


    public static void printBrownField(int r, int c){
        for (int i = r; i < r + 6; i++) {
            for (int j = c; j < c + 3; j++) {
/*brazowy*/    screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(151,102,59), new TextColor.RGB(151,102,59)));
            }
        }
    }

    public static void printSecoundChecker(int row, int col,TextColor.ANSI uCol2) {
        for (int i = row; i < row + 6; i++) {
            for (int j = col; j < col + 3; j++) {

                if (i % 6 < 2 || i % 6 > 3) {
                    if (j % 3 == 0)
         /*brazowy*/    screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(151,102,59), new TextColor.RGB(151,102,59)));
                    else if (j % 3 == 2)
         /*brazowy*/    screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(151,102,59), new TextColor.RGB(151,102,59)));
                    else
                        screen.setCharacter(i, j, new TextCharacter(' ', uCol2, uCol2));
                } else {
                        screen.setCharacter(i, j, new TextCharacter(' ', uCol2, uCol2));
                }
            }
        }
    }

    //zeby nie bawic sie w animacje przenoszenia pionka z jednego pola na drugie
    //latiwej chyba bedzie 1.nacisnac myszka pole na ktore chce sie ruszyc po czym wybrac pionek ktory ma sie w tamto miejsce ruszyc
    // oczywiscie bedzie trzeba to obsluzyc pod wzgledem poprawnosci ruchu
    public void mousePressed(MouseEvent e) {
        int col = e.getX();
        int row = e.getY();
    }


    public void actionPerformed(ActionEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}
