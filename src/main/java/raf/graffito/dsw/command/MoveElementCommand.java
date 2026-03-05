package raf.graffito.dsw.command;

import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.gui.swing.SlideView;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MoveElementCommand extends AbstractCommand {
    private List<SlideElement> elements;
    private Point delta;
    private List<Point> startPositions;

    public MoveElementCommand(SlideView slideView, List<SlideElement> elements, Point start, Point end) {
        super(slideView);
        this.elements = new ArrayList<>(elements);
        this.delta = new Point(end.x - start.x, end.y - start.y);
        this.startPositions = new ArrayList<>();
        for (SlideElement element : elements) {
            startPositions.add(new Point(element.getX(), element.getY()));
        }
    }

    @Override
    public void doCommand() {
        if (elements != null && slideView != null) {
            for (int i = 0; i < elements.size(); i++) {
                SlideElement element = elements.get(i);
                Point startPos = startPositions.get(i);
                element.setX(startPos.x + delta.x);
                element.setY(startPos.y + delta.y);
            }
            slideView.notifySlideChanged();
            slideView.repaint();
        }
    }

    @Override
    public void undoCommand() {
        if (elements != null && slideView != null) {
            for (int i = 0; i < elements.size(); i++) {
                SlideElement element = elements.get(i);
                Point startPos = startPositions.get(i);
                element.setX(startPos.x);
                element.setY(startPos.y);
            }
            slideView.notifySlideChanged();
            slideView.repaint();
        }
    }
}

