package raf.graffito.dsw.state;

import raf.graffito.dsw.command.AddElementCommand;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.graffRepository.implementation.slide.ImageElement;
import raf.graffito.dsw.graffRepository.implementation.slide.LogoElement;
import raf.graffito.dsw.graffRepository.implementation.slide.TextElement;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.SlideView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class AddState implements State {
    private ElementType elementType;
    private BufferedImage selectedImage;

    public enum ElementType {
        IMAGE, TEXT, LOGO
    }

    public AddState() {
        this.elementType = ElementType.TEXT;
    }

    public void setElementType(ElementType type) {
        this.elementType = type;
    }

    public void setSelectedImage(BufferedImage image) {
        this.selectedImage = image;
    }

    @Override
    public void mousePressed(MouseEvent e, SlideView slideView) {
        Point adjustedPoint = slideView.adjustPoint(e.getPoint());

        if (!isWithinSlideBounds(adjustedPoint, slideView)) {
            return;
        }

        switch (elementType) {
            case IMAGE:
                if (selectedImage != null) {
                    int width = Math.min(selectedImage.getWidth(), 200);
                    int height = Math.min(selectedImage.getHeight(), 200);
                    int x = adjustedPoint.x - width / 2;
                    int y = adjustedPoint.y - height / 2;

                    x = Math.max(0, Math.min(x, Slide.SLIDE_WIDTH - width));
                    y = Math.max(0, Math.min(y, Slide.SLIDE_HEIGHT - height));

                    ImageElement imageElement = new ImageElement(
                            x, y,
                            width, height,
                            selectedImage,
                            null
                    );
                    AddElementCommand command =
                            new AddElementCommand(slideView, imageElement);
                    slideView.getCommandManager().addCommand(command);
                    markProjectChanged(slideView);
                }
                break;
            case TEXT:
                String text = JOptionPane.showInputDialog("Enter text:");
                if (text != null && !text.isEmpty()) {
                    int width = 100;
                    int height = 30;
                    int x = adjustedPoint.x;
                    int y = adjustedPoint.y;

                    x = Math.max(0, Math.min(x, Slide.SLIDE_WIDTH - width));
                    y = Math.max(0, Math.min(y, Slide.SLIDE_HEIGHT - height));

                    TextElement textElement = new TextElement(
                            x, y,
                            width, height,
                            text
                    );
                    AddElementCommand command =
                            new AddElementCommand(slideView, textElement);
                    slideView.getCommandManager().addCommand(command);
                    markProjectChanged(slideView);
                }
                break;
            case LOGO:
                int logoSize = 100;
                int x = adjustedPoint.x - logoSize / 2;
                int y = adjustedPoint.y - logoSize / 2;

                x = Math.max(0, Math.min(x, Slide.SLIDE_WIDTH - logoSize));
                y = Math.max(0, Math.min(y, Slide.SLIDE_HEIGHT - logoSize));

                LogoElement logoElement = new LogoElement(
                        x, y,
                        logoSize, logoSize
                );
                AddElementCommand command =
                        new AddElementCommand(slideView, logoElement);
                slideView.getCommandManager().addCommand(command);
                markProjectChanged(slideView);
                break;
        }
        slideView.repaint();
    }

    private boolean isWithinSlideBounds(Point point, SlideView slideView) {
        return point.x >= 0 && point.x <= Slide.SLIDE_WIDTH &&
                point.y >= 0 && point.y <= Slide.SLIDE_HEIGHT;
    }

    @Override
    public void mouseReleased(MouseEvent e, SlideView slideView) {

    }

    @Override
    public void mouseDragged(MouseEvent e, SlideView slideView) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e, SlideView slideView) {
        slideView.zoom(e);
    }

    private void markProjectChanged(SlideView slideView) {
        if (slideView.getPresentationView() != null) {
            raf.graffito.dsw.graffRepository.implementation.Presentation presentation =
                    slideView.getPresentationView().getPresentation();
            if (presentation != null && presentation.getParent() != null) {
                raf.graffito.dsw.graffRepository.composite.GraffNode parent = presentation.getParent();
                while (parent != null && !(parent instanceof raf.graffito.dsw.graffRepository.implementation.Project)) {
                    parent = parent.getParent();
                }
                if (parent instanceof raf.graffito.dsw.graffRepository.implementation.Project) {
                    ((raf.graffito.dsw.graffRepository.implementation.Project) parent).setChanged(true);

                    if (MainFrame.getInstance() != null) {
                        MainFrame.getInstance().getActionManager().getSaveAction().enable();
                    }
                }
            }
        }
    }
}

