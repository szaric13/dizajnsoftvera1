package raf.graffito.dsw.factory;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.observer.Subscriber;

import javax.swing.*;
import java.io.IOException;

public class ProjectFactory extends GraffNodeFactory{
    public ProjectFactory(String name, GraffNode parent) {
        super(name, parent);
    }

    @Override
    public GraffNode createNode(GraffNode node) throws IOException {
        Project p = new Project("Projekat" , node);
        String s = JOptionPane.showInputDialog("Enter authors name");
        p.setAuthor(s);

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
