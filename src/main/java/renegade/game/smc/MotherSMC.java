package renegade.game.smc;

import renegade.game.Countermeasure;
import renegade.game.Game;
import renegade.game.Server;

public class MotherSMC extends SMC{
    public MotherSMC(){
        super("Mother", 1, 2, 1, 1, 2, 2, "SMC3.jpg");
    }

    @Override
    public void setup(Game game) {
        super.setup(game);
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream().forEach(p -> p.getCountermeasures().add(Countermeasure.SPARK));

        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream().forEach(p -> {
            game.getBoard().getNeighbors(p).stream().filter(n -> n.getServer() != Server.PURPLE).forEach(n -> {
                n.getCountermeasures().add(Countermeasure.SPARK);
            });
        });

        game.getBoard().getServerTiles().stream().forEach(st -> {
            st.getPartitions().stream()
                    .filter(p -> p.countCountermeasures(Countermeasure.SPARK) == 0 && game.getBoard().getNeighbors(p).size() == 6)
                    .forEach(p -> {
                        p.getCountermeasures().add(Countermeasure.SPARK);
                    });
        });
    }

    @Override
    public void startOfTurn(Game game) {
        super.startOfTurn(game);
        game.getBoard().getServerTile(game.getCurrentPlayer().getServer()).getPartition(6).getCountermeasures().add(Countermeasure.SPARK);
    }
}
