package com.minesweeper.controller;

import com.minesweeper.config.GameConfig;
import com.minesweeper.model.MinesweeperBoard;
import com.minesweeper.model.GameStatistics;
import com.minesweeper.model.Cell;

/**
 * Controller class that manages the game logic and coordinates between
 * the model (MinesweeperBoard) and the view.
 */
public class MinesweeperController {
    private MinesweeperBoard board;
    private final GameStatistics statistics;
    private final GameConfig config;
    private GameStateListener gameStateListener;

    /**
     * Interface for listening to game state changes.
     */
    public interface GameStateListener {
        void onGameWon();
        void onGameLost();
        void onBoardUpdated();
        void onCellRevealed(int row, int col);
        void onCellFlagged(int row, int col);
    }

    /**
     * Creates a new controller with the specified configuration.
     */
    public MinesweeperController(GameConfig config) {
        this.config = config;
        this.statistics = new GameStatistics();
        this.board = new MinesweeperBoard(config);
    }

    /**
     * Sets the game state listener.
     */
    public void setGameStateListener(GameStateListener listener) {
        this.gameStateListener = listener;
    }

    /**
     * Handles a left-click on a cell (reveal).
     */
    public void handleCellLeftClick(int row, int col) {
        if (board.isGameOver()) {
            return;
        }

        Cell cell = board.getCell(row, col);
        if (cell == null || cell.isFlagged() || cell.isRevealed()) {
            return;
        }

        boolean success = board.revealCell(row, col);

        if (!success) {
            // Mine was hit - game lost
            board.revealAllMines();
            statistics.recordLoss();
            if (gameStateListener != null) {
                gameStateListener.onGameLost();
            }
        } else if (board.isGameWon()) {
            // All non-mine cells revealed - game won
            statistics.recordWin();
            if (gameStateListener != null) {
                gameStateListener.onGameWon();
            }
        }

        if (gameStateListener != null) {
            gameStateListener.onCellRevealed(row, col);
            gameStateListener.onBoardUpdated();
        }
    }

    /**
     * Handles a right-click on a cell (flag toggle).
     */
    public void handleCellRightClick(int row, int col) {
        if (board.isGameOver()) {
            return;
        }

        Cell cell = board.getCell(row, col);
        if (cell == null || cell.isRevealed()) {
            return;
        }

        board.toggleFlag(row, col);

        if (gameStateListener != null) {
            gameStateListener.onCellFlagged(row, col);
            gameStateListener.onBoardUpdated();
        }
    }

    /**
     * Resets the game with a new board.
     */
    public void resetGame() {
        board = new MinesweeperBoard(config);
        if (gameStateListener != null) {
            gameStateListener.onBoardUpdated();
        }
    }

    /**
     * Resets all statistics.
     */
    public void resetStatistics() {
        statistics.reset();
    }

    // Getters
    public MinesweeperBoard getBoard() {
        return board;
    }

    public GameStatistics getStatistics() {
        return statistics;
    }

    public GameConfig getConfig() {
        return config;
    }

    public Cell getCell(int row, int col) {
        return board.getCell(row, col);
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public boolean isGameWon() {
        return board.isGameWon();
    }
}
