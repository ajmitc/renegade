package renegade.game.board;

import renegade.game.Server;

import java.util.ArrayList;
import java.util.List;

public class ServerTile {
    private Server server;
    private List<Partition> partitions = new ArrayList<>(6);

    public ServerTile(Server server) {
        this.server = server;
        for (int i = 1; i <= 6; ++i)
            partitions.add(new Partition(i));
    }

    /**
     * Set the partition coordinates given the coordinate of partition 6
     * @param x
     * @param y
     */
    public void setPartitionLocations(int x, int y){
        partitions.get(5).setCoords(x, y);
        switch (server){
            case RED:
                /*
                 *     x-1  x  x+1
                 * y-1      5
                 * y-1          4
                 * y        6
                 * y    1       3
                 * y+1      2
                 */
                partitions.get(0).setCoords(x - 1, y);
                partitions.get(1).setCoords(x, y + 1);
                partitions.get(2).setCoords(x + 1, y);
                partitions.get(3).setCoords(x + 1, y - 1);
                partitions.get(4).setCoords(x, y - 1);
                break;
            case BLUE:
                /*
                 *     x-1  x  x+1
                 * y-1      3
                 * y-1  4       2
                 * y        6
                 * y    5       1
                 */
                partitions.get(0).setCoords(x + 1, y);
                partitions.get(1).setCoords(x + 1, y - 1);
                partitions.get(2).setCoords(x, y - 1);
                partitions.get(3).setCoords(x - 1, y - 1);
                partitions.get(4).setCoords(x - 1, y);
                break;
            case GREEN:
                /*
                 *        4
                 *    5       3
                 *        6
                 *            2
                 *        1
                 */
                partitions.get(0).setCoords(x, y);
                partitions.get(1).setCoords(x, y);
                partitions.get(2).setCoords(x, y);
                partitions.get(3).setCoords(x, y);
                partitions.get(4).setCoords(x, y);
                break;
            case PURPLE:
                /*
                 *
                 *    1       5
                 *        6
                 *    2       4
                 *        3
                 */
                partitions.get(0).setCoords(x, y);
                partitions.get(1).setCoords(x, y);
                partitions.get(2).setCoords(x, y);
                partitions.get(3).setCoords(x, y);
                partitions.get(4).setCoords(x, y);
                break;
            case YELLOW:
                /*
                 *        2
                 *    3       1
                 *        6
                 *    4
                 *        5
                 */
                partitions.get(0).setCoords(x, y);
                partitions.get(1).setCoords(x, y);
                partitions.get(2).setCoords(x, y);
                partitions.get(3).setCoords(x, y);
                partitions.get(4).setCoords(x, y);
                break;
        }
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
}
