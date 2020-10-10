package renegade.view;

import renegade.Model;
import renegade.game.Countermeasure;
import renegade.game.card.CountermeasureGoalCard;
import renegade.game.smc.SMC;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SMCPanel extends JPanel {
    private Model model;
    private View view;

    public SMCPanel(Model model, View view){
        super();
        this.model = model;
        this.view = view;
        setMinimumSize(new Dimension(SMC.SMC_CARD_WIDTH, 1000));
        setPreferredSize(new Dimension(SMC.SMC_CARD_WIDTH, 1000));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model.getGame() == null || model.getGame().getSmc() == null)
            return;
        Image smcImage = model.getGame().getSmc().getImage();
        g.drawImage(smcImage, 0, 0, null);

        List<CountermeasureGoalCard> goals = model.getGame().getCountermeasureGoals(model.getGame().getSecurityLevel());
        g.drawImage(goals.get(0).getImageFront(), 0, smcImage.getHeight(null), null);
    }
}
