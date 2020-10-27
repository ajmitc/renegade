package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandMicroBionix extends CommandCard {

    public AdvancedCommandMicroBionix() {
        super("Command27.jpg", new Server[]{Server.RED, Server.YELLOW, Server.BLUE, Server.GREEN}, new Server[]{Server.PURPLE, Server.PURPLE, Server.PURPLE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO delete one spark from your partition and every partition adjacent to yours
    }
}
