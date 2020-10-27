package renegade.game.smc;

import renegade.game.*;
import renegade.view.PopupUtil;

public class AlphaMobySMC extends SMC {

    public AlphaMobySMC() {
        super("Alpha-Moby", 1, 1, 1, 0, 1, 1, "SMC1.jpg");
    }

    @Override
    public void setup(Game game) {
        super.setup(game);
        // Add spark to Faith partitions
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream().forEach(p -> {
            game.getBoard().addSpark(p);
        });
        // Add spark to player's access points
        game.getBoard().getServerTiles().stream().forEach(st -> {
            st.getPartitions().stream().filter(p -> !p.getAvatars().isEmpty()).forEach(p -> game.getBoard().addSpark(p));
        });
    }

    @Override
    public void placeStartOfTurnCountermeasure(Game game) {
        // Add spark to current player's partition
        Avatar player = game.getCurrentPlayer();
        if (game.getBoard().addSpark(game.getBoard().getPlayerPartition(player))){
            PopupUtil.popupNotification(null, "Game Over", "Cannot place spark (no tokens left)!  Game Over!");
            game.setPhase(GamePhase.GAMEOVER);
        }
    }
}
