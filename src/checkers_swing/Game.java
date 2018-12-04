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

    private static final String GRACZ_1 = "Gracz 1";
    private static final String GRACZ_2 = "Gracz 2";
    private static final String ARIAL = "Arial";
    private JFrame mainFrame;
    private String name1 = GRACZ_1;
    private String name2 = GRACZ_2;
    private Color color1 = Color.WHITE;
    private Color color2 = Color.BLACK;

    Game() {
        mainFrame = new JFrame("Checkers");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setPreferredSize(new Dimension(1024, 768));
        mainFrame.setLocation(0, 0);
        mainFrame.setLayout(new GridLayout(4, 1));

        JButton newGameButton = new JButton("Nowa gra");
        newGameButton.setFont(new Font(ARIAL, Font.PLAIN, 30));
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
        instructionButton.setFont(new Font(ARIAL, Font.PLAIN, 30));
        instructionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "Złap pionek lewym przyciskiem myszy i upuść go w " +
                        "wybranym przez siebie miejscu", "Instrukcja gry", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mainFrame.add(instructionButton);
        JButton authorsButton = new JButton("Autorzy");
        authorsButton.setFont(new Font(ARIAL, Font.PLAIN, 30));
        authorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "Jakub Pfajfer\nMateusz Janel\nDamian Rosiński",
                        "Autorzy", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mainFrame.add(authorsButton);
        JButton exitButton = new JButton("Wyjście");
        exitButton.setFont(new Font(ARIAL, Font.PLAIN, 30));
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
        CheckersBoard board = new CheckersBoard(color1, color2);

        JPanel gameBoard = new JPanel();
        JPanel player1panel = new JPanel();
        JLabel gracz1 = new JLabel("Gracz 1: ");
        JLabel player1 = new JLabel(name1);
        JLabel turn1 = new JLabel("Twoja tura!");
        JLabel win1 = new JLabel("Wygrałeś!");
        JPanel turnpanel = new JPanel();
        JPanel gui = new JPanel();
        JLabel turnTimeLabel = new JLabel("Czas tury:");
        Date date = new Date(board.getElapsedBoardTime());
        String formatted = getFormattedTime(date);
        JLabel time = new JLabel(formatted);
        JPanel player2panel = new JPanel();
        JLabel gracz2 = new JLabel("Gracz 2:");
        JLabel player2 = new JLabel(name2);
        JLabel turn2 = new JLabel("Twoja tura!");
        JLabel win2 = new JLabel("Wygrałeś!");

        NewGameFrame frame = new NewGameFrame();

        gameBoard.setPreferredSize(new Dimension(1024, 768));

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(board);
        gui.setPreferredSize(new Dimension(256, 128));
        gui.setLayout(new GridLayout(3, 1));

        setupPlayerPanel(player1panel, gracz1, player1, turn1, win1, gui, color1);

        setupTimePanel(turnpanel, gui, turnTimeLabel, time);

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

        setupPlayerPanel(player2panel, gracz2, player2, turn2, win2, gui, color2);
        turn2.setVisible(false);

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
                    timer.stop();
                } else if (board.isGameOver() == 1) {
                    turn1.setVisible(false);
                    turn2.setVisible(false);
                    win2.setVisible(true);
                    timer.stop();
                }
            }
        });
        frame.add(gui, BorderLayout.EAST);
        frame.pack();
        frame.setVisible(true);
    }

    private void setupTimePanel(JPanel turnpanel, JPanel gui, JLabel turnTimeLabel, JLabel time) {
        turnTimeLabel.setFont(new Font(ARIAL, Font.PLAIN, 24));
        turnpanel.add(turnTimeLabel);
        time.setFont(new Font(ARIAL, Font.PLAIN, 24));
        turnpanel.add(time);
        gui.add(turnpanel);
    }

    private void setupPlayerPanel(JPanel panel, JLabel graczLabel, JLabel playerLabel, JLabel turnLabel, JLabel winLabel, JPanel gui, Color color) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        graczLabel.setFont(new Font(ARIAL, Font.PLAIN, 24));
        panel.add(graczLabel);
        playerLabel.setFont(new Font(ARIAL, Font.PLAIN, 24));
        playerLabel.setOpaque(true);
        setPlayerColor(playerLabel, color);
        panel.add(playerLabel);
        turnLabel.setFont(new Font(ARIAL, Font.PLAIN, 24));
        winLabel.setFont(new Font(ARIAL, Font.PLAIN, 24));
        winLabel.setVisible(false);
        panel.add(turnLabel);
        panel.add(winLabel);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        playerLabel.setForeground(color);
        gui.add(panel);
    }

    private void setPlayerColor(JLabel player, Color color) {
        if (color.getRGB() > -8355712) {
            player.setBackground(Color.BLACK);
            player.setBorder(BorderFactory.createLineBorder(color));
        } else {
            player.setBackground(Color.WHITE);
            player.setBorder(BorderFactory.createLineBorder(color));

        }
    }

    private void createGameOptionsPrompt() {
        JPanel popup = new JPanel();
        JTextField nameField1 = new JTextField();
        JTextField nameField2 = new JTextField();
        JPanel colorPanel1 = new JPanel();
        JPanel colorPanel2 = new JPanel();
        JLabel player1label = new JLabel(GRACZ_1);
        JLabel player2label = new JLabel(GRACZ_2);
        JColorChooser colorChooser1 = new JColorChooser();
        JColorChooser colorChooser2 = new JColorChooser();

        colorPanel2.setPreferredSize(new Dimension(100, 30));
        colorPanel2.add(player2label);

        popup.setLayout(new BoxLayout(popup, BoxLayout.Y_AXIS));

        setupColorPanel(colorPanel1, player1label, colorChooser1);
        setupColorPanel(colorPanel2, player2label, colorChooser2);

        createGameOptionsListeners(popup, nameField1, nameField2, player1label, player2label, colorChooser1, colorChooser2);

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
            if (colorChooser2.getColor().getRGB() == colorChooser1.getColor().getRGB()) {
                JOptionPane.showMessageDialog(null, "Gracze nie mogą mieć takich samych kolorów, zmiana koloru jednego z graczy na losowy");
                if (Math.random() < 0.5) {
                    color1 = colorChooser1.getColor();
                    color2 = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
                } else {
                    color2 = colorChooser2.getColor();
                    color1 = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
                }
            } else {
                color1 = colorChooser1.getColor();
                color2 = colorChooser2.getColor();
            }
            if (!nameField1.getText().equals("")) {
                name1 = nameField1.getText();
            } else {
                name1 = GRACZ_1;
            }
            if (!nameField2.getText().equals("")) {
                name2 = nameField2.getText();
            } else {
                name2 = GRACZ_2;
            }
        }
    }

    private void setupColorPanel(JPanel colorPanel, JLabel label, JColorChooser colorChooser) {
        colorPanel.setPreferredSize(new Dimension(100, 30));
        colorPanel.add(label);
        colorChooser.setPreviewPanel(colorPanel);
        AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();
        for (AbstractColorChooserPanel panel : panels) {
            if (panel.getDisplayName().equals("HSV") ||
                    (panel.getDisplayName().equals("RGB")) ||
                    (panel.getDisplayName().equals("HSL")) || (panel.getDisplayName().equals("CMYK"))) {
                colorChooser.removeChooserPanel(panel);
            }
        }
    }

    private void createGameOptionsListeners(JPanel popup, JTextField nameField1, JTextField nameField2, JLabel test1, JLabel test2, JColorChooser colorChooser1, JColorChooser colorChooser2) {
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
