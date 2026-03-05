package raf.graffito.dsw.graffRepository.composite;

import java.io.IOException;

public abstract class GraffLeaf extends GraffNode {
    public GraffLeaf(String name, GraffNode parent) throws IOException {
        super(name, parent);
    }

    protected GraffLeaf() {
        super();
    }
}
