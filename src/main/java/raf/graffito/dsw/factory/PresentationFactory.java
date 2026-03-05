package raf.graffito.dsw.factory;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.observer.Subscriber;

import java.io.IOException;

public class PresentationFactory extends GraffNodeFactory  {
    public PresentationFactory(String name, GraffNode parent) {
        super(name, parent);
    }

    @Override
    public GraffNode createNode(GraffNode node) throws IOException {
        Presentation p = new Presentation("Presentation", node);
        return p;
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {

    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {

    }

    @Override
    public void notifySubscribers(Object notification) throws IOException {

    }
}
