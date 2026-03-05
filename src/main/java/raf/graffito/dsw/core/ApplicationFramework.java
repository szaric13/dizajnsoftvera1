package raf.graffito.dsw.core;
import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.logger.ConsoleLogger;
import raf.graffito.dsw.logger.FileLogger;
import raf.graffito.dsw.logger.LoggerFactory;
import raf.graffito.dsw.message.MessageGenerator;
@Getter
@Setter
public class ApplicationFramework {
    protected Gui gui;
    protected GraffRepository graffRepository;
    private MessageGenerator messageGenerator;
    private ConsoleLogger consoleLogger;
    private FileLogger fileLogger;
    private LoggerFactory loggerFactory;

    private static ApplicationFramework instance;

    private ApplicationFramework() { }

    public static ApplicationFramework getInstance() {
        if (instance == null) {
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public void run() {
        gui.start();
    }

    public void initialize(Gui gui, GraffRepository graffRepository, LoggerFactory loggerFactory, MessageGenerator messageGenerator) {
        this.gui = gui;
        this.graffRepository = graffRepository;
        this.messageGenerator = messageGenerator;
        this.loggerFactory = loggerFactory;

        MainFrame.getInstance().setVisible(true);

        loggerFactory.createLogger("file");
        loggerFactory.createLogger("console");
    }

}
