package renegade.game.card;

public class CountermeasureGoalGoldDeck extends Deck<CountermeasureGoalCard>{
    public CountermeasureGoalGoldDeck(){
        super();
        addCard(new CountermeasureGoalCopper1());

        shuffle();
    }
}
