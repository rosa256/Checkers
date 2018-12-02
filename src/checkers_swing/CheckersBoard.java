package checkers_swing;
import data.CheckersMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.CheckersData;

public class CheckersBoard extends JPanel{
    CheckersData checkersData = new CheckersData();

    private Image image;
    private ImageIcon image_ico;
    private ImageIcon image_ico_pawn_white;
    private ImageIcon image_ico_pawn_black;
    private Image image_pawn_white;
    private Image image_pawn_black;
    private Pawn temp_pawn =new Pawn();
    private Pawn pressed_pawn=null;
    private Pawn printed_pawn=null;
    private Pawn dragged_pawn=null;

    HashMap<Point, Pawn> pawns = new HashMap<>();
    private CheckersData board;
    private int turn;
    int xx=0, yy=0;

    public CheckersBoard() {
        board = new CheckersData();

        image = new ImageIcon(getClass().getResource("/pictures/board.png")).getImage()
                .getScaledInstance(1024, 768, Image.SCALE_SMOOTH);
        image_ico = new ImageIcon(image);

        image_pawn_white = new ImageIcon(getClass().getResource("/pictures/white_pawn.png")).getImage()
                .getScaledInstance(128,96,Image.SCALE_SMOOTH);
        image_ico_pawn_white = new ImageIcon(image_pawn_white);

        image_pawn_black = new ImageIcon(getClass().getResource("/pictures/black_pawn.png")).getImage()
                .getScaledInstance(128,96,Image.SCALE_SMOOTH);
        image_ico_pawn_black = new ImageIcon(image_pawn_black);

        addPawns(board.getBoard());
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));

        //***Listeners****
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (pawns.containsKey(new Point(evt.getX()/128,evt.getY()/96)))
                    System.out.println("JAKIS JESSSt!");
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {

                if (pawns.containsKey(new Point((evt.getX()/128),evt.getY()/96))) {
                    pressed_pawn = pawns.get(new Point((evt.getX()/128), evt.getY()/96));
                    System.out.println("Wzialem pionek");
                    printed_pawn = new Pawn(new Point(evt.getX(),evt.getY()),image_ico_pawn_white);
                }
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(pressed_pawn!=null) {
                    dragged_pawn = new Pawn(new Point((evt.getX()/128),evt.getY()/96), pressed_pawn.getImage());

                    int selectedRowFrom = pressed_pawn.point.y;
                    int selectedColFrom = pressed_pawn.point.x;
                    int selectedRowTo = dragged_pawn.point.y;
                    int selectedColTo = dragged_pawn.point.x;
                    System.out.println("CF"+selectedColFrom+" RF"+selectedRowFrom+" CT"+selectedColTo+" RT"+selectedRowTo);

                    int currentChecker = board.getBoard()[selectedRowFrom][selectedColFrom];
                    CheckersMove myMove = new CheckersMove(selectedRowFrom, selectedColFrom, selectedRowTo, selectedColTo);

                    if ((selectedRowFrom + 1 == selectedRowTo || selectedRowFrom - 1 == selectedRowTo) && (currentChecker == CheckersData.WHITE || currentChecker == CheckersData.BLACK)) {
                        if (board.canMove(currentChecker, selectedRowFrom, selectedColFrom, selectedRowTo, selectedColTo, turn)) {
                            board.makeMove(myMove);
                            pawns.remove(pressed_pawn.point);//usuwam stary
                            pawns.put(dragged_pawn.point,dragged_pawn);//wkladamy nowy
                            changeTurn();
                        }
                    } else if ((selectedRowFrom + 2 == selectedRowTo || selectedRowFrom - 2 == selectedRowTo) && (currentChecker == 1 || currentChecker == 2)) {
                        if (board.canJump(currentChecker, selectedRowFrom, selectedColFrom, selectedRowTo, selectedColTo, turn)) {
                            board.makeMove(myMove);
                            int jumpRow = (selectedRowFrom + selectedRowTo) / 2;
                            int jumpCol = (selectedColFrom + selectedColTo) / 2;
                            pawns.remove(new Point(jumpCol,jumpRow));

                            pawns.remove(pressed_pawn.point);//usuwam stary
                            pawns.put(dragged_pawn.point,dragged_pawn);//wkladamy nowy
                            changeTurn();

                        }
                    } else if ((currentChecker == CheckersData.WHITE_KING || currentChecker == CheckersData.BLACK_KING)
                            && board.canKingMoveJump(currentChecker, selectedRowFrom, selectedRowTo, selectedColFrom, selectedColTo, turn)) {
                        board.kingMove(myMove);
                        pawns.remove(pressed_pawn.point);//usuwam stary
                        pawns.put(dragged_pawn.point,dragged_pawn);//wkladamy nowy
                        changeTurn();
                    }
                    System.out.println("From: "+pressed_pawn.toString()+" To: "+ dragged_pawn.toString());
                }

                    System.out.println("Mouse Release");
                    repaint();
                    pressed_pawn = null;
                    printed_pawn = null;

            }
            public void mouseEntered(java.awt.event.MouseEvent evt) { }
            public void mouseExited(java.awt.event.MouseEvent evt) { }
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) { }
            public void mouseDragged(MouseEvent e) {

                if(pressed_pawn!=null) {
                    //pawns.put(pressed_pawn.point, new Pawn(new Point(e.getX() / 128, e.getY() / 96), pressed_pawn.getImage()));
                    printed_pawn.setP(e.getX() - 60, e.getY() - 60);
                    printed_pawn.setImage(pressed_pawn.getImage());
                    repaint();
                }

            }
        });
        //*****Listeners_END****
    }

    private void addPawns(int[][] board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == 1) { //white
                    pawns.put(new Point(col , row), new Pawn(new Point(col, row), image_ico_pawn_white));
                } else if (board[row][col] == 2) {
                    pawns.put(new Point(col , row), new Pawn(new Point(col, row), image_ico_pawn_black));
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
        if(printed_pawn!=null)
        printed_pawn.image.paintIcon(this,g,(int)printed_pawn.point.getX(),(int)printed_pawn.point.getY());
    }

    public void drawPawns(Graphics g) {
        for (Map.Entry<Point, Pawn> e : pawns.entrySet()) {
            e.getValue().image.paintIcon(this, g, (int) e.getValue().point.getX()*128, (int) e.getValue().point.getY()*96);
        }
    }

    private void changeTurn() {
        if (turn == 0) {
            turn = 1;
        } else if (turn == 1) {
            turn = 0;
        }
    }
}
