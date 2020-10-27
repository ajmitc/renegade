package renegade.view;

import renegade.game.Contaminant;
import renegade.game.ContaminantType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContaminantSelectionDialog extends JDialog {
    private static final int SPACING = 5;
    private static final int CONTAMINANT_SIZE = 50;
    private static final Color SELECTED_COLOR = Color.RED;

    public static final int POLICY_UP_TO_3_SAME_COLOR = 0;
    public static final int POLICY_SINGLE = 1;

    private List<SelectableContaminant> selectableContaminants = new ArrayList<>();

    public ContaminantSelectionDialog(String title, List<Contaminant> contaminants, int policy){
        super((JFrame) null, title, true);

        setPreferredSize(new Dimension(Math.min(contaminants.size() * (SPACING + CONTAMINANT_SIZE), 300), CONTAMINANT_SIZE + 50));
        setSize(getPreferredSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        for (int i = 0; i < contaminants.size(); ++i) {
            Image image = ImageUtil.get(contaminants.get(i).getType().getFilename(), CONTAMINANT_SIZE, contaminants.get(i).getType().getFilename() + CONTAMINANT_SIZE);
            Rectangle bounds = new Rectangle(i * (image.getWidth(null) + SPACING), 0, CONTAMINANT_SIZE, CONTAMINANT_SIZE);
            SelectableContaminant selectableContaminant = new SelectableContaminant(contaminants.get(i), bounds, image);
            selectableContaminants.add(selectableContaminant);
        }

        JPanel contaminantPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics graphics){
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, getWidth(), getHeight());
                for (int i = 0; i < selectableContaminants.size(); ++i) {
                    SelectableContaminant c = selectableContaminants.get(i);
                    Image image = c.getImage();
                    graphics.drawImage(image, c.bounds.x, c.bounds.y, null);
                    if (c.isSelected()){
                        graphics.setColor(SELECTED_COLOR);
                        graphics.drawRect(c.bounds.x, c.bounds.y, c.bounds.width, c.bounds.height);
                    }
                }
            }
        };

        contaminantPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (SelectableContaminant selectableContaminant: selectableContaminants){
                    if (selectableContaminant.getBounds().contains(e.getX(), e.getY())){
                        selectableContaminant.setSelected(!selectableContaminant.isSelected());
                        enforcePolicy(policy, selectableContaminant);
                        contaminantPanel.repaint();
                        break;
                    }
                }
            }
        });

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrapperPanel.add(contaminantPanel, BorderLayout.CENTER);

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

    public List<Contaminant> getSelected() {
        return selectableContaminants.stream().filter(c -> c.isSelected()).map(sc -> sc.getContaminant()).collect(Collectors.toList());
    }

    public void enforcePolicy(int policy, SelectableContaminant lastSelected){
        switch (policy){
            case POLICY_UP_TO_3_SAME_COLOR:
                enforcePolicyUpTo3SameColor(lastSelected);
                break;
            case POLICY_SINGLE:
                enforcePolicySingle(lastSelected);
                break;
        }
    }

    private void enforcePolicySingle(SelectableContaminant lastSelected){
        if (selectableContaminants.stream().filter(sc -> sc.isSelected()).count() > 1)
            lastSelected.setSelected(false);
    }

    private void enforcePolicyUpTo3SameColor(SelectableContaminant lastSelected){
        List<Contaminant> selected = selectableContaminants.stream().filter(sc -> sc.isSelected()).map(sc -> sc.getContaminant()).distinct().collect(Collectors.toList());
        if (selected.size() == 0)
            return;
        if (selected.size() > 1) {
            // Uh oh, selected a different color contaminant, deselect all except lastSelected
            selectableContaminants.stream().forEach(sc -> sc.setSelected(false));
            lastSelected.setSelected(true);
        }
        else{
            // Ensure no more than 3 are selected
            if (selectableContaminants.stream().filter(sc -> sc.isSelected()).count() > 3){
                lastSelected.setSelected(false);
            }
        }
    }

    private static class SelectableContaminant{
        private Contaminant contaminant;
        private boolean selected;
        private Rectangle bounds;
        private Image image;

        public SelectableContaminant(Contaminant contaminant, Rectangle bounds, Image image){
            this.contaminant = contaminant;
            this.selected = false;
            this.bounds = bounds;
            this.image = image;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isSelected() {
            return selected;
        }

        public Contaminant getContaminant() {
            return contaminant;
        }

        public Rectangle getBounds() {
            return bounds;
        }

        public Image getImage() {
            return image;
        }
    }
}
