package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandResetSpark extends CommandCard {

    public AdvancedCommandResetSpark() {
        super("Command40.jpg", new Server[]{Server.RED, Server.RED, Server.RED}, new Server[]{Server.RED, Server.PURPLE, Server.PURPLE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO delete all sparks on your partition OR delete the guardian on your partition and add one spark
    }
}
