package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.observer.Subscriber;
import javax.swing.*;
import java.awt.*;

public class ProjectView extends JPanel implements Subscriber {
    private final Project project;
    private Presentation presentation;
    private JLabel lblProjectName;
    private JLabel lblAuthor;
    private JLabel lblPresentationName;

    public ProjectView(Project project, Presentation presentation) {
        this.project = project;
        this.presentation = presentation;
        project.addSubscriber(this);
        presentation.addSubscriber(this);
        initUI();
        updateLabels();
    }

    private void initUI() {
        setLayout(new GridLayout(3, 1));
        lblProjectName = new JLabel();
        lblAuthor = new JLabel();
        lblPresentationName = new JLabel();
        add(lblProjectName);
        add(lblAuthor);
        add(lblPresentationName);
    }
    private void updateLabels() {
        lblProjectName.setText("Project: " + project.getName());
        lblAuthor.setText("Author: " + project.getAuthor());
        lblPresentationName.setText("Presentation: " + presentation.getName());
    }

    @Override
    public void update(Object notification) {
        updateLabels();
        revalidate();
        repaint();
    }

    public void setPresentation(Presentation newPresentation) {
        presentation.removeSubscriber(this);
        this.presentation = newPresentation;
        presentation.addSubscriber(this);
        updateLabels();
    }
}

