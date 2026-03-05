package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;

import java.awt.event.ActionEvent;

public class AddLogoAction extends AbstractGraffAction {
    public AddLogoAction() {
        putValue(SMALL_ICON, loadIcon("/images/addlogo.png"));
        putValue(NAME, "Add Logo");
        putValue(SHORT_DESCRIPTION, "Add logo element");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv != null) {
            pv.setAddLogoState();
        }
    }
}

