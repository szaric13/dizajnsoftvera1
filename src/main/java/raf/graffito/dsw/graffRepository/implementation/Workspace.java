package raf.graffito.dsw.graffRepository.implementation;

import lombok.Getter;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.composite.GraffNodeComposite;
import raf.graffito.dsw.observer.Subscriber;
@Getter
public class Workspace extends GraffNodeComposite {
    public Workspace(String name) {
        super(name, null);
    }

    @Override
    public void addChild(GraffNode child) {
        if (child != null && child instanceof Project) {
            Project project = (Project) child;
            if (!this.getChildren().contains(project)) {
                this.getChildren().add(project);
            }
        }
    }

    @Override
    public void removeChild(GraffNode child) {
        Project project = (Project) child;
        if(this.getChildren().contains(project))
        {
            this.getChildren().remove(project);
        }

        if (child instanceof Project) {
            project.deleteAll(project);
        }
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        if (subscriber == null || subs.contains(subscriber))
            return;
        subs.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        if (subscriber == null || !subs.contains(subscriber))
            return;
        subs.remove(subscriber);
    }

    @Override
    public void notifySubscribers(Object notification) {
        if (notification == null || subs.isEmpty())
            return;
        for (Subscriber s : subs) {
            s.update(this);
        }
    }
}
