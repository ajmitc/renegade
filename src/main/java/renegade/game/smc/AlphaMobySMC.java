package renegade.game.smc;

import renegade.game.Avatar;
import renegade.game.Countermeasure;
import renegade.game.Game;
import renegade.game.Server;

public class AlphaMobySMC extends SMC {

    public AlphaMobySMC() {
        super("Alpha-Moby", 1, 1, 1, 0, 1, 1, "SMC1.jpg");
    }

    @Override
    public void setup(Game game) {
        super.setup(game);
        // Add spark to Faith partitions
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream().forEach(p -> {
            p.getCountermeasures().add(Countermeasure.SPARK);
        });
        // Add spark to player's access points
        game.getBoard().getServerTiles().stream().forEach(st -> {
            st.getPartitions().stream().filter(p -> !p.getAvatars().isEmpty()).forEach(p -> p.getCountermeasures().add(Countermeasure.SPARK));
        });
    }

    @Override
    public void startOfTurn(Game game) {
        super.startOfTurn(game);
        // Add spark to current player's partition
        Avatar player = game.getCurrentPlayer();
        game.getBoard().getPlayerPartition(player).getCountermeasures().add(Countermeasure.SPARK);
    }
}
