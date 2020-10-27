package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandLivewireVoodoo extends CommandCard {

    public AdvancedCommandLivewireVoodoo() {
        super("Command25.jpg", new Server[]{Server.YELLOW, Server.YELLOW}, new Server[]{Server.RED, Server.RED});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO flip up to 3 flares on your server.  Any neutralized containments are restored (or returned to the stock if there are more than 3 there)
    }
}
