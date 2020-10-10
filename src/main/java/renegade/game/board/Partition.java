package renegade.game.board;

import renegade.game.Avatar;
import renegade.game.Containment;
import renegade.game.Countermeasure;
import renegade.game.Server;

import java.util.ArrayList;
import java.util.List;

public class Partition {
    private Server server;
    private int number;
    private List<Containment> containments = new ArrayList<>();
    private List<Countermeasure> countermeasures = new ArrayList<>();
    private List<Avatar> avatars = new ArrayList<>();

    private int x, y;

    public Partition(Server server, int number) {
        this.server = server;
        this.number = number;
        this.x = 0;
        this.y = 0;
    }

    public int countContainments(final Containment containment){
        return (int) containments.stream().filter(c -> c == containment).count();
    }

    public int countCountermeasures(final Countermeasure countermeasure){
        return (int) countermeasures.stream().filter(c -> c == countermeasure).count();
    }

    public Server getServer() {
        return server;
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
