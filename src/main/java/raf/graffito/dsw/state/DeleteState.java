package raf.graffito.dsw.state;

import raf.graffito.dsw.command.DeleteElementCommand;
import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;

public class DeleteState implements State {
    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {

        List<SlideElement> selected = slideView.getSelectedElements();
        if (!selected.isEmpty()) {
            DeleteElementCommand command =
                    new DeleteElementCommand(slideView, selected);
            slideView.getCommandManager().addCommand(command);
            slideView.clearSelection();
            markProjectChanged(slideView);
            return;
        }

        SlideElement clickedElement = slideView.getElementAt(slideView.adjustPoint(e.getPoint()));
        if (clickedElement != null) {
            List<SlideElement> toDelete = new java.util.ArrayList<>();
            toDelete.add(clickedElement);
            DeleteElementCommand command =
                    new DeleteElementCommand(slideView, toDelete);
            slideView.getCommandManager().addCommand(command);
            markProjectChanged(slideView);
        }
    }

    private void markProjectChanged(SlideView slideView) {
        if (slideView.getPresentationView() != null) {
            raf.graffito.dsw.graffRepository.implementation.Presentation presentation =
                    slideView.getPresentationView().getPresentation();
            if (presentation != null && presentation.getParent() != null) {
                raf.graffito.dsw.graffRepository.composite.GraffNode parent = presentation.getParent();
                while (parent != null && !(parent instanceof raf.graffito.dsw.graffRepository.implementation.Project)) {
                    parent = parent.getParent();
                }
                if (parent instanceof raf.graffito.dsw.graffRepository.implementation.Project) {
                    ((raf.graffito.dsw.graffRepository.implementation.Project) parent).setChanged(true);

                    if (MainFrame.getInstance() != null) {
                        MainFrame.getInstance().getActionManager().getSaveAction().enable();
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {

    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e, SlideView slideView) {
        slideView.zoom(e);
    }
}

