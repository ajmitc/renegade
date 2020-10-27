package renegade.view;

import renegade.Model;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel {
    private Model model;
    private View view;

    private JButton btnMove      = new JButton("Move");
    private JButton btnShift     = new JButton("Shift");
    private JButton btnUpload    = new JButton("Upload");
    private JButton btnModify    = new JButton("Modify");
    private JButton btnInstall   = new JButton("Install");
    private JButton btnInfect    = new JButton("Infect");
    private JButton btnExecute   = new JButton("Execute");
    private JButton btnShop      = new JButton("Shop");
    private JButton btnEndAction = new JButton("End Action");

    public ActionPanel(Model model, View view){
        super(new GridLayout(5, 2));
        this.model = model;
        this.view = view;

        add(btnMove);    add(btnShift);
        add(btnUpload);  add(btnModify);
        add(btnInstall); add(btnInfect);
        add(btnExecute); add(btnShop);
        add(new JLabel(""));  add(btnEndAction);

        btnMove.setToolTipText("Move to another partition. Cost: 1 Information Point per partition to enter.");
        btnShift.setToolTipText("Push a containment or spark from your partition to an adjacent partition. Cost: 1 Cognition Point per containment/spark to shift.");
        btnUpload.setToolTipText("Add a containment.  Most have no countermeasures and less than 3 of the uploaded containment. Cost: 3 Command Points of same color.");
        btnModify.setToolTipText("Convert a spark to a containment.  Must have more replicants than sparks. Cost: 1 Deception (Yellow) + 1 Command Point of desired containment");
        btnInstall.setToolTipText("Convert 3 containments to an installation. Cost: 1 Command Point + 3 Containments of desired installation");
        btnInfect.setToolTipText("Attack a spark or guardian with viruses. Cost: 1 Destruction Point + >0 Viruses");
        btnExecute.setToolTipText("Execute special action on Advanced Card.");
        btnShop.setToolTipText("Purchase Advanced Cards from Hack Shack.");
        btnEndAction.setToolTipText("End the current action");
    }

    public JButton getBtnMove() {
        return btnMove;
    }

    public JButton getBtnShift() {
        return btnShift;
    }

    public JButton getBtnUpload() {
        return btnUpload;
    }

    public JButton getBtnModify() {
        return btnModify;
    }

    public JButton getBtnInstall() {
        return btnInstall;
    }

    public JButton getBtnInfect() {
        return btnInfect;
    }

    public JButton getBtnExecute() {
        return btnExecute;
    }

    public JButton getBtnShop() {
        return btnShop;
    }

    public JButton getBtnEndAction() {
        return btnEndAction;
    }
}
