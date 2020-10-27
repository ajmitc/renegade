package renegade.game.board;

import renegade.game.Countermeasure;
import renegade.game.CountermeasureType;
import renegade.game.Server;
import renegade.view.ImageUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ServerTile {
    private Image image;
    private Server server;
    private List<Partition> partitions = new ArrayList<>(6);
    private boolean placed = false;

    public ServerTile(Server server) {
        this.server = server;
        for (int i = 1; i <= 6; ++i)
            partitions.add(new Partition(this.server, i));
        switch (this.server){
            case YELLOW:
                image = ImageUtil.get("Map Yellow Front.png");
                break;
            case GREEN:
                image = ImageUtil.get("Map Green Front.png");
                break;
            case BLUE:
                image = ImageUtil.get("Map Blue Front.png");
                break;
            case RED:
                image = ImageUtil.get("Map Red Front.png");
                break;
            case PURPLE:
                image = ImageUtil.get("Map Violet Front.png");
                break;
        }
    }

    /**
     * Set the partition coordinates given the coordinate of partition 6
     * @param x
     * @param y
     */
    public void setPartitionLocations(int x, int y){
        partitions.get(5).setCoords(x, y);
        boolean odd = x % 2 == 1;
        switch (server){
            case RED:
                /*
                 *     x-1  x  x+1          x-1  x  x+1
                 * y-1      5           y-1      5
                 * y-1          4       y            4
                 * y        6           y        6
                 * y    1       3       y+1  1       3
                 * y+1      2           y+1      2
                 */
                partitions.get(0).setCoords(x - 1, odd? y: y + 1);
                partitions.get(1).setCoords(x, y + 1);
                partitions.get(2).setCoords(x + 1, odd? y: y + 1);
                partitions.get(3).setCoords(x + 1, odd? y - 1: y);
                partitions.get(4).setCoords(x, y - 1);
                break;
            case BLUE:
                /*
                 *     x-1  x  x+1       x-1  x  x+1
                 * y-1      3        y-1      3
                 * y-1  4       2    y    4       2
                 * y        6        y        6
                 * y    5       1    y+1  5       1
                 */

                partitions.get(0).setCoords(x + 1, odd? y: y + 1);
                partitions.get(1).setCoords(x + 1, odd? y - 1: y);
                partitions.get(2).setCoords(x, y - 1);
                partitions.get(3).setCoords(x - 1, odd? y - 1: y);
                partitions.get(4).setCoords(x - 1, odd? y: y + 1);
                break;
            case GREEN:
                /*
                 *     x-1  x  x+1       x-1  x  x+1
                 * y-1      4        y-1      4
                 * y-1  5       3    y    5       3
                 * y        6        y        6
                 * y            2    y+1          2
                 * y+1      1        y+1      1
                 */
                partitions.get(0).setCoords(x, y + 1);
                partitions.get(1).setCoords(x + 1, odd? y: y + 1);
                partitions.get(2).setCoords(x + 1, odd? y - 1: y);
                partitions.get(3).setCoords(x, y - 1);
                partitions.get(4).setCoords(x - 1, odd? y - 1: y);
                break;
            case PURPLE:
                /*
                 *     x-1  x  x+1        x-1  x  x+1
                 * y-1                y-1
                 * y-1  1       5     y    1       5
                 * y        6         y        6
                 * y    2       4     y+1  2       4
                 * y+1      3         y+1      3
                 */
                partitions.get(0).setCoords(x - 1, odd? y - 1: y);
                partitions.get(1).setCoords(x - 1, odd? y: y + 1);
                partitions.get(2).setCoords(x, y + 1);
                partitions.get(3).setCoords(x + 1, odd? y: y + 1);
                partitions.get(4).setCoords(x + 1, odd? y - 1: y);
                break;
            case YELLOW:
                /*
                 *     x-1  x  x+1       x-1  x  x+1
                 * y-1      2        y-1      2
                 * y-1  3       1    y    3       1
                 * y        6        y        6
                 * y    4            y+1  4
                 * y+1      5        y+1      5
                 */
                partitions.get(0).setCoords(x + 1, odd? y - 1: y);
                partitions.get(1).setCoords(x, y - 1);
                partitions.get(2).setCoords(x + 1, odd? y - 1: y);
                partitions.get(3).setCoords(x - 1, odd? y: y + 1);
                partitions.get(4).setCoords(x, y + 1);
                break;
        }
        setPlaced(true);
    }

    public Server getServer() {
        return server;
    }

    public List<Partition> getPartitions() {
        return partitions;
    }

    public Partition getPartition(int number){
        return partitions.get(number - 1);
    }

    public Image getImage() {
        return image;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public int getPartition6YOffset(){
        if (server == Server.BLUE){
            return image.getHeight(null) / 2 + 39;
        }
        if (server == Server.PURPLE){
            return image.getHeight(null) / 2 - 39;
        }
        return image.getHeight(null) / 2;
    }
}
