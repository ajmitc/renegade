package renegade.game;

import renegade.util.Util;

public class GameUtil {
    public static Server getRandomServer(){
        return Server.values()[Util.roll() - 1];
    }

    public static int getRandomPartition(){
        return Util.roll();
    }

    public static int rollVirus(){
        return Util.roll();
    }

    public static int rollCountermeasure(){
        return Util.roll();
    }

    private GameUtil() {

    }
}
