package raf.graffito.dsw.factory;

import raf.graffito.dsw.graffRepository.composite.GraffNode;

import java.io.IOException;

public abstract class GraffNodeFactory extends GraffNode {
    public GraffNodeFactory(String name, GraffNode parent) {
        super(name, parent);
    }
    public GraffNode getClassyNode(GraffNode parent) throws IOException {
        GraffNode n = createNode(parent);
        n.setParent(parent);
        return n;
    }
    public abstract GraffNode createNode(GraffNode node) throws IOException;
}
