package renegade.game.card;

import renegade.game.Containment;
import renegade.game.Game;

public class AdvancedCommandAdrenalineSurge extends CommandCard {

    public AdvancedCommandAdrenalineSurge() {
        super("Command1.jpg", new Containment[]{Containment.UPLINK}, new Containment[]{Containment.UPLINK, Containment.UPLINK});
    }

    @Override
    public void doAlso(Game game) {
        super.doAlso(game);
        // TODO If this is UPLOAD action, upload one additional containment of that type
    }
}
