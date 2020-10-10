package renegade.game.card;

public class CountermeasureGoalCopperDeck extends Deck<CountermeasureGoalCard>{
    public CountermeasureGoalCopperDeck(){
        super();
        addCard(new CountermeasureGoalCopper1());

        shuffle();
    }
}
