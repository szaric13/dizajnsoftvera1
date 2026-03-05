package raf.graffito.dsw.core;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.composite.GraffNodeComposite;
import raf.graffito.dsw.graffRepository.implementation.Workspace;

public interface GraffRepository {
    Workspace getWorkspace();
    void addChild(GraffNodeComposite parent, GraffNode child);
}
