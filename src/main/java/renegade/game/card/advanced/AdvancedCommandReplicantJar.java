package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandReplicantJar extends CommandCard {

    public AdvancedCommandReplicantJar() {
        super("Command39.jpg", new Server[]{Server.YELLOW, Server.YELLOW, Server.YELLOW}, new Server[]{Server.YELLOW, Server.PURPLE, Server.PURPLE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO flip any number of contaminants on your server
    }
}
