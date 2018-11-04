package checkers;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
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
    int selectedRow, selectedCol;   // sluzy do przechowania na ktore miejsce sie ruszyl gracz
    private static Screen screen;

//    static {
//        try {
//            screen = new DefaultTerminalFactory().createScreen();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public CheckersBoard() throws IOException, InterruptedException { // konstruktor
        screen = new DefaultTerminalFactory().createScreen();
        ChekersData board = new ChekersData(); // nasza plansza pomocnicza
        doNewGame();
        screen.startScreen();
        TextGraphics TG = screen.newTextGraphics();
        screen.doResizeIfNecessary();
        printBoard(board.getBoard());
        screen.refresh();

    }

    public void closeGame() throws IOException {
        if (screen != null)
            screen.stopScreen();
    }

    public static void printBoard(int[][] board) throws IOException {
//        screen.startScreen();
//        screen.doResizeIfNecessary();
        System.out.println(board[0][0]);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                //if ( row % 2 == col % 2 ) {
                if ( board[row][col] == 1) {
                    printFirstChecker(col * 6,row* 3);
                }
                else if (board[row][col]==2){
                    printSecoundChecker(col * 6, row * 3);
                }
                else {
                    printEmptyField(col * 6,row * 3);
                }
            }
        }
    screen.refresh();
    }

    //Ustawia poczatkowe parametry rozgrywki
    //glownie zmienne pomocnicze: czyj ruch
    // i pionki na mapie

    void doNewGame(){
        gameInProgress = true;
        selectedCol = -1;
        selectedRow = -1;

    }

    public static void printFirstChecker(int row, int col) {
        for (int i = row; i < row + 6; i++) {
            for (int j = col; j < col + 3; j++) {

                if (i % 6 < 2 || i % 6 > 3) {
                    if (j % 3 == 0)
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(245, 195, 121), new TextColor.RGB(245, 195, 121)));
                    else if (j % 3 == 2)
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(245, 195, 121), new TextColor.RGB(245, 195, 121)));
                    else
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(168, 168, 168), new TextColor.RGB(168, 168, 168)));
                } else {
                    screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(168, 168, 168), new TextColor.RGB(168, 168, 168)));
                }
            }
        }
    }

    public static void printEmptyField(int r, int c){
        for (int i = r; i < r + 6; i++) {
            for (int j = c; j < c + 3; j++) {
                screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(245, 195, 121), new TextColor.RGB(245, 195, 121)));
            }
        }
    }

    public static void printSecoundChecker(int row, int col) {
        for (int i = row; i < row + 6; i++) {
            for (int j = col; j < col + 3; j++) {

                if (i % 6 < 2 || i % 6 > 3) {
                    if (j % 3 == 0)
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(245, 195, 121), new TextColor.RGB(245, 195, 121)));
                    else if (j % 3 == 2)
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(245, 195, 121), new TextColor.RGB(245, 195, 121)));
                    else
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(255, 255, 255), new TextColor.RGB(255, 255, 255)));
                } else {
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(255, 255, 255), new TextColor.RGB(255, 255, 255)));
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
