package renegade.game;

public class ContaminantPlacement {
    private ContaminantType contaminant;
    private Server server;

    public ContaminantPlacement(ContaminantType contaminant, Server server){
        this.contaminant = contaminant;
        this.server = server;
    }

    public ContaminantType getContainment() {
        return contaminant;
    }

    public Server getServer() {
        return server;
    }
}
