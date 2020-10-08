package renegade.view;

import renegade.Model;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private Model model;
    private View view;

    public BoardPanel(Model model, View view) {
        super();
        this.model = model;
        this.view = view;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        // TODO draw server tiles

        // TODO draw containments

        // TODO draw countermeasures

        // TODO draw avatars
    }

    private void drawServerTiles(Graphics2D g){

        model.getGame().getBoard().getServerTiles().forEach(st -> {

        });
    }

    public void refresh(){
        repaint();
    }
}
