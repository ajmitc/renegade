package renegade.game.smc;

import renegade.game.Containment;
import renegade.game.Countermeasure;
import renegade.game.Game;
import renegade.game.Server;
import renegade.game.board.Partition;

import java.util.Comparator;

public class LogiSMC extends SMC{

    public LogiSMC(){
        super("Logi", 1, 2, 1, 1, 2, 2, "SMC2.jpg");
    }

    @Override
    public void setup(Game game) {
        super.setup(game);
        // Add 2 sparks to every closed partition
        game.getBoard().getServerTiles().stream().forEach(st -> {
            st.getPartitions().stream().forEach(p -> {
                if (game.getBoard().getNeighbors(p).size() == 6){
                    p.getCountermeasures().add(Countermeasure.SPARK);
                    p.getCountermeasures().add(Countermeasure.SPARK);
                }
            });
        });
        // Add spark to every odd-numbered partition on Faith
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream()
                .filter(p -> p.getNumber() % 2 == 1)
                .forEach(p -> {
                    p.getCountermeasures().add(Countermeasure.SPARK);
                });
    }

    @Override
    public void placeStartOfTurnCountermeasure(Game game){
        // Place flare on highest numbered partition on player's server with A) No guardian and B) fewest viruses
        Partition partition =
                game.getBoard().getServerTile(game.getCurrentPlayer().getServer()).getPartitions().stream()
                        .filter(p -> p.countCountermeasures(Countermeasure.GUARDIAN) == 0)
                        .sorted(new Comparator<Partition>() {
                            @Override
                            public int compare(Partition o1, Partition o2) {
                                int numViruses1 = o1.countContainments(Containment.VIRUS);
                                int numViruses2 = o2.countContainments(Containment.VIRUS);
                                if (numViruses1 < numViruses2)
                                    return -1;
                                if (numViruses1 > numViruses2)
                                    return 1;
                                return o1.getNumber() > o2.getNumber() ? -1 : o1.getNumber() < o2.getNumber() ? 1 : 0;
                            }
                        })
                        .findFirst().get();
        partition.getCountermeasures().add(Countermeasure.FLARE);
    }
}
