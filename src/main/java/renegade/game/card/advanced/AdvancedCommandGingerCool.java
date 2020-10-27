package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandGingerCool extends CommandCard {

    public AdvancedCommandGingerCool() {
        super("Command21.jpg", new Server[]{Server.GREEN, Server.GREEN}, new Server[]{Server.GREEN, Server.PURPLE});
    }

    @Override
    public void doInterrupt(Game game) {
        super.doInterrupt(game);
        // TODO interrupt after any spark roll to instead change the face of one of the spark dice to whatever you desire
    }
}
