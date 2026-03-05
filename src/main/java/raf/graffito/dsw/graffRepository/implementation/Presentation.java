package raf.graffito.dsw.graffRepository.implementation;

import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.composite.GraffNodeComposite;
import raf.graffito.dsw.observer.Subscriber;

import java.io.IOException;

@Getter
@Setter
public class Presentation extends GraffNodeComposite {
    private static int counter = 1;
    private String author;


    public Presentation(String name, GraffNode parent) throws IOException {
        super(name, parent);
        setName(name + counter);
        counter++;
        if(parent instanceof Project){
            this.author = ((Project) parent).getAuthor();
        } else if (parent instanceof  Presentation) {
            this.author = ((Presentation) parent).getAuthor();
        }
    }
    public Presentation() {
        super();
    }

    public void setAuthor(String name) throws IOException {
        author = name;
        notifySubscribers("ime");
    }

    @Override
    public void addChild(GraffNode child) {
        if (child != null && child instanceof Slide) {
            Slide project = (Slide) child;
            if (!this.getChildren().contains(project)) {
                this.getChildren().add(project);
                try {
                    System.out.println(project);
                    notifySubscribers("child");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void removeChild(GraffNode child) {
        Slide diagram = (Slide) child;
        if (this.getChildren().contains(diagram)) {
            this.getChildren().remove(diagram);
            try {
                notifySubscribers("child");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (child.getParent() instanceof Presentation) {
            try {
                child.notifySubscribers("clear");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @Override
    public void setName(String name) throws IOException {
        super.setName(name);
        notifySubscribers("ime");
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        if (subscriber == null || subs.contains(subscriber)) return;
        subs.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        if (subscriber == null || !(subs.contains((subscriber)))) return;
        subs.remove(subscriber);
    }

    @Override
    public void notifySubscribers(Object notification) throws IOException {
        if (notification == null || subs.isEmpty()) return;
        for (Subscriber s : subs) {
            s.update(this);

        }
    }
}
