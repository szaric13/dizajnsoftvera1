package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.graffRepository.implementation.Workspace;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.serializer.JacksonSerializer;

import java.awt.event.ActionEvent;

public class LoadTemplateAction extends AbstractGraffAction {
    public LoadTemplateAction() {
        putValue(SMALL_ICON, loadIcon("/images/openastemplate.png"));
        putValue(NAME, "Load Template");
        putValue(SHORT_DESCRIPTION, "Load Project Template");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JacksonSerializer jacksonSerializer = new JacksonSerializer();
        Project project = jacksonSerializer.loadProjectTemplate();
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

