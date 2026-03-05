package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;

import java.awt.event.ActionEvent;

public class AddImageAction extends AbstractGraffAction {
    public AddImageAction() {
        putValue(SMALL_ICON, loadIcon("/images/addimage.png"));
        putValue(NAME, "Add Image");
        putValue(SHORT_DESCRIPTION, "Add image element");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv != null) {
            pv.setAddImageState();
        }
    }
}

