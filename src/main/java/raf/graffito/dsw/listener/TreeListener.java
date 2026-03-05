package raf.graffito.dsw.listener;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.tree.model.GraffTreeItem;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.gui.swing.PresentationView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TreeListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        try {
            GraffTreeItem selectedItem = MainFrame.getInstance().getGraffTree().getSelectedNode();
            if (selectedItem == null) return;
            GraffNode node = selectedItem.getGraffNode();
            if (node instanceof Project project && e.getClickCount() == 2) {
                Color chosen = JColorChooser.showDialog(null, "Choose project color", Color.WHITE);
                if (chosen == null) return;
                if (MainFrame.getInstance().isColorUsed(chosen)) {
                    JOptionPane.showMessageDialog(null, "This color is already used!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                MainFrame.getInstance().registerColor(chosen);
                MainFrame.getInstance().setProjectColor(project, chosen);
                for (GraffNode child : project.getChildren()) {
                    if (child instanceof Presentation p) {
                        PresentationView pv = new PresentationView(p);
                        p.addSubscriber(pv);
                        MainFrame.getInstance().reload(pv);
                    }
                }
                MainFrame.getInstance().colorTabsForProject(project);
                return;
            }
            if (node instanceof Presentation p && e.getClickCount() == 2) {
                PresentationView pv = new PresentationView(p);
                p.addSubscriber(pv);
                MainFrame.getInstance().reload(pv);
                if (p.getParent() instanceof Project pr) {
                    Color c = MainFrame.getInstance().getProjectColor(pr);
                    if (c != null) {
                        int index = MainFrame.getInstance().getTabIndex(pv);
                        if (index >= 0) {
                            MainFrame.getInstance().getTabbedPane().setBackgroundAt(index, c);
                        }
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
    }
}
