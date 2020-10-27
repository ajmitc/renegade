package renegade.game;

public class Countermeasure {
    private CountermeasureType type;

    public Countermeasure(CountermeasureType type){
        this.type = type;
    }

    public void flip(){
        switch (type){
            case SPARK:
                type = CountermeasureType.FLARE;
                break;
            case FLARE:
                type = CountermeasureType.SPARK;
                break;
            case FIREWALL:
            case GUARDIAN:
                break;
        }
    }

    public CountermeasureType getType() {
        return type;
    }
}
