package renegade.game;

public class Contaminant {
    private ContaminantType type;
    private boolean neutralized;

    public Contaminant(ContaminantType type){
        this.type = type;
    }

    public void flip(){
        Server flippedServer = type.getServer().flip();
        if (type.isInstallation()){
            type = ContaminantType.installationOfServer(flippedServer);
        }
        else {
            type = ContaminantType.ofServer(flippedServer);
        }
    }

    public ContaminantType getType() {
        return type;
    }

    public boolean isNeutralized() {
        return neutralized;
    }

    public void setNeutralized(boolean neutralized) {
        this.neutralized = neutralized;
    }

    @Override
    public String toString() {
        return "" + type;
    }
}
