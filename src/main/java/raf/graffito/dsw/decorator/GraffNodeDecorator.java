package raf.graffito.dsw.decorator;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.observer.Subscriber;
import java.io.IOException;
import java.util.List;

public abstract class GraffNodeDecorator extends GraffNode {

    protected GraffNode node;

    public GraffNodeDecorator(GraffNode node) {
        super(node.getName(), node.getParent());
        this.node = node;
    }
    @Override
    public String getName() {
        return node.getName();
    }
    @Override
    public void setName(String name) throws IOException {
        node.setName(name);
    }
    @Override
    public GraffNode getParent() {
        return node.getParent();
    }
    @Override
    public void setParent(GraffNode parent) {
        node.setParent(parent);
    }
    @Override
    public List<Subscriber> getSubs() {
        return node.getSubs();
    }
    @Override
    public List<GraffNode> getChildren() {
        return node.getChildren();
    }
    @Override
    public void addChild(GraffNode child) {
        node.addChild(child);
    }
    @Override
    public void removeChild(GraffNode child) {
        node.removeChild(child);
    }
    @Override
    public void addSubscriber(Subscriber s) {
        node.addSubscriber(s);
    }
    @Override
    public void removeSubscriber(Subscriber s) {
        node.removeSubscriber(s);
    }
    @Override
    public void notifySubscribers(Object event) throws  IOException {
        node.notifySubscribers(event);
    }
    @Override
    public boolean isDecorator() {
        return true;
    }
}
