package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.graffRepository.implementation.Workspace;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.serializer.JacksonSerializer;

import java.awt.event.ActionEvent;

public class OpenAction extends AbstractGraffAction {
    public OpenAction() {
        putValue(SMALL_ICON, loadIcon("/images/open.png"));
        putValue(NAME, "Open");
        putValue(SHORT_DESCRIPTION, "Open");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JacksonSerializer jacksonSerializer = new JacksonSerializer();
        Project project = jacksonSerializer.openProject();
        if (project == null) {
            return;
        }

        Workspace root = ApplicationFramework.getInstance().getGraffRepository().getWorkspace();

        for (GraffNode c : root.getChildren()) {
            if (c.equals(project)) {

                return;
            }
        }

        root.addChild(project);

        MainFrame.getInstance().getGraffTree().addProjectToTree(project);
    }
}

