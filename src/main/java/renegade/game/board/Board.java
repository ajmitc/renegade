package renegade.game.board;

import renegade.game.Avatar;
import renegade.game.Server;

import java.util.ArrayList;
import java.util.List;

/*      x  x+1 x+2
 * y        5
 * y    0       4
 * y+1      6
 * y+1  1       3
 * y+2      2
 */
public class Board {
    private List<ServerTile> serverTiles = new ArrayList<>(5);

    public Board() {
        serverTiles.add(new ServerTile(Server.BLUE));
        serverTiles.add(new ServerTile(Server.RED));
        serverTiles.add(new ServerTile(Server.GREEN));
        serverTiles.add(new ServerTile(Server.YELLOW));
        serverTiles.add(new ServerTile(Server.PURPLE));
    }

    public List<ServerTile> getServerTiles() {
        return serverTiles;
    }

    public ServerTile getServerTile(Server server){
        return serverTiles.stream().filter(st -> st.getServer() == server).findFirst().get();
    }

    public List<Partition> getNeighbors(Partition partition){
        List<Partition> neighbors = new ArrayList<>();
        int partitionX = partition.getX();
        int partitionY = partition.getY();
        /*     x-1  x  x+1        x-1  x  x+1
         * y-1      5
         * y-1  0       4     y-1      5
         * y        6         y    0       4
         * y    1       3     y        6
         * y+1      2         y+1  1       3
         *                    y+1      2
         */
        // 1 or 0
        Partition neighbor = getPartitionAtCoord(partitionX - 1, partitionY);
        if (neighbor != null)
            neighbors.add(neighbor);

        // 3 or 4
        neighbor = getPartitionAtCoord(partitionX + 1, partitionY);
        if (neighbor != null)
            neighbors.add(neighbor);

        if (partitionY % 2 == 1) {
            // 0 (if y is odd)
            neighbor = getPartitionAtCoord(partitionX - 1, partitionY - 1);
            if (neighbor != null)
                neighbors.add(neighbor);

            // 4 if (y is odd)
            neighbor = getPartitionAtCoord(partitionX + 1, partitionY - 1);
            if (neighbor != null)
                neighbors.add(neighbor);
        }
        else {
            // 1 (if y is even)
            neighbor = getPartitionAtCoord(partitionX - 1, partitionY + 1);
            if (neighbor != null)
                neighbors.add(neighbor);

            // 3 (if y is even)
            neighbor = getPartitionAtCoord(partitionX + 1, partitionY + 1);
            if (neighbor != null)
                neighbors.add(neighbor);
        }

        // 5
        neighbor = getPartitionAtCoord(partitionX, partitionY - 1);
        if (neighbor != null)
            neighbors.add(neighbor);

        // 2
        neighbor = getPartitionAtCoord(partitionX, partitionY + 1);
        if (neighbor != null)
            neighbors.add(neighbor);

        return neighbors;
    }

    public Partition getPartitionAtCoord(int x, int y){
        for (ServerTile serverTile: serverTiles) {
            for (Partition partition: serverTile.getPartitions()){
                if (partition.getX() == x && partition.getY() == y)
                    return partition;
            }
        }
        return null;
    }

    public Partition getPlayerPartition(Avatar avatar){
        for (ServerTile st: serverTiles){
            for (Partition partition: st.getPartitions()){
                if (partition.getAvatars().contains(avatar))
                    return partition;
            }
        }
        return null;
    }
}
