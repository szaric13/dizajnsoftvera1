package raf.graffito.dsw.tree;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Workspace;
import raf.graffito.dsw.tree.model.GraffTreeItem;
import raf.graffito.dsw.tree.view.GraffTreeView;

import java.io.IOException;

public interface GraffTree {
    GraffTreeView generateTree(Workspace projectExplorer);
    void addChild(GraffTreeItem parent) throws IOException;
    void addProjectToTree(GraffNode project);
    GraffTreeItem getSelectedNode();
    GraffTreeItem getRoot();
    GraffTreeItem getNode(GraffNode node);

    void deleteNode(GraffTreeItem selectedNode);
    void editNode (GraffTreeItem classyTreeItem);
}
