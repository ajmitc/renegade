package renegade.game;

import renegade.game.card.CardSet;
import renegade.game.card.CommandCard;
import renegade.game.card.CommandDeck;
import renegade.view.ImageUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Avatar {
    private static Logger logger = Logger.getLogger(Avatar.class.getName());

    private Server server;
    private List<CommandCard> hand = new ArrayList<>();
    private CommandDeck deck;
    private Image image;

    public Avatar(Server server) {
        this.server = server;
        deck = new CommandDeck(server);

        switch (server){
            case RED:
                image = ImageUtil.get("Player Token Red 1.png");
                break;
            case BLUE:
                image = ImageUtil.get("Player Token Blue 2.png");
                break;
            case GREEN:
                image = ImageUtil.get("Player Token Green 1.png");
                break;
            case PURPLE:
                image = ImageUtil.get("Player Token Violet 1.png");
                break;
            case YELLOW:
                image = ImageUtil.get("Player Token Yellow 2.png");
                break;
        }
    }

    public List<CommandCard> getSelectedCardsInHand(){
        return hand.stream().filter(card -> card.isSelected()).collect(Collectors.toList());
    }

    public CardSet getSelectedCardsInHandAsSet(){
        return new CardSet(getSelectedCardsInHand());
    }

    public void dealHand(){
        for (int i = 0; i < 5; ++i){
            CommandCard card = deck.draw();
            logger.info("Adding " + card + " to " + server.name() + "'s hand");
            hand.add(card);
        }
    }

    public void discardFromHand(List<CommandCard> cards){
        hand.removeAll(cards);
        deck.discard(cards);
    }

    public void discardFromHand(CardSet cardSet){
        cardSet.deselectAll();
        discardFromHand(cardSet.getCards());
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

    public Image getImage() {
        return image;
    }
}
