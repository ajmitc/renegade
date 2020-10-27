package renegade.game.card;

import renegade.game.Game;
import renegade.game.MoveSpark;
import renegade.game.SecurityLevel;
import renegade.game.smc.SMC;
import renegade.view.ImageUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CountermeasureGoalCard implements Card{
    private SecurityLevel securityLevel;
    private String filenameFront;
    private String filenameBack;
    private boolean resolved = false;
    // Success moveSparks, reverse the direction for failure
    private List<MoveSpark> moveSparks = new ArrayList<>();
    private boolean success;

    public CountermeasureGoalCard(SecurityLevel securityLevel, String filenameFront, String filenameBack, MoveSpark ... moveSparks){
        this.securityLevel = securityLevel;
        this.filenameFront = filenameFront;
        this.filenameBack = filenameBack;
        for (MoveSpark moveSpark: moveSparks)
            this.moveSparks.add(moveSpark);
        success = false;
    }

    public abstract boolean goalSatisfied(Game game);

    public abstract void applyFailure(Game game);

    public abstract void applySuccess(Game game);

    public SecurityLevel getSecurityLevel() {
        return securityLevel;
    }

    public Image getImageFront(){
        return ImageUtil.get(filenameFront, SMC.SMC_CARD_WIDTH);
    }

    public Image getImageBack(){
        return ImageUtil.get(filenameBack, SMC.SMC_CARD_WIDTH);
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public List<MoveSpark> getMoveSparks() {
        return moveSparks;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
