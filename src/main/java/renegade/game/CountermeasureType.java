package renegade.game;

public enum CountermeasureType {
    SPARK("Spark.png"),
    FLARE("Flare.png"),
    GUARDIAN("Guardian.jpg"),
    FIREWALL("Firewall.jpg");

    private String filename;
    CountermeasureType(String filename){
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public boolean isInstallation(){
        return this == GUARDIAN || this == FIREWALL;
    }
}
