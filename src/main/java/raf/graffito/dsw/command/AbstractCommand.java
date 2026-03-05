package raf.graffito.dsw.command;

import raf.graffito.dsw.gui.swing.SlideView;

public abstract class AbstractCommand {
    protected SlideView slideView;

    public AbstractCommand(SlideView slideView) {
        this.slideView = slideView;
    }

    public abstract void doCommand();
    public abstract void undoCommand();

    protected SlideView getSlideView() {
        return slideView;
    }
}

