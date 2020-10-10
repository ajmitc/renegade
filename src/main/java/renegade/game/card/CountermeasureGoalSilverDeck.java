package renegade.game.card;

public class CountermeasureGoalSilverDeck extends Deck<CountermeasureGoalCard>{
    public CountermeasureGoalSilverDeck(){
        super();
        addCard(new CountermeasureGoalCopper1());

        shuffle();
    }
}
