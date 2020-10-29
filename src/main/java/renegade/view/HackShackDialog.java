package renegade.view;

import renegade.Model;
import renegade.game.Server;
import renegade.game.card.CardSet;
import renegade.game.card.CommandCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

/**
 * Hack Shack will have 4 cards that the user can purchase using their hand
 * User should select one Hack Shack card and one or more hand cards, then click purchase
 */
public class HackShackDialog extends JDialog {
    private Model model;
    private View view;

    private HandPanel handPanel;
    private HackShackPanel hackShackPanel;
    private JButton btnBuy;

    public HackShackDialog(Model model, View view){
        super(view.getFrame(), "Hack Shack", true);
        this.model = model;
        this.view = view;

        setSize(920, 700);
        setLocationRelativeTo(null);

        handPanel = new HandPanel(model, view);
        handPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CommandCard card = handPanel.getSelectedCommandCard(e.getX(), e.getY());
                if (card != null){
                    card.setSelected(!card.isSelected());
                    repaint();
                }
                btnBuy.setEnabled(!model.getGame().getCurrentPlayer().getSelectedCardsInHand().isEmpty() && model.getGame().getHackShackMarket().stream().filter(c -> c != null).anyMatch(c -> c.isSelected()));
            }
        });
        JPanel handWrapperPanel = new JPanel(new BorderLayout());
        handWrapperPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Hand"));
        handWrapperPanel.add(handPanel, BorderLayout.CENTER);

        hackShackPanel = new HackShackPanel(model, view);
        hackShackPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CommandCard card = hackShackPanel.getSelectedCommandCard(e.getX(), e.getY());
                if (card != null){
                    card.setSelected(!card.isSelected());
                    repaint();
                }
                btnBuy.setEnabled(!model.getGame().getCurrentPlayer().getSelectedCardsInHand().isEmpty() && model.getGame().getHackShackMarket().stream().filter(c -> c != null).anyMatch(c -> c.isSelected()));
            }
        });
        JPanel hackShackWrapperPanel = new JPanel(new BorderLayout());
        hackShackWrapperPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Hack Shack Market"));
        hackShackWrapperPanel.add(hackShackPanel, BorderLayout.CENTER);


        btnBuy = new JButton("Purchase");
        btnBuy.setEnabled(false);
        btnBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<CommandCard> selectedAdvancedCards = model.getGame().getHackShackMarket().stream().filter(card -> card != null && card.isSelected()).collect(Collectors.toList());
                if (selectedAdvancedCards.size() > 1){
                    PopupUtil.popupNotification(view.getFrame(), "Shop", "You can only purchase one card at a time");
                    return;
                }
                CommandCard advancedCard = selectedAdvancedCards.get(0);
                CardSet selectedCards = model.getGame().getCurrentPlayer().getSelectedCardsInHandAsSet();
                // Check if selectedCards can pay for advanced card
                List<Server> cost = advancedCard.getCost();
                Map<Server, Integer> costMap = new HashMap<>();
                Arrays.stream(Server.values()).forEach(server -> costMap.put(server, 0));
                cost.stream().forEach(server -> {
                    costMap.put(server, costMap.get(server) + 1);
                });
                Map<Server, Integer> currency = selectedCards.getColorCounts();
                boolean canBuy = true;
                int requiredPurples = 0;
                int requiredMissing = 0;
                for (Server server: Server.values()){
                    if (server == Server.PURPLE){
                        requiredPurples = costMap.get(server);
                        continue;
                    }
                    int required = costMap.get(server);
                    int available = currency.get(server);
                    if (available < required){
                        requiredMissing += (required - available);
                    }
                }

                // Check purples
                int purpleAvailable = currency.get(Server.PURPLE);
                if (purpleAvailable < costMap.get(Server.PURPLE)){
                    canBuy = false;
                }
                else {
                    purpleAvailable -= costMap.get(Server.PURPLE);
                }

                if (canBuy && purpleAvailable < requiredMissing){
                    canBuy = false;
                }

                if (canBuy){
                    // One card used to purchase an Advanced card must be removed from the game.  Any others can be discarded
                    if (selectedCards.getCards().size() == 1) {
                        model.getGame().getCurrentPlayer().getHand().removeAll(selectedCards.getCards());
                    }
                    else {
                        // TODO Ask player which card to remove from game
                        CommandCard removed = selectedCards.getCards().remove(0);
                        model.getGame().getCurrentPlayer().getHand().remove(removed);
                        view.getGamePanel().getLogPanel().writeln("Removed card from game");
                        // Discard others
                        model.getGame().getCurrentPlayer().discardFromHand(selectedCards);
                        if (!selectedCards.getCards().isEmpty())
                            view.getGamePanel().getLogPanel().writeln("Discarded " + selectedCards.getCards().size() + " cards");
                    }
                    model.getGame().getCurrentPlayer().getHand().add(advancedCard);
                    model.getGame().getHackShackMarket().remove(advancedCard);
                }
                else {
                    PopupUtil.popupNotification(view.getFrame(), "Shop", "Insufficient Command Points to purchase Advanced card");
                }
                deselectAll();
                btnBuy.setEnabled(false);
                repaint();
            }
        });

        JPanel marketpanel = new JPanel();
        marketpanel.setLayout(new BoxLayout(marketpanel, BoxLayout.PAGE_AXIS));
        marketpanel.add(hackShackWrapperPanel);
        marketpanel.add(handWrapperPanel);

        JPanel purchasebtnpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        purchasebtnpanel.add(btnBuy);

        JPanel contentpanel = new JPanel(new BorderLayout());
        contentpanel.setBorder(BorderFactory.createEtchedBorder());
        contentpanel.add(marketpanel, BorderLayout.CENTER);
        contentpanel.add(purchasebtnpanel, BorderLayout.SOUTH);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deselectAll();
                setVisible(false);
            }
        });
        JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonpanel.add(btnClose);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentpanel, BorderLayout.CENTER);
        getContentPane().add(buttonpanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                deselectAll();
            }
        });
    }

    private void deselectAll(){
        // Deselect all cards in hand and market
        model.getGame().getHackShackMarket().stream().filter(card -> card != null).forEach(card -> card.setSelected(false));
        model.getGame().getCurrentPlayer().getHand().stream().forEach(card -> card.setSelected(false));
    }
}
