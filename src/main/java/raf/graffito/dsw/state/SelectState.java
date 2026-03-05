package raf.graffito.dsw.state;

import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;

public class SelectState implements State {
    private Point selectionStart;
    private boolean isSelecting;

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
        Point adjustedPoint = slideView.adjustPoint(e.getPoint());

        for (SlideElement selectedElement : slideView.getSelectedElements()) {
            Rectangle bounds = selectedElement.getBounds();
            if (isOnResizeCorner(adjustedPoint, bounds)) {

                slideView.getStateManager().setState(new raf.graffito.dsw.state.ResizeState());
                slideView.getStateManager().getCurrentState().mousePressed(e, slideView);
                return;
            }
        }

        SlideElement clickedElement = slideView.getElementAt(adjustedPoint);

        if (clickedElement != null) {

            if (!e.isControlDown()) {
                slideView.clearSelection();
            }
            slideView.addToSelection(clickedElement);
        } else {

            if (!e.isControlDown()) {
                slideView.clearSelection();
            }
            selectionStart = adjustedPoint;
            isSelecting = true;
            slideView.setSelectionStart(selectionStart);
            slideView.setSelectionEnd(selectionStart);
        }
        slideView.repaint();
    }

    private boolean isOnResizeCorner(Point point, Rectangle bounds) {
        int cornerSize = 8;

        return (Math.abs(point.x - bounds.x) <= cornerSize && Math.abs(point.y - bounds.y) <= cornerSize) ||
                (Math.abs(point.x - (bounds.x + bounds.width)) <= cornerSize && Math.abs(point.y - bounds.y) <= cornerSize) ||
                (Math.abs(point.x - (bounds.x + bounds.width)) <= cornerSize && Math.abs(point.y - (bounds.y + bounds.height)) <= cornerSize) ||
                (Math.abs(point.x - bounds.x) <= cornerSize && Math.abs(point.y - (bounds.y + bounds.height)) <= cornerSize);
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {
        if (isSelecting && selectionStart != null) {
            Point adjustedPoint = slideView.adjustPoint(e.getPoint());
            slideView.setSelectionEnd(adjustedPoint);

            Rectangle selectionRect = slideView.getSelectionRectangle();
            if (selectionRect != null && !selectionRect.isEmpty()) {
                List<SlideElement> elementsInRect = slideView.getElementsInRectangle(selectionRect);
                for (SlideElement element : elementsInRect) {
                    slideView.addToSelection(element);
                }
            }

            slideView.setSelectionStart(null);
            slideView.setSelectionEnd(null);
            isSelecting = false;
        }
        slideView.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {
        if (isSelecting && selectionStart != null) {
            Point adjustedPoint = slideView.adjustPoint(e.getPoint());
            slideView.setSelectionEnd(adjustedPoint);
            slideView.repaint();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e, SlideView slideView) {
        slideView.zoom(e);
    }
}

