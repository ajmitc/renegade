package renegade.view;

import renegade.Model;

import javax.swing.*;

public class GamePanel extends JPanel {
    private BoardPanel boardPanel;
    private Model model;
    private View view;

    public GamePanel(Model model, View view) {
        super();
        this.model = model;
        this.view = view;

        boardPanel = new BoardPanel(model, view);
    }

    public void refresh(){
        boardPanel.refresh();
    }
}
