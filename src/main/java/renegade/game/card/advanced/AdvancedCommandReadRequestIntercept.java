package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandReadRequestIntercept extends CommandCard {

    public AdvancedCommandReadRequestIntercept() {
        super("Command37.jpg", new Server[]{Server.BLUE, Server.BLUE}, new Server[]{Server.BLUE, Server.PURPLE});
    }

    @Override
    public void doExecute(Game game) {
        super.doExecute(game);
        // TODO place this card (plus up to two others) from your hand to the bottom of your command deck, then draw back the same amount
    }
}
