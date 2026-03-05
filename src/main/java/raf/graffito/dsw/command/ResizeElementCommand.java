package raf.graffito.dsw.command;

import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.Dimension;
import java.awt.Point;

public class ResizeElementCommand extends AbstractCommand {
    private SlideElement element;
    private Point startPosition;
    private Dimension startSize;
    private Point endPosition;
    private Dimension endSize;

    public ResizeElementCommand(SlideView slideView, SlideElement element,
                                Point startPos, Dimension startSize,
                                Point endPos, Dimension endSize) {
        super(slideView);
        this.element = element;
        this.startPosition = new Point(startPos);
        this.startSize = new Dimension(startSize);
        this.endPosition = new Point(endPos);
        this.endSize = new Dimension(endSize);
    }

    @Override
    public void doCommand() {
        if (element != null && slideView != null) {
            element.setX(endPosition.x);
            element.setY(endPosition.y);
            element.setWidth(endSize.width);
            element.setHeight(endSize.height);
            slideView.notifySlideChanged();
            slideView.repaint();
        }
    }

    @Override
    public void undoCommand() {
        if (element != null && slideView != null) {
            element.setX(startPosition.x);
            element.setY(startPosition.y);
            element.setWidth(startSize.width);
            element.setHeight(startSize.height);
            slideView.notifySlideChanged();
            slideView.repaint();
        }
    }
}

