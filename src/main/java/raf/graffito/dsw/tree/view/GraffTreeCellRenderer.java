package raf.graffito.dsw.tree.view;

import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.graffRepository.implementation.Workspace;
import raf.graffito.dsw.tree.model.GraffTreeItem;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class GraffTreeCellRenderer extends DefaultTreeCellRenderer {
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row, hasFocus);
        URL imageURL = null;

        if (((GraffTreeItem)value).getGraffNode() instanceof Workspace) {
            imageURL = getClass().getResource("/images/workspace.png");
        }
        else if (((GraffTreeItem)value).getGraffNode() instanceof Project) {
            imageURL = getClass().getResource("/images/projectimg.png");
        }
        else if (((GraffTreeItem)value).getGraffNode() instanceof Presentation) {
            imageURL = getClass().getResource("/images/presentationimg.png");
        } else if (((GraffTreeItem)value).getGraffNode() instanceof Slide) {
            imageURL = getClass().getResource("/images/slide.png");
        }

        ImageIcon icon = null;
        if (imageURL != null)
            icon = new ImageIcon(imageURL);
        Image image = icon.getImage();
        Image modImage = image.getScaledInstance(16,16,Image.SCALE_SMOOTH);
        icon = new ImageIcon(modImage);
        setIcon(icon);
        return this;
    }
}
