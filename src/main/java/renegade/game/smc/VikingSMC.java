package renegade.game.smc;

import renegade.game.CountermeasureType;
import renegade.game.Game;
import renegade.game.GameUtil;
import renegade.game.Server;
import renegade.game.board.Partition;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VikingSMC extends SMC{

    public VikingSMC(){
        super("Viking", 1, 1, 1, 1, 2, 2, "SMC.jpg");
    }

    @Override
    public void setup(Game game) {
        super.setup(game);
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream()
                .filter(p -> p.getNumber() % 2 == 1)
                .forEach(p -> {
                    game.getBoard().addSpark(p);
                });
        game.getBoard().getServerTiles().stream().forEach(st -> {
            st.getPartitions().stream()
                    .filter(p -> p.getNumber() % 2 == 1 && game.getBoard().getNeighbors(p).size() < 6)
                    .forEach(p -> {
                        game.getBoard().addSpark(p);
                    });
        });
    }

    @Override
    public void placeStartOfTurnCountermeasure(Game game) {
        Server server = GameUtil.getRandomServer();
        int partitionNum = GameUtil.getRandomPartition();
        Partition partition = game.getBoard().getServerTile(server).getPartition(partitionNum);
        if (partition.countCountermeasures(CountermeasureType.SPARK) + partition.countCountermeasures(CountermeasureType.GUARDIAN) == 0){
            // Find highest numbered neighbor with spark.  If ties, add spark to all
            List<Partition> targetPartitions =
                    game.getBoard().getNeighbors(partition).stream()
                            .filter(n -> n.countCountermeasures(CountermeasureType.SPARK) > 0)
                            .sorted(new Comparator<Partition>() {
                                @Override
                                public int compare(Partition o1, Partition o2) {
                                    return o1.getNumber() > o2.getNumber()? -1: o1.getNumber() < o2.getNumber()? 1: 0;
                                }
                            })
                            .collect(Collectors.toList());
            if (!targetPartitions.isEmpty()){
                int maxNumber = targetPartitions.get(0).getNumber();
                targetPartitions = targetPartitions.stream().filter(p -> p.getNumber() == maxNumber).collect(Collectors.toList());
                for (Partition tp: targetPartitions){
                    game.getBoard().addSpark(tp);
                }
            }
        }
        else {
            game.getBoard().addSpark(partition);
        }
    }
}
