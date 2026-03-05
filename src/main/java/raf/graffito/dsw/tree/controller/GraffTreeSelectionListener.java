package raf.graffito.dsw.tree.controller;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.tree.model.GraffTreeItem;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;

public class GraffTreeSelectionListener extends MouseAdapter implements TreeSelectionListener {
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        if (path == null) {
            updateActions(null);
            return;
        }
        GraffTreeItem treeItemSelected = (GraffTreeItem)path.getLastPathComponent();
        if (treeItemSelected == null) {
            updateActions(null);
            return;
        }
        System.out.println("Selektovan cvor:"+ treeItemSelected.getGraffNode().getName());
        System.out.println("getPath: "+e.getPath());

        updateActions(treeItemSelected.getGraffNode());
    }

    private void updateActions(GraffNode selectedNode) {
        if (selectedNode == null) {
            MainFrame.getInstance().getActionManager().getSaveAction().disable();
            MainFrame.getInstance().getActionManager().getSaveAsAction().disable();
            return;
        }

        Project project = getProject(selectedNode);
        if (project != null) {
            if (project.isChanged()) {
                MainFrame.getInstance().getActionManager().getSaveAction().enable();
            } else {
                MainFrame.getInstance().getActionManager().getSaveAction().disable();
            }
            MainFrame.getInstance().getActionManager().getSaveAsAction().enable();
            MainFrame.getInstance().getActionManager().getSaveProjectAsTemplateAction().enable();
        } else {
            MainFrame.getInstance().getActionManager().getSaveAction().disable();
            MainFrame.getInstance().getActionManager().getSaveAsAction().disable();
            MainFrame.getInstance().getActionManager().getSaveProjectAsTemplateAction().disable();
        }

        MainFrame.getInstance().getActionManager().getUndoAction().updateEnabledState();
        MainFrame.getInstance().getActionManager().getRedoAction().updateEnabledState();
    }

    private Project getProject(GraffNode node) {
        while (node != null && !(node instanceof Project)) {
            node = node.getParent();
        }
        return (Project) node;
    }
}
