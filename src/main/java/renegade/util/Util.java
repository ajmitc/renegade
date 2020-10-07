package renegade.util;

import java.util.Date;
import java.util.Random;

public class Util {
    private static Random GEN = new Random(new Date().getTime());

    public static int roll(){
        return GEN.nextInt(6) + 1;
    }

    private Util() {

    }
}
