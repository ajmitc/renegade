package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandConsoleCowboy extends CommandCard {

    public AdvancedCommandConsoleCowboy() {
        super("Command7.jpg", new Server[]{Server.PURPLE, Server.PURPLE, Server.PURPLE}, new Server[]{Server.PURPLE, Server.PURPLE, Server.PURPLE, Server.PURPLE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO delete a guardian from your partition
    }
}
