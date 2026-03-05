package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;

import java.awt.event.ActionEvent;

public class RotateLeftAction extends AbstractGraffAction {
    public RotateLeftAction() {
        putValue(SMALL_ICON, loadIcon("/images/rotateleft.png"));
        putValue(NAME, "Rotate Left");
        putValue(SHORT_DESCRIPTION, "Rotate selected elements 90° left");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv != null) {
            pv.rotateSelectedElements(-90);
        }
    }
}

