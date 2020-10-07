package renegade.game;

import static renegade.game.Server.*;

public enum Containment {
    VIRUS(RED),
    DATA_NODE(BLUE),
    UPLINK(GREEN),
    REPLICANT(YELLOW),
    ROOTKIT(PURPLE),
    ANY_CONTAINMENT(null),

    PROPAGATOR(RED),
    DATA_PORT(BLUE),
    NEURAL_HUB(GREEN),
    REPLICATOR(YELLOW),
    ANY_INSTALLATION(null);

    private Server server;
    Containment(Server server) {
        this.server = server;
    }

    public boolean isInstallation(){
        return this == PROPAGATOR ||
                this == DATA_PORT ||
                this == NEURAL_HUB ||
                this == REPLICATOR;
    }
}
