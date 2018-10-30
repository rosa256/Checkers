package checkers;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.Arrays;


public class Game {
    public static void main(String[] args) {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        try {
            screen = terminalFactory.createScreen();
            screen.startScreen();
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            final Window window = new BasicWindow("Checkers");
            window.setHints(Arrays.asList(Window.Hint.CENTERED));
            Panel contentPanel = new Panel(new GridLayout(1));
            GridLayout gridLayout = (GridLayout) contentPanel.getLayoutManager();
            Label title = new Label("Checkers");
            title.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.BEGINNING, true, false, 1, 1));
            contentPanel.addComponent(title);
            contentPanel.addComponent(new Button("Nowa gra", new Runnable() {
                @Override
                public void run() {
                    Panel newPanel = new Panel(new GridLayout(2));
                    newPanel.addComponent(new Button("Cofnij", new Runnable() {
                        @Override
                        public void run() {
                            window.setComponent(contentPanel);
                            ((BasicWindow) window).setTitle("Checkers");
                        }
                    }));
                    ((BasicWindow) window).setTitle("Opcje rozgrywki");
                    window.setComponent(newPanel);


                }
            }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));
            contentPanel.addComponent(new Button("Zasady", new Runnable() {
                @Override
                public void run() {
                    MessageDialog.showMessageDialog(textGUI, "Zasady gry", "Tutaj będą zapisane\n zasady gry", MessageDialogButton.OK, MessageDialogButton.Cancel);
                }
            }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));
            contentPanel.addComponent(new Button("Autorzy", new Runnable() {
                @Override
                public void run() {
                    MessageDialog.showMessageDialog(textGUI, "Autorzy", "Tutaj beda zapisane nasze imiona :)", MessageDialogButton.OK, MessageDialogButton.Cancel);
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
