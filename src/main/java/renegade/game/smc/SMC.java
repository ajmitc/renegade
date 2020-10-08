package renegade.game.smc;

import renegade.game.Game;
import renegade.view.ImageUtil;

import java.awt.*;

public class SMC {
    private String name;
    private Image image;
    private int numCopper;
    private int numSilver;
    private int numGold;
    private int numCopperCountermeasures;
    private int numSilverCountermeasures;
    private int numGoldCountermeasures;

    public SMC(String name, int copper, int silver, int gold, int copperCM, int silverCM, int goldCM, String filename) {
        this.name = name;
        this.numCopper = copper;
        this.numSilver = silver;
        this.numGold = gold;
        this.numCopperCountermeasures = copperCM;
        this.numSilverCountermeasures = silverCM;
        this.numGoldCountermeasures = goldCM;
        image = ImageUtil.get(filename);
    }

    public void setup(Game game){

    }

    public void startOfTurn(Game game){

    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public int getNumCopper() {
        return numCopper;
    }

    public int getNumSilver() {
        return numSilver;
    }

    public int getNumGold() {
        return numGold;
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
}
