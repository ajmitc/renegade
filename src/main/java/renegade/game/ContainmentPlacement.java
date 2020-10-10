package renegade.game;

public class ContainmentPlacement {
    private Containment containment;
    private Server server;

    public ContainmentPlacement(Containment containment, Server server){
        this.containment = containment;
        this.server = server;
    }

    public Containment getContainment() {
        return containment;
    }

    public Server getServer() {
        return server;
    }
}
