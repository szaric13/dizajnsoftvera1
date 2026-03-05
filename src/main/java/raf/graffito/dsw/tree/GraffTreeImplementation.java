package raf.graffito.dsw.tree;

import raf.graffito.dsw.factory.FactoryUtils;
import raf.graffito.dsw.factory.GraffNodeFactory;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.composite.GraffNodeComposite;
import raf.graffito.dsw.graffRepository.implementation.Workspace;
import raf.graffito.dsw.tree.model.GraffTreeItem;
import raf.graffito.dsw.tree.view.GraffTreeView;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;

public class GraffTreeImplementation implements GraffTree{
    private GraffTreeView treeView;
    private DefaultTreeModel treeModel;

    @Override
    public GraffTreeView generateTree(Workspace workspace) {
        GraffTreeItem root = new GraffTreeItem(workspace);
        treeModel = new DefaultTreeModel(root);
        treeView = new GraffTreeView(treeModel);
        return treeView;
    }

    @Override
    public void addChild(GraffTreeItem parent) throws IOException {

        if (!(parent.getGraffNode() instanceof GraffNodeComposite))
            return;

        GraffNode child = createChild(parent.getGraffNode());
        if(child == null) return;
        parent.add(new GraffTreeItem(child));
        ((GraffNodeComposite) parent.getGraffNode()).addChild(child);
        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public GraffTreeItem getSelectedNode() {
        return (GraffTreeItem) treeView.getLastSelectedPathComponent();
    }

    @Override
    public GraffTreeItem getRoot() {
        return (GraffTreeItem) treeModel.getRoot();
    }

    @Override
    public GraffTreeItem getNode(GraffNode node) {
        return findNode((GraffTreeItem) treeModel.getRoot(), node);
    }

    private GraffTreeItem findNode(GraffTreeItem item, GraffNode node) {
        if (item.getGraffNode() == node) {
            return item;
        }
        for (int i = 0; i < item.getChildCount(); i++) {
            GraffTreeItem child = (GraffTreeItem) item.getChildAt(i);
            GraffTreeItem found = findNode(child, node);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    @Override
    public void addProjectToTree(GraffNode project) {
        if (treeModel == null || treeView == null) {
            return;
        }

        GraffTreeItem root = (GraffTreeItem) treeModel.getRoot();
        if (root == null) {
            return;
        }

        GraffTreeItem existing = findNode(root, project);
        if (existing != null) {
            return;
        }

        GraffTreeItem projectItem = new GraffTreeItem(project);

        treeModel.insertNodeInto(projectItem, root, root.getChildCount());

        if (project instanceof GraffNodeComposite) {
            addChildrenToTree(projectItem, (GraffNodeComposite) project);
        }

        SwingUtilities.updateComponentTreeUI(treeView);
    }

    private void addChildrenToTree(GraffTreeItem parentItem, GraffNodeComposite parentNode) {
        if (parentNode.getChildren() != null && treeModel != null) {
            for (GraffNode child : parentNode.getChildren()) {
                GraffTreeItem childItem = new GraffTreeItem(child);
                treeModel.insertNodeInto(childItem, parentItem, parentItem.getChildCount());

                if (child instanceof GraffNodeComposite) {
                    addChildrenToTree(childItem, (GraffNodeComposite) child);
                }
            }
        }
    }

    @Override
    public void deleteNode(GraffTreeItem selectedNode) {
        GraffNodeComposite p = (GraffNodeComposite) selectedNode.getGraffNode().getParent();
        p.removeChild(selectedNode.getGraffNode());
        selectedNode.removeAllChildren();
        selectedNode.removeFromParent();
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void editNode(GraffTreeItem classyTreeItem) {
        SwingUtilities.updateComponentTreeUI(treeView);
    }


    private GraffNode createChild(GraffNode parent) throws IOException {

        GraffNodeFactory nodeFactory = FactoryUtils.getFactory(parent);
        if(nodeFactory == null)return null;
        return nodeFactory.getClassyNode(parent);
    }
}
