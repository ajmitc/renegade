package renegade.view;

import renegade.Model;

import javax.swing.*;
import java.awt.*;
import renegade.game.Action;

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
    private JLabel lblInfo       = new JLabel("");

    public ActionPanel(Model model, View view){
        super(new GridLayout(5, 2));
        this.model = model;
        this.view = view;

        add(btnMove);    add(btnShift);
        add(btnUpload);  add(btnModify);
        add(btnInstall); add(btnInfect);
        add(btnExecute); add(btnShop);
        add(lblInfo);    add(btnEndAction);

        btnMove.setToolTipText("Move to another partition. Cost: 1 Information Point per partition to enter.");
        btnShift.setToolTipText("Push a containment or spark from your partition to an adjacent partition. Cost: 1 Cognition Point per containment/spark to shift.");
        btnUpload.setToolTipText("Add a containment.  Most have no countermeasures and less than 3 of the uploaded containment. Cost: 3 Command Points of same color.");
        btnModify.setToolTipText("Convert a spark to a containment.  Must have more replicants than sparks. Cost: 1 Deception (Yellow) + 1 Command Point of desired containment");
        btnInstall.setToolTipText("Convert 3 containments to an installation. Cost: 1 Command Point + 3 Containments of desired installation");
        btnInfect.setToolTipText("Attack a spark or guardian with viruses. Cost: 1+ Viruses + 1 Destruction Point");
        btnExecute.setToolTipText("Execute special action on Advanced Card.");
        btnShop.setToolTipText("Purchase Advanced Cards from Hack Shack.");
        btnEndAction.setToolTipText("End the current action");
    }

    public void enableAll(){
        btnMove.setEnabled(true);
        btnShift.setEnabled(true);
        btnUpload.setEnabled(true);
        btnModify.setEnabled(true);
        btnInstall.setEnabled(true);
        btnInfect.setEnabled(true);
        btnExecute.setEnabled(true);
        btnShop.setEnabled(true);
    }

    public void disableAll() {
        btnMove.setEnabled(false);
        btnShift.setEnabled(false);
        btnUpload.setEnabled(false);
        btnModify.setEnabled(false);
        btnInstall.setEnabled(false);
        btnInfect.setEnabled(false);
        btnExecute.setEnabled(false);
        btnShop.setEnabled(false);
    }

    public void disableAllExcept(Action action){
        disableAll();
        switch (action){
            case MOVE:
                btnMove.setEnabled(true);
                break;
            case SHIFT:
                btnShift.setEnabled(true);
                break;
            case UPLOAD:
                btnUpload.setEnabled(true);
                break;
            case MODIFY:
                btnModify.setEnabled(true);
                break;
            case INSTALL:
                btnInstall.setEnabled(true);
                break;
            case INFECT:
                btnInfect.setEnabled(true);
                break;
            case EXECUTE:
                btnExecute.setEnabled(true);
                break;
            case SHOP:
                btnShop.setEnabled(true);
                break;
        }
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

    public void displayInfo(String text){
        lblInfo.setText(text);
    }

    public void clearInfo(){
        displayInfo("");
    }

    public void displayMovementPoints(int movementPoints){
        displayInfo("<html><center>Movement Points:<br/><b>" + movementPoints + "</b></center></html>");
    }
}
