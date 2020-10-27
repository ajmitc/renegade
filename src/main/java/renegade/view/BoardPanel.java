package renegade.view;

import renegade.Model;
import renegade.game.ContaminantType;
import renegade.game.CountermeasureType;
import renegade.game.board.Hexagon;
import renegade.game.board.Partition;
import renegade.game.board.ServerTile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import java.util.List;

public class BoardPanel extends JPanel {
    private static final int XOFFSET = 120;
    private static final int YOFFSET = 200;
    private static final int CELL_SIZE = 100;
    private static final int GRID_WIDTH = 15;
    private static final int GRID_HEIGHT = 15;

    private static final int CONTAMINANT_SIZE = 40;
    private static final Font CONTAMINANT_FONT = new Font("Serif", Font.BOLD, 16);

    private static final Map<Integer, List<Point>> COUNTERMEASURE_LOCATION_OFFSETS = new HashMap<>();
    private static final Map<Integer, List<Point>> CONTAMINANT_LOCATION_OFFSETS = new HashMap<>();
    static {
        COUNTERMEASURE_LOCATION_OFFSETS.put(1, new ArrayList<>());
        COUNTERMEASURE_LOCATION_OFFSETS.get(1).add(new Point(130, 30));

        COUNTERMEASURE_LOCATION_OFFSETS.put(2, new ArrayList<>());
        COUNTERMEASURE_LOCATION_OFFSETS.get(2).add(new Point(130, 30));
        COUNTERMEASURE_LOCATION_OFFSETS.get(2).add(new Point(130, 70));


        CONTAMINANT_LOCATION_OFFSETS.put(1, new ArrayList<>());
        CONTAMINANT_LOCATION_OFFSETS.get(1).add(new Point(60, 110));

        CONTAMINANT_LOCATION_OFFSETS.put(2, new ArrayList<>());
        CONTAMINANT_LOCATION_OFFSETS.get(2).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(2).add(new Point(100, 110));

        CONTAMINANT_LOCATION_OFFSETS.put(3, new ArrayList<>());
        CONTAMINANT_LOCATION_OFFSETS.get(3).add(new Point(40, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(3).add(new Point(80, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(3).add(new Point(120, 110));

        CONTAMINANT_LOCATION_OFFSETS.put(4, new ArrayList<>());
        CONTAMINANT_LOCATION_OFFSETS.get(4).add(new Point(30, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(4).add(new Point(70, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(4).add(new Point(110, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(4).add(new Point(150, 110));

        CONTAMINANT_LOCATION_OFFSETS.put(5, new ArrayList<>());
        CONTAMINANT_LOCATION_OFFSETS.get(5).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(5).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(5).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(5).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(5).add(new Point(60, 110));

        CONTAMINANT_LOCATION_OFFSETS.put(6, new ArrayList<>());
        CONTAMINANT_LOCATION_OFFSETS.get(6).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(6).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(6).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(6).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(6).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(6).add(new Point(60, 110));

        CONTAMINANT_LOCATION_OFFSETS.put(7, new ArrayList<>());
        CONTAMINANT_LOCATION_OFFSETS.get(7).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(7).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(7).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(7).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(7).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(7).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(7).add(new Point(60, 110));

        CONTAMINANT_LOCATION_OFFSETS.put(8, new ArrayList<>());
        CONTAMINANT_LOCATION_OFFSETS.get(8).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(8).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(8).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(8).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(8).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(8).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(8).add(new Point(60, 110));
        CONTAMINANT_LOCATION_OFFSETS.get(8).add(new Point(60, 110));
    }

    private static final int AVATAR_XOFFSET = 30;
    private static final int AVATAR_YOFFSET = 30;
    private static final int MULTIPLE_AVATAR_XOFFSET = 30;
    private static final int MULTIPLE_AVATAR_YOFFSET = 30;

    private static final Image SPARK      = ImageUtil.get("Spark.png", CONTAMINANT_SIZE);
    private static final Image FLARE      = ImageUtil.get("Flare.png", CONTAMINANT_SIZE);
    private static final Image FIREWALL   = ImageUtil.get("Firewall.jpg", CONTAMINANT_SIZE);
    private static final Image GUARDIAN   = ImageUtil.get("Guardian.jpg", CONTAMINANT_SIZE);

    private static final Image VIRUS      = ImageUtil.get("Virus.png", CONTAMINANT_SIZE);
    private static final Image REPLICANT  = ImageUtil.get("Replicant.png", CONTAMINANT_SIZE);
    private static final Image DATANODE   = ImageUtil.get("Data Node.png", CONTAMINANT_SIZE);
    private static final Image UPLINK     = ImageUtil.get("Uplink.png", CONTAMINANT_SIZE);

    private static final Image PROPAGATOR = ImageUtil.get("Propagator.jpg", CONTAMINANT_SIZE);
    private static final Image REPLICATOR = ImageUtil.get("Replicator.jpg", CONTAMINANT_SIZE);
    private static final Image DATAPORT   = ImageUtil.get("Data Port.jpg", CONTAMINANT_SIZE);
    private static final Image NEURALHUB  = ImageUtil.get("Neural Hub.jpg", CONTAMINANT_SIZE);

    private Model model;
    private View view;

    private int mx, my;
    private List<Hexagon> grid = new ArrayList<>();

    private ServerTile serverTileToPlace = null;

    public BoardPanel(Model model, View view) {
        super();
        this.model = model;
        this.view = view;
        setPreferredSize(new Dimension(CELL_SIZE * GRID_WIDTH * 2, CELL_SIZE * GRID_HEIGHT * 2));

        for (int y = 0; y < GRID_HEIGHT; ++y){
            for (int x = 0; x < GRID_WIDTH; ++x){
                int px = XOFFSET + (x * (int) (CELL_SIZE * 1.5));
                int py = YOFFSET + (y * Hexagon.getHeightOffset(CELL_SIZE) * 2) - (Hexagon.getHeightOffset(CELL_SIZE) * (x % 2));
                Hexagon hexagon = new Hexagon(x, y, px, py, CELL_SIZE);
                grid.add(hexagon);
            }
        }

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

        drawGrid(g);

        /*
        g.setColor(Color.BLACK);
        grid.stream().forEach(h -> {
            g.drawString(h.getX() + ", " + h.getY(), h.getBounds().x + (h.getBounds().width / 2), h.getBounds().y + (h.getBounds().height / 2));
        });
         */

        // draw server tiles
        drawServerTiles(g);

        drawContainments(g);

        drawCountermeasures(g);

        drawAvatars(g);

        // Draw server tile to place
        if (serverTileToPlace != null){
            g.drawImage(serverTileToPlace.getImage(), mx - (serverTileToPlace.getImage().getWidth(null) / 2), my - (serverTileToPlace.getImage().getHeight(null) / 2), null);
        }

        g.setColor(Color.BLACK);
        g.drawString(mx + ", " + my, 50, 50);
    }

    private void drawGrid(Graphics2D g){
        grid.stream().forEach(h -> g.draw(h));
    }

    private void drawServerTiles(Graphics2D g){
        model.getGame().getBoard().getServerTiles().stream().filter(st -> st.isPlaced()).forEach(st -> {
            drawServerTile(g, st);
        });
    }

    private void drawServerTile(Graphics2D g, ServerTile serverTile){
        Image serverTileImage = serverTile.getImage();
        int x = serverTile.getPartition(6).getX();
        int y = serverTile.getPartition(6).getY();
        int px = XOFFSET + (x * (int) (CELL_SIZE * 1.5));
        int py = YOFFSET + (y * Hexagon.getHeightOffset(CELL_SIZE) * 2) - (Hexagon.getHeightOffset(CELL_SIZE) * (x % 2));
        px -= (serverTileImage.getWidth(null) / 2);
        py -= serverTile.getPartition6YOffset();
        g.drawImage(serverTileImage, px, py, null);

        /*
        g.setColor(Color.WHITE);
        serverTile.getPartitions().stream().forEach(p -> {
            if (p.getBounds() != null)
                g.drawString(p.getX() + ", " + p.getY(), p.getBounds().x + (p.getBounds().width / 2), p.getBounds().y + (p.getBounds().height / 2));
        });
         */
    }

    private void drawContainmentImage(Graphics2D g, Image image, int count, int x, int y){
        g.drawImage(image, x, y, null);
        if (count > 1)
            g.drawString("" + count, x + (CONTAMINANT_SIZE / 3) + 3, y + (int) (CONTAMINANT_SIZE * 0.8) - 3);
    }

    private void drawContainments(Graphics2D g){
        g.setColor(Color.WHITE);
        g.setFont(CONTAMINANT_FONT);
        model.getGame().getBoard().getServerTiles().stream().filter(st -> st.isPlaced()).forEach(st -> {
            st.getPartitions().stream().forEach(p -> {
                drawContainments(g, p);
            });
        });
    }

    private void drawContainments(Graphics2D g, Partition partition){
        if (partition.getBounds() == null)
            return;
        int uniqueCount    = partition.countUniqueContaminants();
        int virusCount     = partition.countContaminants(ContaminantType.VIRUS);
        int replicantCount = partition.countContaminants(ContaminantType.REPLICANT);
        int dataNodeCount  = partition.countContaminants(ContaminantType.DATA_NODE);
        int uplinkCount    = partition.countContaminants(ContaminantType.UPLINK);
        int totalDrawn = 0;
        if (virusCount > 0) {
            drawContainmentImage(g, VIRUS, virusCount,
                    partition.getBounds().x + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
        if (replicantCount > 0) {
            drawContainmentImage(g, REPLICANT, replicantCount,
                    partition.getBounds().x + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
        if (dataNodeCount > 0) {
            drawContainmentImage(g, DATANODE, dataNodeCount,
                    partition.getBounds().x + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
        if (uplinkCount > 0) {
            drawContainmentImage(g, UPLINK, uplinkCount,
                    partition.getBounds().x + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
        if (partition.countContaminants(ContaminantType.PROPAGATOR) > 0) {
            drawContainmentImage(g, PROPAGATOR, 1,
                    partition.getBounds().x + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
        if (partition.countContaminants(ContaminantType.DATA_PORT) > 0) {
            drawContainmentImage(g, DATAPORT, 1,
                    partition.getBounds().x + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
        if (partition.countContaminants(ContaminantType.NEURAL_HUB) > 0) {
            drawContainmentImage(g, NEURALHUB, 1,
                    partition.getBounds().x + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
        if (partition.countContaminants(ContaminantType.REPLICATOR) > 0) {
            drawContainmentImage(g, REPLICATOR, 1,
                    partition.getBounds().x + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + CONTAMINANT_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
    }

    private void drawCountermeasures(Graphics2D g){
        g.setColor(Color.WHITE);
        g.setFont(CONTAMINANT_FONT);
        model.getGame().getBoard().getServerTiles().stream().filter(st -> st.isPlaced()).forEach(st -> {
            st.getPartitions().stream().forEach(p -> {
                drawCountermeasures(g, p);
            });
        });
    }

    private void drawCountermeasures(Graphics2D g, Partition partition){
        if (partition.getBounds() == null)
            return;
        int uniqueCount   = partition.countUniqueCountermeasures();
        int sparkCount    = partition.countCountermeasures(CountermeasureType.SPARK);
        int flareCount    = partition.countCountermeasures(CountermeasureType.FLARE);
        int guardianCount = partition.countCountermeasures(CountermeasureType.GUARDIAN);
        int firewallCount = partition.countCountermeasures(CountermeasureType.FIREWALL);
        int totalDrawn = 0;
        if (sparkCount > 0){
            drawContainmentImage(g, SPARK, sparkCount,
                    partition.getBounds().x + COUNTERMEASURE_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + COUNTERMEASURE_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
        if (flareCount > 0){
            drawContainmentImage(g, FLARE, flareCount,
                    partition.getBounds().x + COUNTERMEASURE_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + COUNTERMEASURE_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
        if (guardianCount > 0){
            drawContainmentImage(g, GUARDIAN, 1,
                    partition.getBounds().x + COUNTERMEASURE_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + COUNTERMEASURE_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
        if (firewallCount > 0){
            drawContainmentImage(g, FIREWALL, 1,
                    partition.getBounds().x + COUNTERMEASURE_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).x,
                    partition.getBounds().y + COUNTERMEASURE_LOCATION_OFFSETS.get(uniqueCount).get(totalDrawn).y);
            totalDrawn += 1;
        }
    }

    private void drawAvatars(Graphics2D g){
        model.getGame().getBoard().getServerTiles().stream().forEach(st -> {
            st.getPartitions().stream().filter(p -> !p.getAvatars().isEmpty()).forEach(p -> {
                drawAvatars(g, p);
            });
        });
    }

    private void drawAvatars(Graphics2D g, Partition partition){
        for (int i = 0; i < partition.getAvatars().size(); ++i){
            g.drawImage(
                    partition.getAvatars().get(i).getImage(),
                    partition.getBounds().x + AVATAR_XOFFSET + (i * MULTIPLE_AVATAR_XOFFSET),
                    partition.getBounds().y + AVATAR_YOFFSET + (i * MULTIPLE_AVATAR_YOFFSET),
                    null);
        }
    }

    public void refresh(){
        repaint();
    }

    public ServerTile getServerTileToPlace() {
        return serverTileToPlace;
    }

    public void setServerTileToPlace(ServerTile serverTileToPlace) {
        this.serverTileToPlace = serverTileToPlace;
    }

    public List<Hexagon> getGrid() {
        return grid;
    }

    public Hexagon getHexagonAt(int mx, int my){
        Optional<Hexagon> opt = grid.stream().filter(h -> h.contains(mx, my)).findFirst();
        return opt.isPresent()? opt.get(): null;
    }

    public Hexagon getHexagon(int x, int y){
        Optional<Hexagon> opt = grid.stream().filter(h -> h.getX() == x && h.getY() == y).findFirst();
        return opt.isPresent()? opt.get(): null;
    }
}
