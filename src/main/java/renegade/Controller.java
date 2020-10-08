package renegade;

import renegade.game.GamePhase;
import renegade.game.GamePhaseStep;
import renegade.view.View;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void run(){
        switch (this.model.getGame().getPhase()){
            case SETUP:
                switch (this.model.getGame().getPhaseStep()){
                    case START_PHASE:
                        // TODO Choose Players
                        // TODO Choose SMC
                        this.model.getGame().setPhaseStep(GamePhaseStep.END_PHASE);
                        break;
                    case END_PHASE:
                        this.model.getGame().setupAvatars();
                        this.model.getGame().setupSMC();
                        this.model.getGame().setPhase(GamePhase.START_ROUND);
                        break;
                }
                break;
            case START_ROUND:
                switch (this.model.getGame().getPhaseStep()){
                    case START_PHASE:
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
                switch (this.model.getGame().getPhaseStep()){
                    case START_PHASE:
                        this.model.getGame().getSmc().startOfTurn(this.model.getGame());
                        this.model.getGame().setPhaseStep(GamePhaseStep.EXECUTE_COMMANDS);
                        break;
                    case EXECUTE_COMMANDS:
                        // If all players have empty decks
                        if (this.model.getGame().getAvatars().stream().allMatch(a -> a.getDeck().isEmpty())) {
                            this.model.getGame().setPhaseStep(GamePhaseStep.END_PHASE);
                            break;
                        }

                        // TODO If current player has played all cards in hand or passed, set next player

                        if (!this.model.getGame().getCurrentPlayer().getDeck().isEmpty()) {
                            this.model.getGame().getCurrentPlayer().dealHand();
                        }
                        this.model.getGame().nextPlayer();

                        break;
                    case END_PHASE:
                        this.model.getGame().setPhase(GamePhase.END_ROUND);
                        break;
                }
                break;
            case END_ROUND:
                switch (this.model.getGame().getPhaseStep()){
                    case START_PHASE:
                        this.model.getGame().setPhaseStep(GamePhaseStep.END_PHASE);
                        break;
                    case END_PHASE:
                        this.model.getGame().setPhase(GamePhase.START_ROUND);
                        break;
                }
                break;
        }
    }
}
