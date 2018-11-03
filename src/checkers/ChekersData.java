package checkers;

public class ChekersData {
    // Klasa ktora przechowuje informacje o Białych i Czarnych pionkach.
    // Informacje o polozeniu pionkow na mapie itp. rozwinie się jeszcze

    // Białe Pionki to 1 a czarne to 2 i to będzie na tablicy intow
    public static final int EMPTY = 0, WHITE = 1, BLACK = 2;


    private int board [][];     // Mapa intow - tak naprawde to bedzie nasza plansza pomocnicza - board[r][c]

    public ChekersData(){       //Konstruktor
        board = new int[8][8];  // Nasza plansza
        setUpGame();            //Funkcja ktora ustawi pionki na planszy
    }

    public void setUpGame(){
        board = new int[][]{{0, 2, 0, 2, 0, 2, 0, 2},
                {2, 0, 2, 0, 2, 0, 2, 0},
                {0, 2, 0, 2, 0, 2, 0, 2},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0}};
    } //tworzy czystą planszę z poustawianymi pionkami
}
