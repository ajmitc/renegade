package renegade.view;

import renegade.game.Avatar;
import renegade.game.Contaminant;
import renegade.game.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvatarSelectionDialog extends JDialog {
    private static final int SPACING = 5;
    private static final int AVATAR_SIZE = 72;
    private static final Color SELECTED_COLOR = Color.RED;

    private Avatar redPrimary      = new Avatar(Server.RED, true);
    private Avatar redAlternate    = new Avatar(Server.RED, false);
    private Avatar yellowPrimary   = new Avatar(Server.YELLOW, true);
    private Avatar yellowAlternate = new Avatar(Server.YELLOW, false);
    private Avatar bluePrimary     = new Avatar(Server.BLUE, true);
    private Avatar blueAlternate   = new Avatar(Server.BLUE, false);
    private Avatar greenPrimary    = new Avatar(Server.GREEN, true);
    private Avatar greenAlternate  = new Avatar(Server.GREEN, false);
    private Avatar purple          = new Avatar(Server.PURPLE);

    private List<Avatar> selectedAvatars = new ArrayList<>();
    private Map<Avatar, Rectangle> avatarLocations = new HashMap<>();

    public AvatarSelectionDialog(){
        super((JFrame) null, "Select Avatars", true);

        setPreferredSize(new Dimension(400, 250));
        setSize(getPreferredSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        JPanel avatarPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics graphics){
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, getWidth(), getHeight());
                int x = 0;
                int y = 0;
                graphics.drawImage(redPrimary.getTokenImage(), x, y, null);
                avatarLocations.put(redPrimary, new Rectangle(x, y, AVATAR_SIZE, AVATAR_SIZE));
                x += AVATAR_SIZE + SPACING;

                graphics.drawImage(yellowPrimary.getTokenImage(), x, y, null);
                avatarLocations.put(yellowPrimary, new Rectangle(x, y, AVATAR_SIZE, AVATAR_SIZE));
                x += AVATAR_SIZE + SPACING;

                graphics.drawImage(bluePrimary.getTokenImage(), x, y, null);
                avatarLocations.put(bluePrimary, new Rectangle(x, y, AVATAR_SIZE, AVATAR_SIZE));
                x += AVATAR_SIZE + SPACING;

                graphics.drawImage(greenPrimary.getTokenImage(), x, y, null);
                avatarLocations.put(greenPrimary, new Rectangle(x, y, AVATAR_SIZE, AVATAR_SIZE));
                x += AVATAR_SIZE + SPACING;

                graphics.drawImage(purple.getTokenImage(), x, y, null);
                avatarLocations.put(purple, new Rectangle(x, y, AVATAR_SIZE, AVATAR_SIZE));
                x += AVATAR_SIZE + SPACING;

                x = 0;
                y += AVATAR_SIZE + SPACING;

                graphics.drawImage(redAlternate.getTokenImage(), x, y, null);
                avatarLocations.put(redAlternate, new Rectangle(x, y, AVATAR_SIZE, AVATAR_SIZE));
                x += AVATAR_SIZE + SPACING;

                graphics.drawImage(yellowAlternate.getTokenImage(), x, y, null);
                avatarLocations.put(yellowAlternate, new Rectangle(x, y, AVATAR_SIZE, AVATAR_SIZE));
                x += AVATAR_SIZE + SPACING;

                graphics.drawImage(blueAlternate.getTokenImage(), x, y, null);
                avatarLocations.put(blueAlternate, new Rectangle(x, y, AVATAR_SIZE, AVATAR_SIZE));
                x += AVATAR_SIZE + SPACING;

                graphics.drawImage(greenAlternate.getTokenImage(), x, y, null);
                avatarLocations.put(greenAlternate, new Rectangle(x, y, AVATAR_SIZE, AVATAR_SIZE));
                x += AVATAR_SIZE + SPACING;

                graphics.setColor(SELECTED_COLOR);
                for (Avatar selected: selectedAvatars){
                    graphics.drawRect(avatarLocations.get(selected).x, avatarLocations.get(selected).y, AVATAR_SIZE, AVATAR_SIZE);
                }
            }
        };

        avatarPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (Map.Entry<Avatar, Rectangle> entry: avatarLocations.entrySet()){
                    if (entry.getValue().contains(e.getX(), e.getY())){
                        if (selectedAvatars.contains(entry.getKey()))
                            selectedAvatars.remove(entry.getKey());
                        else if (selectedAvatars.size() < 4)
                            selectedAvatars.add(entry.getKey());
                        avatarPanel.repaint();
                        break;
                    }
                }
            }
        });

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrapperPanel.add(avatarPanel, BorderLayout.CENTER);

        JButton btnClose = new JButton("OK");
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        JPanel southpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southpanel.add(btnClose);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(wrapperPanel, BorderLayout.CENTER);
        getContentPane().add(southpanel, BorderLayout.SOUTH);
    }

    public List<Avatar> getSelected() {
        return selectedAvatars;
    }
}
