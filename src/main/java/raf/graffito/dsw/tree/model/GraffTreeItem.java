package raf.graffito.dsw.tree.model;

import raf.graffito.dsw.graffRepository.composite.GraffNode;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;

public class GraffTreeItem extends DefaultMutableTreeNode {
    private GraffNode graffNode;

    public GraffTreeItem(GraffNode nodeModel) {
        this.graffNode = nodeModel;
    }

    @Override
    public String toString() {
        return graffNode.getName();
    }

    public void setName(String name) throws IOException {
        this.graffNode.setName(name);
    }

    public GraffNode getGraffNode() {
        return graffNode;
    }
}
