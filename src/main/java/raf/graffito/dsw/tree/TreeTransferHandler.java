package raf.graffito.dsw.tree;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.graffRepository.composite.GraffNodeComposite;
import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.message.EventType;
import raf.graffito.dsw.tree.model.GraffTreeItem;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.List;

public class TreeTransferHandler extends TransferHandler {
    private DefaultTreeModel treeModel;
    private DataFlavor nodesFlavor;
    private DataFlavor[] flavors = new DataFlavor[1];

    public TreeTransferHandler(DefaultTreeModel model) {
        this.treeModel = model;
        try {
            String mimeType = DataFlavor.javaJVMLocalObjectMimeType +
                    ";class=\"" + GraffTreeItem[].class.getName() + "\"";
            nodesFlavor = new DataFlavor(mimeType);
            flavors[0] = nodesFlavor;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }

        support.setShowDropLocation(true);

        if (!support.isDataFlavorSupported(nodesFlavor)) {
            return false;
        }

        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        TreePath dest = dl.getPath();

        if (dest == null) {
            return false;
        }

        GraffTreeItem target = (GraffTreeItem) dest.getLastPathComponent();

        if (target.getGraffNode() instanceof Presentation) {
            return true;
        }

        if (target.getGraffNode() instanceof Slide && dl.getChildIndex() != -1) {
            return true;
        }

        return false;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree) c;
        TreePath[] paths = tree.getSelectionPaths();

        if (paths != null) {
            List<GraffTreeItem> nodes = new ArrayList<>();

            for (TreePath path : paths) {
                GraffTreeItem node = (GraffTreeItem) path.getLastPathComponent();
                if (node.getGraffNode() instanceof Slide) {
                    nodes.add(node);
                }
            }

            if (!nodes.isEmpty()) {
                return new NodesTransferable(nodes.toArray(new GraffTreeItem[0]));
            }
        }

        return null;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        GraffTreeItem[] nodes;
        try {
            Transferable t = support.getTransferable();
            nodes = (GraffTreeItem[]) t.getTransferData(nodesFlavor);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        int childIndex = dl.getChildIndex();
        TreePath dest = dl.getPath();
        GraffTreeItem parent = (GraffTreeItem) dest.getLastPathComponent();
        JTree tree = (JTree) support.getComponent();

        GraffTreeItem actualParent;
        int insertIndex;

        if (childIndex == -1) {
            if (!(parent.getGraffNode() instanceof Presentation)) {
                return false;
            }
            actualParent = parent;
            insertIndex = parent.getChildCount();
        } else {
            if (parent.getGraffNode() instanceof Presentation) {
                actualParent = parent;
                insertIndex = childIndex;
            } else if (parent.getGraffNode() instanceof Slide) {
                actualParent = (GraffTreeItem) parent.getParent();
                insertIndex = childIndex;
            } else {
                return false;
            }
        }

        for (GraffTreeItem node : nodes) {
            if (!(node.getGraffNode() instanceof Slide)) {
                continue;
            }

            GraffTreeItem oldParent = (GraffTreeItem) node.getParent();

            if (oldParent != actualParent) {
                ApplicationFramework.getInstance().getMessageGenerator()
                        .generateMessage(EventType.CANNOT_MOVE_BETWEEN_PRESENTATIONS);
                return false;
            }

            int oldIndex = oldParent.getIndex(node);

            GraffNodeComposite graffParent = (GraffNodeComposite) oldParent.getGraffNode();
            graffParent.removeChild(node.getGraffNode());
            treeModel.removeNodeFromParent(node);

            if (oldIndex < insertIndex && oldParent == actualParent) {
                insertIndex--;
            }

            graffParent.addChild(node.getGraffNode(), insertIndex);
            treeModel.insertNodeInto(node, actualParent, insertIndex);

            insertIndex++;
        }

        TreePath newPath = new TreePath(treeModel.getPathToRoot(nodes[0]));
        tree.setSelectionPath(newPath);
        tree.expandPath(newPath);

        return true;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
    }

    public class NodesTransferable implements Transferable {
        private GraffTreeItem[] nodes;

        public NodesTransferable(GraffTreeItem[] nodes) {
            this.nodes = nodes;
        }

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return nodes;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return nodesFlavor.equals(flavor);
        }
    }
}
