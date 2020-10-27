package renegade;

import renegade.game.*;
import renegade.game.board.Hexagon;
import renegade.game.board.Partition;
import renegade.game.board.ServerTile;
import renegade.game.card.CardSet;
import renegade.game.card.CommandCard;
import renegade.game.card.CountermeasureGoalCard;
import renegade.game.smc.AlphaMobySMC;
import renegade.util.Util;
import renegade.view.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Controller extends MouseAdapter {
    private static Logger logger = Logger.getLogger(Controller.class.getName());

    private Model model;
    private View view;

    private List<ServerTile> placeServerTileList = new ArrayList<>();

    private int movementPoints;
    private Set<Partition> validMovementPartitions = new HashSet<>();
    private boolean allPartitionsValidForMovement = false;

    private int shiftPoints;
    private Partition shiftSourcePartition;
    private Set<Partition> validShiftPartitions = new HashSet<>();

    private int setupDataNodesToPlace = -1;

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

        this.view.getGamePanel().getHandPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CommandCard card = view.getGamePanel().getHandPanel().getSelectedCommandCard(e.getX(), e.getY());
                if (card != null) {
                    if (e.getClickCount() >= 2){
                        CommandCardDetailsDialog detailsDialog = new CommandCardDetailsDialog(card);
                        detailsDialog.setVisible(true);
                    }
                    else {
                        card.setSelected(!card.isSelected());
                        view.refresh();
                    }
                }
            }
        });

        this.view.getGamePanel().getActionPanel().getBtnExecute().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardSet selectedCards = model.getGame().getCurrentPlayer().getSelectedCardsInHandAsSet();
                if (selectedCards.getCards().size() != 1){
                    PopupUtil.popupNotification(view.getFrame(), "Execute", "You can only execute one card at a time");
                    return;
                }
                CommandCard card = selectedCards.getCards().get(0);
                card.doExecute(model.getGame());
                model.getGame().getCurrentPlayer().discardFromHand(selectedCards);
            }
        });

        this.view.getGamePanel().getActionPanel().getBtnMove().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardSet selectedCards = model.getGame().getCurrentPlayer().getSelectedCardsInHandAsSet();
                movementPoints = selectedCards.countCommandPoints(Server.BLUE) + selectedCards.countCommandPoints(Server.PURPLE);
                if (movementPoints == 0){
                    logger.warning("Select at least one Card with an Information Command Point");
                    PopupUtil.popupNotification(view.getFrame(), "Move", "Select at least one Card with an Information (Blue) Command Point");
                    return;
                }
                logger.info("Assigning " + movementPoints + " movement points");
                view.getGamePanel().getLogPanel().writeln("Gained " + movementPoints + " movement points");

                // Identify valid moves and enable those partitions
                updateValidMovementPartitions();

                model.getGame().getCurrentPlayer().discardFromHand(selectedCards);

                model.getGame().setPhaseStep(GamePhaseStep.EXECUTE_COMMANDS_MOVE);
                run();
            }
        });

        this.view.getGamePanel().getActionPanel().getBtnUpload().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Avatar me = model.getGame().getCurrentPlayer();
                // TODO If player at Neural Hub, ask which partition to perform action
                Partition targetPartition = model.getGame().getBoard().getPlayerPartition(me);
                doUpload(me, targetPartition);
            }
        });

        this.view.getGamePanel().getActionPanel().getBtnShift().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Avatar me = model.getGame().getCurrentPlayer();
                // TODO If player at Neural Hub, ask which partition to perform action
                Partition targetPartition = model.getGame().getBoard().getPlayerPartition(me);
                doShift(me, targetPartition);
            }
        });

        this.view.getGamePanel().getActionPanel().getBtnInstall().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Avatar me = model.getGame().getCurrentPlayer();
                // TODO If player at Neural Hub, ask which partition to perform action
                Partition targetPartition = model.getGame().getBoard().getPlayerPartition(me);
                doInstall(me, targetPartition);
            }
        });

        this.view.getGamePanel().getActionPanel().getBtnInfect().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Attack a spark or guardian with viruses. Cost: 1 Destruction Point + >0 Viruses
                Avatar me = model.getGame().getCurrentPlayer();
                // TODO If player at Neural Hub, ask which partition to perform action
                Partition targetPartition = model.getGame().getBoard().getPlayerPartition(me);
                doInfect(me, targetPartition);
            }
        });

        this.view.getGamePanel().getActionPanel().getBtnShop().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Hack Shack dialog
                HackShackDialog dialog = new HackShackDialog(model, view);
                dialog.setVisible(true);
                view.refresh();
            }
        });

        this.view.getGamePanel().getActionPanel().getBtnModify().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Avatar me = model.getGame().getCurrentPlayer();
                // TODO If player at Neural Hub, ask which partition to perform action
                Partition targetPartition = model.getGame().getBoard().getPlayerPartition(me);
                doModify(me, targetPartition);
            }
        });

        this.view.getGamePanel().getActionPanel().getBtnEndAction().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getGame().getPhaseStep() == GamePhaseStep.EXECUTE_COMMANDS){
                    // If user has 1+ card in hand, verify they want to end turn
                    if (model.getGame().getCurrentPlayer().getHand().size() > 0){
                        if (PopupUtil.popupConfirm(view.getFrame(), "End Turn", "End your turn?")){
                            // If user has >1 card in hand, they must discard down to 1 card
                            if (model.getGame().getCurrentPlayer().getHand().size() > 1){
                                // TODO Open dialog to have player choose cards to discard
                            }
                            model.getGame().setPhaseStep(GamePhaseStep.COMMANDS_END_TURN);
                        }
                        else
                            return;
                    }
                }
                else {
                    movementPoints = 0;
                    validMovementPartitions.clear();
                    allPartitionsValidForMovement = false;
                    model.getGame().setPhaseStep(GamePhaseStep.EXECUTE_COMMANDS);
                }
                run();
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (this.model.getGame().getPhaseStep() == GamePhaseStep.SETUP_BUILD_BOARD){
            Hexagon selectedHexagon = this.view.getGamePanel().getBoardPanel().getHexagonAt(e.getX(), e.getY());
            if (selectedHexagon != null){
                // Put Partition 6 at this coordinate
                this.view.getGamePanel().getBoardPanel().getServerTileToPlace().setPartitionLocations(
                        selectedHexagon.getX(),
                        selectedHexagon.getY()
                );
                this.view.getGamePanel().getBoardPanel().getServerTileToPlace().getPartitions().stream().forEach(p -> {
                    Hexagon hexagon = this.view.getGamePanel().getBoardPanel().getHexagon(p.getX(), p.getY());
                    if (hexagon != null){
                        p.setBounds(new Rectangle(hexagon.getBounds()));
                    }
                });
                this.view.getGamePanel().getBoardPanel().setServerTileToPlace(null);
                run();
            }
        }
        else if (this.model.getGame().getPhaseStep() == GamePhaseStep.SETUP_PLACE_DATA_NODES){
            if (setupDataNodesToPlace > 0){
                Hexagon selectedHexagon = this.view.getGamePanel().getBoardPanel().getHexagonAt(e.getX(), e.getY());
                if (selectedHexagon != null){
                    // Place Data Node at this partition
                    Partition p = this.model.getGame().getBoard().getPartitionAtCoord(selectedHexagon.getX(), selectedHexagon.getY());
                    this.model.getGame().getBoard().addDataNode(p);
                    setupDataNodesToPlace -= 1;
                    view.refresh();
                }
            }
            run();
        }
        else if (this.model.getGame().getPhaseStep() == GamePhaseStep.EXECUTE_COMMANDS_MOVE){
            Partition destPartition = getPartitionAt(e.getX(), e.getY());
            if (destPartition == null)
                return;
            if (allPartitionsValidForMovement || validMovementPartitions.contains(destPartition)){
                Avatar me = this.model.getGame().getCurrentPlayer();
                Partition myPartition = this.model.getGame().getBoard().getPlayerPartition(me);
                myPartition.getAvatars().remove(me);
                destPartition.getAvatars().add(me);
                // If moved from Data Port to Data Port/Node, then no movement cost
                if (myPartition.countContaminants(ContaminantType.DATA_PORT) > 0 &&
                        (destPartition.countContaminants(ContaminantType.DATA_PORT) > 0 ||
                                destPartition.countContaminants(ContaminantType.DATA_NODE) > 0)){
                    // free movement
                }
                else {
                    movementPoints -= 1;
                    allPartitionsValidForMovement = false;
                    validMovementPartitions.clear();
                }

                if (!myPartition.getContaminants().isEmpty()) {
                    // Ask user if they want to take any contaminants with them (up to 3 of same color)
                    ContaminantSelectionDialog dialog =
                            new ContaminantSelectionDialog(
                                    "Select up to 3 contaminants of the same color to carry",
                                    myPartition.getContaminants(),
                                    ContaminantSelectionDialog.POLICY_UP_TO_3_SAME_COLOR);
                    dialog.setVisible(true);
                    for (Contaminant carriedContaminant: dialog.getSelected()){
                        myPartition.getContaminants().remove(carriedContaminant);
                        destPartition.getContaminants().add(carriedContaminant);
                    }
                }

                this.view.refresh();
                run();
            }
            else {
                logger.warning("Unable to move to that partition!");
                PopupUtil.popupNotification(view.getFrame(), "Move", "Unable to move to that partition");
            }
        }
        else if (this.model.getGame().getPhaseStep() == GamePhaseStep.EXECUTE_COMMANDS_SHIFT){
            Partition destPartition = getPartitionAt(e.getX(), e.getY());
            if (destPartition == null)
                return;
            if (validShiftPartitions.contains(destPartition)){
                Avatar me = this.model.getGame().getCurrentPlayer();
                Contaminant contaminant;
                if (shiftSourcePartition.getContaminants().size() == 1){
                    contaminant = shiftSourcePartition.getContaminants().get(0);
                }
                else {
                    // Ask player which contaminant to shift
                    ContaminantSelectionDialog contaminantSelectionDialog =
                            new ContaminantSelectionDialog(
                                    "Which contaminant to shift?",
                                    shiftSourcePartition.getContaminants(),
                                    ContaminantSelectionDialog.POLICY_SINGLE);
                    contaminantSelectionDialog.setVisible(true);
                    if (contaminantSelectionDialog.getSelected().isEmpty())
                        return;
                    contaminant = contaminantSelectionDialog.getSelected().get(0);
                }
                if (contaminant.getType().isInstallation()){
                    PopupUtil.popupNotification(view.getFrame(), "Shift", "Cannot shift Installations!");
                    return;
                }
                shiftSourcePartition.getContaminants().remove(contaminant);
                destPartition.getContaminants().add(contaminant);
                shiftPoints -= 1;
                validShiftPartitions.clear();
                shiftSourcePartition = null;
                this.view.refresh();
                run();
            }
            else {
                logger.warning("Unable to shift to that partition!");
                PopupUtil.popupNotification(view.getFrame(), "Shift", "Unable to shift to that partition");
            }
        }
    }

    public void run(){
        while (this.model.getGame().getPhase() != GamePhase.GAMEOVER) {
            this.view.refresh();
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
                            this.model.getGame().setupSMC();

                            placeServerTileList.addAll(model.getGame().getBoard().getServerTiles());
                            Collections.shuffle(placeServerTileList);
                            this.model.getGame().setPhaseStep(GamePhaseStep.SETUP_BUILD_BOARD);
                            break;
                        case SETUP_BUILD_BOARD:
                            // Let player build board
                            if (this.view.getGamePanel().getBoardPanel().getServerTileToPlace() == null){
                                if (placeServerTileList.isEmpty())
                                    this.model.getGame().setPhaseStep(GamePhaseStep.SETUP_PLACE_AVATARS);
                                else {
                                    this.view.getGamePanel().getBoardPanel().setServerTileToPlace(placeServerTileList.remove(0));
                                    return;
                                }
                            }
                            break;
                        case SETUP_PLACE_AVATARS:
                            this.model.getGame().setupAvatars();
                            this.model.getGame().setPhaseStep(GamePhaseStep.SETUP_PLACE_DATA_NODES);
                            break;
                        case SETUP_PLACE_DATA_NODES:
                            if (setupDataNodesToPlace < 0) {
                                setupDataNodesToPlace = 6 - this.model.getGame().getAvatars().size();
                                view.getGamePanel().getLogPanel().writeln("Add " + setupDataNodesToPlace + " Data Nodes to network");
                            }
                            if (setupDataNodesToPlace > 0)
                                return;
                            this.model.getGame().setPhaseStep(GamePhaseStep.END_PHASE);
                            break;
                        case END_PHASE:
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
                            this.view.getGamePanel().getLogPanel().writeln(this.model.getGame().getCurrentPlayer().getServer() + "'s Turn");
                            // Refill Hack Shack
                            this.model.getGame().refillHackShackMarket();
                            // Add new sparks per Security Level
                            this.model.getGame().getSmc().startOfTurn(this.model.getGame());
                            this.model.getGame().setPhaseStep(GamePhaseStep.EXECUTE_COMMANDS);
                            break;
                        case EXECUTE_COMMANDS:
                            if (!this.model.getGame().getCurrentPlayer().getHand().isEmpty() && !this.model.getGame().isCurrentPlayerPassed()) {
                                return;
                            }

                            this.model.getGame().setPhaseStep(GamePhaseStep.COMMANDS_END_TURN);
                            break;
                        case EXECUTE_COMMANDS_MOVE:
                            if (movementPoints == 0) {
                                this.model.getGame().setPhaseStep(GamePhaseStep.EXECUTE_COMMANDS);
                                break;
                            }
                            else
                                updateValidMovementPartitions();
                            return;
                        case EXECUTE_COMMANDS_CHOOSE_SOURCE_PARTITION:
                            return;
                        case EXECUTE_COMMANDS_SHIFT:
                            if (shiftPoints == 0){
                                this.model.getGame().setPhaseStep(GamePhaseStep.EXECUTE_COMMANDS);
                                break;
                            }
                            return;
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
                                    if (p.getContaminants().contains(ContaminantType.VIRUS) && !p.getCountermeasures().isEmpty()){
                                        int virusRoll = Util.roll();
                                        int cmRoll = Util.roll();
                                        virusRoll += p.countContaminants(ContaminantType.VIRUS);
                                        cmRoll += p.countCountermeasures(CountermeasureType.SPARK) + (4 * p.countCountermeasures(CountermeasureType.GUARDIAN));
                                        if (virusRoll > cmRoll){
                                            p.getCountermeasures().clear();
                                        }
                                        else {
                                            p.getContaminants().remove(ContaminantType.VIRUS);
                                        }
                                    }
                                });
                            });

                            // Delete Contaminants Segment
                            this.model.getGame().getBoard().getServerTiles().stream().forEach(st -> {
                                st.getPartitions().stream().filter(p -> !p.getContaminants().isEmpty() && !p.getCountermeasures().isEmpty()).forEach(p -> {
                                    List<Contaminant> installations = p.getContaminants().stream().filter(c -> c.getType().isInstallation()).collect(Collectors.toList());
                                    p.getContaminants().clear();
                                    p.getContaminants().addAll(installations);
                                });
                            });

                            // Delete Installations Segment
                            this.model.getGame().getBoard().getServerTiles().stream().forEach(st -> {
                                st.getPartitions().stream().filter(p -> p.getCountermeasures().contains(CountermeasureType.GUARDIAN)).forEach(p -> {
                                    p.getContaminants().clear();
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
                            if (!this.model.getGame().getContaminantPlacements().isEmpty())
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
                                        model.getGame().getBoard().moveSparks(model.getGame().getBoard().getServerTile(server), up);
                                    }
                                }
                            }
                            // TODO Check for Guardian/Firewall creation and explosions
                            this.model.getGame().setPhaseStep(GamePhaseStep.COUNTERMEASURES_SCORING);
                            break;
                        case COUNTERMEASURES_SCORING:
                            // Gain scoring token if completed all CountermeasureType Goal cards at current security level
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
                            // Check end of game (all CountermeasureType Goal cards resolved)
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

    private void updateValidMovementPartitions(){
        validMovementPartitions.clear();

        Avatar me = model.getGame().getCurrentPlayer();
        Partition myPartition = model.getGame().getBoard().getPlayerPartition(me);

        // If myPartition has Data Port, enable all partitions
        if (myPartition.countContaminants(ContaminantType.DATA_PORT) > 0){
            allPartitionsValidForMovement = true;
            logger.info("Set all partitions valid for movement");
            return;
        }

        List<Partition> neighbors = model.getGame().getBoard().getNeighbors(myPartition);
        validMovementPartitions.addAll(neighbors);
        for (Partition partition: neighbors){
            logger.info("Added valid movement destination: " + partition);
        }

        // TODO Add partitions connected via Data Nodes

    }

    private void doInstall(Avatar currentPlayer, Partition targetPartition){
        // Convert 3 contaminants to an installation. Cost: 1 Command Point + 3 Contaminants of desired installation
        List<Server> contaminantsWithAtLeast3 = targetPartition.getColorsWithAtLeastNContaminants(3);

        CardSet selectedCards = model.getGame().getCurrentPlayer().getSelectedCardsInHandAsSet();
        List<Server> possibleColors =
                selectedCards.getCards().stream()
                        .flatMap(card -> card.getCommands().stream())
                        .distinct()
                        .filter(s -> contaminantsWithAtLeast3.contains(s))
                        .collect(Collectors.toList());
        if (possibleColors.size() > 1){
            // TODO Ask user what to color to install and update colorsWith3OrMoreCmdPts
        }
        if (possibleColors.size() == 1){
            // Install this color
            Server color = possibleColors.get(0);
            if (!model.getGame().getBoard().addInstallation(color, targetPartition)){
                // Uh Oh!  No more left!
                PopupUtil.popupNotification(view.getFrame(), "Install", "No " + ContaminantType.installationOfServer(color) + " tokens left!  Cannot install!");
                return;
            }
            // Remove 3 contaminants
            ContaminantType contaminant = ContaminantType.ofServer(color);
            targetPartition.getContaminants().remove(contaminant);
            targetPartition.getContaminants().remove(contaminant);
            targetPartition.getContaminants().remove(contaminant);
            // Discard command cards
            currentPlayer.discardFromHand(selectedCards);
            logger.info("Installed " + color.getInstallationName() + " at " + targetPartition);
            view.getGamePanel().getLogPanel().writeln("Installed " + color.getInstallationName() + " at " + targetPartition);
        }
        run();
    }

    public void doUpload(Avatar player, Partition targetPartition){
        CardSet selectedCards = model.getGame().getCurrentPlayer().getSelectedCardsInHandAsSet();
        List<Server> colorsWith3OrMoreCmdPts = selectedCards.getColorsWithAtLeast(3);
        if (colorsWith3OrMoreCmdPts.size() > 1){
            // TODO Ask user what to color to upload and update colorsWith3OrMoreCmdPts
        }
        if (colorsWith3OrMoreCmdPts.size() == 1){
            // Upload this color
            Server color = colorsWith3OrMoreCmdPts.get(0);
            if (model.getGame().getBoard().addContaminant(color, targetPartition)){
                // Uh Oh!  No more tokens left!
                PopupUtil.popupNotification(view.getFrame(), "Upload", "No " + color + " tokens left!  Cannot upload!");
                return;
            }
            player.discardFromHand(selectedCards);
            logger.info("Uploaded " + color.getContaminantName());
            view.getGamePanel().getLogPanel().writeln("Uploaded " + color.getInstallationName() + " at " + targetPartition);
        }
        run();
    }

    public void doShift(Avatar player, Partition targetPartition){
        // Push a containment or spark from target partition to an adjacent partition. Cost: 1 Cognition Point per containment/spark to shift.
        CardSet selectedCards = model.getGame().getCurrentPlayer().getSelectedCardsInHandAsSet();
        int cognitionPoints = selectedCards.countCommandPoints(Server.GREEN) + selectedCards.countCommandPoints(Server.PURPLE);
        if (cognitionPoints == 0){
            PopupUtil.popupNotification(view.getFrame(), "Shift", "You must select at least one Cognition (Green) Command Point");
            return;
        }
        view.getGamePanel().getLogPanel().writeln("Gained " + cognitionPoints + " Cognition Points");
        shiftPoints = cognitionPoints;

        if (targetPartition.getContaminants().contains(ContaminantType.NEURAL_HUB)){
            model.getGame().setPhaseStep(GamePhaseStep.EXECUTE_COMMANDS_CHOOSE_SOURCE_PARTITION);
        }
        else {
            shiftSourcePartition = targetPartition;

            // Identify valid shift partitions and enable those partitions
            validShiftPartitions.clear();
            validShiftPartitions.addAll(model.getGame().getBoard().getNeighbors(targetPartition));

            model.getGame().setPhaseStep(GamePhaseStep.EXECUTE_COMMANDS_SHIFT);
        }
        run();
    }

    public void doInfect(Avatar player, Partition targetPartition){
        // Attack a spark or guardian with viruses. Cost: 1 Destruction Point + >0 Viruses
        CardSet selectedCards = model.getGame().getCurrentPlayer().getSelectedCardsInHandAsSet();
        int destructionPoints = selectedCards.countCommandPoints(Server.RED) + selectedCards.countCommandPoints(Server.PURPLE);
        if (destructionPoints == 0){
            PopupUtil.popupNotification(view.getFrame(), "Infect", "You must select at least one Destruction (Red) Command Point");
            return;
        }
        int numVirus = targetPartition.countContaminants(ContaminantType.VIRUS);
        if (numVirus == 0){
            PopupUtil.popupNotification(view.getFrame(), "Infect", "There must be at least one Virus in the partition");
            return;
        }

        doBattle(targetPartition, destructionPoints);
    }

    public void doBattle(Partition targetPartition, int destructionPoints){
        int numVirus = targetPartition.countContaminants(ContaminantType.VIRUS);
        int virusRoll = Util.roll();
        int cmRoll = Util.roll();

        int infectionScore = virusRoll + destructionPoints + numVirus;
        int sparksGuardians = targetPartition.countCountermeasures(CountermeasureType.SPARK) + (4 * targetPartition.countCountermeasures(CountermeasureType.GUARDIAN));
        int resistanceScore = cmRoll + sparksGuardians;

        PopupUtil.popupNotification(view.getFrame(),
                (destructionPoints > 0? "Infect": "Battle") + " at " + targetPartition,
                "Infection Score: " + infectionScore +
                        "\nResistance Score: " + resistanceScore + "\n" +
                        (infectionScore > resistanceScore? "You win the battle, removing sparks/guardian": "You lose the battle, removing one virus"));

        view.getGamePanel().getLogPanel().writeln("Battle at " + targetPartition, LogPanel.BOLD);
        view.getGamePanel().getLogPanel().writeln("Infection Roll: " + virusRoll);
        view.getGamePanel().getLogPanel().writeln("Destruction Points: " + destructionPoints);
        view.getGamePanel().getLogPanel().writeln("Viruses: " + numVirus);
        view.getGamePanel().getLogPanel().writeln("Infection Score: " + infectionScore, LogPanel.BOLD);
        view.getGamePanel().getLogPanel().writeln("Resistance Roll: " + cmRoll);
        view.getGamePanel().getLogPanel().writeln("Sparks/Guardians: " + sparksGuardians);
        view.getGamePanel().getLogPanel().writeln("Resistance Score: " + resistanceScore, LogPanel.BOLD);
        view.getGamePanel().getLogPanel().writeln("Outcome: " + (infectionScore > resistanceScore? "You win, removing sparks/guardian": "You lose, removing one virus"), LogPanel.BOLD);

        if (infectionScore > resistanceScore){
            // Delete all sparks and guardians
            while (targetPartition.getCountermeasures().contains(CountermeasureType.SPARK))
                targetPartition.getCountermeasures().remove(CountermeasureType.SPARK);
            if (targetPartition.getCountermeasures().contains(CountermeasureType.GUARDIAN))
                targetPartition.getCountermeasures().remove(CountermeasureType.GUARDIAN);
        }
        else {
            targetPartition.getContaminants().remove(ContaminantType.VIRUS);
        }

        run();
    }

    private void doModify(Avatar currentPlayer, Partition targetPartition){
        // Convert a spark to a containment.  Must have more replicants than sparks. Cost: 1 Deception (Yellow) + 1 Command Point of desired containment
        CardSet selectedCards = model.getGame().getCurrentPlayer().getSelectedCardsInHandAsSet();
        int deceptionPoints = selectedCards.countCommandPoints(Server.YELLOW) + selectedCards.countCommandPoints(Server.PURPLE);
        if (deceptionPoints == 0){
            PopupUtil.popupNotification(view.getFrame(), "Modify", "You must select at least 1 Deception (Yellow) Command Point");
            return;
        }
        int numReplicants = targetPartition.countContaminants(ContaminantType.REPLICANT);
        int numSparks     = targetPartition.countCountermeasures(CountermeasureType.SPARK);
        if (numReplicants <= numSparks){
            PopupUtil.popupNotification(view.getFrame(), "Modify", "You must have more Replicants (Yellow) than Sparks in the partition");
            return;
        }

        Map<Server, Integer> commandCounts = selectedCards.getColorCounts();

        List<Server> possibleColors =
                commandCounts.entrySet().stream()
                        .filter(es -> es.getValue() == 0)
                        .filter(es -> es.getKey() != Server.YELLOW || es.getValue() > 1)
                        .map(es -> es.getKey())
                        .collect(Collectors.toList());

        if (possibleColors.size() > 1){
            // TODO Ask user what to color to modify to and update possibleColors
        }

        if (possibleColors.size() == 1){
            // Modify to this color
            Server color = possibleColors.get(0);
            if (!model.getGame().getBoard().addContaminant(color, targetPartition)){
                PopupUtil.popupNotification(view.getFrame(), "Modify", "No " + color + " tokens left!  Cannot modify!");
                return;
            }
            // Remove 1 spark
            Countermeasure spark = targetPartition.removeCountermeasure(CountermeasureType.SPARK);
            model.getGame().getBoard().returnToPool(spark);
            // Discard command cards
            currentPlayer.discardFromHand(selectedCards);
            logger.info("Modified Spark to " + color.getContaminantName() + " at " + targetPartition);
        }
        run();
    }

    public Partition getPartitionAt(int mx, int my) {
        Hexagon hexagon = this.view.getGamePanel().getBoardPanel().getHexagonAt(mx, my);
        if (hexagon == null)
            return null;
        return this.model.getGame().getBoard().getPartitionAtCoord(hexagon.getX(), hexagon.getY());
    }
}
