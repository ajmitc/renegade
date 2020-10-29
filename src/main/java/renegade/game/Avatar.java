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
    private Image tokenImage;
    private Image matImage;

    public Avatar(Server server) {
        this(server, true);
    }

    public Avatar(Server server, boolean primary) {
        this.server = server;
        deck = new CommandDeck(server);

        switch (server){
            case RED:
                if (primary) {
                    tokenImage = ImageUtil.get("Player Token Red 1.png");
                    matImage   = ImageUtil.get("Red Player Board2.jpg");
                }
                else {
                    tokenImage = ImageUtil.get("Player Token Red 2.png");
                    matImage   = ImageUtil.get("Red Player Board1.jpg");
                }
                break;
            case BLUE:
                if (primary) {
                    tokenImage = ImageUtil.get("Player Token Blue 2.png");
                    matImage = ImageUtil.get("Blue Player Board1.jpg");
                }
                else {
                    tokenImage = ImageUtil.get("Player Token Blue 1.png");
                    matImage = ImageUtil.get("Blue Player Board2.jpg");
                }
                break;
            case GREEN:
                if (primary) {
                    tokenImage = ImageUtil.get("Player Token Green 1.png");
                    matImage   = ImageUtil.get("Green Player Board2.jpg");
                }
                else {
                    tokenImage = ImageUtil.get("Player Token Green 2.png");
                    matImage   = ImageUtil.get("Green Player Board1.jpg");
                }
                break;
            case PURPLE:
                tokenImage = ImageUtil.get("Player Token Violet 1.png");
                matImage   = ImageUtil.get("Violet Player Board2.jpg");
                break;
            case YELLOW:
                if (primary) {
                    tokenImage = ImageUtil.get("Player Token Yellow 2.png");
                    matImage   = ImageUtil.get("Yellow Player Board1.jpg");
                }
                else {
                    tokenImage = ImageUtil.get("Player Token Yellow 1.png");
                    matImage   = ImageUtil.get("Yellow Player Board2.jpg");
                }
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

    public Image getTokenImage() {
        return tokenImage;
    }

    public Image getMatImage() {
        return matImage;
    }
}
