package renegade.view;

import renegade.game.card.CommandCard;

import javax.swing.*;
import java.awt.*;

public class CommandCardDetailsDialog extends JDialog {

    public CommandCardDetailsDialog(CommandCard card){
        super((JFrame) null, "Command Card", true);

        setSize(card.getFullSizeImage().getWidth(null) + 15, card.getFullSizeImage().getHeight(null) + 50);
        setLocationRelativeTo(null);

        JPanel cardpanel = new JPanel(){
            @Override
            public void paintComponent(Graphics graphics){
                graphics.drawImage(card.getFullSizeImage(), 0, 0, null);
            }
        };

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrapperPanel.add(cardpanel, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(wrapperPanel, BorderLayout.CENTER);
    }
}
