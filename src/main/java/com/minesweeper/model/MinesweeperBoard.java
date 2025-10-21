package com.minesweeper.model;

import com.minesweeper.config.GameConfig;
import java.util.Random;

/**
 * Represents the Minesweeper game board.
 * Manages the grid of cells, mine placement, and game state.
 */
public class MinesweeperBoard {
    private final GameConfig config;
    private final Cell[][] cells;
    private boolean gameOver;
    private boolean gameWon;
    private int revealedCells;
    private int totalMines;
    private final Random random;

    /**
     * Creates a new game board with the specified configuration.
     *
     * @param config The game configuration
     */
    public MinesweeperBoard(GameConfig config) {
        this.config = config;
        this.cells = new Cell[config.getBoardSize()][config.getBoardSize()];
        this.gameOver = false;
        this.gameWon = false;
        this.revealedCells = 0;
        this.random = new Random();
        initializeBoard();
    }

    /**
     * Initializes the game board by creating cells and placing mines.
     */
    private void initializeBoard() {
        // Create all cells
        for (int row = 0; row < config.getBoardSize(); row++) {
            for (int col = 0; col < config.getBoardSize(); col++) {
                cells[row][col] = new Cell(row, col);
            }
        }

        // Place mines randomly
        placeMines();

        // Calculate adjacent mine counts
        calculateAdjacentMines();
    }

    /**
     * Places mines randomly on the board based on the configured density.
     */
    private void placeMines() {
        totalMines = 0;
        for (int row = 0; row < config.getBoardSize(); row++) {
            for (int col = 0; col < config.getBoardSize(); col++) {
                if (random.nextDouble() < config.getMineDensity()) {
                    cells[row][col].setMine(true);
                    totalMines++;
                }
            }
        }
    }

    /**
     * Calculates the number of adjacent mines for each cell.
     */
    private void calculateAdjacentMines() {
        for (int row = 0; row < config.getBoardSize(); row++) {
            for (int col = 0; col < config.getBoardSize(); col++) {
                if (!cells[row][col].isMine()) {
                    cells[row][col].setAdjacentMines(countAdjacentMines(row, col));
                }
            }
        }
    }

    /**
     * Counts the number of mines adjacent to the specified cell.
     *
     * @param row The row of the cell
     * @param col The column of the cell
     * @return The number of adjacent mines
     */
    public int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int newRow = row + dr;
                int newCol = col + dc;
                if (isValidPosition(newRow, newCol) && cells[newRow][newCol].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if the specified position is valid on the board.
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < config.getBoardSize() &&
               col >= 0 && col < config.getBoardSize();
    }

    /**
     * Reveals a cell at the specified position.
     * If the cell has no adjacent mines, recursively reveals adjacent cells.
     *
     * @param row The row of the cell to reveal
     * @param col The column of the cell to reveal
     * @return true if the reveal was successful, false if a mine was hit
     */
    public boolean revealCell(int row, int col) {
        if (!isValidPosition(row, col) || gameOver) {
            return false;
        }

        Cell cell = cells[row][col];

        // Cannot reveal flagged or already revealed cells
        if (cell.isFlagged() || cell.isRevealed()) {
            return true;
        }

        // Reveal the cell
        cell.setRevealed(true);
        revealedCells++;

        // Check if mine was hit
        if (cell.isMine()) {
            gameOver = true;
            return false;
        }

        // If no adjacent mines, reveal surrounding cells
        if (cell.getAdjacentMines() == 0) {
            revealAdjacentCells(row, col);
        }

        // Check for win condition
        checkWinCondition();

        return true;
    }

    /**
     * Recursively reveals adjacent cells when a cell with no adjacent mines is revealed.
     */
    private void revealAdjacentCells(int row, int col) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int newRow = row + dr;
                int newCol = col + dc;
                if (isValidPosition(newRow, newCol)) {
                    Cell adjacentCell = cells[newRow][newCol];
                    if (!adjacentCell.isRevealed() && !adjacentCell.isFlagged()) {
                        revealCell(newRow, newCol);
                    }
                }
            }
        }
    }

    /**
     * Toggles a flag on the specified cell.
     */
    public void toggleFlag(int row, int col) {
        if (isValidPosition(row, col) && !gameOver) {
            cells[row][col].toggleFlag();
        }
    }

    /**
     * Checks if the player has won the game.
     */
    private void checkWinCondition() {
        int totalCells = config.getTotalCells();
        if (revealedCells == totalCells - totalMines) {
            gameWon = true;
            gameOver = true;
        }
    }

    /**
     * Reveals all mines on the board (typically called when game is over).
     */
    public void revealAllMines() {
        for (int row = 0; row < config.getBoardSize(); row++) {
            for (int col = 0; col < config.getBoardSize(); col++) {
                if (cells[row][col].isMine()) {
                    cells[row][col].setRevealed(true);
                }
            }
        }
    }

    // Getters
    public Cell getCell(int row, int col) {
        if (isValidPosition(row, col)) {
            return cells[row][col];
        }
        return null;
    }

    public int getBoardSize() {
        return config.getBoardSize();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public int getRevealedCells() {
        return revealedCells;
    }
}
