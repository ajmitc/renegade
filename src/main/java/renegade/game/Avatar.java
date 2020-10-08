package renegade.game;

import renegade.game.card.CommandCard;
import renegade.game.card.CommandDeck;

import java.util.ArrayList;
import java.util.List;

public class Avatar {
    private Server server;
    private List<CommandCard> hand = new ArrayList<>();
    private CommandDeck deck;

    public Avatar(Server server) {
        this.server = server;
        deck = new CommandDeck(server);
    }

    public void dealHand(){
        for (int i = 0; i < 5; ++i){
            hand.add((CommandCard) deck.draw());
        }
    }


    public Server getServer() {
        return server;
    }

    public CommandDeck getDeck() {
        return deck;
    }

    public List<CommandCard> getHand() {
        return hand;
    }
}
