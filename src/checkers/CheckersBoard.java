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

class CheckersBoard {
    //Tu bym zrobil wyswietlanie calej planszy gry
    //i umozliwienie na reagowanie myszki - dlatego implementuje
    // dwa interfejsy ActionListener i MousListener
    //Bardzo możliwe, że coś się zmieni, po prostu teraz mam taka wizję
    //Uwaga: Czesc zmiennych tak implementuje bo moga sie przydac

    private Screen screen;
    private TimeCounter timeCounter;
    private int turn;
    private CheckersData board;


    CheckersBoard(TextColor.ANSI uColor1, TextColor.ANSI uColor2, String name1, String name2) throws IOException { // konstruktor
        int selectedRowTo;
        int selectedColTo;
        int selectedRowFrom = -1;
        int selectedColFrom = -1;
        int enterCount = 0;
        int pomRow = 0;
        int pomCol = 0;
        turn = 0;
        screen = new DefaultTerminalFactory().createScreen();
        board = new CheckersData(); // nasza plansza pomocnicza
        screen.startScreen();
        timeCounter = new TimeCounter();
        TextGraphics textGraphics = screen.newTextGraphics();
        screen.doResizeIfNecessary();
        initBoard();
        printBoard(board.getBoard(), uColor1, uColor2, selectedRowFrom, selectedColFrom);
        printCursor(0, 0);
        screen.refresh();

        while (true) {
            KeyStroke keyPressed = screen.pollInput();
            printInterface(uColor1, uColor2, textGraphics, name1, name2);
            if (keyPressed != null) {

                if (keyPressed.getKeyType() == KeyType.Backspace || keyPressed.getKeyType() == KeyType.EOF) {
                    screen.stopScreen();
                    break;
                } else if (keyPressed.getKeyType() == KeyType.ArrowRight && pomCol < 7) {
                    pomCol = pomCol + 1;
                    refreshBoard(uColor1, uColor2, selectedRowFrom, selectedColFrom, pomRow, pomCol);

                } else if (keyPressed.getKeyType() == KeyType.ArrowLeft && pomCol > 0) {
                    pomCol = pomCol - 1;
                    refreshBoard(uColor1, uColor2, selectedRowFrom, selectedColFrom, pomRow, pomCol);
                } else if (keyPressed.getKeyType() == KeyType.ArrowDown && pomRow < 7) {
                    pomRow = pomRow + 1;
                    refreshBoard(uColor1, uColor2, selectedRowFrom, selectedColFrom, pomRow, pomCol);
                } else if (keyPressed.getKeyType() == KeyType.ArrowUp && pomRow > 0) {
                    pomRow = pomRow - 1;
                    refreshBoard(uColor1, uColor2, selectedRowFrom, selectedColFrom, pomRow, pomCol);
                } else if (keyPressed.getKeyType() == KeyType.Enter) {
                    if (enterCount == 0) {
                        selectedRowFrom = pomRow;
                        selectedColFrom = pomCol;
                        enterCount++;
                    } else if (enterCount == 1) {
                        selectedRowTo = pomRow;
                        selectedColTo = pomCol;
                        enterCount++;


                        int currentChecker = board.getBoard()[selectedRowFrom][selectedColFrom];
                        if (currentChecker != CheckersData.EMPTY) { // zaznaczone pole != EMPTY

                            CheckersMove myMove = new CheckersMove(selectedRowFrom, selectedColFrom, selectedRowTo, selectedColTo);
                            if ((selectedRowFrom + 1 == selectedRowTo || selectedRowFrom - 1 == selectedRowTo) && (currentChecker == CheckersData.WHITE || currentChecker == CheckersData.BLACK)) {
                                if (board.canMove(currentChecker, selectedRowFrom, selectedColFrom, selectedRowTo, selectedColTo, turn)) {
                                    board.makeMove(myMove);
                                    changeTurn();

                                }
                            } else if ((selectedRowFrom + 2 == selectedRowTo || selectedRowFrom - 2 == selectedRowTo) && (currentChecker == 1 || currentChecker == 2)) {
                                if (board.canJump(currentChecker, selectedRowFrom, selectedColFrom, selectedRowTo, selectedColTo, turn)) {
                                    board.makeMove(myMove);
                                    changeTurn();

                                }
                            } else if ((currentChecker == CheckersData.WHITE_KING || currentChecker == CheckersData.BLACK_KING)
                                    && board.canKingMoveJump(currentChecker, selectedRowFrom, selectedRowTo, selectedColFrom, selectedColTo, turn)) {
                                board.kingMove(myMove);
                                changeTurn();
                            }
                        }
                    }
                    if (enterCount == 2) {
                        selectedRowFrom = -1;
                        selectedColFrom = -1;
                        enterCount = 0;
                    }
                    refreshBoard(uColor1, uColor2, selectedRowFrom, selectedColFrom, pomRow, pomCol);

                } else if (keyPressed.getKeyType() == KeyType.Escape) {
                    selectedRowFrom = -1;
                    selectedColFrom = -1;
                    enterCount = 0;
                    refreshBoard(uColor1, uColor2, selectedRowFrom, selectedColFrom, pomRow, pomCol);
                }
            }
            screen.refresh();
        }
        screen.refresh();
        screen.stopScreen();
    }

