package raf.graffito.dsw.state;

import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ResizeState implements State {
    private Point dragStart;
    private SlideElement elementBeingResized;
    private Point elementStartPosition;
    private Dimension elementStartSize;
    private int resizeCorner;

    private static final int CORNER_SIZE = 8;

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
        Point adjustedPoint = slideView.adjustPoint(e.getPoint());

        for (SlideElement element : slideView.getSelectedElements()) {
            Rectangle bounds = element.getBounds();
            int corner = getCornerAt(adjustedPoint, bounds);

            if (corner >= 0) {
                dragStart = adjustedPoint;
                elementBeingResized = element;
                elementStartPosition = new Point(element.getX(), element.getY());
                elementStartSize = new Dimension(element.getWidth(), element.getHeight());
                resizeCorner = corner;
                return;
            }
        }

        slideView.getStateManager().setState(new raf.graffito.dsw.state.SelectState());
        slideView.getStateManager().getCurrentState().mousePressed(e, slideView);
    }

    private int getCornerAt(Point point, Rectangle bounds) {

        if (isNearPoint(point, new Point(bounds.x, bounds.y))) return 0;
        if (isNearPoint(point, new Point(bounds.x + bounds.width, bounds.y))) return 1;
        if (isNearPoint(point, new Point(bounds.x + bounds.width, bounds.y + bounds.height))) return 2;
        if (isNearPoint(point, new Point(bounds.x, bounds.y + bounds.height))) return 3;
        return -1;
    }

    private boolean isNearPoint(Point p1, Point p2) {
        int distance = (int) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        return distance <= CORNER_SIZE;
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {
        if (dragStart != null && elementBeingResized != null) {
            Point adjustedPoint = slideView.adjustPoint(e.getPoint());
            resizeElement(adjustedPoint, slideView);

            Point finalPosition = new Point(elementBeingResized.getX(), elementBeingResized.getY());
            Dimension finalSize = new Dimension(elementBeingResized.getWidth(), elementBeingResized.getHeight());
            raf.graffito.dsw.command.ResizeElementCommand command =
                    new raf.graffito.dsw.command.ResizeElementCommand(
                            slideView, elementBeingResized,
                            elementStartPosition, elementStartSize,
                            finalPosition, finalSize
                    );
            slideView.getCommandManager().addCommand(command);
            markProjectChanged(slideView);

            dragStart = null;
            elementBeingResized = null;
            elementStartPosition = null;
            elementStartSize = null;
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

                    if (raf.graffito.dsw.gui.swing.MainFrame.getInstance() != null) {
                        raf.graffito.dsw.gui.swing.MainFrame.getInstance().getActionManager().getSaveAction().enable();
                    }
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {
        if (dragStart != null && elementBeingResized != null) {
            Point adjustedPoint = slideView.adjustPoint(e.getPoint());
            resizeElement(adjustedPoint, slideView);
            slideView.repaint();
        }
    }

    private void resizeElement(Point currentPoint, SlideView slideView) {
        int deltaX = currentPoint.x - dragStart.x;
        int deltaY = currentPoint.y - dragStart.y;

        int newX = elementStartPosition.x;
        int newY = elementStartPosition.y;
        int newWidth = elementStartSize.width;
        int newHeight = elementStartSize.height;

        switch (resizeCorner) {
            case 0:
                newX = elementStartPosition.x + deltaX;
                newY = elementStartPosition.y + deltaY;
                newWidth = elementStartSize.width - deltaX;
                newHeight = elementStartSize.height - deltaY;
                break;
            case 1:
                newY = elementStartPosition.y + deltaY;
                newWidth = elementStartSize.width + deltaX;
                newHeight = elementStartSize.height - deltaY;
                break;
            case 2:
                newWidth = elementStartSize.width + deltaX;
                newHeight = elementStartSize.height + deltaY;
                break;
            case 3:
                newX = elementStartPosition.x + deltaX;
                newWidth = elementStartSize.width - deltaX;
                newHeight = elementStartSize.height + deltaY;
                break;
        }

        newWidth = Math.max(20, newWidth);
        newHeight = Math.max(20, newHeight);

        newX = Math.max(0, Math.min(newX, Slide.SLIDE_WIDTH - newWidth));
        newY = Math.max(0, Math.min(newY, Slide.SLIDE_HEIGHT - newHeight));
        newWidth = Math.min(newWidth, Slide.SLIDE_WIDTH - newX);
        newHeight = Math.min(newHeight, Slide.SLIDE_HEIGHT - newY);

        elementBeingResized.setX(newX);
        elementBeingResized.setY(newY);
        elementBeingResized.setWidth(newWidth);
        elementBeingResized.setHeight(newHeight);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e, SlideView slideView) {
        slideView.zoom(e);
    }
}

