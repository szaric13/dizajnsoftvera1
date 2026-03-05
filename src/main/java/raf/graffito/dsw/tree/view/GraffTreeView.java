package raf.graffito.dsw.tree.view;

import raf.graffito.dsw.graffRepository.implementation.Workspace;
import raf.graffito.dsw.listener.TreeListener;
import raf.graffito.dsw.tree.TreeTransferHandler;
import raf.graffito.dsw.tree.controller.GraffTreeCellEditor;
import raf.graffito.dsw.tree.controller.GraffTreeSelectionListener;
import raf.graffito.dsw.tree.model.GraffTreeItem;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;

public class GraffTreeView extends JTree {
    public GraffTreeView(DefaultTreeModel defaultTreeModel) {
        setModel(defaultTreeModel);
        GraffTreeCellRenderer ruTreeCellRenderer = new GraffTreeCellRenderer();
        addTreeSelectionListener(new GraffTreeSelectionListener());
        setCellEditor(new GraffTreeCellEditor(this, ruTreeCellRenderer));
        setCellRenderer(ruTreeCellRenderer);
        setEditable(true);
        addMouseListener(new TreeListener());
        setDragEnabled(true);
        setDropMode(DropMode.ON_OR_INSERT);
        setTransferHandler(new TreeTransferHandler(defaultTreeModel));
    }
}