    private void refreshBoard(TextColor.ANSI uColor1, TextColor.ANSI uColor2, int selectedRowFrom, int selectedColFrom, int pomRow, int pomCol) throws IOException {
        initBoard();
        printBoard(board.getBoard(), uColor1, uColor2, selectedRowFrom, selectedColFrom);
        printCursor(pomCol * 6, pomRow * 3);
        screen.refresh();
    }

    private void changeTurn() {
        if (turn == 0) {
            turn = 1;
        } else if (turn == 1) {
            turn = 0;
        }
    }

    private void printInterface(TextColor.ANSI uColor1, TextColor.ANSI uColor2, TextGraphics tg, String username1, String username2) {
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
        Date date = new Date(timeCounter.getElapsedTime());
        String formatted = getFormattedTime(date);
        tg.putString(new TerminalPosition(60, 10), formatted);
    }

    private String getFormattedTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    private void printBoard(int[][] board, TextColor.ANSI uColor1, TextColor.ANSI uColor2, int selectedRowFrom, int selectedColFrom) throws IOException {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == 1) {
                    printChecker(col * 6, row * 3, uColor1);
                } else if (board[row][col] == 2) {
                    printChecker(col * 6, row * 3, uColor2);
                } else if (board[row][col] == 3)
                    printKing(col * 6, row * 3, uColor1);
                else if (board[row][col] == 4)
                    printKing(col * 6, row * 3, uColor2);
            }
        }
        if (selectedColFrom != -1 && selectedRowFrom != -1) {
            printSelectedField(selectedColFrom * 6, selectedRowFrom * 3);
        }
        screen.refresh();
    }

    private void printSelectedField(int selectedCol, int selectedRow) {
        TextColor.RGB pink = new TextColor.RGB(243, 58, 255);
        for (int i = selectedCol; i < selectedCol + 6; i++) {
            for (int j = selectedRow; j < selectedRow + 3; j++) {
                if ((i % 6 > 1 && i % 6 < 4) && j % 3 == 1) {
                    screen.setCharacter(i, j, new TextCharacter(' ', pink, pink));
                }
            }
        }
    }

    //Ustawia poczatkowe parametry rozgrywki
    //glownie zmienne pomocnicze: czyj ruch
    // i pionki na mapie

    private void initBoard() {
        TextColor.RGB white = new TextColor.RGB(255, 255, 255);
        TextColor.RGB gray = new TextColor.RGB(56, 56, 56);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2) {
                    printField(col * 6, row * 3, white);
                } else {
                    printField(col * 6, row * 3, gray);
                }
            }
        }
    }

    private void printChecker(int col, int row, TextColor.ANSI uColor) {
        for (int i = col; i < col + 6; i++) {
            for (int j = row; j < row + 3; j++) {
                if (i % 6 < 2 || i % 6 > 3) {
                    if (j % 3 != 0 && j % 3 != 2)
                        screen.setCharacter(i, j, new TextCharacter(' ', uColor, uColor));
                } else {
                    screen.setCharacter(i, j, new TextCharacter(' ', uColor, uColor));
                }
            }
        }
    }


    private void printField(int col, int row, TextColor.RGB color) {
        for (int i = col; i < col + 6; i++) {
            for (int j = row; j < row + 3; j++) {
                screen.setCharacter(i, j, new TextCharacter(' ', color, color));
            }
        }
    }


    private void printCursor(int col, int row) {
        TextColor.RGB pink = new TextColor.RGB(243, 58, 255);
        for (int i = col; i < col + 6; i++) {
            for (int j = row; j < row + 3; j++) {
                if (j % 3 == 0 || j % 3 == 2 || ((i % 6 == 0 || i % 6 == 5) && j % 3 == 1))
                    screen.setCharacter(i, j, new TextCharacter(' ', pink, pink));
            }
        }
    }

    private void printKing(int col, int row, TextColor.ANSI uColor) {
        for (int i = col; i < col + 6; i++) {
            for (int j = row; j < row + 3; j++) {
                if ((j % 3 == 2) || ((j % 3 == 1 && (i % 6 > 0 && i % 6 < 5)) || (j % 3 == 0 && (i % 6 > 1 && i % 6 < 4)))) {
                    screen.setCharacter(i, j, new TextCharacter(' ', uColor, uColor));
                }
            }
        }
    }
}
