package renegade.game.board;

import renegade.game.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Partition {
    private Server server;
    private int number;
    private List<Contaminant> contaminants = new ArrayList<>();
    private List<Countermeasure> countermeasures = new ArrayList<>();
    private List<Avatar> avatars = new ArrayList<>();
    private Rectangle bounds;

    private int x, y;

    public Partition(Server server, int number) {
        this.server = server;
        this.number = number;
        this.x = 0;
        this.y = 0;
    }

    public int countContaminants(final ContaminantType ... contaminantTypes){
        return (int) contaminants.stream().filter(c -> Arrays.stream(contaminantTypes).anyMatch(ct -> ct == c.getType())).count();
    }

    public int countCountermeasures(final CountermeasureType ... countermeasureTypes){
        return (int) countermeasures.stream().filter(c -> Arrays.stream(countermeasureTypes).anyMatch(ct -> ct == c.getType())).count();
    }

    public int countUniqueContaminants(){
        return (int) contaminants.stream().map(c -> c.getType()).distinct().count();
    }

    public int countUniqueCountermeasures(){
        return (int) countermeasures.stream().map(c -> c.getType()).distinct().count();
    }

    public List<Server> getColorsWithAtLeastNContaminants(int count){
        List<Server> servers = new ArrayList<>();
        Arrays.stream(Server.values()).forEach(s -> {
            if (countContaminants(ContaminantType.ofServer(s)) >= count)
                servers.add(s);
        });
        return servers;
    }

    public void addContaminant(Contaminant contaminant){
        getContaminants().add(contaminant);
    }

    public void addCountermeasure(Countermeasure countermeasure){
        getCountermeasures().add(countermeasure);
    }

    /**
     * Be sure to return the Countermeasure to the pool!
     * @param type
     * @return
     */
    public Countermeasure removeCountermeasure(CountermeasureType type){
        Optional<Countermeasure> opt = countermeasures.stream().filter(c -> c.getType() == type).findFirst();
        if (opt.isPresent()){
            countermeasures.remove(opt.get());
            return opt.get();
        }
        return null;
    }

    /**
     * Be sure to return the Contaminant to the pool!
     * @param type
     * @return
     */
    public Contaminant removeContaminant(ContaminantType type){
        Optional<Contaminant> opt = contaminants.stream().filter(c -> c.getType() == type).findFirst();
        if (opt.isPresent()){
            contaminants.remove(opt.get());
            return opt.get();
        }
        return null;
    }

    public Server getServer() {
        return server;
    }

    public int getNumber() {
        return number;
    }

    public boolean hasContaminants(){ return !contaminants.isEmpty(); }

    public List<Contaminant> getContaminants() {
        return contaminants;
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

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public String toString(){
        return server.name() + "/" + number;
    }
}
