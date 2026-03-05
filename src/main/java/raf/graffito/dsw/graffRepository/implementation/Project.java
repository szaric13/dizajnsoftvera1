package raf.graffito.dsw.graffRepository.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.composite.GraffNodeComposite;
import raf.graffito.dsw.observer.Subscriber;

import java.io.IOException;
@Getter
@Setter
public class Project extends GraffNodeComposite {
    @JsonIgnore
    private static int counter = 1;
    private String title;
    private String author;
    @JsonIgnore
    private int number;
    protected String filePath;
    @Getter
    @Setter
    private boolean changed;

    public Project(String name, GraffNode parent) throws IOException {
        super(name, parent);
        setName(name + counter);
        counter++;
        this.number = 0;
        this.changed = false;
    }

    public Project() {
        super();
        this.number = 0;
        this.changed = false;
    }
    @Override
    public void addChild(GraffNode child) {
        if (child != null && (child instanceof Presentation || child instanceof Slide)) {
            if (!this.getChildren().contains(child)) {
                this.getChildren().add(child);
                if (child instanceof Slide) {
                    this.number++;
                } else if (child instanceof Presentation) {
                    Presentation presentation = (Presentation) child;
                    this.number += presentation.getChildren().size();
                }
                notifySubscribers("presentation added:" + child.getName());
            }
        }
    }

    @Override
    @JsonIgnore
    public void addSubscriber(Subscriber subscriber) {
        if (subscriber == null || subs.contains(subscriber))
            return;
        subs.add(subscriber);
    }

    @Override
    @JsonIgnore
    public void removeSubscriber(Subscriber subscriber) {
        if (subscriber == null || !subs.contains(subscriber))
            return;
        subs.remove(subscriber);
    }

    @Override
    @JsonIgnore
    public void notifySubscribers(Object notification) {
        if (notification == null || subs.isEmpty())
            return;
        for (Subscriber s : subs) {
            s.update(this);
        }
    }

    @JsonIgnore
    public void deleteAll(GraffNodeComposite graffNodeComposite) {
        if (graffNodeComposite.getChildren() == null || graffNodeComposite.getChildren().isEmpty()) {
            try {
                graffNodeComposite.notifySubscribers("clear");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        for (GraffNode child : graffNodeComposite.getChildren()) {
            if (child instanceof Project) {
                deleteAll((Project) child);
                try {
                    child.notifySubscribers("clear");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    child.getParent().notifySubscribers("clear");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }
    }

    public void setTitle(String title) {
        this.title = title;
        notifySubscribers(this);
    }

    public void setName(String name) throws IOException {
        super.setName(name);
        for (GraffNode child : this.getChildren()) {
            if (child instanceof Presentation) ((Presentation) child).notifySubscribers("ime");
            notifySubscribers("project renamed");
        }
    }

    @Override
    public void removeChild(GraffNode child) {
        if (child instanceof Presentation || child instanceof Slide) {
            if (this.getChildren().contains(child)) {
                if (child instanceof Slide) {
                    this.number--;
                } else if (child instanceof Presentation) {
                    Presentation presentation = (Presentation) child;
                    this.number -= presentation.getChildren().size();
                }
                this.getChildren().remove(child);
                notifySubscribers("presentation removed:" + child.getName());

            }
        }
    }

    public void setAuthor(String name) throws IOException {
        author = name;
        for (GraffNode child : this.getChildren()) {
            if (child instanceof Presentation) ((Presentation) child).setAuthor(this.author);
            notifySubscribers("project author changed");
        }
    }
}