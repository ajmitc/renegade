package renegade.view;

import renegade.Model;
import renegade.game.Avatar;
import renegade.game.card.CommandCard;

import javax.swing.*;
import java.awt.*;

public class HandPanel extends JPanel {
    private static final Color SELECTED_BORDER_COLOR = Color.RED;
    private static final Stroke SELECTED_BORDER_STROKE = new BasicStroke(3.f);
    private static final int CARD_SPACING = 5;
    private Model model;
    private View view;

    public HandPanel(Model model, View view){
        super(new FlowLayout(FlowLayout.LEFT));
        this.model = model;
        this.view = view;
        setPreferredSize(new Dimension(((CommandCard.CARD_WIDTH + CARD_SPACING) * 6) + 20, 260));
        setMinimumSize(getPreferredSize());
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics;

        if (model.getGame() == null || model.getGame().getCurrentPlayer() == null)
            return;

        // Clear the background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        Avatar player = model.getGame().getCurrentPlayer();
        for (int i = 0; i < player.getHand().size(); ++i){
            CommandCard card = player.getHand().get(i);
            g.drawImage(card.getImage(), i * (card.getImage().getWidth(null) + CARD_SPACING), 0, null);
            if (card.isSelected()){
                g.setColor(SELECTED_BORDER_COLOR);
                g.setStroke(SELECTED_BORDER_STROKE);
                g.drawRect(i * (card.getImage().getWidth(null) + CARD_SPACING), 0, card.getImage().getWidth(null), card.getImage().getHeight(null));
            }
        }
    }

    public CommandCard getSelectedCommandCard(int mx, int my){
        int index = mx / (model.getGame().getCurrentPlayer().getHand().get(0).getImage().getWidth(null) + CARD_SPACING);
        if (index >= 0 && index < model.getGame().getCurrentPlayer().getHand().size())
            return model.getGame().getCurrentPlayer().getHand().get(index);
        return null;
    }


    public void refresh(){
        repaint();
    }
}
