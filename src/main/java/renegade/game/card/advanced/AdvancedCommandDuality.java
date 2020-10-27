package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandDuality extends CommandCard {

    public AdvancedCommandDuality() {
        super("Command15.jpg", new Server[]{Server.YELLOW}, new Server[]{Server.YELLOW});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO flip one containment on your partition
    }
}
