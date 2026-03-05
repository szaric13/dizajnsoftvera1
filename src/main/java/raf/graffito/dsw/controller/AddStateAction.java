package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;

import java.awt.event.ActionEvent;

public class AddStateAction extends AbstractGraffAction {
    public AddStateAction() {
        putValue(SMALL_ICON, loadIcon("/images/addstate.png"));
        putValue(NAME, "Add Element");
        putValue(SHORT_DESCRIPTION, "Add new elements to slide");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv != null) {
            pv.setAddState();
        }
    }
}

