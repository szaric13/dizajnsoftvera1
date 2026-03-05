package raf.graffito.dsw.graffRepository;

import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.core.GraffRepository;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.composite.GraffNodeComposite;
import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.graffRepository.implementation.Workspace;

@Getter
@Setter
public class GraffRepositoryImplementation implements GraffRepository {
    private Workspace workspace;

    public GraffRepositoryImplementation() {
        workspace = new Workspace("Workspace");
    }

    public Workspace getProjectExplorer() {
        return workspace;
    }

    @Override
    public void addChild(GraffNodeComposite parent, GraffNode child) {
        if (parent == null || child == null) return;

        if (parent instanceof Workspace && child instanceof Project) {
            parent.addChild(child);
        } else if (parent instanceof Project &&
                (child instanceof Presentation || child instanceof Slide)) {
            parent.addChild(child);
        } else if (parent instanceof Presentation && child instanceof Slide) {
            parent.addChild(child);
        } else {
            System.out.println("Neispravna hijerarhija");
        }
    }
}
