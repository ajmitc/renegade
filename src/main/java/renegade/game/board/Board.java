package renegade.game.board;

import renegade.game.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*      x  x+1 x+2
 * y        5
 * y    0       4
 * y+1      6
 * y+1  1       3
 * y+2      2
 */
public class Board {
    private List<ServerTile> serverTiles = new ArrayList<>(5);
    private TokenPool tokenPool = new TokenPool();

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

        if (partitionX % 2 == 1) {
            // 0 (if x is odd)
            neighbor = getPartitionAtCoord(partitionX - 1, partitionY - 1);
            if (neighbor != null)
                neighbors.add(neighbor);

            // 4 if (x is odd)
            neighbor = getPartitionAtCoord(partitionX + 1, partitionY - 1);
            if (neighbor != null)
                neighbors.add(neighbor);
        }
        else {
            // 1 (if x is even)
            neighbor = getPartitionAtCoord(partitionX - 1, partitionY + 1);
            if (neighbor != null)
                neighbors.add(neighbor);

            // 3 (if x is even)
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

    /**
     * Be sure to return Contaminant to Pool!
     * @param server
     * @return
     */
    public Contaminant getContaminant(Server server){
        return tokenPool.getContaminant(ContaminantType.ofServer(server));
    }

    /**
     * Be sure to return Contaminant to Pool!
     * @param server
     * @return
     */
    public Contaminant getInstallation(Server server){
        return tokenPool.getContaminant(ContaminantType.installationOfServer(server));
    }

    public boolean addContaminant(Server color, Partition partition){
        return addContaminant(ContaminantType.ofServer(color), partition);
    }

    public boolean addInstallation(Server color, Partition partition){
        return addContaminant(ContaminantType.installationOfServer(color), partition);
    }

    public boolean addContaminant(ContaminantType type, Partition partition){
        Contaminant contaminant = null;
        switch (type){
            case VIRUS      -> contaminant = tokenPool.getVirus();
            case UPLINK     -> contaminant = tokenPool.getUplink();
            case REPLICANT  -> contaminant = tokenPool.getReplicant();
            case DATA_NODE  -> contaminant = tokenPool.getDataNode();
            case DATA_PORT  -> contaminant = tokenPool.getDataPort();
            case REPLICATOR -> contaminant = tokenPool.getReplicator();
            case PROPAGATOR -> contaminant = tokenPool.getPropagator();
            case NEURAL_HUB -> contaminant = tokenPool.getNeuralHub();
        }
        if (contaminant == null){
            return false;
        }
        partition.addContaminant(contaminant);
        return true;
    }

    public boolean addVirus(Partition partition){
        return addContaminant(ContaminantType.VIRUS, partition);
    }

    public boolean addReplicant(Partition partition){
        return addContaminant(ContaminantType.REPLICANT, partition);
    }

    public boolean addDataNode(Partition partition){
        return addContaminant(ContaminantType.DATA_NODE, partition);
    }

    public boolean addUplink(Partition partition){
        return addContaminant(ContaminantType.UPLINK, partition);
    }

    public boolean addNeuralHub(Partition partition){
        return addContaminant(ContaminantType.NEURAL_HUB, partition);
    }

    public boolean addPropagator(Partition partition){
        return addContaminant(ContaminantType.PROPAGATOR, partition);
    }

    public boolean addDataPort(Partition partition){
        return addContaminant(ContaminantType.DATA_PORT, partition);
    }

    public boolean addReplicator(Partition partition){
        return addContaminant(ContaminantType.REPLICATOR, partition);
    }

    public boolean addCountermeasure(CountermeasureType countermeasure, Partition destPartition){
        switch (countermeasure){
            case SPARK:
                return addSpark(destPartition);
            case FLARE:
                return addFlare(destPartition);
            case GUARDIAN:
                return addGuardian(destPartition);
            case FIREWALL:
                return addFirewall(destPartition);
        }
        return true;
    }

    public boolean addSpark(Partition destPartition){
        if (destPartition.getCountermeasures().contains(CountermeasureType.GUARDIAN) || destPartition.getCountermeasures().contains(CountermeasureType.FIREWALL)){
            // Explosion!  Add 2 Flares to next-lowest numbered partition
            int nextLowerPartition = destPartition.getNumber() - 1;
            if (nextLowerPartition == 0)
                nextLowerPartition = 6;
            Partition next = getServerTile(destPartition.getServer()).getPartition(nextLowerPartition);
            if (!addFlare(next))
                return false;
            return addFlare(next);
        }
        int existingSparks = destPartition.countCountermeasures(CountermeasureType.SPARK);
        int existingFlares = destPartition.countCountermeasures(CountermeasureType.FLARE);
        if (existingSparks + existingFlares < 2) {
            Countermeasure spark = tokenPool.getSpark();
            if (spark == null){
                return false;
            }
            destPartition.addCountermeasure(spark);
        }
        else {
            // Add Guardian or Firewall
            if (existingFlares > 0){
                return addFirewall(destPartition);
            }
            else
                return addGuardian(destPartition);
        }
        return true;
    }

    public boolean addFlare(Partition destPartition){
        if (destPartition.getCountermeasures().contains(CountermeasureType.GUARDIAN) || destPartition.getCountermeasures().contains(CountermeasureType.FIREWALL)){
            // Move to next lower numbered partition and add an additional flare
            int nextLowerPartition = destPartition.getNumber() - 1;
            if (nextLowerPartition == 0)
                nextLowerPartition = 6;
            Partition next = getServerTile(destPartition.getServer()).getPartition(nextLowerPartition);
            if (!addFlare(next))
                return false;
            return addFlare(next);
        }
        int existingSparks = destPartition.countCountermeasures(CountermeasureType.SPARK);
        int existingFlares = destPartition.countCountermeasures(CountermeasureType.FLARE);
        if (existingSparks + existingFlares < 2) {
            Countermeasure flare = tokenPool.getFlare();
            if (flare == null){
                return false;
            }
            destPartition.getCountermeasures().add(flare);
            // TODO neutralize a contaminant
        }
        else {
            // Add Firewall
            return addFirewall(destPartition);
        }
        return true;
    }

    public boolean addGuardian(Partition destPartition){
        while (destPartition.getCountermeasures().contains(CountermeasureType.SPARK))
            destPartition.getCountermeasures().remove(CountermeasureType.SPARK);
        destPartition.getContaminants().clear();
        if (!destPartition.getCountermeasures().contains(CountermeasureType.GUARDIAN)) {
            Countermeasure guardian = tokenPool.getGuardian();
            if (guardian == null)
                return false;
            destPartition.getCountermeasures().add(guardian);
        }
        return true;
    }

    public boolean addFirewall(Partition destPartition){
        while (destPartition.getCountermeasures().contains(CountermeasureType.FLARE))
            destPartition.getCountermeasures().remove(CountermeasureType.FLARE);
        while (destPartition.getCountermeasures().contains(CountermeasureType.SPARK))
            destPartition.getCountermeasures().remove(CountermeasureType.SPARK);
        destPartition.getContaminants().clear();
        if (!destPartition.getCountermeasures().contains(CountermeasureType.FIREWALL)) {
            Countermeasure firewall = tokenPool.getGuardian();
            if (firewall == null)
                return false;
            destPartition.getCountermeasures().add(firewall);
        }
        return true;
    }

    public void returnContaminantsToPool(List<Contaminant> contaminants){
        contaminants.stream().forEach(c -> tokenPool.returnToPool(c));
    }

    public void returnCountermeasuresToPool(List<Countermeasure> countermeasures){
        countermeasures.stream().forEach(c -> tokenPool.returnToPool(c));
    }

    public void returnToPool(Contaminant contaminant){
        tokenPool.returnToPool(contaminant);
    }

    public void returnToPool(Countermeasure countermeasure){
        tokenPool.returnToPool(countermeasure);
    }

    public void removeFromPartition(ContaminantType contaminantType, Partition partition){
        Contaminant contaminant = partition.removeContaminant(contaminantType);
        if (contaminant != null)
            returnToPool(contaminant);
    }

    public void removeFromPartition(CountermeasureType countermeasureType, Partition partition){
        Countermeasure countermeasure = partition.removeCountermeasure(countermeasureType);
        if (countermeasure != null)
            returnToPool(countermeasure);
    }

    public void removeAllContaminantsFromPartition(Partition partition){
        returnContaminantsToPool(partition.getContaminants());
        partition.getContaminants().clear();
    }

    public void removeAllNonInstallationsFromPartition(Partition partition){
        List<Contaminant> nonInstallations = partition.getContaminants().stream().filter(c -> !c.getType().isInstallation()).collect(Collectors.toList());
        returnContaminantsToPool(nonInstallations);
        partition.getContaminants().removeAll(nonInstallations);
    }

    public void removeAllCountermeasuresFromPartition(Partition partition){
        returnCountermeasuresToPool(partition.getCountermeasures());
        partition.getCountermeasures().clear();
    }

    public boolean moveContaminant(Contaminant contaminant, Partition fromPartition, Partition toPartition){
        if (!fromPartition.getContaminants().remove(contaminant))
            return false;
        toPartition.getContaminants().add(contaminant);
        return true;
    }

    public boolean moveCountermeasure(Countermeasure countermeasure, Partition fromPartition, Partition toPartition){
        if (!fromPartition.getCountermeasures().remove(countermeasure))
            return false;
        toPartition.getCountermeasures().add(countermeasure);
        return true;
    }

    public boolean moveSparks(ServerTile serverTile, boolean up){
        return up? moveSparksUp(serverTile): moveSparksDown(serverTile);
    }

    private boolean moveSparksUp(ServerTile serverTile){
        // Find lowest numbered partition with sparks
        Optional<Partition> partition =
                serverTile.getPartitions().stream()
                        .filter(p -> p.getCountermeasures().contains(CountermeasureType.SPARK))
                        .sorted(new Comparator<Partition>() {
                            @Override
                            public int compare(Partition o1, Partition o2) {
                                return o1.getNumber() < o2.getNumber()? -1: o1.getNumber() > o2.getNumber()? 1: 0;
                            }
                        })
                        .findFirst();
        if (!partition.isPresent())
            return true;
        // Move those sparks up one partition
        int numSparks = partition.get().countCountermeasures(CountermeasureType.SPARK);
        int nextPartitionNumber = (partition.get().getNumber() + 1) % 6;
        Partition nextPartition = serverTile.getPartition(nextPartitionNumber);
        while (partition.get().countCountermeasures(CountermeasureType.SPARK) > 0)
            tokenPool.returnToPool(partition.get().removeCountermeasure(CountermeasureType.SPARK));
        for (int i = 0; i < numSparks; ++i) {
            if (!addSpark(nextPartition)) {
                return false;
            }
        }
        return true;
    }

    private boolean moveSparksDown(ServerTile serverTile){
        // Find highest numbered partition with sparks
        Optional<Partition> partition =
                serverTile.getPartitions().stream()
                        .filter(p -> p.getCountermeasures().contains(CountermeasureType.SPARK))
                        .sorted(new Comparator<Partition>() {
                            @Override
                            public int compare(Partition o1, Partition o2) {
                                return o1.getNumber() > o2.getNumber()? -1: o1.getNumber() < o2.getNumber()? 1: 0;
                            }
                        })
                        .findFirst();
        if (!partition.isPresent())
            return true;
        // Move those sparks down one partition
        int numSparks = partition.get().countCountermeasures(CountermeasureType.SPARK);
        int nextPartitionNumber = partition.get().getNumber() == 1? 6: partition.get().getNumber() - 1;
        Partition nextPartition = serverTile.getPartition(nextPartitionNumber);
        while (partition.get().getCountermeasures().contains(CountermeasureType.SPARK)) {
            tokenPool.returnToPool(partition.get().removeCountermeasure(CountermeasureType.SPARK));
        }
        for (int i = 0; i < numSparks; ++i) {
            if (!addSpark(nextPartition))
                return false;
        }
        return true;
    }
}
