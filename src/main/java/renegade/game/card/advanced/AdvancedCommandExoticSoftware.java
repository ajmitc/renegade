package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandExoticSoftware extends CommandCard {

    public AdvancedCommandExoticSoftware() {
        super("Command20.jpg", new Server[]{Server.RED, Server.YELLOW, Server.BLUE, Server.GREEN}, new Server[]{Server.PURPLE, Server.PURPLE, Server.PURPLE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO delete any two containments on your partition and add any one Installation there
    }
}
