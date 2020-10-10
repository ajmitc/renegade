package renegade.view;

import renegade.Model;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Model model;
    private View view;

    private BoardPanel boardPanel;
    private SMCPanel smcPanel;
    private LogPanel logPanel;

    public GamePanel(Model model, View view) {
        super(new BorderLayout());
        this.model = model;
        this.view = view;

        boardPanel = new BoardPanel(model, view);
        smcPanel = new SMCPanel(model, view);
        logPanel = new LogPanel();
        add(boardPanel, BorderLayout.CENTER);
        add(smcPanel, BorderLayout.EAST);
        add(logPanel, BorderLayout.SOUTH);
    }

    public void refresh(){
        boardPanel.refresh();
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public LogPanel getLogPanel() {
        return logPanel;
    }
}
