package checkers_swing;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Game {

    private JFrame mainFrame;
    private String name1;
    private String name2;
    private Color color1;
    private Color color2;

    Game() {
        mainFrame = new JFrame("Checkers");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setPreferredSize(new Dimension(1024, 768));
        mainFrame.setLocation(50, 50);
        mainFrame.setLayout(new GridLayout(4, 1));
        JButton newGameButton = new JButton("Nowa gra");
        newGameButton.setPreferredSize(new Dimension(300, 300));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
