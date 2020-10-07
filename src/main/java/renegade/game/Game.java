package renegade.game;

public class Game {
    private GamePhase phase;
    private GamePhaseStep phaseStep;

    public Game() {
        phase = GamePhase.SETUP;
        phaseStep = GamePhaseStep.START_PHASE;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        setPhase(phase, GamePhaseStep.START_PHASE);
    }

    public void setPhase(GamePhase phase, GamePhaseStep phaseStep) {
        this.phase = phase;
        this.phaseStep = phaseStep;
    }

    public GamePhaseStep getPhaseStep() {
        return phaseStep;
    }

    public void setPhaseStep(GamePhaseStep phaseStep) {
        this.phaseStep = phaseStep;
    }
}
