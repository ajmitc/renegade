package renegade.game.card;

import renegade.game.Containment;
import renegade.game.Game;
import renegade.view.ImageUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommandCard implements Card{
    private Image image;
    private List<Containment> cost = new ArrayList<>();
    private List<Containment> commands = new ArrayList<>();
    private boolean execute;

    public CommandCard(String filename, Containment[] commands){
        this(filename, false, new Containment[]{}, commands);
    }

    public CommandCard(String filename, Containment[] cost, Containment[] commands){
        this(filename, false, cost, commands);
    }

    public CommandCard(String filename, boolean execute, Containment[] cost, Containment[] commands) {
        image = ImageUtil.get(filename);
        this.execute = execute;
        addCost(cost);
        addCommands(commands);
    }

    public void doAlso(Game game){

    }

    public void doExecute(Game game){

    }

    public Image getImage() {
        return image;
    }

    public List<Containment> getCommands() {
        return commands;
    }

    public List<Containment> getCost() {
        return cost;
    }

    public void addCost(Containment ... containments){
        for (Containment containment: containments)
            cost.add(containment);
    }

    public void addCommands(Containment ... containments){
        for (Containment containment: containments)
            commands.add(containment);
    }

    public boolean hasExecute() {
        return execute;
    }
}
