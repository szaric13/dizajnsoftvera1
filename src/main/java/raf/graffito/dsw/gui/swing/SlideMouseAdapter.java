package raf.graffito.dsw.gui.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class SlideMouseAdapter extends MouseAdapter {
    private final SlideView slideView;

    public SlideMouseAdapter(SlideView slideView) {
        this.slideView = slideView;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        slideView.handleMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        slideView.handleMouseReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        slideView.handleMouseDragged(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        slideView.handleMouseWheelMoved(e);
    }
}

