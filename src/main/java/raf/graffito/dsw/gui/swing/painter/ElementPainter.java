package raf.graffito.dsw.gui.swing.painter;

import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;

import java.awt.*;

public abstract class ElementPainter {
    protected SlideElement element;

    public ElementPainter(SlideElement element) {
        this.element = element;
    }

    public abstract void draw(Graphics2D g);

    public boolean contains(Point point) {
        return element.getBounds().contains(point);
    }

    public SlideElement getElement() {
        return element;
    }
}

