package raf.graffito.dsw.gui.swing.painter;

import raf.graffito.dsw.graffRepository.implementation.slide.LogoElement;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class LogoPainter extends ElementPainter {

    public LogoPainter(LogoElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        LogoElement logoElement = (LogoElement) element;

        AffineTransform oldTransform = g.getTransform();

        if (logoElement.getRotation() != 0) {
            double centerX = element.getX() + element.getWidth() / 2.0;
            double centerY = element.getY() + element.getHeight() / 2.0;
            g.rotate(Math.toRadians(logoElement.getRotation()), centerX, centerY);
        }

        g.setColor(Color.YELLOW);
        g.fillOval(element.getX(), element.getY(), element.getWidth(), element.getHeight());

        g.setStroke(new BasicStroke(logoElement.getStrokeWidth()));
        g.setColor(Color.BLACK);
        g.drawOval(element.getX(), element.getY(), element.getWidth(), element.getHeight());

        int centerX = element.getX() + element.getWidth() / 2;
        int centerY = element.getY() + element.getHeight() / 2;
        int eyeSize = Math.max(3, element.getWidth() / 15);
        int eyeOffsetX = element.getWidth() / 4;
        int eyeOffsetY = element.getHeight() / 4;
        int mouthWidth = element.getWidth() / 3;
        int mouthHeight = element.getHeight() / 6;
        int mouthY = centerY + element.getHeight() / 6;

        g.setColor(Color.BLACK);
        g.fillOval(centerX - eyeOffsetX - eyeSize/2, centerY - eyeOffsetY - eyeSize/2, eyeSize, eyeSize);
        g.fillOval(centerX + eyeOffsetX - eyeSize/2, centerY - eyeOffsetY - eyeSize/2, eyeSize, eyeSize);

        g.setStroke(new BasicStroke(Math.max(2, element.getWidth() / 30), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawArc(centerX - mouthWidth/2, mouthY - mouthHeight/2, mouthWidth, mouthHeight, 0, -180);

        g.setTransform(oldTransform);
    }
}

