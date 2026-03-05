package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.observer.Subscriber;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PresentationView extends JPanel implements Subscriber {
    private Presentation presentation;
    private JLabel lblPresentationName;
    private JLabel lblProjectName;
    private JLabel lblAuthor;
    private JTabbedPane slideTabs;
    private List<SlideView> slideViews;
    public PresentationView(Presentation presentation) {
        this.presentation = presentation;
        initialize();
        loadPresentation();
    }
    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        lblPresentationName = new JLabel();
        lblPresentationName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblProjectName = new JLabel();
        lblProjectName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAuthor = new JLabel();
        lblAuthor.setAlignmentX(Component.CENTER_ALIGNMENT);
        slideTabs = new JTabbedPane();
        slideViews = new ArrayList<>();

        slideTabs.addChangeListener(e -> {
            if (MainFrame.getInstance().getSideBar() != null) {
                MainFrame.getInstance().getSideBar().updateImagesPanel();
            }

            if (MainFrame.getInstance().getActionManager() != null) {
                MainFrame.getInstance().getActionManager().getUndoAction().updateEnabledState();
                MainFrame.getInstance().getActionManager().getRedoAction().updateEnabledState();
            }
        });

        add(lblPresentationName);
        add(lblProjectName);
        add(lblAuthor);
        add(slideTabs);
    }
    private void loadPresentation() {
        if (presentation == null) return;
        presentation.addSubscriber(this);
        if (presentation.getParent() instanceof Project project) {
            project.addSubscriber(this);
        }
        updateLabels();
        loadSlides();
    }
    public void updateLabels() {
        lblPresentationName.setText("Presentation: " + presentation.getName());
        if (presentation.getParent() instanceof Project project) {
            lblProjectName.setText("Project: " + project.getName());
            lblAuthor.setText("Author: " + project.getAuthor());
        }
    }
    public void updateTabName() {
        int index = MainFrame.getInstance().getTabIndex(this);
        if (index >= 0) {
            MainFrame.getInstance().getTabbedPane().setTitleAt(index, presentation.getName());
        }
    }
    public void loadSlides() {
        slideTabs.removeAll();
        slideViews.clear();

        for (GraffNode child : presentation.getChildren()) {
            if (child instanceof Slide slide) {
                SlideView view = new SlideView(slide);
                view.setPresentationView(this);
                slideViews.add(view);
                slideTabs.addTab(slide.getName(), view);
            }
        }
    }

    public SlideView getCurrentSlideView() {
        Component selected = slideTabs.getSelectedComponent();
        if (selected instanceof SlideView) {
            return (SlideView) selected;
        }
        return null;
    }

    public void setSelectState() {
        SlideView sv = getCurrentSlideView();
        if (sv != null) {
            sv.getStateManager().setState(new raf.graffito.dsw.state.SelectState());
        }
    }

    public void setAddState() {
        SlideView sv = getCurrentSlideView();
        if (sv != null) {
            sv.getStateManager().setState(new raf.graffito.dsw.state.AddState());
        }
    }

    public void setAddImageState() {
        SlideView sv = getCurrentSlideView();
        if (sv != null) {
            raf.graffito.dsw.state.AddState addState = new raf.graffito.dsw.state.AddState();
            addState.setElementType(raf.graffito.dsw.state.AddState.ElementType.IMAGE);
            sv.getStateManager().setState(addState);
        }
    }

    public void setAddTextState() {
        SlideView sv = getCurrentSlideView();
        if (sv != null) {
            raf.graffito.dsw.state.AddState addState = new raf.graffito.dsw.state.AddState();
            addState.setElementType(raf.graffito.dsw.state.AddState.ElementType.TEXT);
            sv.getStateManager().setState(addState);
        }
    }

    public void setAddLogoState() {
        SlideView sv = getCurrentSlideView();
        if (sv != null) {
            raf.graffito.dsw.state.AddState addState = new raf.graffito.dsw.state.AddState();
            addState.setElementType(raf.graffito.dsw.state.AddState.ElementType.LOGO);
            sv.getStateManager().setState(addState);
        }
    }

    public void setMoveState() {
        SlideView sv = getCurrentSlideView();
        if (sv != null) {
            sv.getStateManager().setState(new raf.graffito.dsw.state.MoveState());
        }
    }

    public void setDeleteState() {
        SlideView sv = getCurrentSlideView();
        if (sv != null) {
            sv.getStateManager().setState(new raf.graffito.dsw.state.DeleteState());
        }
    }

    public void setResizeState() {
        SlideView sv = getCurrentSlideView();
        if (sv != null) {
            sv.getStateManager().setState(new raf.graffito.dsw.state.ResizeState());
        }
    }

    public void rotateSelectedElements(double angle) {
        SlideView sv = getCurrentSlideView();
        if (sv != null) {
            sv.rotateSelectedElements(angle);
        }
    }
    @Override
    public void update(Object notification) {
        if (notification instanceof Presentation || notification instanceof Project) {
            updateLabels();
            updateTabName();
            loadSlides();
            return;
        }
        if (notification instanceof String msg) {
            switch (msg) {
                case "presentation renamed":
                case "project renamed":
                case "project author changed":
                    updateLabels();
                    updateTabName();
                    break;
                case "child":
                    loadSlides();
                    break;
                case "child removed":
                    MainFrame.getInstance().removeTabForPresentation(presentation);
                    break;
                case "clear":
                    slideTabs.removeAll();
                    slideViews.clear();
                    lblPresentationName.setText("");
                    lblProjectName.setText("");
                    lblAuthor.setText("");
                    break;
            }
        }
    }
    public Presentation getPresentation() {
        return presentation;
    }
}
