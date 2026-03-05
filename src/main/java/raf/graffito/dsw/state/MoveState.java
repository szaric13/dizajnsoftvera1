package raf.graffito.dsw.state;

import raf.graffito.dsw.command.MoveElementCommand;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class MoveState implements State {
    private Point dragStart;
    private List<Point> elementStartPositions;
    private List<SlideElement> elementsBeingMoved;

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
        Point adjustedPoint = slideView.adjustPoint(e.getPoint());
        SlideElement clickedElement = slideView.getElementAt(adjustedPoint);

        if (clickedElement != null) {

            if (!slideView.isElementSelected(clickedElement)) {
                slideView.clearSelection();
                slideView.addToSelection(clickedElement);
            }

            dragStart = adjustedPoint;
            elementsBeingMoved = new ArrayList<>(slideView.getSelectedElements());
            elementStartPositions = new ArrayList<>();

            for (SlideElement element : elementsBeingMoved) {
                elementStartPositions.add(new Point(element.getX(), element.getY()));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {
        if (dragStart != null && elementsBeingMoved != null && !elementsBeingMoved.isEmpty()) {
            Point adjustedPoint = slideView.adjustPoint(e.getPoint());

            MoveElementCommand command =
                    new MoveElementCommand(slideView, elementsBeingMoved, dragStart, adjustedPoint);
            slideView.getCommandManager().addCommand(command);
            markProjectChanged(slideView);

            dragStart = null;
            elementsBeingMoved = null;
            elementStartPositions = null;
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
    public void mouseDragged(MouseEvent e, SlideView slideView) {
        if (dragStart != null && elementsBeingMoved != null) {
            Point adjustedPoint = slideView.adjustPoint(e.getPoint());
            int deltaX = adjustedPoint.x - dragStart.x;
            int deltaY = adjustedPoint.y - dragStart.y;

            for (int i = 0; i < elementsBeingMoved.size(); i++) {
                SlideElement element = elementsBeingMoved.get(i);
                Point startPos = elementStartPositions.get(i);
                int newX = startPos.x + deltaX;
                int newY = startPos.y + deltaY;

                newX = Math.max(0, Math.min(newX, Slide.SLIDE_WIDTH - element.getWidth()));
                newY = Math.max(0, Math.min(newY, Slide.SLIDE_HEIGHT - element.getHeight()));

                element.setX(newX);
                element.setY(newY);
            }
            slideView.repaint();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e, SlideView slideView) {
        slideView.zoom(e);
    }
}

