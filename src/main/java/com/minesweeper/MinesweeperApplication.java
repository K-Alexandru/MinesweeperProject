package com.minesweeper;

import com.minesweeper.config.GameConfig;
import com.minesweeper.controller.MinesweeperController;
import com.minesweeper.view.MinesweeperGUI;
import com.minesweeper.view.SplashScreen;

import javax.swing.*;

/**
 * Main application class for Minesweeper.
 * Entry point for the game.
 */
public class MinesweeperApplication {

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }

        // Show splash screen
        new SplashScreen();

        // Create and start the game on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            GameConfig config = new GameConfig();
            MinesweeperController controller = new MinesweeperController(config);
            new MinesweeperGUI(controller);
        });
    }
}
