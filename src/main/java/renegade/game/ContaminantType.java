package renegade.game;

import static renegade.game.Server.*;

public enum ContaminantType {
    VIRUS(RED, "Virus.png"),
    DATA_NODE(BLUE, "Data Node.png"),
    UPLINK(GREEN, "Uplink.png"),
    REPLICANT(YELLOW, "Replicant.png"),
    ROOTKIT(PURPLE, "Rootkit.png"),
    ANY_CONTAINMENT(null, null),

    PROPAGATOR(RED, "Propagator.jpg"),
    DATA_PORT(BLUE, "Data Port.jpg"),
    NEURAL_HUB(GREEN, "Neural Hub.jpg"),
    REPLICATOR(YELLOW, "Replicator.jpg"),
    ANY_INSTALLATION(null, null);

    private Server server;
    private String filename;
    ContaminantType(Server server, String filename) {
        this.server = server;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public Server getServer() {
        return server;
    }

    public boolean isInstallation(){
        return this == PROPAGATOR ||
                this == DATA_PORT ||
                this == NEURAL_HUB ||
                this == REPLICATOR;
    }

    public static ContaminantType ofServer(Server server){
        for (ContaminantType contaminant : ContaminantType.values()){
            if (!contaminant.isInstallation() && contaminant.getServer() == server)
                return contaminant;
        }
        return null;
    }

    public static ContaminantType installationOfServer(Server server){
        for (ContaminantType contaminant : ContaminantType.values()){
            if (contaminant.isInstallation() && contaminant.getServer() == server)
                return contaminant;
        }
        return null;
    }
}
