package renegade.game;

import renegade.game.board.Board;
import renegade.game.smc.SMC;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private GamePhase phase;
    private GamePhaseStep phaseStep;
    private Board board;
    // Player at index 0 is Alpha Player
    private List<Avatar> avatars = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private SMC smc;

    public Game() {
        phase = GamePhase.SETUP;
        phaseStep = GamePhaseStep.START_PHASE;
        board = new Board();
    }

    public void setupAvatars(){
        // Place avatars on board
        avatars.stream().forEach(avatar -> {
            board.getServerTiles().stream().filter(st -> st.getServer() == avatar.getServer()).forEach(st -> {
                st.getPartition(6).getAvatars().add(avatar);
            });
        });
    }

    public void setupSMC(){
        smc.setup(this);
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

    public Board getBoard() {
        return board;
    }

    public List<Avatar> getAvatars() {
        return avatars;
    }

    public Avatar getCurrentPlayer() {
        return avatars.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % avatars.size();
    }

    public SMC getSmc() {
        return smc;
    }

    public void setSmc(SMC smc) {
        this.smc = smc;
    }
}
