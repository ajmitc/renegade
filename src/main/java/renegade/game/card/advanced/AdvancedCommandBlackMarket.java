package renegade.game.card.advanced;

import renegade.game.Server;
import renegade.game.Game;
import renegade.game.card.CommandCard;

public class AdvancedCommandBlackMarket extends CommandCard {

    public AdvancedCommandBlackMarket() {
        super("Command3.jpg", new Server[]{Server.PURPLE}, new Server[]{Server.PURPLE, Server.PURPLE});
    }

    @Override
    public void doAlso(Game game) {
        super.doAlso(game);
        // TODO discard any cards you desire in the Hack Shack and immediately restock them
    }
}
