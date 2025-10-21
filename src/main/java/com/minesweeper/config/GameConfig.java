package com.minesweeper.config;

/**
 * Configuration class for Minesweeper game settings.
 * Provides centralized configuration management with default values.
 */
public class GameConfig {
    // Game board dimensions
    public static final int DEFAULT_BOARD_SIZE = 20;
    public static final int MIN_BOARD_SIZE = 5;
    public static final int MAX_BOARD_SIZE = 30;

    // Game difficulty levels
    public static final double EASY_MINE_DENSITY = 0.05;    // 5% mines
    public static final double MEDIUM_MINE_DENSITY = 0.15;  // 15% mines
    public static final double HARD_MINE_DENSITY = 0.25;    // 25% mines

    // UI dimensions
    public static final int CELL_SIZE = 35;
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;

    // Colors
    public static final String BG_COLOR_BOARD = "#FFC0CB";  // Pink
    public static final String BG_COLOR_PANEL = "#FF0000";  // Red

    private int boardSize;
    private double mineDensity;

    /**
     * Creates a default game configuration with standard settings.
     */
    public GameConfig() {
        this(DEFAULT_BOARD_SIZE, EASY_MINE_DENSITY);
    }

    /**
     * Creates a game configuration with custom settings.
     *
     * @param boardSize The size of the game board (boardSize x boardSize)
     * @param mineDensity The density of mines (0.0 to 1.0)
     */
    public GameConfig(int boardSize, double mineDensity) {
        setBoardSize(boardSize);
        setMineDensity(mineDensity);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        if (boardSize < MIN_BOARD_SIZE || boardSize > MAX_BOARD_SIZE) {
            throw new IllegalArgumentException(
                "Board size must be between " + MIN_BOARD_SIZE + " and " + MAX_BOARD_SIZE);
        }
        this.boardSize = boardSize;
    }

    public double getMineDensity() {
        return mineDensity;
    }

    public void setMineDensity(double mineDensity) {
        if (mineDensity < 0.0 || mineDensity > 1.0) {
            throw new IllegalArgumentException("Mine density must be between 0.0 and 1.0");
        }
        this.mineDensity = mineDensity;
    }

    /**
     * Gets the total number of cells on the board.
     */
    public int getTotalCells() {
        return boardSize * boardSize;
    }

    /**
     * Gets the expected number of mines based on board size and density.
     */
    public int getExpectedMineCount() {
        return (int) (getTotalCells() * mineDensity);
    }
}
