package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;

import java.awt.event.ActionEvent;

public class RotateRightAction extends AbstractGraffAction {
    public RotateRightAction() {
        putValue(SMALL_ICON, loadIcon("/images/rotateright.png"));
        putValue(NAME, "Rotate Right");
        putValue(SHORT_DESCRIPTION, "Rotate selected elements 90° right");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv != null) {
            pv.rotateSelectedElements(90);
        }
    }
}

