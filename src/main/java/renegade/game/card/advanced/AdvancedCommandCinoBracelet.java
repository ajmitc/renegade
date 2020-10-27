package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandCinoBracelet extends CommandCard {

    public AdvancedCommandCinoBracelet() {
        super("Command5.jpg", new Server[]{Server.RED, Server.RED}, new Server[]{Server.YELLOW, Server.YELLOW});
        setInterrupt(true);
    }

    @Override
    public void doInterrupt(Game game) {
        super.doInterrupt(game);
        // TODO play this card with one other card to have this card produce the same Commands (not Event functions) as that other card
    }
}
