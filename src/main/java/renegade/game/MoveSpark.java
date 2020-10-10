package renegade.game;

public class MoveSpark {
    private Server server;
    private boolean up;

    public MoveSpark(Server server, boolean up){
        this.server = server;
        this.up = up;
    }

    public Server getServer() {
        return server;
    }

    public boolean isUp() {
        return up;
    }
}
