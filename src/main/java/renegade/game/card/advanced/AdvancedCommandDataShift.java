package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandDataShift extends CommandCard {

    public AdvancedCommandDataShift() {
        super("Command10.jpg", new Server[]{Server.BLUE}, new Server[]{Server.BLUE, Server.BLUE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO move up to 3 containments from any adjacent partition(s) to yours
    }
}
