package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandSidekick extends CommandCard {

    public AdvancedCommandSidekick() {
        super("Command43.jpg", new Server[]{Server.RED}, new Server[]{Server.RED, Server.RED});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO delete one spark on your partition or an adjacent partition
    }
}
