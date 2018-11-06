package checkers;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CheckersBoard {
    //Tu bym zrobil wyswietlanie calej planszy gry
    //i umozliwienie na reagowanie myszki - dlatego implementuje
    // dwa interfejsy ActionListener i MousListener
    //Bardzo możliwe, że coś się zmieni, po prostu teraz mam taka wizję
    //Uwaga: Czesc zmiennych tak implementuje bo moga sie przydac


    boolean gameInProgress;         // moze sie przydac
    int enterCount;              // czyj jest teraz ruch
    int selectedRowFrom, selectedColFrom;   // sluzy do przechowania na ktore miejsce sie ruszyl gracz
    int selectedRowTo, selectedColTo;   // sluzy do przechowania na ktore miejsce sie ruszyl gracz
    private static Screen screen;
    static int pom_row, pom_col;
    static TextGraphics TG;
    static TimeCounter tc;
    static int turn;
    static ChekersData board;


    public CheckersBoard(TextColor.ANSI uColor1, TextColor.ANSI uColor2, String name1, String name2) throws IOException, InterruptedException { // konstruktor
        selectedRowTo = -1;
        selectedColTo = -1;
        selectedRowFrom = -1;
        selectedColFrom = -1;
        enterCount = 0;
        pom_row = 0;
        pom_col = 0;
        turn = 0;
        screen = new DefaultTerminalFactory().createScreen();
        board = new ChekersData(); // nasza plansza pomocnicza
        doNewGame(board);
        screen.startScreen();
        tc = new TimeCounter();
        TG = screen.newTextGraphics();
        screen.doResizeIfNecessary();
        initBoard();
        printBoard(board.getBoard(),uColor1,uColor2, selectedRowFrom, selectedColFrom);
        printCursor(0,0);
        screen.refresh();

        boolean keepRunning = true;
        while (keepRunning) {
            KeyStroke keyPressed = screen.pollInput();
            printInterface(uColor1, uColor2, TG, name1, name2);
            if (keyPressed != null) {
                System.out.println(keyPressed.toString());
                if (keyPressed.getKeyType() == KeyType.Backspace || keyPressed.getKeyType() == KeyType.EOF) {
                    screen.stopScreen();
                    break;
                }else if (keyPressed.getKeyType() == KeyType.ArrowRight && pom_col < 7) {
                        pom_col = pom_col + 1;
                        initBoard();
                        printBoard(board.getBoard(),uColor1,uColor2, selectedRowFrom, selectedColFrom);
                    printCursor(pom_col * 6, pom_row * 3);
                        screen.refresh();

                }else if (keyPressed.getKeyType() == KeyType.ArrowLeft && pom_col > 0) {
                        pom_col = pom_col - 1;
                        initBoard();
                        printBoard(board.getBoard(),uColor1,uColor2, selectedRowFrom, selectedColFrom);
                    printCursor(pom_col * 6,pom_row * 3);
                        screen.refresh();
                }else if (keyPressed.getKeyType() == KeyType.ArrowDown && pom_row < 7) {
                    pom_row = pom_row +1;
                    initBoard();
                    printBoard(board.getBoard(),uColor1,uColor2, selectedRowFrom, selectedColFrom);
                    printCursor(pom_col * 6,pom_row * 3);
                    screen.refresh();
                }else if (keyPressed.getKeyType() == KeyType.ArrowUp && pom_row > 0) {
                    pom_row = pom_row -1;
                    initBoard();
                    printBoard(board.getBoard(),uColor1,uColor2, selectedRowFrom, selectedColFrom);
                    printCursor(pom_col * 6,pom_row * 3);
                    screen.refresh();
                }else if (keyPressed.getKeyType() == KeyType.Enter) {
                    if(enterCount==0) {
                        selectedRowFrom = pom_row;
                        selectedColFrom = pom_col;

                        enterCount++;
                    }else if(enterCount==1) {
                        selectedRowTo = pom_row;
                        selectedColTo = pom_col;
                        System.out.println("FromRow:"+selectedRowFrom);
                        System.out.println("FromCol:"+selectedColFrom);
                        System.out.println("ToRow:"+selectedRowTo);
                        System.out.println("ToCol:"+selectedColTo);
                        enterCount++;
                        System.out.println(board.getBoard()[0][3]);
                        System.out.println(board.getBoard()[7][4]);
                        int current_checker = board.getBoard()[selectedRowFrom][selectedColFrom];
                        if (current_checker != 0) { // zaznaczone pole != EMPTY

                            CheckersMove myMove = new CheckersMove(selectedRowFrom,selectedColFrom,selectedRowTo,selectedColTo);
                            if ((selectedRowFrom + 1 == selectedRowTo || selectedRowFrom - 1 == selectedRowTo) && (current_checker == 1 || current_checker == 2)) {
                                if (board.canMove(current_checker, selectedRowFrom, selectedColFrom, selectedRowTo, selectedColTo, turn)) {
                                    board.makeMove(myMove);
                                    if (turn == 0) {
                                        turn = 1;
                                    } else if (turn == 1) {
                                        turn = 0;
                                    }
                                    System.out.println("Move");
                                }
                            } else if ((selectedRowFrom + 2 == selectedRowTo || selectedRowFrom - 2 == selectedRowTo) && (current_checker == 1 || current_checker == 2)) {
                                if (board.canJump(current_checker, selectedRowFrom, selectedColFrom, selectedRowTo, selectedColTo, turn)) {
                                    board.makeMove(myMove);
                                    if (turn == 0) {
                                        turn = 1;
                                    } else if (turn == 1) {
                                        turn = 0;
                                    }
                                    System.out.println("Jump");
                                }
                            } else if (current_checker == 3) {
                                if (board.canKingMoveJump(current_checker, selectedRowFrom, selectedRowTo, selectedColFrom, selectedColTo, turn)) {
                                    System.out.println("Damka ruszyla sie");
                                    board.kingMove(myMove);
                                    if (turn == 0) {
                                        turn = 1;
                                    } else if (turn == 1) {
                                        turn = 0;
                                    }
                                } else System.out.println("NI HU JA");
                            } else if (current_checker == 4) {
                                if (board.canKingMoveJump(current_checker, selectedRowFrom, selectedRowTo, selectedColFrom, selectedColTo, turn)) {
                                    System.out.println("Damka ruszyla sie");
                                    board.kingMove(myMove);
                                    if (turn == 0) {
                                        turn = 1;
                                    } else if (turn == 1) {
                                        turn = 0;
                                    }
                                } else System.out.println("NI HU JA");
                            }
                        }
                    }

                    if (enterCount==2) {
                        selectedRowFrom = -1;
                        selectedColFrom = -1;
                        selectedRowTo = -1;
                        selectedColTo = -1;
                        enterCount = 0;
                    }
                    initBoard();
                    printBoard(board.getBoard(),uColor1,uColor2, selectedRowFrom, selectedColFrom);
                    printCursor(pom_col * 6,pom_row * 3);
                    screen.refresh();

                } else if (keyPressed.getKeyType() == KeyType.Escape){
                    selectedRowFrom = -1;
                    selectedColFrom = -1;
                    selectedRowTo = -1;
                    selectedColTo = -1;
                    enterCount = 0;
                    initBoard();
                    printBoard(board.getBoard(),uColor1,uColor2, selectedRowFrom, selectedColFrom);
                    printCursor(pom_col * 6,pom_row * 3);
                    screen.refresh();
                }
                if (board.isOver() == 0) {
                    System.out.println("Wygral bialy");
                } else if (board.isOver() == 1) {
                    System.out.println("Wygral czarny");
                }
            }
            screen.refresh();
        }

        screen.refresh();
        screen.stopScreen();
    }

    public static void printInterface(TextColor.ANSI uColor1, TextColor.ANSI uColor2, TextGraphics tg, String username1, String username2) {
        tg.drawRectangle(new TerminalPosition(48, 0), new TerminalSize(screen.getTerminalSize().getColumns() - 48, screen.getTerminalSize().getRows()), new TextCharacter(Symbols.BLOCK_MIDDLE, new TextColor.RGB(132, 216, 99), new TextColor.RGB(10, 10, 10)));
        tg.putString(new TerminalPosition(49, 1), "Gracz 1: ", SGR.BOLD);
        if (turn == 0 && board.isOver() != 1)
            tg.putString(new TerminalPosition(49, 2), "Twoja tura!", SGR.BOLD);
        else if (turn == 0 && board.isOver() == 1) {
            tg.putString(new TerminalPosition(49, 3), "Wygrałeś!", SGR.BOLD);
            tg.putString(new TerminalPosition(49, 4), "Naciśnij backspace aby wrócić\n do menu", SGR.BOLD);
            tg.putString(new TerminalPosition(49, 5), "do menu", SGR.BOLD);
        } else
            tg.drawLine(new TerminalPosition(49, 2), new TerminalPosition(screen.getTerminalSize().getColumns() - 2, 2), new TextCharacter(' ', TextColor.ANSI.BLACK, TextColor.ANSI.BLACK));
        tg.setForegroundColor(uColor1);
        tg.putString(new TerminalPosition(58, 1), username1, SGR.BOLD, SGR.ITALIC);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        if (turn == 1 && board.isOver() != 0)
            tg.putString(new TerminalPosition(49, 21), "Twoja tura!", SGR.BOLD);
        else if (turn == 1 && board.isOver() == 0) {
            tg.putString(new TerminalPosition(49, 18), "Wygrałeś!", SGR.BOLD);
            tg.putString(new TerminalPosition(49, 19), "Naciśnij backspace aby wrócić\n do menu", SGR.BOLD);
            tg.putString(new TerminalPosition(49, 20), "do menu", SGR.BOLD);
        } else {
            tg.drawLine(new TerminalPosition(49, 21), new TerminalPosition(screen.getTerminalSize().getColumns() - 2, 21), new TextCharacter(' ', TextColor.ANSI.BLACK, TextColor.ANSI.BLACK));
        }
        tg.putString(new TerminalPosition(49, 22), "Gracz 2: ", SGR.BOLD);
        tg.setForegroundColor(uColor2);
        tg.putString(new TerminalPosition(58, 22), username2, SGR.BOLD, SGR.ITALIC);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        Date date = new Date(tc.getElapsedTime());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatted = formatter.format(date);
        tg.putString(new TerminalPosition(60, 10), formatted);
    }

    public static void printBoard(int[][] board, TextColor.ANSI uColor1, TextColor.ANSI uColor2, int selectedRowFrom, int selectedColFrom) throws IOException {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ( board[row][col] == 1) {
                    printFirstChecker(col * 6,row* 3,uColor1);
                }
                else if (board[row][col]==2){
                    printSecoundChecker(col * 6, row * 3,uColor2);
                }
                else if( board[row][col] == 3)
                    printFirstKing(col * 6, row * 3,uColor1);
                else if( board[row][col] == 4)
                    printSecoundKing(col * 6, row * 3,uColor2);
            }
        }
        if(selectedColFrom != -1 && selectedRowFrom != -1){
            printSelectedField(selectedColFrom * 6,selectedRowFrom * 3);
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
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(56, 56, 56), new TextColor.RGB(56, 56, 56)));
                    }else if (j % 3 == 2)
                        /*brazowy*/
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(56, 56, 56), new TextColor.RGB(56, 56, 56)));
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

    public static void printFirstKing(int row, int col, TextColor.ANSI uCol1){
        for (int i = row; i < row + 6; i++) {
            for (int j = col; j < col + 3; j++) {
                if(j%3==2)
                screen.setCharacter(i, j, new TextCharacter(' ', uCol1, uCol1));
                else if ((j%3==1 && (i%6>0 && i%6< 5))||((j%3==0 && (i%6>1 && i%6< 4))))
                screen.setCharacter(i, j, new TextCharacter(' ', uCol1, uCol1));
            }
        }
    }

    public static void printSecoundKing(int row, int col, TextColor.ANSI uCol2){
        for (int i = row; i < row + 6; i++) {
            for (int j = col; j < col + 3; j++) {
                if(j%3==2)
                    screen.setCharacter(i, j, new TextCharacter(' ', uCol2, uCol2));
                else if (j%3==1 && (i%6>0 && i%6< 5))
                    screen.setCharacter(i, j, new TextCharacter(' ', uCol2, uCol2));
                else if (j%3==0 && (i%6>1 && i%6< 4))
                    screen.setCharacter(i, j, new TextCharacter(' ', uCol2, uCol2));
            }
        }
    }


    public static void printBrownField(int r, int c){
        for (int i = r; i < r + 6; i++) {
            for (int j = c; j < c + 3; j++) {
                /*brazowy*/
                screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(56, 56, 56), new TextColor.RGB(56, 56, 56)));
            }
        }
    }

    public static void printSecoundChecker(int row, int col,TextColor.ANSI uCol2) {
        for (int i = row; i < row + 6; i++) {
            for (int j = col; j < col + 3; j++) {

                if (i % 6 < 2 || i % 6 > 3) {
                    if (j % 3 == 0)
                        /*brazowy*/
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(56, 56, 56), new TextColor.RGB(56, 56, 56)));
                    else if (j % 3 == 2)
                        /*brazowy*/
                        screen.setCharacter(i, j, new TextCharacter(' ', new TextColor.RGB(56, 56, 56), new TextColor.RGB(56, 56, 56)));
                    else
                        screen.setCharacter(i, j, new TextCharacter(' ', uCol2, uCol2));
                } else {
                        screen.setCharacter(i, j, new TextCharacter(' ', uCol2, uCol2));
                }
            }
        }
    }
}
