package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandDancingPig extends CommandCard {

    public AdvancedCommandDancingPig() {
        super("Command8.jpg", new Server[]{Server.YELLOW}, new Server[]{Server.YELLOW, Server.YELLOW});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO swap one spark on server with containment on server
    }
}
