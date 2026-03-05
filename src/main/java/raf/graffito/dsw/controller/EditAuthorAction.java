package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.message.EventType;
import raf.graffito.dsw.tree.GraffTree;
import raf.graffito.dsw.tree.model.GraffTreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class EditAuthorAction extends AbstractGraffAction{
    private GraffTree graffTree;

    public EditAuthorAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/authorimg.png"));
        putValue(NAME, "Author");
        putValue(SHORT_DESCRIPTION, "Author");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraffTreeItem selected = MainFrame.getInstance().getGraffTree().getSelectedNode();

        if (selected == null) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.NODE_NOT_SELECTED);
            return;
        }
        if (selected.getGraffNode() instanceof Project project) {
            String newAuthor = JOptionPane.showInputDialog(
                    MainFrame.getInstance(),
                    "Enter author's name:",
                    "Set Author",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (newAuthor == null || newAuthor.trim().isEmpty()) {
                return;
            }

            try {
                project.setAuthor(newAuthor.trim());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
