package renegade.game.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck<C extends Card> {
    private List<C> deck = new ArrayList<>();
    private List<C> discardList = new ArrayList<>();

    public Deck() {

    }


    public C draw(){
        if (deck.isEmpty()){
            return null;
        }
        return deck.remove(0);
    }


    public void discard(C card){
        discardList.add(card);
    }

    public void discard(List<C> cards){
        discardList.addAll(cards);
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public void shuffleDiscard(){
        deck.addAll(discardList);
        discardList.clear();
        Collections.shuffle(deck);
    }

    public int size(){return deck.size();}

    public boolean isEmpty(){return size() == 0;}


    public void addCard(C card){
        deck.add(card);
    }
}
