package renegade.game.smc;

import renegade.game.Containment;
import renegade.game.Countermeasure;
import renegade.game.Game;
import renegade.game.Server;
import renegade.game.board.Partition;

public class RS20SMC extends SMC{

    public RS20SMC(){
        super("RS20 - Simulator", 1, 1, 1, 0, 1, 1, "SMC4.jpg");
    }

    @Override
    public void setup(Game game) {
        super.setup(game);
        game.getAvatars().stream().forEach(avatar -> {
            Partition partition4 = game.getBoard().getServerTile(avatar.getServer()).getPartition(4);
            partition4.getContainments().add(Containment.REPLICANT);
            partition4.getCountermeasures().add(Countermeasure.SPARK);
            partition4.getCountermeasures().add(Countermeasure.SPARK);

            Partition partition6 = game.getBoard().getServerTile(avatar.getServer()).getPartition(6);
            partition6.getContainments().add(Containment.REPLICANT);
        });
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream()
                .filter(p -> p.getNumber() % 2 == 1 && game.getBoard().getNeighbors(p).stream().anyMatch(n -> n.countCountermeasures(Countermeasure.SPARK) == 2))
                .forEach(p -> {
                    p.getCountermeasures().add(Countermeasure.SPARK);
                });
    }
}
