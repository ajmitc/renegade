package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandNydamShip extends CommandCard {

    public AdvancedCommandNydamShip() {
        super("Command28.jpg", new Server[]{Server.BLUE}, new Server[]{Server.BLUE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO move from an open partition to any other open partition as a move action
    }
}
