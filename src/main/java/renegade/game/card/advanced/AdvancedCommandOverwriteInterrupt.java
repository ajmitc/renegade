package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandOverwriteInterrupt extends CommandCard {

    public AdvancedCommandOverwriteInterrupt() {
        super("Command31.jpg", new Server[]{Server.RED}, new Server[]{Server.RED});
    }

    @Override
    public void doInterrupt(Game game) {
        super.doInterrupt(game);
        // TODO interrupt after any Infect action to instead add +2 to the Renegade's total infection score
    }
}
