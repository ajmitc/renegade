package renegade.game.board;

import renegade.game.Avatar;
import renegade.game.Containment;
import renegade.game.Countermeasure;

import java.util.ArrayList;
import java.util.List;

public class Partition {
    private int number;
    private List<Containment> containments = new ArrayList<>();
    private List<Countermeasure> countermeasures = new ArrayList<>();
    private List<Avatar> avatars = new ArrayList<>();

    private int x, y;

    public Partition(int number) {
        this.number = number;
        this.x = 0;
        this.y = 0;
    }

    public int getNumber() {
        return number;
    }

    public List<Containment> getContainments() {
        return containments;
    }

    public List<Countermeasure> getCountermeasures() {
        return countermeasures;
    }

    public List<Avatar> getAvatars() {
        return avatars;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoords(int x, int y){
        this.x = x;
        this.y = y;
    }
}
