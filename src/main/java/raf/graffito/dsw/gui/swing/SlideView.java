package raf.graffito.dsw.gui.swing;

import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.command.CommandManager;
import raf.graffito.dsw.command.RotateElementCommand;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.graffRepository.implementation.slide.ImageElement;
import raf.graffito.dsw.graffRepository.implementation.slide.LogoElement;
import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.graffRepository.implementation.slide.TextElement;
import raf.graffito.dsw.gui.swing.painter.ElementPainter;
import raf.graffito.dsw.gui.swing.painter.ImagePainter;
import raf.graffito.dsw.gui.swing.painter.LogoPainter;
import raf.graffito.dsw.gui.swing.painter.TextPainter;
import raf.graffito.dsw.observer.Subscriber;
import raf.graffito.dsw.state.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SlideView extends JPanel implements Subscriber {
    private Slide slide;
    private PresentationView presentationView;
    private StateManager stateManager;

    private List<ElementPainter> elementPainters;
    private List<SlideElement> selectedElements;

    @Getter
    private CommandManager commandManager;

    private AffineTransform transform;
    private double zoomFactor;
    private static final double MIN_ZOOM = 0.5;
    private static final double MAX_ZOOM = 3.0;
    private static final double ZOOM_STEP = 0.1;

    private Point selectionStart;
    private Point selectionEnd;

    @Getter
    private List<BufferedImage> loadedImages;

    private SlideMouseAdapter mouseAdapter;

    public SlideView(Slide slide) {
        this.slide = slide;
        this.elementPainters = new ArrayList<>();
        this.selectedElements = new ArrayList<>();
        this.stateManager = new StateManager();
        this.commandManager = new CommandManager();
        this.zoomFactor = 1.0;
        this.transform = new AffineTransform();
        this.loadedImages = new ArrayList<>();

        initializeComponent();
        loadSlideElements();
        loadTestImage();
    }

    private void initializeComponent() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        JPanel slidePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.transform(transform);

                g2d.setColor(slide.getBackgroundColor());
                g2d.fillRect(0, 0, Slide.SLIDE_WIDTH, Slide.SLIDE_HEIGHT);

                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(0, 0, Slide.SLIDE_WIDTH, Slide.SLIDE_HEIGHT);

                for (ElementPainter painter : elementPainters) {
                    painter.draw(g2d);
                }

                for (SlideElement element : selectedElements) {
                    Rectangle bounds = element.getBounds();
                    g2d.setColor(Color.BLUE);
                    g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                            10.0f, new float[]{5.0f}, 0.0f));
                    g2d.drawRect(bounds.x - 2, bounds.y - 2, bounds.width + 4, bounds.height + 4);

                    int cornerSize = 8;
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect(bounds.x - 2 - cornerSize/2, bounds.y - 2 - cornerSize/2, cornerSize, cornerSize);
                    g2d.fillRect(bounds.x + bounds.width + 2 - cornerSize/2, bounds.y - 2 - cornerSize/2, cornerSize, cornerSize);
                    g2d.fillRect(bounds.x + bounds.width + 2 - cornerSize/2, bounds.y + bounds.height + 2 - cornerSize/2, cornerSize, cornerSize);
                    g2d.fillRect(bounds.x - 2 - cornerSize/2, bounds.y + bounds.height + 2 - cornerSize/2, cornerSize, cornerSize);
                }

                if (selectionStart != null && selectionEnd != null) {
                    Rectangle selectionRect = getSelectionRectangle();
                    if (selectionRect != null && !selectionRect.isEmpty()) {
                        g2d.setColor(Color.RED);
                        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                10.0f, new float[]{5.0f}, 0.0f));
                        g2d.draw(selectionRect);
                    }
                }
            }
        };

        slidePanel.setPreferredSize(new Dimension(Slide.SLIDE_WIDTH, Slide.SLIDE_HEIGHT));
        slidePanel.setBackground(Color.WHITE);

        mouseAdapter = new SlideMouseAdapter(this);
        slidePanel.addMouseListener(mouseAdapter);
        slidePanel.addMouseMotionListener(mouseAdapter);
        slidePanel.addMouseWheelListener(mouseAdapter);

        JScrollPane scrollPane = new JScrollPane(slidePanel);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadTestImage() {

        String[] imagePaths = {
                "/images/dr.jpg",
                "/images/mj.png",
                "/images/slide.png",
                "/images/workspace.png"
        };

        for (String imagePath : imagePaths) {
            try {
                java.net.URL imageURL = getClass().getResource(imagePath);
                if (imageURL != null) {
                    ImageIcon icon = new ImageIcon(imageURL);
                    BufferedImage img = new BufferedImage(
                            icon.getIconWidth(),
                            icon.getIconHeight(),
                            BufferedImage.TYPE_INT_ARGB
                    );
                    Graphics2D g = img.createGraphics();
                    icon.paintIcon(null, g, 0, 0);
                    g.dispose();
                    loadedImages.add(img);
                }
            } catch (Exception e) {

            }
        }

        if (loadedImages.isEmpty()) {
            BufferedImage testImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = testImage.createGraphics();
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, 200, 200);
            g.setColor(Color.WHITE);
            g.drawString("Test Image", 50, 100);
            g.dispose();
            loadedImages.add(testImage);
        }

        updateImagePanel();
    }

    private void updateImagePanel() {

        if (MainFrame.getInstance().getSideBar() != null) {
            MainFrame.getInstance().getSideBar().updateImagesPanel();
        }
    }

    private void loadSlideElements() {
        elementPainters.clear();
        if (slide != null && slide.getElements() != null) {
            for (SlideElement element : slide.getElements()) {
                ElementPainter painter = createPainter(element);
                if (painter != null) {
                    elementPainters.add(painter);
                }
            }
        }
    }

    private ElementPainter createPainter(SlideElement element) {
        if (element instanceof ImageElement) {
            return new ImagePainter((ImageElement) element);
        } else if (element instanceof TextElement) {
            return new TextPainter((TextElement) element);
        } else if (element instanceof LogoElement) {
            return new LogoPainter((LogoElement) element);
        }
        return null;
    }

    public void setSlide(Slide slide) {
        if (this.slide != null) {
            this.slide.removeSubscriber(this);
        }
        this.slide = slide;
        if (slide != null) {
            slide.addSubscriber(this);
            loadSlideElements();
            repaint();
        }
    }

    public void setPresentationView(PresentationView presentationView) {
        this.presentationView = presentationView;
    }

    public void handleMousePressed(MouseEvent e) {
        stateManager.getCurrentState().mousePressed(e, this);
    }

    public void handleMouseReleased(MouseEvent e) {
        stateManager.getCurrentState().mouseReleased(e, this);
    }

    public void handleMouseDragged(MouseEvent e) {
        stateManager.getCurrentState().mouseDragged(e, this);
    }

    public void handleMouseWheelMoved(MouseWheelEvent e) {
        stateManager.getCurrentState().mouseWheelMoved(e, this);
    }

    public void zoom(MouseWheelEvent e) {
        double oldZoom = zoomFactor;

        if (e.getWheelRotation() > 0) {
            zoomFactor = Math.max(MIN_ZOOM, zoomFactor - ZOOM_STEP);
        } else {
            zoomFactor = Math.min(MAX_ZOOM, zoomFactor + ZOOM_STEP);
        }

        if (oldZoom != zoomFactor) {
            updateTransform();
            repaint();
        }
    }

    private void updateTransform() {
        transform.setToIdentity();
        transform.scale(zoomFactor, zoomFactor);
    }

    public Point adjustPoint(Point point) {
        try {
            Point2D.Double srcPoint = new Point2D.Double(point.x, point.y);
            Point2D.Double dstPoint = new Point2D.Double();
            transform.inverseTransform(srcPoint, dstPoint);
            return new Point((int) dstPoint.x, (int) dstPoint.y);
        } catch (NoninvertibleTransformException e) {
            return point;
        }
    }

    public void addElement(SlideElement element) {
        if (element != null && slide != null) {
            slide.addElement(element);
            ElementPainter painter = createPainter(element);
            if (painter != null) {
                elementPainters.add(painter);
            }
            notifySlideChanged();
            repaint();
        }
    }

    public void removeElement(SlideElement element) {
        if (element != null && slide != null) {
            slide.removeElement(element);
            elementPainters.removeIf(p -> p.getElement() == element);
            selectedElements.remove(element);
            notifySlideChanged();
            repaint();
        }
    }

    public void removeElements(List<SlideElement> elements) {
        for (SlideElement element : elements) {
            removeElement(element);
        }
    }

    public void notifySlideChanged() {
        if (slide != null) {
            try {
                slide.notifySubscribers(slide);
            } catch (Exception e) {
            }
        }
    }

    public SlideElement getElementAt(Point point) {
        for (int i = elementPainters.size() - 1; i >= 0; i--) {
            ElementPainter painter = elementPainters.get(i);
            if (painter.contains(point)) {
                return painter.getElement();
            }
        }
        return null;
    }

    public void addToSelection(SlideElement element) {
        if (element != null && !selectedElements.contains(element)) {
            selectedElements.add(element);
            repaint();
        }
    }

    public void clearSelection() {
        selectedElements.clear();
        repaint();
    }

    public boolean isElementSelected(SlideElement element) {
        return selectedElements.contains(element);
    }

    public List<SlideElement> getSelectedElements() {
        return new ArrayList<>(selectedElements);
    }

    public void rotateSelectedElements(double angle) {
        if (selectedElements == null || selectedElements.isEmpty()) {
            return;
        }

        RotateElementCommand command =
                new RotateElementCommand(this, new java.util.ArrayList<>(selectedElements), angle);
        commandManager.addCommand(command);

        if (presentationView != null) {
            Presentation presentation = presentationView.getPresentation();
            if (presentation != null && presentation.getParent() != null) {
                GraffNode parent = presentation.getParent();
                while (parent != null && !(parent instanceof Project)) {
                    parent = parent.getParent();
                }
                if (parent instanceof Project) {
                    ((Project) parent).setChanged(true);
                    if (MainFrame.getInstance() != null) {
                        MainFrame.getInstance().getActionManager().getSaveAction().enable();
                    }
                }
            }
        }
    }

    public List<SlideElement> getElementsInRectangle(Rectangle rect) {
        return elementPainters.stream()
                .map(ElementPainter::getElement)
                .filter(element -> rect.intersects(element.getBounds()))
                .collect(Collectors.toList());
    }

    public Rectangle getSelectionRectangle() {
        if (selectionStart == null || selectionEnd == null) {
            return null;
        }

        int x = Math.min(selectionStart.x, selectionEnd.x);
        int y = Math.min(selectionStart.y, selectionEnd.y);
        int width = Math.abs(selectionEnd.x - selectionStart.x);
        int height = Math.abs(selectionEnd.y - selectionStart.y);

        return new Rectangle(x, y, width, height);
    }

    @Override
    public void update(Object notification) {
        if (notification instanceof Slide) {
            loadSlideElements();
            repaint();
        } else if (notification instanceof SlideElement) {
            loadSlideElements();
            repaint();
        }
    }
}
