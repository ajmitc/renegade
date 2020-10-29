package renegade.game;

import renegade.game.board.Board;
import renegade.game.card.*;
import renegade.game.smc.SMC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private GamePhase phase;
    private GamePhaseStep phaseStep;
    private Board board;
    // Player at index 0 is Alpha Player
    private List<Avatar> avatars = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private SMC smc;
    private Map<SecurityLevel, List<CountermeasureGoalCard>> countermeasureGoals = new HashMap<>();
    private int countermeasureGoalCardIndex = 0;
    private SecurityLevel securityLevel = SecurityLevel.COPPER;
    private boolean currentPlayerPassed = false;
    private CommandDeck hackShackDeck = new CommandDeck(null);
    private List<CommandCard> hackShackMarket = new ArrayList<>(4);
    private int score = 0;

    private List<ContaminantPlacement> contaminantPlacements = new ArrayList<>();

    public Game() {
        phase = GamePhase.SETUP;
        phaseStep = GamePhaseStep.START_PHASE;
        board = new Board();
    }

    public void setupAvatars(){
        // Place avatars on board
        // Place a containment matching their color on partition 6
        avatars.stream().forEach(avatar -> {
            board.getServerTiles().stream().filter(st -> st.getServer() == avatar.getServer()).forEach(st -> {
                st.getPartition(6).getAvatars().add(avatar);
                board.addContaminant(ContaminantType.ofServer(st.getServer()), st.getPartition(6));
            });
        });
    }

    public void setupSMC(){
        // Add CountermeasureGoal cards
        CountermeasureGoalCopperDeck copperDeck = new CountermeasureGoalCopperDeck();
        CountermeasureGoalSilverDeck silverDeck = new CountermeasureGoalSilverDeck();
        CountermeasureGoalGoldDeck goldDeck     = new CountermeasureGoalGoldDeck();

        countermeasureGoals.put(SecurityLevel.COPPER, new ArrayList<>());
        countermeasureGoals.put(SecurityLevel.SILVER, new ArrayList<>());
        countermeasureGoals.put(SecurityLevel.GOLD, new ArrayList<>());
        for (int i = 0; i < smc.getNumCopperCountermeasureGoals(); ++i){
            countermeasureGoals.get(SecurityLevel.COPPER).add(copperDeck.draw());
        }
        for (int i = 0; i < smc.getNumSilverCountermeasureGoals(); ++i){
            countermeasureGoals.get(SecurityLevel.SILVER).add(silverDeck.draw());
        }
        for (int i = 0; i < smc.getNumGoldCountermeasureGoals(); ++i){
            countermeasureGoals.get(SecurityLevel.GOLD).add(goldDeck.draw());
        }

        // Do special SMC setup
        smc.setup(this);
    }

    public void refillHackShackMarket(){
        while (hackShackMarket.size() < 4){
            hackShackMarket.add(hackShackDeck.draw());
        }
    }

    public void purgeHackShackMarket(){
        for(CommandCard card: hackShackMarket){
            hackShackDeck.discard(card);
        }
        hackShackMarket.clear();
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
        if (currentPlayerIndex >= 0 && currentPlayerIndex < avatars.size())
            return avatars.get(currentPlayerIndex);
        return null;
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

    public void setCurrentPlayerPassed(boolean currentPlayerPassed) {
        this.currentPlayerPassed = currentPlayerPassed;
    }

    public boolean isCurrentPlayerPassed() {
        return currentPlayerPassed;
    }

    public List<CountermeasureGoalCard> getCountermeasureGoals(SecurityLevel securityLevel) {
        return countermeasureGoals.get(securityLevel);
    }

    public List<CountermeasureGoalCard> getCountermeasureGoals() {
        return countermeasureGoals.get(securityLevel);
    }

    public CountermeasureGoalCard getCountermeasureGoal(){
        return countermeasureGoals.get(securityLevel).get(countermeasureGoalCardIndex);
    }

    public SecurityLevel getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(SecurityLevel securityLevel) {
        this.securityLevel = securityLevel;
    }

    public void setNextCountermeasureGoal(){
        countermeasureGoalCardIndex += 1;
        List<CountermeasureGoalCard> goals = countermeasureGoals.get(securityLevel);
        if (countermeasureGoalCardIndex >= goals.size()){
            securityLevel = SecurityLevel.values()[securityLevel.ordinal() + 1];
            countermeasureGoalCardIndex = 0;
        }
    }

    public List<CommandCard> getHackShackMarket() {
        return hackShackMarket;
    }

    public List<ContaminantPlacement> getContaminantPlacements() {
        return contaminantPlacements;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void adjScore(int amount){
        this.score += amount;
    }
}
