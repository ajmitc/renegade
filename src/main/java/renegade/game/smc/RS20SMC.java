package renegade.game.smc;

import renegade.game.*;
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
            game.getBoard().addReplicant(partition4);
            game.getBoard().addSpark(partition4);
            game.getBoard().addSpark(partition4);

            Partition partition6 = game.getBoard().getServerTile(avatar.getServer()).getPartition(6);
            game.getBoard().addReplicant(partition6);
        });
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream()
                .filter(p -> p.getNumber() % 2 == 1 && game.getBoard().getNeighbors(p).stream().anyMatch(n -> n.countCountermeasures(CountermeasureType.SPARK) == 2))
                .forEach(p -> {
                    game.getBoard().addSpark(p);
                });
    }
}
