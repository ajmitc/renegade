package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandIteration extends CommandCard {

    public AdvancedCommandIteration() {
        super("Command24.jpg", new Server[]{Server.BLUE, Server.GREEN, Server.YELLOW, Server.RED}, new Server[]{Server.PURPLE, Server.PURPLE, Server.PURPLE});
    }

    @Override
    public void doReveal(Game game) {
        super.doReveal(game);
        // TODO reveal on your turn along with another card from your hand to perform that card's EXECUTE event
    }
}
