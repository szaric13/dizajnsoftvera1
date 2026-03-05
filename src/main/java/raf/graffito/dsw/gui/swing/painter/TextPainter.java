package raf.graffito.dsw.gui.swing.painter;

import raf.graffito.dsw.graffRepository.implementation.slide.TextElement;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class TextPainter extends ElementPainter {

    public TextPainter(TextElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        TextElement textElement = (TextElement) element;

        AffineTransform oldTransform = g.getTransform();

        if (textElement.getRotation() != 0) {
            double centerX = element.getX() + element.getWidth() / 2.0;
            double centerY = element.getY() + element.getHeight() / 2.0;
            g.rotate(Math.toRadians(textElement.getRotation()), centerX, centerY);
        }

        g.setFont(textElement.getFont());
        g.setColor(textElement.getTextColor());

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(textElement.getText());
        int textHeight = fm.getHeight();
        int x = element.getX() + (element.getWidth() - textWidth) / 2;
        int y = element.getY() + (element.getHeight() + textHeight) / 2 - fm.getDescent();

        g.drawString(textElement.getText(), x, y);

        g.setTransform(oldTransform);
    }
}

