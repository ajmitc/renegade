package renegade.game;

public class Avatar {
    private Server server;

    public Avatar(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
}
