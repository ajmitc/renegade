package renegade.view;

import renegade.Model;

import javax.swing.*;
import java.awt.*;

public class View {
    private static final String MAINMENU = "mainmenu";
    private static final String GAME = "game";

    private Model model;
    private JFrame frame = new JFrame();

    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;

    public View(Model model) {
        this.model = model;

        frame.setTitle("Renegade");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocation(0, 0);

        mainMenuPanel = new MainMenuPanel(model, this);
        gamePanel = new GamePanel(model, this);

        frame.getContentPane().setLayout(new CardLayout());
        frame.getContentPane().add(mainMenuPanel, MAINMENU);
        frame.getContentPane().add(gamePanel, GAME);
    }

    public void showGame(){
        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), GAME);
    }

    public void showMainMenu(){
        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), MAINMENU);
    }

    public void refresh(){
        gamePanel.refresh();
    }

    public JFrame getFrame() {
        return frame;
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
