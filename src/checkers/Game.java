package checkers;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Game {
    public static void main(String[] args) throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;

        try {

            screen = terminalFactory.createScreen();
            screen.startScreen();
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            final BasicWindow window = new BasicWindow("Checkers");

            TextBox user1 = new TextBox();
            TextBox user2 = new TextBox();
            List<TextColor.ANSI> colorValues = new ArrayList<>();
            for (int i = 0; i < TextColor.ANSI.values().length - 1; i++) {
                colorValues.add(TextColor.ANSI.values()[i]);
            }
            ComboBox<TextColor.ANSI> userColor1 = new ComboBox<>(colorValues);
            ComboBox<TextColor.ANSI> userColor2 = new ComboBox<>(colorValues);

            window.setHints(Collections.singletonList(Window.Hint.CENTERED));
            Panel contentPanel = new Panel(new GridLayout(1));

            Panel gamePanel = new Panel(new GridLayout(1));
            gamePanel.addComponent(new Label("W trakcie gry, wróć do okna gry"));
            Button backButton = new Button("Cofnij", new Runnable() {
                @Override
                public void run() {
                    window.setComponent(contentPanel);
                    window.setTitle("Checkers");
                }
            });

            Button startGameButton = new Button("Start", new Runnable() {
                @Override
                public void run() {
                    if (userColor1.getSelectedItem() == userColor2.getSelectedItem()) {
                        MessageDialog.showMessageDialog(textGUI, "Błąd: te same kolory graczy", "Kolory graczy nie mogą być takie same.", MessageDialogButton.OK);
                    } else if (user1.getText().equals("") || user2.getText().equals("")) {
                        MessageDialog.showMessageDialog(textGUI, "Błąd: Któryś z graczy nie posiada nazwy", "Wszyscy gracze muszą mieć przypisane nazwy.", MessageDialogButton.OK);
                    } else {
                        try {
                            window.setComponent(gamePanel);
                            new CheckersBoard(userColor1.getSelectedItem(), userColor2.getSelectedItem(), user1.getText(), user2.getText());
                            window.setComponent(contentPanel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            contentPanel.addComponent(new Button("Nowa gra", new Runnable() {
                @Override
                public void run() {
                    Panel newPanel = new Panel(new GridLayout(2));
                    newPanel.addComponent(new Label("Opcje graczy").setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, true, 2, 1)));
                    newPanel.addComponent(new Label("Nazwa gracza 1"));
                    newPanel.addComponent(user1);
                    newPanel.addComponent(new Label("Kolor gracza 1"));
                    newPanel.addComponent(userColor1);
                    newPanel.addComponent(new Label("Nazwa gracza 2"));
                    newPanel.addComponent(user2);
                    newPanel.addComponent(new Label("Kolor gracza 2"));
                    newPanel.addComponent(userColor2);
                    newPanel.addComponent(startGameButton);
                    newPanel.addComponent(backButton);
                    window.setTitle("Nowa gra");
                    window.setComponent(newPanel);


                }
            }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));
            contentPanel.addComponent(new Button("Instrukcja gry", new Runnable() {
                @Override
                public void run() {
                    MessageDialog.showMessageDialog(textGUI, "Instrukcja gry", "Strzałki - wybór pola planszy\n" +
                            "Enter - zatwierdzenie wyboru\n" +
                            "Escape - anulowanie ruchu\n" +
                            "Backspace - powrót do menu\n", MessageDialogButton.OK);
                }
            }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));
            contentPanel.addComponent(new Button("Autorzy", new Runnable() {
                @Override
                public void run() {
                    MessageDialog.showMessageDialog(textGUI, "Autorzy", "Jakub Pfajfer\nMateusz Janel\nDamian Rosiński", MessageDialogButton.OK);
                }
            }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));
            contentPanel.addComponent(new Button("Wyjscie", new Runnable() {
                @Override
                public void run() {
                    window.close();
                }
            }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));
            window.setComponent(contentPanel);
            textGUI.addWindowAndWait(window);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            screen.close();
        }
    }
}
