package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandSelfModifyingInhibitor extends CommandCard {

    public AdvancedCommandSelfModifyingInhibitor() {
        super("Command41.jpg", new Server[]{Server.YELLOW, Server.YELLOW}, new Server[]{Server.YELLOW, Server.PURPLE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO delete one spark on your partition.  You may then add one replicant (yellow) there if you desire.
    }
}
