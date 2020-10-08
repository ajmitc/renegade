package renegade.game.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck = new ArrayList<>();
    private List<Card> discardList = new ArrayList<>();

    public Deck() {

    }


    public Card draw(){
        if (deck.isEmpty()){
            return null;
        }
        return deck.remove(0);
    }


    public void discard(Card card){
        discardList.add(card);
    }


    public void shuffleDiscard(){
        deck.addAll(discardList);
        discardList.clear();
        Collections.shuffle(deck);
    }

    public int size(){return deck.size();}

    public boolean isEmpty(){return size() == 0;}


    public void addCard(Card card){
        deck.add(card);
    }
}
