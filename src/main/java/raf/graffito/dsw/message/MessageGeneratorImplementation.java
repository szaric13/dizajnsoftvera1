package raf.graffito.dsw.message;

import raf.graffito.dsw.observer.Subscriber;

import java.time.LocalDateTime;

import static raf.graffito.dsw.message.EventType.*;

public class MessageGeneratorImplementation extends MessageGenerator {
    private Message message;

    @Override
    public void generateMessage(EventType eventType) {
        switch (eventType) {
            case ERROR:
                createMessage("Greska u sistemu", eventType);
                break;
            case WORKSPACE_CANNOT_BE_DELETED:
                createMessage("Workspace ne moze biti obrisan", eventType);
                break;
            case NODE_CANNOT_BE_DELETED:
                createMessage("Cvor ne moze biti obrisan", eventType);
                break;
            case CANNOT_ADD_CHILD:
                createMessage("Ne moze da se doda child na ovu komponentu", eventType);
                break;
            case CANNOT_DELETE_FILE:
                createMessage("Fajl ne može da se obrise", eventType);
                break;
            case NODE_ALREADY_EXISTS:
                createMessage("Cvor sa ovim imenom već postoji", eventType);
                break;
            case WARNING:
                createMessage("Upozorenje", eventType);
                break;
            case NODE_NOT_SELECTED:
                createMessage("Nije selektovan nijedan cvor", eventType);
                break;
            case COMPONENT_NOT_SELECTED:
                createMessage("Nije selektovana nijedna komponenta", eventType);
                break;
            case NOTIFICATION:
                createMessage("Obavestenje", eventType);
                break;
            case NAME_CANNOT_BE_EMPTY:
                createMessage("Ime ne moze biti prazno", eventType);
                break;
            case MUST_INSERT_NAME:
                createMessage("Morate uneti ime", eventType);
                break;
            case RESOURCE_NOT_FOUND:
                createMessage("Resurs nije pronađen", eventType);
                break;
            case PROJECT_MUST_HAVE_PRESENTATION_OR_SLIDE:
                createMessage("Projekat mora imati barem jednu prezentaciju ili slajd", eventType);
                break;
            case SLIDE_CANNOT_CONTAIN_CHILDREN:
                createMessage("Slajd ne moze sadrzati druge slajdove", eventType);
                break;
            case PRESENTATION_CAN_ONLY_CONTAIN_SLIDES:
                createMessage("Prezentacija moze sadrzati samo slajdove", eventType);
                break;
            case CANNOT_MOVE_BETWEEN_PRESENTATIONS:
                createMessage("Samo slajdovi mogu menjati redosled", eventType);
                break;
            default:
                createMessage("Nepoznat dogadjaj", eventType);
                break;
        }
    }


    @Override
    public void addSubscriber(Subscriber subscriber) {
        if (subscriber == null  && subscribers.contains(subscriber)) return;
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        if (subscriber == null && !(subscribers.contains(subscriber)) && subscribers.isEmpty()) return;
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(Object notification) {
        if (notification == null || subscribers.isEmpty()) return;
        for (Subscriber s: subscribers){
            s.update(this.message);
        }
    }

    private void createMessage(String tekst, EventType eventType) {
        this.message = new Message(tekst, eventType, LocalDateTime.now());
        notifySubscribers(this);
    }
}
