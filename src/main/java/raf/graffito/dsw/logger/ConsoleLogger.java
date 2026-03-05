package raf.graffito.dsw.logger;

import raf.graffito.dsw.message.Message;

public class ConsoleLogger implements Logger {
    @Override
    public void log(String greska) {
        System.out.println(greska);
    }


    @Override
    public void update(Object notification) {
        Message message = (Message) notification;
        String greska = message.toString();
        this.log(greska);
    }
}
