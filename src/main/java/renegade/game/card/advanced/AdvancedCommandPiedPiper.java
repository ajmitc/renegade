package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandPiedPiper extends CommandCard {

    public AdvancedCommandPiedPiper() {
        super("Command34.jpg", new Server[]{Server.GREEN, Server.GREEN}, new Server[]{Server.BLUE, Server.BLUE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO teleport one contaminant from any partition to your partition
    }
}
