package raf.graffito.dsw.gui.swing;

import javax.swing.*;
import java.awt.*;

public class MySideBar extends JPanel {
    private JPanel toolsPanel;
    private JPanel imagesPanel;
    private JScrollPane imagesScrollPane;

    public MySideBar() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Tools"));
        setPreferredSize(new Dimension(200, 0));
        setMinimumSize(new Dimension(180, 0));

        toolsPanel = new JPanel();
        toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.Y_AXIS));
        toolsPanel.setBorder(BorderFactory.createTitledBorder("Slide Tools"));
        toolsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton selectBtn = new JButton(MainFrame.getInstance().getActionManager().getSelectStateAction());
        selectBtn.setText(null);
        selectBtn.setToolTipText("Select elements");
        selectBtn.setPreferredSize(new Dimension(50, 50));
        selectBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        selectBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolsPanel.add(selectBtn);

        JButton moveBtn = new JButton(MainFrame.getInstance().getActionManager().getMoveStateAction());
        moveBtn.setText(null);
        moveBtn.setToolTipText("Move elements");
        moveBtn.setPreferredSize(new Dimension(50, 50));
        moveBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        moveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolsPanel.add(moveBtn);

        JButton deleteBtn = new JButton(MainFrame.getInstance().getActionManager().getDeleteStateAction());
        deleteBtn.setText(null);
        deleteBtn.setToolTipText("Delete elements");
        deleteBtn.setPreferredSize(new Dimension(50, 50));
        deleteBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        deleteBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolsPanel.add(deleteBtn);

        toolsPanel.add(Box.createVerticalStrut(5));

        JButton addImageBtn = new JButton(MainFrame.getInstance().getActionManager().getAddImageAction());
        addImageBtn.setText(null);
        addImageBtn.setToolTipText("Add image");
        addImageBtn.setPreferredSize(new Dimension(50, 50));
        addImageBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        addImageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolsPanel.add(addImageBtn);

        JButton addTextBtn = new JButton(MainFrame.getInstance().getActionManager().getAddTextAction());
        addTextBtn.setText(null);
        addTextBtn.setToolTipText("Add text");
        addTextBtn.setPreferredSize(new Dimension(50, 50));
        addTextBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        addTextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolsPanel.add(addTextBtn);

        JButton addLogoBtn = new JButton(MainFrame.getInstance().getActionManager().getAddLogoAction());
        addLogoBtn.setText(null);
        addLogoBtn.setToolTipText("Add logo");
        addLogoBtn.setPreferredSize(new Dimension(50, 50));
        addLogoBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        addLogoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolsPanel.add(addLogoBtn);

        toolsPanel.add(Box.createVerticalStrut(5));

        JButton rotateLeftBtn = new JButton(MainFrame.getInstance().getActionManager().getRotateLeftAction());
        rotateLeftBtn.setText(null);
        rotateLeftBtn.setToolTipText("Rotate left 90°");
        rotateLeftBtn.setPreferredSize(new Dimension(50, 50));
        rotateLeftBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        rotateLeftBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolsPanel.add(rotateLeftBtn);

        JButton rotateRightBtn = new JButton(MainFrame.getInstance().getActionManager().getRotateRightAction());
        rotateRightBtn.setText(null);
        rotateRightBtn.setToolTipText("Rotate right 90°");
        rotateRightBtn.setPreferredSize(new Dimension(50, 50));
        rotateRightBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        rotateRightBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolsPanel.add(rotateRightBtn);

        toolsPanel.add(Box.createVerticalStrut(5));

        JButton loadImageBtn = new JButton(MainFrame.getInstance().getActionManager().getLoadImageAction());
        loadImageBtn.setText(null);
        loadImageBtn.setToolTipText("Load image from disk");
        loadImageBtn.setPreferredSize(new Dimension(50, 30));
        loadImageBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        loadImageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolsPanel.add(loadImageBtn);

        toolsPanel.add(Box.createVerticalStrut(5));

        imagesPanel = new JPanel();
        imagesPanel.setLayout(new BoxLayout(imagesPanel, BoxLayout.Y_AXIS));
        imagesPanel.setBorder(BorderFactory.createTitledBorder("Loaded Images"));
        imagesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        imagesScrollPane = new JScrollPane(imagesPanel);
        imagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        imagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        imagesScrollPane.setPreferredSize(new Dimension(180, 200));
        imagesScrollPane.setMinimumSize(new Dimension(180, 100));
        imagesScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        toolsPanel.add(imagesScrollPane);

        JScrollPane toolsScrollPane = new JScrollPane(toolsPanel);
        toolsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        toolsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(toolsScrollPane, BorderLayout.CENTER);

        imagesScrollPane.setVisible(true);
    }

    public void updateImagesPanel() {
        imagesPanel.removeAll();

        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        boolean hasImages = false;

        if (pv != null) {
            SlideView sv = pv.getCurrentSlideView();
            if (sv != null && sv.getLoadedImages() != null && !sv.getLoadedImages().isEmpty()) {
                hasImages = true;
                for (java.awt.image.BufferedImage image : sv.getLoadedImages()) {
                    JLabel imageLabel = new JLabel(new ImageIcon(
                            image.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                    imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {

                            if (sv.getStateManager().getCurrentState() instanceof raf.graffito.dsw.state.AddState) {
                                ((raf.graffito.dsw.state.AddState) sv.getStateManager().getCurrentState())
                                        .setSelectedImage(image);
                            }
                        }
                    });
                    imagesPanel.add(imageLabel);
                    imagesPanel.add(Box.createVerticalStrut(3));
                }
            }
        }

        if (!hasImages) {
            JLabel noImagesLabel = new JLabel("No images");
            noImagesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noImagesLabel.setForeground(Color.GRAY);
            imagesPanel.add(noImagesLabel);
        }

        if (imagesScrollPane != null) {
            imagesScrollPane.setVisible(true);
        }

        imagesPanel.revalidate();
        imagesPanel.repaint();
    }
}

