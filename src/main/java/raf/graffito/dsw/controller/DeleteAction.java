package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.graffRepository.implementation.Workspace;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.message.EventType;
import raf.graffito.dsw.tree.GraffTree;
import raf.graffito.dsw.tree.model.GraffTreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteAction extends AbstractGraffAction{
    public DeleteAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/deleteimg.png"));
        putValue(NAME, "Delete Project");
        putValue(SHORT_DESCRIPTION, "Delete Project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraffTreeItem selected = MainFrame.getInstance().getGraffTree().getSelectedNode();
        if (selected == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.NODE_NOT_SELECTED);
            return;
        }
        if (selected.getGraffNode() instanceof Workspace) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.WORKSPACE_CANNOT_BE_DELETED);
            return;
        }
        MainFrame.getInstance().getGraffTree().deleteNode(selected);
    }
}
