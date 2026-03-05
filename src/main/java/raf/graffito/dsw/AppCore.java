package raf.graffito.dsw;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.core.GraffRepository;
import raf.graffito.dsw.core.Gui;
import raf.graffito.dsw.graffRepository.GraffRepositoryImplementation;
import raf.graffito.dsw.gui.swing.SwingGui;
import raf.graffito.dsw.logger.LoggerFactory;
import raf.graffito.dsw.message.MessageGenerator;
import raf.graffito.dsw.message.MessageGeneratorImplementation;

public class AppCore {
    public static void main(String[] args) {
        ApplicationFramework appCore = ApplicationFramework.getInstance();

        MessageGenerator messageGenerator = new MessageGeneratorImplementation();
        Gui gui = new SwingGui(messageGenerator);
        LoggerFactory loggerFactory = new LoggerFactory();
        GraffRepository graffRepository = new GraffRepositoryImplementation();


        appCore.initialize(gui, graffRepository,loggerFactory,messageGenerator);
        appCore.run();
    }
}