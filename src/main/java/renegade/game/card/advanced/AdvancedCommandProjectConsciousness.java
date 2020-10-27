package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandProjectConsciousness extends CommandCard {

    public AdvancedCommandProjectConsciousness() {
        super("Command36.jpg", new Server[]{Server.GREEN, Server.GREEN, Server.GREEN}, new Server[]{Server.GREEN, Server.PURPLE, Server.PURPLE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO teleport one Installation to your partition
    }
}
