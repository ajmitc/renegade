package renegade.game.card.advanced;

import renegade.game.Server;
import renegade.game.Game;
import renegade.game.card.CommandCard;

public class AdvancedCommandAdrenalineSurge extends CommandCard {

    public AdvancedCommandAdrenalineSurge() {
        super("Command1.jpg", new Server[]{Server.GREEN}, new Server[]{Server.GREEN, Server.GREEN});
    }

    @Override
    public void doAlso(Game game) {
        super.doAlso(game);
        // TODO If this is UPLOAD action, upload one additional containment of that type
    }
}
