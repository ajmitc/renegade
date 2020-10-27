package renegade.game.card.advanced;

import renegade.game.Game;
import renegade.game.Server;
import renegade.game.card.CommandCard;

public class AdvancedCommandEmitEMP extends CommandCard {

    public AdvancedCommandEmitEMP() {
        super("Command18.jpg", new Server[]{Server.RED, Server.RED}, new Server[]{Server.RED, Server.PURPLE});
    }

    @Override
    public void doAlso(Game game) {
        super.doAlso(game);
        // TODO when this card is used to initiate an Infect action and you succeed, delete all sparks from one partition adjacent to yours
    }
}
