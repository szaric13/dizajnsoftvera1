package raf.graffito.dsw.tree.controller;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.graffRepository.implementation.Workspace;
import raf.graffito.dsw.message.EventType;
import raf.graffito.dsw.tree.model.GraffTreeItem;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;

import static javax.swing.text.StyleConstants.setIcon;

public class GraffTreeCellEditor extends DefaultTreeCellEditor implements ActionListener {
    private Object clickedOn = null;
    private JTextField edit = null;

    public GraffTreeCellEditor(JTree arg0, DefaultTreeCellRenderer arg1) {
        super(arg0, arg1);
    }

    public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5) {
        clickedOn = arg1;
        edit = new JTextField(arg1.toString());
        edit.addActionListener(this);
        return edit;
    }


    public boolean isCellEditable(EventObject arg0) {
        if (arg0 instanceof MouseEvent)
            if (((MouseEvent) arg0).getClickCount() == 3) {
                return true;
            }
        return false;
    }


    public void actionPerformed(ActionEvent e) {

        if (!(clickedOn instanceof GraffTreeItem))
            return;

        GraffTreeItem clicked = (GraffTreeItem) clickedOn;
        String newName = e.getActionCommand();

        if(newName == null)
            return;

        try {

            if(!(clicked.getGraffNode() instanceof Workspace)){
                for (GraffNode child: clicked.getGraffNode().getParent(1).getChildren()){
                    if (child.getName().equals(newName) && !(child.equals(clicked))) {
                        ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.NODE_ALREADY_EXISTS);
                        return;
                    }
                }
            }else{
                for (GraffNode child: ((Workspace) clicked.getGraffNode()).getChildren()){
                    if (child.getName().equals(newName) && !(child.equals(clicked))) {
                        ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.NODE_ALREADY_EXISTS);
                        return;
                    }
                }
            }


            clicked.setName(newName);

        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }


    }
}
