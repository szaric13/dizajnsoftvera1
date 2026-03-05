package raf.graffito.dsw.command;

import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.gui.swing.SlideView;
import raf.graffito.dsw.gui.swing.painter.ElementPainter;

import java.util.ArrayList;
import java.util.List;

public class DeleteElementCommand extends AbstractCommand {
    private List<SlideElement> elements;
    private List<ElementPainter> painters;

    public DeleteElementCommand(SlideView slideView, List<SlideElement> elements) {
        super(slideView);
        this.elements = new ArrayList<>(elements);
        this.painters = new ArrayList<>();
        for (SlideElement element : elements) {
            for (ElementPainter painter : slideView.getElementPainters()) {
                if (painter.getElement() == element) {
                    painters.add(painter);
                    break;
                }
            }
        }
    }

    @Override
    public void doCommand() {
        if (elements != null && slideView != null) {
            slideView.removeElements(elements);
        }
    }

    @Override
    public void undoCommand() {
        if (elements != null && slideView != null) {
            for (SlideElement element : elements) {
                slideView.addElement(element);
            }
        }
    }
}

