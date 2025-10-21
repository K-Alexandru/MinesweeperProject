package com.minesweeper.model;

/**
 * Tracks game statistics across multiple games.
 * Maintains counts of games played, won, and lost.
 */
public class GameStatistics {
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;

    /**
     * Creates a new GameStatistics instance with zero counts.
     */
    public GameStatistics() {
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
    }

    /**
     * Records a game win.
     */
    public void recordWin() {
        gamesPlayed++;
        gamesWon++;
    }

    /**
     * Records a game loss.
     */
    public void recordLoss() {
        gamesPlayed++;
        gamesLost++;
    }

    /**
     * Resets all statistics to zero.
     */
    public void reset() {
        gamesPlayed = 0;
        gamesWon = 0;
        gamesLost = 0;
    }

    // Getters
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    /**
     * Calculates the win percentage.
     *
     * @return Win percentage (0-100), or 0 if no games played
     */
    public double getWinPercentage() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        return (gamesWon * 100.0) / gamesPlayed;
    }

    @Override
    public String toString() {
        return String.format("Games: %d, Won: %d, Lost: %d, Win%%: %.1f%%",
            gamesPlayed, gamesWon, gamesLost, getWinPercentage());
    }
}
