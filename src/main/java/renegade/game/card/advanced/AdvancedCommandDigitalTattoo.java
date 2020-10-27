package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandDigitalTattoo extends CommandCard {

    public AdvancedCommandDigitalTattoo() {
        super("Command12.jpg", new Server[]{Server.GREEN}, new Server[]{Server.GREEN});
    }

    @Override
    public void doAlso(Game game) {
        super.doAlso(game);
        // TODO when used as part of Shop action, also generates one purple command point
    }
}
