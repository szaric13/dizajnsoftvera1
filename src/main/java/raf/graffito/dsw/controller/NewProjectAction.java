package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.message.EventType;
import raf.graffito.dsw.tree.GraffTree;
import raf.graffito.dsw.tree.model.GraffTreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class NewProjectAction extends AbstractGraffAction{
    public NewProjectAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/newprojectimg.png"));
        putValue(NAME, "New Project");
        putValue(SHORT_DESCRIPTION, "New Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraffTreeItem selected = MainFrame.getInstance().getGraffTree().getSelectedNode();
        if (selected == null) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.NODE_NOT_SELECTED);
            return;
        }
        if (selected.getGraffNode() instanceof Slide) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.CANNOT_ADD_CHILD);
            return;
        }
        try {
            MainFrame.getInstance().getGraffTree().addChild(selected);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
