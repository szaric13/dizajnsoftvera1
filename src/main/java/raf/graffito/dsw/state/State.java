package raf.graffito.dsw.state;

import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface State {
    void mousePressed(MouseEvent e, SlideView slideView);
    void mouseReleased(MouseEvent e, SlideView slideView);
    void mouseDragged(MouseEvent e, SlideView slideView);
    void mouseWheelMoved(MouseWheelEvent e, SlideView slideView);
}

