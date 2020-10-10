package renegade;

import renegade.game.*;
import renegade.game.card.CountermeasureGoalCard;
import renegade.game.smc.AlphaMobySMC;
import renegade.util.Util;
import renegade.view.LogPanel;
import renegade.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class Controller extends MouseAdapter {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        this.view.getGamePanel().getBoardPanel().addMouseListener(this);
        this.view.getGamePanel().getBoardPanel().addMouseMotionListener(this);

        this.view.getMainMenuPanel().getBtnNewGame().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setGame(new Game());
                view.showGame();
                view.refresh();
                view.getGamePanel().getLogPanel().writeln("New Game Started", LogPanel.BOLD_ITALIC);
                run();
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    public void run(){
        while (this.model.getGame().getPhase() != GamePhase.GAMEOVER) {
            switch (this.model.getGame().getPhase()) {
                case SETUP:
                    switch (this.model.getGame().getPhaseStep()) {
                        case START_PHASE:
                            this.view.getGamePanel().getLogPanel().writeln("*** SETUP ***", LogPanel.BOLD_ITALIC);
                            // TODO Choose Players
                            this.model.getGame().getAvatars().add(new Avatar(Server.BLUE));
                            this.model.getGame().getAvatars().add(new Avatar(Server.YELLOW));
                            // TODO Choose SMC
                            this.model.getGame().setSmc(new AlphaMobySMC());
                            this.view.getGamePanel().getLogPanel().writeln("SMC: " + this.model.getGame().getSmc().getName());

                            this.model.getGame().setPhaseStep(GamePhaseStep.END_PHASE);
                            break;
                        case END_PHASE:
                            this.model.getGame().setupAvatars();
                            this.model.getGame().setupSMC();
                            this.model.getGame().setPhase(GamePhase.INTEL);
                            break;
                    }
                    break;
                case INTEL:
                    switch (this.model.getGame().getPhaseStep()) {
                        case START_PHASE:
                            this.view.getGamePanel().getLogPanel().writeln("*** INTEL ***", LogPanel.BOLD_ITALIC);
                            this.model.getGame().setPhaseStep(GamePhaseStep.END_PHASE);
                            break;
                        case END_PHASE:
                            // Deal hand of command cards
                            this.model.getGame().getAvatars().stream().forEach(avatar -> {
                                avatar.getDeck().shuffleDiscard();
                                avatar.dealHand();
                            });
                            this.model.getGame().setPhase(GamePhase.COMMANDS);
                            break;
                    }
                    break;
                case COMMANDS:
                    switch (this.model.getGame().getPhaseStep()) {
                        case START_PHASE:
                            this.view.getGamePanel().getLogPanel().writeln("*** COMMAND ACTIONS ***", LogPanel.BOLD_ITALIC);
                            this.model.getGame().setPhaseStep(GamePhaseStep.COMMANDS_START_TURN);
                            break;
                        case COMMANDS_START_TURN:
                            // Refill Hack Shack
                            this.model.getGame().refillHackShackMarket();
                            // Add new sparks per Security Level
                            this.model.getGame().getSmc().startOfTurn(this.model.getGame());
                            // TODO Check for Guardian/Firewall creation and explosions
                            this.model.getGame().setPhaseStep(GamePhaseStep.EXECUTE_COMMANDS);
                            break;
                        case EXECUTE_COMMANDS:
                            if (!this.model.getGame().getCurrentPlayer().getHand().isEmpty() && !this.model.getGame().isCurrentPlayerPassed()) {
                                return;
                            }

                            this.model.getGame().setPhaseStep(GamePhaseStep.COMMANDS_END_TURN);
                            break;
                        case COMMANDS_END_TURN:
                            this.model.getGame().getSmc().endOfTurn(this.model.getGame());

                            // If all players have empty decks
                            if (this.model.getGame().getAvatars().stream().allMatch(a -> a.getDeck().isEmpty())) {
                                this.model.getGame().setPhaseStep(GamePhaseStep.END_PHASE);
                                break;
                            }
                            // Replenish hand
                            if (!this.model.getGame().getCurrentPlayer().getDeck().isEmpty()) {
                                this.model.getGame().getCurrentPlayer().dealHand();
                            }
                            // Set next player
                            this.model.getGame().nextPlayer();
                            this.model.getGame().setPhaseStep(GamePhaseStep.COMMANDS_START_TURN);
                            break;
                        case END_PHASE:
                            this.model.getGame().setPhase(GamePhase.COUNTERMEASURES);
                            break;
                    }
                    break;
                case COUNTERMEASURES:
                    switch (this.model.getGame().getPhaseStep()) {
                        case START_PHASE:
                            this.view.getGamePanel().getLogPanel().writeln("*** COUNTERMEASURES ***", LogPanel.BOLD_ITALIC);
                            this.model.getGame().setPhaseStep(GamePhaseStep.COUNTERMEASURES_SMC_REVENGE);
                            break;
                        case COUNTERMEASURES_SMC_REVENGE:
                            // Virus Battle Segment
                            this.model.getGame().getBoard().getServerTiles().stream().forEach(st -> {
                                st.getPartitions().stream().forEach(p -> {
                                    if (p.getContainments().contains(Containment.VIRUS) && !p.getCountermeasures().isEmpty()){
                                        int virusRoll = Util.roll();
                                        int cmRoll = Util.roll();
                                        virusRoll += p.countContainments(Containment.VIRUS);
                                        cmRoll += p.countCountermeasures(Countermeasure.SPARK) + (4 * p.countCountermeasures(Countermeasure.GUARDIAN));
                                        if (virusRoll > cmRoll){
                                            p.getCountermeasures().clear();
                                        }
                                        else {
                                            p.getContainments().remove(Containment.VIRUS);
                                        }
                                    }
                                });
                            });

                            // Delete Containments Segment
                            this.model.getGame().getBoard().getServerTiles().stream().forEach(st -> {
                                st.getPartitions().stream().filter(p -> !p.getContainments().isEmpty() && !p.getCountermeasures().isEmpty()).forEach(p -> {
                                    List<Containment> installations = p.getContainments().stream().filter(c -> c.isInstallation()).collect(Collectors.toList());
                                    p.getContainments().clear();
                                    p.getContainments().addAll(installations);
                                });
                            });

                            // Delete Installations Segment
                            this.model.getGame().getBoard().getServerTiles().stream().forEach(st -> {
                                st.getPartitions().stream().filter(p -> p.getCountermeasures().contains(Countermeasure.GUARDIAN)).forEach(p -> {
                                    p.getContainments().clear();
                                });
                            });

                            this.model.getGame().setPhaseStep(GamePhaseStep.COUNTERMEASURES_SUCCESS_FAILURE);
                            break;
                        case COUNTERMEASURES_SUCCESS_FAILURE:
                            // Determine if completed Countermeasures Goal Card
                            boolean success = true;
                            List<CountermeasureGoalCard> goals = this.model.getGame().getCountermeasureGoals();
                            for (CountermeasureGoalCard goal: goals){
                                if (!goal.goalSatisfied(model.getGame())){
                                    success = false;
                                    break;
                                }
                            }

                            // Resolve the success/failure
                            for (CountermeasureGoalCard goal: goals){
                                if (success){
                                    goal.applySuccess(model.getGame());
                                }
                                else {
                                    goal.applyFailure(model.getGame());
                                }
                                if (!goal.isResolved()){
                                    return;
                                }
                            }

                            this.model.getGame().setNextCountermeasureGoal();
                            this.model.getGame().setPhaseStep(GamePhaseStep.COUNTERMEASURES_MOVE_SPARKS);
                            break;
                        case COUNTERMEASURES_SUCCESS_FAILURE_PLACE:
                            if (!this.model.getGame().getContainmentPlacements().isEmpty())
                                return;
                            this.model.getGame().setPhaseStep(GamePhaseStep.COUNTERMEASURES_SUCCESS_FAILURE);
                            break;
                        case COUNTERMEASURES_MOVE_SPARKS:
                            if (this.model.getGame().getSmc().isMoveSparks()) {
                                // Move sparks based on success/failure orientation
                                for (CountermeasureGoalCard goal : this.model.getGame().getCountermeasureGoals()) {
                                    for (MoveSpark moveSpark : goal.getMoveSparks()) {
                                        Server server = moveSpark.getServer();
                                        boolean up = goal.isSuccess() ? moveSpark.isUp() : !moveSpark.isUp();
                                        model.getGame().getBoard().getServerTile(server).moveSparks(up);
                                    }
                                }
                            }
                            // TODO Check for Guardian/Firewall creation and explosions
                            this.model.getGame().setPhaseStep(GamePhaseStep.COUNTERMEASURES_SCORING);
                            break;
                        case COUNTERMEASURES_SCORING:
                            // Gain scoring token if completed all Countermeasure Goal cards at current security level
                            if (this.model.getGame().getCountermeasureGoals().stream().allMatch(c -> c.isSuccess())){
                                this.model.getGame().adjScore(25);
                            }
                            this.model.getGame().setPhaseStep(GamePhaseStep.END_PHASE);
                            break;
                        case END_PHASE:
                            this.model.getGame().setPhase(GamePhase.REFRESH);
                            break;
                    }
                    break;
                case REFRESH:
                    switch (this.model.getGame().getPhaseStep()) {
                        case START_PHASE:
                            this.view.getGamePanel().getLogPanel().writeln("*** REFRESH ***", LogPanel.BOLD_ITALIC);
                            // Check end of game (all Countermeasure Goal cards resolved)
                            if (this.model.getGame().getSecurityLevel() == SecurityLevel.GOLD){
                                this.model.getGame().setPhase(GamePhase.GAMEOVER);
                                return;
                            }
                            // Set the next Security Level
                            this.model.getGame().setNextCountermeasureGoal();
                            this.model.getGame().setPhaseStep(GamePhaseStep.END_PHASE);
                            break;
                        case END_PHASE:
                            // Reshuffle all player's cards and deal new hand
                            this.model.getGame().getAvatars().forEach(avatar -> {
                                avatar.getDeck().discard(avatar.getHand());
                                avatar.getDeck().shuffleDiscard();
                                avatar.dealHand();
                            });

                            // Purge Hack Shack and draw new market
                            this.model.getGame().purgeHackShackMarket();
                            this.model.getGame().refillHackShackMarket();

                            this.model.getGame().setPhase(GamePhase.INTEL);
                            break;
                    }
                    break;
            }
        }
    }
}
