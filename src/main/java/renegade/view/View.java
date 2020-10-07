package renegade.view;

import renegade.Model;

import javax.swing.*;
import java.awt.*;

public class View {
    private Model model;
    private JFrame frame = new JFrame();

    public View(Model model) {
        this.model = model;

        frame.setTitle("Renegade");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocation(0, 0);
    }

    public void refresh(){

    }

    public JFrame getFrame() {
        return frame;
    }
}
