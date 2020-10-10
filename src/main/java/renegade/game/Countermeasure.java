package renegade.game;

public enum Countermeasure {
    SPARK,
    FLARE,
    GUARDIAN,
    FIREWALL;

    public boolean isInstallation(){
        return this == GUARDIAN || this == FIREWALL;
    }
}
