package checkers_swing;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import data.CheckersData;

public class CheckersBoard extends JPanel{
    CheckersData checkersData = new CheckersData();

    private Image image;
    private ImageIcon image_ico;
    private ImageIcon image_ico_pawn_white;
    private ImageIcon image_ico_pawn_black;
    private Image image_pawn_white;
    private Image image_pawn_black;
    private Point temp_point;
    ArrayList<Pawn> pawns = new ArrayList<Pawn>();
    private int[][] board;
    int xx=0, yy=0;

    public CheckersBoard() {
        board = checkersData.getBoard();

        image = new ImageIcon(getClass().getResource("/pictures/board.png")).getImage()
                .getScaledInstance(1024, 768, Image.SCALE_SMOOTH);
        image_ico = new ImageIcon(image);

        image_pawn_white = new ImageIcon(getClass().getResource("/pictures/white_pawn.png")).getImage()
                .getScaledInstance(128,96,Image.SCALE_SMOOTH);
        image_ico_pawn_white = new ImageIcon(image_pawn_white);

        image_pawn_black = new ImageIcon(getClass().getResource("/pictures/black_pawn.png")).getImage()
                .getScaledInstance(128,96,Image.SCALE_SMOOTH);
        image_ico_pawn_black = new ImageIcon(image_pawn_black);

        addPawns();
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));

        //***Listeners****
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int x,y;
                x=evt.getX();
                y=evt.getY();
                xx=x;
                yy=y;
                if (pawns.contains(new Pawn(new Point(x/128,y/96),image_ico_pawn_white))
                        || pawns.contains(new Pawn(new Point(x/128,y/96), image_ico_pawn_black))
                   )
                    System.out.println("JAKIS JESSSt!");
                System.out.println("mouseClicked - X: "+ x/128 +", Y: "+y/96);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) { }
            public void mousePressed(java.awt.event.MouseEvent evt) { }
            public void mouseExited(java.awt.event.MouseEvent evt) { }
            public void mouseReleased(java.awt.event.MouseEvent evt) { }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                System.out.println("Mouse moved X: "+e.getX()+" Y: "+e.getY());
            }

            public void mouseDragged(MouseEvent e) {
                System.out.println("Mouse DRAGGED X: "+e.getX()+" Y: "+e.getY());

            }
        });
        //*****Listeners_END****
    }

    private void addPawns() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == 1) { //white
                    pawns.add(new Pawn(new Point(col , row), image_ico_pawn_white));
                } else if (board[row][col] == 2) {
                    pawns.add(new Pawn(new Point(col, row), image_ico_pawn_black));
                } else if (board[row][col] == 3)
                    System.out.println("TU POWINIEN BYC KING");
                else if (board[row][col] == 4)
                    System.out.println("TU POWINIEN BYC KING");
            }
        }
    }


    public void paint(Graphics g) {
        super.paintComponent(g);
        image_ico.paintIcon(this, g, 0, 0);
        drawPawns(g);
    }

    public void drawPawns(Graphics g) {
        for (Pawn p : pawns) {
            p.image.paintIcon(this, g, (int) p.point.getX()*128, (int) p.point.getY()*96);
        }
    }
}
