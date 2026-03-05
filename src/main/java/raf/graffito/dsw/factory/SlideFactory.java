package raf.graffito.dsw.factory;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.observer.Subscriber;

import java.io.IOException;

public class SlideFactory extends GraffNodeFactory {
    public SlideFactory(String name, GraffNode parent) {
        super(name, parent);
    }

    @Override
    public GraffNode createNode(GraffNode node) throws IOException {
        return new Slide("Slide" , node);
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
