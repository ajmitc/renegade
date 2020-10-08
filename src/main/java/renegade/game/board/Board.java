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
