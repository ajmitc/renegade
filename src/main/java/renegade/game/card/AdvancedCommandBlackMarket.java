package renegade.game.card;

import renegade.game.Containment;
import renegade.game.Game;

public class AdvancedCommandBlackMarket extends CommandCard {

    public AdvancedCommandBlackMarket() {
        super("Command3.jpg", new Containment[]{Containment.ROOTKIT}, new Containment[]{Containment.ROOTKIT, Containment.ROOTKIT});
    }

    @Override
    public void doAlso(Game game) {
        super.doAlso(game);
        // TODO discard any cards you desire in the Hack Shack and immediately restock them
    }
}
