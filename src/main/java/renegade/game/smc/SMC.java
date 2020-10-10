package renegade.game.smc;

import renegade.game.*;
import renegade.view.ImageUtil;

import java.awt.*;

public class SMC {
    public static final int SMC_CARD_WIDTH = 250;

    private String name;
    private Image image;
    private int numCopperCountermeasureGoals;
    private int numSilverCountermeasureGoals;
    private int numGoldCountermeasureGoals;
    private int numCopperCountermeasures;
    private int numSilverCountermeasures;
    private int numGoldCountermeasures;
    private boolean moveSparks;

    public SMC(String name, int copper, int silver, int gold, int copperCM, int silverCM, int goldCM, String filename) {
        this.name = name;
        this.numCopperCountermeasureGoals = copper;
        this.numSilverCountermeasureGoals = silver;
        this.numGoldCountermeasureGoals = gold;
        this.numCopperCountermeasures = copperCM;
        this.numSilverCountermeasures = silverCM;
        this.numGoldCountermeasures = goldCM;
        this.moveSparks = true;
        image = ImageUtil.get(filename, SMC_CARD_WIDTH, null);
    }

    public void setup(Game game){

    }

    public void startOfTurn(Game game){
        addStartOfTurnCountermeasures(game);
    }

    public void addStartOfTurnCountermeasures(Game game){
        int numCountermeasures = 0;
        switch (game.getSecurityLevel()){
            case COPPER -> numCountermeasures = numCopperCountermeasures;
            case SILVER -> numCountermeasures = numSilverCountermeasures;
            case GOLD ->   numCountermeasures = numGoldCountermeasures;
        }
        placeStartOfTurnCountermeasures(game, numCountermeasures);
    }

    public void placeStartOfTurnCountermeasures(Game game, int numCountermeasures){
        for (int i = 0; i < numCountermeasures; ++i){
            placeStartOfTurnCountermeasure(game);
        }
    }

    public void placeStartOfTurnCountermeasure(Game game){
        // Roll server die and 1d6
        Server server = GameUtil.getRandomServer();
        int partition = GameUtil.getRandomPartition();
        game.getBoard().getServerTile(server).getPartition(partition).getCountermeasures().add(Countermeasure.SPARK);
    }

    public void endOfTurn(Game game){}

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public int getNumCopperCountermeasureGoals() {
        return numCopperCountermeasureGoals;
    }

    public int getNumSilverCountermeasureGoals() {
        return numSilverCountermeasureGoals;
    }

    public int getNumGoldCountermeasureGoals() {
        return numGoldCountermeasureGoals;
    }

    public int getNumCopperCountermeasures() {
        return numCopperCountermeasures;
    }

    public int getNumSilverCountermeasures() {
        return numSilverCountermeasures;
    }

    public int getNumGoldCountermeasures() {
        return numGoldCountermeasures;
    }

    public boolean isMoveSparks() {
        return moveSparks;
    }

    public void setMoveSparks(boolean moveSparks) {
        this.moveSparks = moveSparks;
    }
}
