package checkers_swing;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class Game {

    private JFrame mainFrame;
    private String name1 = "Gracz 1";
    private String name2 = "Gracz 2";
    private Color color1 = Color.BLACK;
    private Color color2 = Color.YELLOW;

    Game() {
        mainFrame = new JFrame("Checkers");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setPreferredSize(new Dimension(1024, 768));
        mainFrame.setLocation(0, 0);
        mainFrame.setLayout(new GridLayout(4, 1));

        JButton newGameButton = new JButton("Nowa gra");
        newGameButton.setPreferredSize(new Dimension(300, 300));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGameOptionsPrompt();
                createGameWindow();

            }
        });
        mainFrame.add(newGameButton);

        JButton instructionButton = new JButton("Instrukcja gry");
        instructionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "Strzałki - wybór pola planszy\n" +
                        "Enter - zatwierdzenie wyboru\n" +
                        "Escape - anulowanie ruchu\n" +
                        "Backspace - powrót do menu\n");
            }
        });
        mainFrame.add(instructionButton);
        JButton authorsButton = new JButton("Autorzy");
        authorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "Jakub Pfajfer\nMateusz Janel\nDamian Rosiński");
            }
        });
        mainFrame.add(authorsButton);
        JButton exitButton = new JButton("Wyjście");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mainFrame.add(exitButton);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void createGameWindow() {
        JPanel gameBoard = new JPanel();
        gameBoard.setPreferredSize(new Dimension(1024, 768));

        NewGameFrame frame = new NewGameFrame();

        CheckersBoard board = new CheckersBoard(color1, color2);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(board);
        JPanel gui = new JPanel();
        gui.setPreferredSize(new Dimension(256, 128));
        gui.setLayout(new GridLayout(3, 1));

        JPanel player1panel = new JPanel();
        player1panel.setLayout(new BoxLayout(player1panel, BoxLayout.Y_AXIS));
        player1panel.add(new JLabel("Gracz 1: "));
        JLabel player1 = new JLabel(name1);
        player1panel.add(player1);
        JLabel turn1 = new JLabel("Twoja tura!");
        JLabel win1 = new JLabel("Wygrałeś!");
        win1.setVisible(false);
        player1panel.add(turn1);
        player1panel.add(win1);
        player1panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        player1.setForeground(color1);
        gui.add(player1panel);

        JPanel turnpanel = new JPanel();
        turnpanel.add(new JLabel("Czas tury:"));
        Date date = new Date(board.getElapsedBoardTime());
        String formatted = getFormattedTime(date);
        JLabel time = new JLabel(formatted);
        turnpanel.add(time);
        gui.add(turnpanel);
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date currentTime = new Date(board.getElapsedBoardTime());
                String format = getFormattedTime(currentTime);
                time.setText(format);
            }
        });
        timer.setInitialDelay(0);
        timer.start();
        JPanel player2panel = new JPanel();
        player2panel.setLayout(new BoxLayout(player2panel, BoxLayout.Y_AXIS));
        player2panel.add(new JLabel("Gracz 2:"));
        JLabel player2 = new JLabel(name2);
        player2panel.add(player2);
        JLabel turn2 = new JLabel("Twoja tura!");
        JLabel win2 = new JLabel("Wygrałeś!");
        turn2.setVisible(false);
        win2.setVisible(false);
        player2panel.add(turn2);
        player2panel.add(win2);
        player2panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        player2.setForeground(color2);
        gui.add(player2panel);
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (board.getTurn() == 0 && board.isGameOver() == -1) {
                    turn1.setVisible(true);
                    turn2.setVisible(false);
                    win1.setVisible(false);
                    win2.setVisible(false);
                } else if (board.getTurn() == 1 && board.isGameOver() == -1) {
                    turn2.setVisible(true);
                    turn1.setVisible(false);
                    win1.setVisible(false);
                    win2.setVisible(false);
                }
                if (board.isGameOver() == 0) {
                    turn1.setVisible(false);
                    turn2.setVisible(false);
                    win1.setVisible(true);
                } else if (board.isGameOver() == 1) {
                    turn1.setVisible(false);
                    turn2.setVisible(false);
                    win2.setVisible(true);
                }
            }
        });
        frame.add(gui, BorderLayout.EAST);
        frame.pack();
        frame.setVisible(true);
    }

    private void createGameOptionsPrompt() {
        JPanel popup = new JPanel();
        JTextField nameField1 = new JTextField();
        JTextField nameField2 = new JTextField();
        JPanel colorPanel1 = new JPanel();
        JPanel colorPanel2 = new JPanel();
        JLabel test1 = new JLabel("Gracz 1");
        JLabel test2 = new JLabel("Gracz 2");

        colorPanel1.setPreferredSize(new Dimension(100, 30));
        colorPanel2.setPreferredSize(new Dimension(100, 30));
        colorPanel1.add(test1);
        colorPanel2.add(test2);

        popup.setLayout(new BoxLayout(popup, BoxLayout.Y_AXIS));

        JColorChooser colorChooser1 = new JColorChooser();
        JColorChooser colorChooser2 = new JColorChooser();

        colorChooser1.setPreviewPanel(colorPanel1);
        AbstractColorChooserPanel[] panels = colorChooser1.getChooserPanels();
        for (AbstractColorChooserPanel panel : panels) {
            if (panel.getDisplayName().equals("HSV") ||
                    (panel.getDisplayName().equals("RGB")) ||
                    (panel.getDisplayName().equals("HSL")) || (panel.getDisplayName().equals("CMYK"))) {
                colorChooser1.removeChooserPanel(panel);
            }
        }

        colorChooser2.setPreviewPanel(colorPanel2);
        panels = colorChooser2.getChooserPanels();
        for (AbstractColorChooserPanel panel : panels) {
            if (panel.getDisplayName().equals("HSV") ||
                    (panel.getDisplayName().equals("RGB")) ||
                    (panel.getDisplayName().equals("HSL")) || (panel.getDisplayName().equals("CMYK"))) {
                colorChooser2.removeChooserPanel(panel);
            }
        }

        nameField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if ((nameField2.getText().equals(nameField1.getText()) && !nameField2.getText().equals("")) ||
                        (nameField1.getText().equals(nameField2.getText()) && !nameField1.getText().equals(""))) {
                    JOptionPane.showMessageDialog(popup, "Gracze nie mogą mieć takich samych nazw");
                    nameField1.setText("");
                }
            }
        });

        nameField2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if ((nameField2.getText().equals(nameField1.getText()) && !nameField2.getText().equals("")) ||
                        (nameField1.getText().equals(nameField2.getText()) && !nameField1.getText().equals(""))) {
                    JOptionPane.showMessageDialog(popup, "Gracze nie mogą mieć takich samych nazw");
                    nameField2.setText("");
                }
            }
        });

        nameField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                test1.setText(nameField1.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                test1.setText(nameField1.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException();
            }
        });
        nameField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                test2.setText(nameField2.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                test2.setText(nameField2.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException();
            }
        });

        colorChooser1.getSelectionModel().addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        Color newColor = colorChooser1.getColor();
                        test1.setForeground(newColor);
                    }
                }
        );
        colorChooser2.getSelectionModel().addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        Color newColor = colorChooser2.getColor();
                        test2.setForeground(newColor);
                    }
                }
        );

        popup.add(new Label("Nazwa gracza 1:"));
        popup.add(nameField1);
        popup.add(new Label("Kolor gracza 1:"));
        popup.add(colorChooser1);
        popup.add(new Label("Nazwa gracza 2:"));
        popup.add(nameField2);
        popup.add(new Label("Kolor gracza 2:"));
        popup.add(colorChooser2);

        int result = JOptionPane.showConfirmDialog(null, popup, "Opcje gry", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            name1 = nameField1.getText();
            name2 = nameField2.getText();
            color1 = colorChooser1.getColor();
            color2 = colorChooser2.getColor();
        }
    }

    private String getFormattedTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }
}


class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game();
            }
        });
    }
}
