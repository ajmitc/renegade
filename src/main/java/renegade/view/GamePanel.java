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
    private HandPanel handPanel;
    private ActionPanel actionPanel;

    public GamePanel(Model model, View view) {
        super(new BorderLayout());
        this.model = model;
        this.view = view;

        boardPanel = new BoardPanel(model, view);
        smcPanel = new SMCPanel(model, view);
        logPanel = new LogPanel();
        handPanel = new HandPanel(model, view);
        actionPanel = new ActionPanel(model, view);

        JPanel actionlogpanel = new JPanel(new BorderLayout());
        actionlogpanel.add(actionPanel, BorderLayout.WEST);
        actionlogpanel.add(logPanel, BorderLayout.CENTER);

        JSplitPane southpanel = new JSplitPane();
        southpanel.setLeftComponent(new JScrollPane(handPanel));
        southpanel.setRightComponent(actionlogpanel);
        southpanel.setDividerLocation(handPanel.getPreferredSize().width);

        add(new JScrollPane(boardPanel), BorderLayout.CENTER);
        add(new JScrollPane(smcPanel), BorderLayout.EAST);
        add(southpanel, BorderLayout.SOUTH);
    }

    public void refresh(){
        boardPanel.refresh();
        handPanel.refresh();
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public LogPanel getLogPanel() {
        return logPanel;
    }

    public HandPanel getHandPanel() {
        return handPanel;
    }

    public ActionPanel getActionPanel() {
        return actionPanel;
    }
}
