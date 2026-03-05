package raf.graffito.dsw.gui.swing.painter;

import raf.graffito.dsw.graffRepository.implementation.slide.ImageElement;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ImagePainter extends ElementPainter {

    public ImagePainter(ImageElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        ImageElement imageElement = (ImageElement) element;
        if (imageElement.getImage() == null) return;

        AffineTransform oldTransform = g.getTransform();

        if (imageElement.getRotation() != 0) {
            double centerX = element.getX() + element.getWidth() / 2.0;
            double centerY = element.getY() + element.getHeight() / 2.0;
            g.rotate(Math.toRadians(imageElement.getRotation()), centerX, centerY);
        }

        g.drawImage(
                imageElement.getImage(),
                element.getX(),
                element.getY(),
                element.getWidth(),
                element.getHeight(),
                null
        );

        g.setTransform(oldTransform);
    }
}

