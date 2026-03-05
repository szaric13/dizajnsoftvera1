package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;

import java.awt.event.ActionEvent;

public class DeleteStateAction extends AbstractGraffAction {
    public DeleteStateAction() {
        putValue(SMALL_ICON, loadIcon("/images/deleteimg.png"));
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete elements");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv != null) {
            pv.setDeleteState();
        }
    }
}

