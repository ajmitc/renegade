package renegade.view;

import renegade.Model;
import renegade.game.Server;
import renegade.game.board.ServerTile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BoardPanel extends JPanel {
    private static final int XOFFSET = 100;
    private static final int YOFFSET = 100;
    private static final int CELL_WIDTH = 100;
    private static final int CELL_HEIGHT = 100;
    private static final int PARTITION_6_XOFFSET = 200;
    private static final int PARTITION_6_YOFFSET = 180;

    private Model model;
    private View view;

    private int mx, my;

    public BoardPanel(Model model, View view) {
        super();
        this.model = model;
        this.view = view;

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mx = e.getX();
                my = e.getY();
                refresh();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        if (model.getGame() == null)
            return;

        // draw server tiles
        drawServerTiles(g);

        // TODO draw containments

        // TODO draw countermeasures

        // TODO draw avatars

        g.setColor(Color.BLACK);
        g.drawString(mx + ", " + my, 50, 50);
    }

    private void drawServerTiles(Graphics2D g){
        model.getGame().getBoard().getServerTiles().forEach(st -> {
            if (st.getServer() == Server.PURPLE) return;
            drawServerTile(g, st);
        });
    }

    private void drawServerTile(Graphics2D g, ServerTile serverTile){
        Image serverTileImage = serverTile.getImage();
        int px = XOFFSET + (serverTile.getPartition(6).getX() * CELL_WIDTH) - PARTITION_6_XOFFSET;
        int py = YOFFSET + (serverTile.getPartition(6).getY() * CELL_HEIGHT) - PARTITION_6_YOFFSET;
        g.drawImage(serverTileImage, px, py, null);
    }

    public void refresh(){
        repaint();
    }
}
