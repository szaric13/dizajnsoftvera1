package raf.graffito.dsw.observer;

import raf.graffito.dsw.graffRepository.composite.GraffNode;

import java.io.IOException;

public interface Publisher {
    void addSubscriber(Subscriber subscriber);
    void removeSubscriber (Subscriber subscriber);
    void notifySubscribers (Object notification) throws IOException;
}
