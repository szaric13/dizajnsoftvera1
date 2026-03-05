package raf.graffito.dsw.graffRepository.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.graffRepository.composite.GraffLeaf;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.slide.SlideElement;
import raf.graffito.dsw.observer.Subscriber;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Slide extends GraffLeaf {
    @JsonIgnore
    private static int counter = 1;
    private String content;
    @JsonIgnore
    private Color backgroundColor;
    private List<SlideElement> elements;
    public static final int SLIDE_WIDTH = 1200;
    public static final int SLIDE_HEIGHT = 800;

    public Slide(String name, GraffNode parent) throws IOException {
        super(name, parent);
        setName(name + counter);
        counter++;
        this.backgroundColor = Color.WHITE;
        this.elements = new ArrayList<>();
    }
    public Slide() {
        super();
        this.backgroundColor = Color.WHITE;
        this.elements = new ArrayList<>();
    }
    public Slide(String name, GraffNode parent, String content, Color backgroundColor, int duration) throws IOException {
        super(name, parent);
        setName(name + counter);
        counter++;
        this.content = content;
        this.backgroundColor = backgroundColor;
        this.elements = new ArrayList<>();
    }

    public void setContent(String content) {
        this.content = content;
        notifySubscribers(this);
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        notifySubscribers(this);
    }

    public void addElement(SlideElement element) {
        if (element != null && !elements.contains(element)) {
            elements.add(element);
            notifySubscribers(this);
        }
    }

    public void removeElement(SlideElement element) {
        if (element != null && elements.contains(element)) {
            elements.remove(element);
            notifySubscribers(this);
        }
    }

    public void removeElements(List<SlideElement> elementsToRemove) {
        for (SlideElement element : elementsToRemove) {
            removeElement(element);
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
    public void notifySubscribers(Object obj) {
        if (obj == null || subs.isEmpty()) {
            return;
        }

        for (Subscriber subscriber : subs)
            subscriber.update(obj);
    }
}
