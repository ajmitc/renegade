package renegade.game.smc;

import renegade.game.*;
import renegade.view.PopupUtil;

public class MotherSMC extends SMC{
    public MotherSMC(){
        super("Mother", 1, 2, 1, 1, 2, 2, "SMC3.jpg");
    }

    @Override
    public void setup(Game game) {
        super.setup(game);
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream().forEach(p -> game.getBoard().addSpark(p));

        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream().forEach(p -> {
            game.getBoard().getNeighbors(p).stream().filter(n -> n.getServer() != Server.PURPLE).forEach(n -> {
                game.getBoard().addSpark(n);
            });
        });

        game.getBoard().getServerTiles().stream().forEach(st -> {
            st.getPartitions().stream()
                    .filter(p -> p.countCountermeasures(CountermeasureType.SPARK) == 0 && game.getBoard().getNeighbors(p).size() == 6)
                    .forEach(p -> {
                        game.getBoard().addSpark(p);
                    });
        });
    }

    @Override
    public void startOfTurn(Game game) {
        super.startOfTurn(game);
        if (game.getBoard().addSpark(game.getBoard().getServerTile(game.getCurrentPlayer().getServer()).getPartition(6))){
            PopupUtil.popupNotification(null, "Game Over", "Unable to add spark (no tokens left)!  Game Over!");
            game.setPhase(GamePhase.GAMEOVER);
        }
    }
}
