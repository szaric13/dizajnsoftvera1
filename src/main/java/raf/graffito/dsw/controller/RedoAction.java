package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;
import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.event.ActionEvent;

public class RedoAction extends AbstractGraffAction {
    public RedoAction() {
        putValue(SMALL_ICON, loadIcon("/images/redo.png"));
        putValue(NAME, "Redo");
        putValue(SHORT_DESCRIPTION, "Redo");
        disable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv != null) {
            SlideView sv = pv.getCurrentSlideView();
            if (sv != null) {
                sv.getCommandManager().redoCommand();
                updateEnabledState();

                MainFrame.getInstance().getActionManager().getUndoAction().updateEnabledState();
            }
        }
    }

    public void enable() {
        super.setEnabled(true);
    }

    public void disable() {
        super.setEnabled(false);
    }

    public void updateEnabledState() {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv != null) {
            SlideView sv = pv.getCurrentSlideView();
            if (sv != null && sv.getCommandManager().canRedo()) {
                enable();
            } else {
                disable();
            }
        } else {
            disable();
        }
    }
}

