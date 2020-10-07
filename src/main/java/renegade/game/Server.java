package renegade.game;

public enum Server {
    RED(""),
    GREEN(""),
    BLUE(""),
    YELLOW(""),
    PURPLE("Faith");

    private String name;
    Server(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
