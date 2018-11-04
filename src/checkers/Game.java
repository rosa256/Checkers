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
    public static void main(String[] args) {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        final CheckersBoard[] gameBoard = {null};
        try {
            screen = terminalFactory.createScreen();
            screen.startScreen();
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            final Window window = new BasicWindow("Checkers");

            TextBox user1 = new TextBox();
            TextBox user2 = new TextBox();
            List<TextColor.ANSI> colorValues = new ArrayList<>();
            for (int i = 0; i < TextColor.ANSI.values().length - 1; i++) {
                colorValues.add(TextColor.ANSI.values()[i]);
            }
            ComboBox<TextColor.ANSI> userColor1 = new ComboBox<>(colorValues);
            ComboBox<TextColor.ANSI> userColor2 = new ComboBox<>(colorValues);
            ComboBox<Integer> turnTime = new ComboBox<>(30, 45, 60, 75, 90);
            turnTime.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.END));
            turnTime.setReadOnly(true);
            CheckBox forceHit = new CheckBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.BEGINNING));
            CheckBox frontHit = new CheckBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.BEGINNING));
            CheckBox backHit = new CheckBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.BEGINNING));
            frontHit.setChecked(true);
            backHit.setChecked(true);

            window.setHints(Collections.singletonList(Window.Hint.CENTERED));
            Panel contentPanel = new Panel(new GridLayout(1));

            Panel gamePanel = new Panel(new GridLayout(1));
            gamePanel.addComponent(new Label("W trakcie gry, wciśnij przycisk aby zamknąć grę i wrócić do menu"));
            gamePanel.addComponent(new Button("Powrót do menu", new Runnable() {
                @Override
                public void run() {
                    window.setComponent(contentPanel);
                    try {
                        gameBoard[0].closeGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }));
            Button backButton = new Button("Cofnij", new Runnable() {
                @Override
                public void run() {
                    window.setComponent(contentPanel);
                    ((BasicWindow) window).setTitle("Checkers");
                }
            });

            Button startGameButton = new Button("Start", new Runnable() {
                @Override
                public void run() {
                    if (userColor1.getSelectedItem() == userColor2.getSelectedItem()) {
                        MessageDialog.showMessageDialog(textGUI, "Błąd: te same kolory graczy", "Kolory graczy nie mogą być takie same.", MessageDialogButton.OK);
                    } else {
                        try {
                            gameBoard[0] = new CheckersBoard();
                            window.setComponent(gamePanel);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                    try {
//                        finalScreen[0].stopScreen();
//                        finalScreen[0] = terminalFactory.createScreen();
//                        finalScreen[0].startScreen();
//                        textGUI[0] = new MultiWindowTextGUI(finalScreen[0]);
//                        window.setComponent(contentPanel);
//                        textGUI[0].addWindowAndWait(window);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            });

            GridLayout gridLayout = (GridLayout) contentPanel.getLayoutManager();
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
                    newPanel.addComponent(new Label("Czas tury"));
                    newPanel.addComponent(turnTime);
                    newPanel.addComponent(new Label("Opcje bicia").setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, true, 2, 1)));
                    newPanel.addComponent(new Label("Wymuszenie bicia"));
                    newPanel.addComponent(forceHit);
                    newPanel.addComponent(new Label("Bicie do przodu"));
                    newPanel.addComponent(frontHit);
                    newPanel.addComponent(new Label("Bicie do tylu"));
                    newPanel.addComponent(backHit);
                    newPanel.addComponent(startGameButton);
                    newPanel.addComponent(backButton);
                    ((BasicWindow) window).setTitle("Nowa gra");
                    window.setComponent(newPanel);


                }
            }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));
            contentPanel.addComponent(new Button("Zasady", new Runnable() {
                @Override
                public void run() {
                    MessageDialog.showMessageDialog(textGUI, "Zasady gry", "Tutaj będą zapisane\n zasady gry", MessageDialogButton.OK);
                }
            }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));
            contentPanel.addComponent(new Button("Autorzy", new Runnable() {
                @Override
                public void run() {
                    MessageDialog.showMessageDialog(textGUI, "Autorzy", "Jakub Pfajfer\nMateusz Janel\nDamian Rosiński:)", MessageDialogButton.OK);
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
        }
    }
}
