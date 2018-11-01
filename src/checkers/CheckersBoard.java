package checkers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CheckersBoard implements ActionListener, MouseListener {
    //Tu bym zrobil wyswietlanie calej planszy gry
    //i umozliwienie na reagowanie myszki - dlatego implementuje
    // dwa interfejsy ActionListener i MousListener
    //Bardzo możliwe, że coś się zmieni, po prostu teraz mam taka wizję
    //Uwaga: Czesc zmiennych tak implementuje bo moga sie przydac


    boolean gameInProgress;         // moze sie przydac
    int currentPlayer;              // czyj jest teraz ruch
    int selectedRow, selectedCol;   // sluzy do przechowania na ktore miejsce sie ruszyl gracz

    public CheckersBoard(){ // konstruktor
        ChekersData board = new ChekersData(); // nasza plansza pomocnicza
        doNewGame();
    }

    //Ustawia poczatkowe parametry rozgrywki
    //glownie zmienne pomocnicze: czyj ruch
    // i pionki na mapie

    void doNewGame(){
        gameInProgress = true;
        selectedCol = -1;
        selectedRow = -1;

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
