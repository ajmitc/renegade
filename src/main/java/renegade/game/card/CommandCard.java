package renegade.game.card;

import renegade.game.Server;
import renegade.game.Game;
import renegade.game.Server;
import renegade.view.ImageUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommandCard implements Card{
    public static final int CARD_WIDTH = 175;

    private String filename;
    private Image fullSizeImage;
    private Image image;
    private List<Server> cost = new ArrayList<>();
    private List<Server> commands = new ArrayList<>();
    private boolean execute;
    private boolean selected;
    private boolean interrupt;
    private boolean revealed;

    public CommandCard(String filename, Server[] commands){
        this(filename, false, new Server[]{}, commands);
    }

    public CommandCard(String filename, Server[] cost, Server[] commands){
        this(filename, false, cost, commands);
    }

    public CommandCard(String filename, boolean execute, Server[] cost, Server[] commands) {
        this.filename = filename;
        fullSizeImage = ImageUtil.get(filename, filename + "fullsize");
        image = ImageUtil.get(filename, CARD_WIDTH);
        this.execute = execute;
        addCost(cost);
        addCommands(commands);
        selected = false;
        interrupt = false;
        revealed = false;
    }

    public void doAlso(Game game){

    }

    public void doExecute(Game game){

    }

    public void doInterrupt(Game game){

    }

    public void doReveal(Game game){

    }

    public Image getImage() {
        return image;
    }

    public Image getFullSizeImage() {
        return fullSizeImage;
    }

    public List<Server> getCommands() {
        return commands;
    }

    public List<Server> getCost() {
        return cost;
    }

    public void addCost(Server ... containments){
        for (Server containment: containments)
            cost.add(containment);
    }

    public void addCommands(Server ... containments){
        for (Server containment: containments)
            commands.add(containment);
    }

    public boolean hasExecute() {
        return execute;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isInterrupt() {
        return interrupt;
    }

    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public String toString(){
        return filename;
    }
}
