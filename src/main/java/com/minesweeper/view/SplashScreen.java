package com.minesweeper.view;

import com.minesweeper.util.ResourceLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Splash screen displayed when the application starts.
 */
public class SplashScreen extends JWindow {
    private static final int DISPLAY_TIME = 2000; // 2 seconds

    /**
     * Creates and displays the splash screen.
     */
    public SplashScreen() {
        setupUI();
        showSplash();
    }

    /**
     * Sets up the splash screen UI.
     */
    private void setupUI() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Title
        JLabel title = new JLabel("Minesweeper", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(new Color(220, 20, 60));
        content.add(title, BorderLayout.CENTER);

        // Subtitle
        JLabel subtitle = new JLabel("Modern Edition - Loading...", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setForeground(Color.DARK_GRAY);
        content.add(subtitle, BorderLayout.SOUTH);

        // Try to load and display the minesweeper image
        try {
            ImageIcon imageIcon = ResourceLoader.getInstance().loadImage("minesweeper.jpg");
            if (imageIcon != null) {
                Image scaledImage = imageIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);
                content.add(imageLabel, BorderLayout.NORTH);
            }
        } catch (Exception e) {
            // If image fails to load, just show text
            System.err.println("Could not load splash image: " + e.getMessage());
        }

        setContentPane(content);
        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    /**
     * Displays the splash screen for a set duration.
     */
    private void showSplash() {
        setVisible(true);

        try {
            Thread.sleep(DISPLAY_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        setVisible(false);
        dispose();
    }
}
