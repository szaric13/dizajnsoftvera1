package raf.graffito.dsw.command;

import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.gui.swing.SlideView;

import java.util.ArrayList;
import java.util.List;

public class RotateElementCommand extends AbstractCommand {
    private List<SlideElement> elements;
    private double angle;
    private List<Double> startRotations;

    public RotateElementCommand(SlideView slideView, List<SlideElement> elements, double angle) {
        super(slideView);
        this.elements = new ArrayList<>(elements);
        this.angle = angle;
        this.startRotations = new ArrayList<>();
        for (SlideElement element : elements) {
            startRotations.add(element.getRotation());
        }
    }

    @Override
    public void doCommand() {
        if (elements != null && slideView != null) {
            for (SlideElement element : elements) {
                double newRotation = element.getRotation() + angle;
                newRotation = ((newRotation % 360) + 360) % 360;
                element.setRotation(newRotation);
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
                element.setRotation(startRotations.get(i));
            }
            slideView.notifySlideChanged();
            slideView.repaint();
        }
    }
}

