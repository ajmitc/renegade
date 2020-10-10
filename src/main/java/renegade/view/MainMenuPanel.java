package renegade.view;

import renegade.Model;
import renegade.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private Model model;
    private View view;
    private JButton btnNewGame;

    public MainMenuPanel(Model model, View view) {
        super(new GridLayout(1, 2));
        this.model = model;
        this.view = view;

        JLabel lblCover = new JLabel(new ImageIcon(ImageUtil.get("Renegade_Box.jpg")));
        add(lblCover);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnNewGame = new JButton("New Game");

        JPanel rightpanel = new JPanel();
        rightpanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        add(rightpanel);
        new GridBagLayoutHelper(rightpanel, true)
                .setAnchor(GridBagConstraints.CENTER)
                .setFill(GridBagConstraints.NONE)
                .add(btnNewGame)
                .nextRow()
                .setExternalPadding(20, 0, 0, 0)
                .add(btnExit);
    }

    public JButton getBtnNewGame() {
        return btnNewGame;
    }
}
