package renegade;

import renegade.view.View;

public class Main {

    public Main() {
        Model model = new Model();
        View view = new View(model);
        new Controller(model, view);
        view.getFrame().setVisible(true);
    }
}