package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;

import java.awt.event.ActionEvent;

public class AddTextAction extends AbstractGraffAction {
    public AddTextAction() {
        putValue(SMALL_ICON, loadIcon("/images/addtext.png"));
        putValue(NAME, "Add Text");
        putValue(SHORT_DESCRIPTION, "Add text element");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv != null) {
            pv.setAddTextState();
        }
    }
}

