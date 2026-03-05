package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.AboutUsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class AboutUsAction extends AbstractGraffAction {
    public AboutUsAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/AboutUs.png"));
        putValue(NAME, "About us");
        putValue(SHORT_DESCRIPTION, "About us");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        AboutUsView aboutUsView = new AboutUsView();
        aboutUsView.setVisible(true);
    }
}
