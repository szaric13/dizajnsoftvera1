package raf.graffito.dsw.controller;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.serializer.JacksonSerializer;
import raf.graffito.dsw.tree.model.GraffTreeItem;

import java.awt.event.ActionEvent;

public class SaveAsAction extends AbstractGraffAction {
    public SaveAsAction() {
        putValue(SMALL_ICON, loadIcon("/images/saveas.png"));
        putValue(NAME, "Save as");
        putValue(SHORT_DESCRIPTION, "Save as");
        disable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraffTreeItem selectedItem = MainFrame.getInstance().getGraffTree().getSelectedNode();
        if (selectedItem == null) {
            return;
        }
        GraffNode selectedNode = selectedItem.getGraffNode();
        if (selectedNode == null) {
            return;
        }

        Project project = getProject(selectedNode);
        if (project == null) {
            return;
        }

        JacksonSerializer jacksonSerializer = new JacksonSerializer();
        jacksonSerializer.setProjectPath(project);
        jacksonSerializer.saveProject(project);
        MainFrame.getInstance().getActionManager().getSaveAction().disable();
    }

    public void enable() {
        super.setEnabled(true);
    }

    public void disable() {
        super.setEnabled(false);
    }

    private Project getProject(GraffNode node) {
        while (node != null && !(node instanceof Project)) {
            node = node.getParent();
        }
        return (Project) node;
    }
}

