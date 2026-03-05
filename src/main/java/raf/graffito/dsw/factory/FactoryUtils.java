package raf.graffito.dsw.factory;

import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.graffRepository.implementation.Workspace;

import javax.swing.*;

public class FactoryUtils {
    public static GraffNodeFactory getFactory(GraffNode parent) {
        if (parent instanceof Workspace) {
            return new ProjectFactory("Project", parent);
        }

        if (parent instanceof Project) {
            Object[] options = {"Presentation", "Slide"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Choose an option to add:",
                    "Add Node",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == JOptionPane.CLOSED_OPTION) {
                System.out.println("Closed without selection.");
                return null;
            }

            switch (options[choice].toString()) {
                case "Presentation":
                    return new PresentationFactory("Presentation", parent);
                case "Slide":
                    return new SlideFactory("Slide", parent);
                default:
                    return null;
            }
        }

        if (parent instanceof Presentation) {
            return new SlideFactory("Slide", parent);
        }

        return null;
    }
}
