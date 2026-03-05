package raf.graffito.dsw.gui.swing;

import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.controller.ActionManager;
import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.message.EventType;
import raf.graffito.dsw.message.Message;
import raf.graffito.dsw.message.MessageGenerator;
import raf.graffito.dsw.observer.Subscriber;
import raf.graffito.dsw.tree.GraffTree;
import raf.graffito.dsw.tree.GraffTreeImplementation;
import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

@Getter
@Setter
public class MainFrame extends JFrame implements Subscriber {
    private static MainFrame instance;
    private JMenuBar menu;
    private JToolBar toolBar;
    private MySideBar sideBar;
    private ActionManager actionManager;
    private MessageGenerator messageGenerator;
    private GraffTree graffTree;
    private JSplitPane split;
    private JTabbedPane tabbedPane;
    private final Set<Color> usedColors = new HashSet<>();
    private final Map<Project, Color> projectColors = new HashMap<>();
    private MainFrame() {
    }
    private void initialize() {
        this.actionManager = new ActionManager();
        this.graffTree = new GraffTreeImplementation();
        ApplicationFramework.getInstance().getMessageGenerator().addSubscriber(this);
        initializeGUI();
    }
    private void initializeGUI() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Graffito");
        menu = new MyMenuBar();
        setJMenuBar(menu);
        toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);

        JTree projectExplorer = graffTree.generateTree(ApplicationFramework.getInstance().getGraffRepository().getWorkspace());
        JScrollPane scroll = new JScrollPane(projectExplorer);
        scroll.setMinimumSize(new Dimension(200, 150));
        tabbedPane = new JTabbedPane();

        sideBar = new MySideBar();

        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, tabbedPane);
        mainSplit.setDividerLocation(250);
        mainSplit.setOneTouchExpandable(true);

        JSplitPane contentSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainSplit, sideBar);
        contentSplit.setDividerLocation(800);
        contentSplit.setOneTouchExpandable(true);
        contentSplit.setResizeWeight(1.0);

        tabbedPane.addChangeListener(e -> {
            if (sideBar != null) {
                sideBar.updateImagesPanel();
            }
        });

        getContentPane().add(contentSplit, BorderLayout.CENTER);
    }
    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
            instance.initialize();
        }
        return instance;
    }
    @Override
    public void update(Object notification) {
        Message msg = (Message) notification;
        for (EventType e : EventType.values()) {
            if (e.equals(msg.getEventType())) {
                JOptionPane.showMessageDialog(
                        this,
                        msg.getText(),
                        msg.getEventType().toString(),
                        msg.getEventType() == EventType.ERROR ?
                                JOptionPane.ERROR_MESSAGE :
                                JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
    public void reload(PresentationView pv) {
        int index = getTabIndex(pv);
        if (index == -1) {
            tabbedPane.addTab(pv.getPresentation().getName(), pv);
            tabbedPane.setSelectedComponent(pv);
        } else {
            tabbedPane.setSelectedIndex(index);
        }
    }
    public int getTabIndex(PresentationView pv) {
        return tabbedPane.indexOfComponent(pv);
    }
    public void removeTabForPresentation(raf.graffito.dsw.graffRepository.implementation.Presentation p) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component c = tabbedPane.getComponentAt(i);
            if (c instanceof PresentationView pv) {
                if (pv.getPresentation() == p) {
                    tabbedPane.removeTabAt(i);
                    break;
                }
            }
        }
    }
    public boolean isColorUsed(Color color) {
        return usedColors.contains(color);
    }

    public void registerColor(Color color) {
        usedColors.add(color);
    }

    public void setProjectColor(Project p, Color c) {
        projectColors.put(p, c);
    }

    public Color getProjectColor(Project p) {
        return projectColors.get(p);
    }

    public void colorTabsForProject(Project project) {
        Color color = projectColors.get(project);
        if (color == null) return;

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component comp = tabbedPane.getComponentAt(i);

            if (comp instanceof PresentationView pv) {
                if (pv.getPresentation().getParent() == project) {
                    tabbedPane.setBackgroundAt(i, color);
                }
            }
        }
    }

    public void renameTab(raf.graffito.dsw.graffRepository.implementation.Presentation p) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component c = tabbedPane.getComponentAt(i);
            if (c instanceof PresentationView pv) {
                if (pv.getPresentation() == p) {
                    tabbedPane.setTitleAt(i, p.getName());
                }
            }
        }
    }

    public PresentationView getCurrentPresentationView() {
        Component selected = tabbedPane.getSelectedComponent();
        if (selected instanceof PresentationView) {
            return (PresentationView) selected;
        }
        return null;
    }

    public GraffTree getGraffTree() {
        return graffTree;
    }
}