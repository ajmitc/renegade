package renegade.view;

import renegade.game.Avatar;
import renegade.game.smc.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class SMCSelectionDialog extends JDialog {
    private static final int SPACING = 5;
    private static final Color SELECTED_COLOR = Color.RED;
    private static final Stroke SELECTED_STROKE = new BasicStroke(3.f);

    private SMC alphaMoby = new AlphaMobySMC();
    private SMC logi      = new LogiSMC();
    private SMC mother    = new MotherSMC();
    private SMC rs20      = new RS20SMC();
    private SMC spider    = new SpiderSMC();
    private SMC viking    = new VikingSMC();

    private SMC selected = rs20;
    private Map<SMC, Rectangle> smcLocations = new HashMap<>();

    public SMCSelectionDialog(){
        super((JFrame) null, "Select SMC", true);

        int smcWidth  = rs20.getImage().getWidth(null);
        int smcHeight = rs20.getImage().getHeight(null);

        setPreferredSize(new Dimension(6 * (smcWidth + SPACING) + 15, smcHeight + 95));
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
                graphics.drawImage(rs20.getImage(), x, y, null);
                smcLocations.put(rs20, new Rectangle(x, y, smcWidth, smcHeight));
                x += smcWidth + SPACING;

                graphics.drawImage(alphaMoby.getImage(), x, y, null);
                smcLocations.put(alphaMoby, new Rectangle(x, y, smcWidth, smcHeight));
                x += smcWidth + SPACING;

                graphics.drawImage(spider.getImage(), x, y, null);
                smcLocations.put(spider, new Rectangle(x, y, smcWidth, smcHeight));
                x += smcWidth + SPACING;

                graphics.drawImage(viking.getImage(), x, y, null);
                smcLocations.put(viking, new Rectangle(x, y, smcWidth, smcHeight));
                x += smcWidth + SPACING;

                graphics.drawImage(logi.getImage(), x, y, null);
                smcLocations.put(logi, new Rectangle(x, y, smcWidth, smcHeight));
                x += smcWidth + SPACING;

                graphics.drawImage(mother.getImage(), x, y, null);
                smcLocations.put(mother, new Rectangle(x, y, smcWidth, smcHeight));
                x += smcWidth + SPACING;

                graphics.setColor(SELECTED_COLOR);
                ((Graphics2D) graphics).setStroke(SELECTED_STROKE);
                for (Map.Entry<SMC, Rectangle> entry: smcLocations.entrySet()) {
                    if (entry.getKey() == selected) {
                        graphics.drawRect(smcLocations.get(selected).x, smcLocations.get(selected).y, smcWidth, smcHeight);
                        break;
                    }
                }
            }
        };

        avatarPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (Map.Entry<SMC, Rectangle> entry: smcLocations.entrySet()){
                    if (entry.getValue().contains(e.getX(), e.getY())){
                        selected = entry.getKey();
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

    public SMC getSelected() {
        return selected;
    }
}
