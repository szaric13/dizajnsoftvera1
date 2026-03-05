package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.core.Gui;
import raf.graffito.dsw.message.MessageGenerator;

public class SwingGui implements Gui {
    private MainFrame mainFrame;
    private MessageGenerator msgGen;

    public SwingGui(MessageGenerator msgGen) {
        this.msgGen = msgGen;
    }

    @Override
    public void start() {
        mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);
    }
}
