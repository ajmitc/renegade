package renegade.game;

public enum Server {
    RED("Salvation", "Virus", "Propagator", "Destruction"),
    GREEN("Freedom", "Uplink", "Neural Hub", "Cognition"),
    BLUE("Justice", "Data Node", "Data Port", "Information"),
    YELLOW("Virtue", "Replicant", "Replicator", "Deception"),
    PURPLE("Faith", "Rootkit", "", "Leadership");

    private String serverName;
    private String contaminantName;
    private String installationName;
    private String commandName;
    Server(String serverName, String contaminantName, String installationName, String commandName) {
        this.serverName = serverName;
        this.contaminantName = contaminantName;
        this.installationName = installationName;
        this.commandName = commandName;
    }

    public String getServerName() {
        return serverName;
    }

    public String getContaminantName() {
        return contaminantName;
    }

    public String getInstallationName() {
        return installationName;
    }

    public String getCommandName() {
        return commandName;
    }

    public Server flip(){
        switch (this){
            case BLUE:
                return GREEN;
            case GREEN:
                return BLUE;
            case RED:
                return YELLOW;
            case YELLOW:
                return RED;
        }
        return this;
    }

    @Override
    public String toString() {
        return serverName;
    }
}
