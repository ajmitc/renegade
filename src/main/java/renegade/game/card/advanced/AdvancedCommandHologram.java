package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandHologram extends CommandCard {

    public AdvancedCommandHologram() {
        super("Command23.jpg", new Server[]{Server.BLUE, Server.BLUE, Server.BLUE}, new Server[]{Server.BLUE, Server.PURPLE, Server.PURPLE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO teleport avatar to any partition on the network as a Move action
    }
}
