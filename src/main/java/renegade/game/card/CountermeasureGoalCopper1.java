package renegade.game.card;

import renegade.game.*;

public class CountermeasureGoalCopper1 extends CountermeasureGoalCard{
    public CountermeasureGoalCopper1(){
        super(SecurityLevel.COPPER, "Copper Front1.jpg", "Copper Back1.jpg",
                new MoveSpark(Server.BLUE, true),
                new MoveSpark(Server.GREEN, true),
                new MoveSpark(Server.YELLOW, true),
                new MoveSpark(Server.RED, true));
    }

    @Override
    public boolean goalSatisfied(Game game) {
        if (!game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream()
                .filter(p -> p.getNumber() <= 4)
                .allMatch(p -> p.countContaminants(ContaminantType.DATA_NODE) > 0 || p.countContaminants(ContaminantType.DATA_PORT) > 0))
            return false;
        if (!game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream()
                .filter(p -> p.getNumber() <= game.getAvatars().size())
                .allMatch(p -> p.countContaminants(ContaminantType.DATA_NODE) >= 2 ||
                        (p.countContaminants(ContaminantType.DATA_NODE) > 0 || p.countContaminants(ContaminantType.DATA_PORT) > 0)))
            return false;
        setSuccess(true);
        return true;
    }

    @Override
    public void applySuccess(Game game) {
        if (!isResolved())
            return;
        // Add one data port and one virus to Faith
        game.getContaminantPlacements().add(new ContaminantPlacement(ContaminantType.DATA_PORT, Server.PURPLE));
        game.getContaminantPlacements().add(new ContaminantPlacement(ContaminantType.VIRUS, Server.PURPLE));

        game.setPhaseStep(GamePhaseStep.COUNTERMEASURES_SUCCESS_FAILURE_PLACE);
        setResolved(true);
    }

    @Override
    public void applyFailure(Game game) {
        if (!isResolved())
            return;
        // Add one spark to every partition on Faith without a data node/port
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream()
                .filter(p -> p.countContaminants(ContaminantType.DATA_PORT) == 0 && p.countContaminants(ContaminantType.DATA_NODE) == 0)
                .forEach(p -> {
                    game.getBoard().addSpark(p);
                });
        setResolved(true);
    }
}
