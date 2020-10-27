package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandChristmasTree extends CommandCard {

    public AdvancedCommandChristmasTree() {
        super("Command4.jpg", new Server[]{Server.RED, Server.YELLOW, Server.BLUE, Server.GREEN}, new Server[]{Server.PURPLE, Server.PURPLE, Server.PURPLE});
    }

    @Override
    public void doExecute(Game game) {
        super.doAlso(game);
        // TODO redistribute any number of sparks on your server to anywhere on the network
    }
}
