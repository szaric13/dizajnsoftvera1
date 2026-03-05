package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;
import raf.graffito.dsw.gui.swing.SlideView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoadImageAction extends AbstractGraffAction {
    public LoadImageAction() {
        putValue(SMALL_ICON, loadIcon("/images/loadimage.png"));
        putValue(NAME, "Load Image");
        putValue(SHORT_DESCRIPTION, "Load Image from Disk");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PresentationView pv = MainFrame.getInstance().getCurrentPresentationView();
        if (pv == null) {
            JOptionPane.showMessageDialog(null, "Please open a presentation first.",
                    "No Presentation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SlideView sv = pv.getCurrentSlideView();
        if (sv == null) {
            JOptionPane.showMessageDialog(null, "Please open a slide first.",
                    "No Slide", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Images");
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                        name.endsWith(".png") || name.endsWith(".gif") ||
                        name.endsWith(".bmp");
            }

            @Override
            public String getDescription() {
                return "Image Files (*.jpg, *.jpeg, *.png, *.gif, *.bmp)";
            }
        });

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            int loadedCount = 0;

            for (File file : selectedFiles) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    if (image != null) {
                        sv.getLoadedImages().add(image);
                        loadedCount++;
                    }
                } catch (IOException ex) {
                    System.err.println("Error loading image: " + file.getName() + " - " + ex.getMessage());
                }
            }

            if (loadedCount > 0) {
                if (MainFrame.getInstance().getSideBar() != null) {
                    MainFrame.getInstance().getSideBar().updateImagesPanel();
                }
                JOptionPane.showMessageDialog(null, "Loaded " + loadedCount + " image(s).",
                        "Images Loaded", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No images were loaded.",
                        "Load Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}

