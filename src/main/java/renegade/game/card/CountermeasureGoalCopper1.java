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
                .allMatch(p -> p.getContainments().contains(Containment.DATA_NODE) || p.getContainments().contains(Containment.DATA_PORT)))
            return false;
        if (!game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream()
                .filter(p -> p.getNumber() <= game.getAvatars().size())
                .allMatch(p -> p.countContainments(Containment.DATA_NODE) >= 2 ||
                        (p.getContainments().contains(Containment.DATA_NODE) || p.getContainments().contains(Containment.DATA_PORT))))
            return false;
        setSuccess(true);
        return true;
    }

    @Override
    public void applySuccess(Game game) {
        if (!isResolved())
            return;
        // Add one data port and one virus to Faith
        game.getContainmentPlacements().add(new ContainmentPlacement(Containment.DATA_PORT, Server.PURPLE));
        game.getContainmentPlacements().add(new ContainmentPlacement(Containment.VIRUS, Server.PURPLE));

        game.setPhaseStep(GamePhaseStep.COUNTERMEASURES_SUCCESS_FAILURE_PLACE);
        setResolved(true);
    }

    @Override
    public void applyFailure(Game game) {
        if (!isResolved())
            return;
        // Add one spark to every partition on Faith without a data node/port
        game.getBoard().getServerTile(Server.PURPLE).getPartitions().stream()
                .filter(p -> !p.getContainments().contains(Containment.DATA_PORT) && !p.getContainments().contains(Containment.DATA_NODE))
                .forEach(p -> {
                    p.getCountermeasures().add(Countermeasure.SPARK);
                });
        setResolved(true);
    }
}
