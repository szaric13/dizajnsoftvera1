package raf.graffito.dsw.gui.swing;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AboutUsView extends JFrame {
    public AboutUsView() throws HeadlessException {
        initGui();
    }

    private void initGui() {
        setSize(650, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("About Us");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 20, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(createMemberPanel("Strahinja Zaric RN73/22", "/images/dr.jpg", 250, 250));
        mainPanel.add(createMemberPanel("Stevan Vukicevic RN90/25", "/images/mj.png", 250, 250));

        add(mainPanel);
    }

    private JPanel createMemberPanel(String name, String imagePath, int width, int height) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));

        ImageIcon icon = new ImageIcon(loadImage(imagePath));
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(nameLabel, BorderLayout.SOUTH);

        return panel;
    }

    private Image loadImage(String fileName) {
        URL imageURL = getClass().getResource(fileName);
        if (imageURL != null) {
            return new ImageIcon(imageURL).getImage();
        } else {
            System.err.println("Resource not found: " + fileName);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AboutUsView().setVisible(true));
    }
}
