package raf.graffito.dsw.message;

import raf.graffito.dsw.observer.Publisher;
import raf.graffito.dsw.observer.Subscriber;

import java.util.ArrayList;
import java.util.List;

public abstract class MessageGenerator implements Publisher {
    List<Subscriber> subscribers = new ArrayList<>();
    public void generateMessage(EventType eventType) {
}}
