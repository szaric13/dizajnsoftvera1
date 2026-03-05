package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;
import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.event.ActionEvent;

public class UndoAction extends AbstractGraffAction {
    public UndoAction() {
        putValue(SMALL_ICON, loadIcon("/images/undo.png"));
        putValue(NAME, "Undo");
        putValue(SHORT_DESCRIPTION, "Undo");
        disable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv != null) {
            SlideView sv = pv.getCurrentSlideView();
            if (sv != null) {
                sv.getCommandManager().undoCommand();
                updateEnabledState();
                MainFrame.getInstance().getActionManager().getRedoAction().updateEnabledState();
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
            if (sv != null && sv.getCommandManager().canUndo()) {
                enable();
            } else {
                disable();
            }
        } else {
            disable();
        }
    }
}

