package raf.graffito.dsw.command;

import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.gui.swing.SlideView;

public class AddElementCommand extends AbstractCommand {
    private SlideElement element;

    public AddElementCommand(SlideView slideView, SlideElement element) {
        super(slideView);
        this.element = element;
    }

    @Override
    public void doCommand() {
        if (element != null && slideView != null) {
            slideView.addElement(element);
        }
    }

    @Override
    public void undoCommand() {
        if (element != null && slideView != null) {
            slideView.removeElement(element);
        }
    }
}

