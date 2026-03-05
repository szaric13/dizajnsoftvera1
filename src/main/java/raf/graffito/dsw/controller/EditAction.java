package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Workspace;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.message.EventType;
import raf.graffito.dsw.tree.model.GraffTreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class EditAction extends AbstractGraffAction{
    public EditAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/editimg.png"));
        putValue(NAME, "Rename");
        putValue(SHORT_DESCRIPTION, "Rename");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraffTreeItem selected = (GraffTreeItem) MainFrame.getInstance().getGraffTree().getSelectedNode();

        if (selected == null) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.NODE_NOT_SELECTED);
            return;
        }

        String newName = JOptionPane.showInputDialog(MainFrame.getInstance(), "New name:\n", "Rename", JOptionPane.QUESTION_MESSAGE);
        if (newName == null) return;
        newName = newName.trim();
        if (newName.isEmpty()) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.MUST_INSERT_NAME);
            return;
        }
        GraffNode[] siblings;
        if (selected.getGraffNode() instanceof Workspace) {
            siblings = ((Workspace) selected.getGraffNode()).getChildren().toArray(new GraffNode[0]);
        } else {
            siblings = selected.getGraffNode().getParent(1).getChildren().toArray(new GraffNode[0]);
        }

        for (GraffNode child : siblings) {
            if (!child.equals(selected.getGraffNode()) && child.getName().equals(newName)) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.NODE_ALREADY_EXISTS);
                return;
            }
        }

        try {
            selected.setName(newName);
            selected.getGraffNode().setName(newName);
            MainFrame.getInstance().getGraffTree().editNode(selected);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
