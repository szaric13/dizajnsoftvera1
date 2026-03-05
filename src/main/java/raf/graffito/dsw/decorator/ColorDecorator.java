package raf.graffito.dsw.decorator;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import java.awt.Color;

public class ColorDecorator extends GraffNodeDecorator {
    private Color color;
    public ColorDecorator(GraffNode node, Color color) {
        super(node);
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    @Override
    public boolean isDecorator() {
        return true;
    }
}
