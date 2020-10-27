package renegade.game.smc;

import renegade.game.CountermeasureType;
import renegade.game.Game;
import renegade.game.GameUtil;
import renegade.game.Server;
import renegade.game.board.Partition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpiderSMC extends SMC{

    public SpiderSMC(){
        super("Spider", 1, 1, 1, 1, 2, 3, "SMC5.jpg");
        setMoveSparks(false);
    }

    @Override
    public void setup(Game game) {
        super.setup(game);
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream().forEach(p -> {
            game.getBoard().addSpark(p);
        });
        game.getBoard().getServerTiles().stream()
                .filter(st -> game.getAvatars().stream().map(a -> a.getServer()).anyMatch(s -> s == st.getServer()))
                .forEach(st -> {
                    st.getPartitions().stream()
                            .filter(p -> p.getNumber() % 2 == 1)
                            .forEach(p -> {
                                game.getBoard().addSpark(p);
                            });
                });
    }

    @Override
    public void placeStartOfTurnCountermeasure(Game game){
        Server server = GameUtil.getRandomServer();
        // Place spark on lowest-numbered partition with fewest sparks (1 Guardian == 3 sparks)
        // Find partitions with fewest sparks
        int fewestSparks = Integer.MAX_VALUE;
        List<Partition> fewestSparkPartitions = new ArrayList<>();
        for (Partition partition : game.getBoard().getServerTile(server).getPartitions()) {
            int numSparks = partition.countCountermeasures(CountermeasureType.SPARK) + (partition.countCountermeasures(CountermeasureType.GUARDIAN) * 3);
            if (numSparks < fewestSparks) {
                fewestSparkPartitions.clear();
                fewestSparkPartitions.add(partition);
                fewestSparks = numSparks;
            } else if (numSparks == fewestSparks)
                fewestSparkPartitions.add(partition);
        }

        Partition lowestNumberedPartition = fewestSparkPartitions.stream().sorted(new Comparator<Partition>() {
            @Override
            public int compare(Partition o1, Partition o2) {
                return o1.getNumber() < o2.getNumber() ? -1 : o1.getNumber() > o2.getNumber() ? 1 : 0;
            }
        }).findFirst().get();

        game.getBoard().addSpark(lowestNumberedPartition);
    }

    @Override
    public void endOfTurn(Game game) {
        super.endOfTurn(game);
        // Delete all containments completely surrounded by sparks/guardians
        game.getBoard().getServerTiles().stream().forEach(st -> {
            st.getPartitions().stream().forEach(p -> {
                if (game.getBoard().getNeighbors(p).stream().allMatch(n -> !n.getCountermeasures().isEmpty())){
                    p.getContaminants().clear();
                }
            });
        });
    }
}
