package com.minesweeper.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GameStatistics class.
 */
public class GameStatisticsTest {
    private GameStatistics stats;

    @BeforeEach
    public void setUp() {
        stats = new GameStatistics();
    }

    @Test
    public void testInitialState() {
        assertEquals(0, stats.getGamesPlayed());
        assertEquals(0, stats.getGamesWon());
        assertEquals(0, stats.getGamesLost());
        assertEquals(0.0, stats.getWinPercentage());
    }

    @Test
    public void testRecordWin() {
        stats.recordWin();
        assertEquals(1, stats.getGamesPlayed());
        assertEquals(1, stats.getGamesWon());
        assertEquals(0, stats.getGamesLost());
        assertEquals(100.0, stats.getWinPercentage());
    }

    @Test
    public void testRecordLoss() {
        stats.recordLoss();
        assertEquals(1, stats.getGamesPlayed());
        assertEquals(0, stats.getGamesWon());
        assertEquals(1, stats.getGamesLost());
        assertEquals(0.0, stats.getWinPercentage());
    }

    @Test
    public void testMultipleGames() {
        stats.recordWin();
        stats.recordWin();
        stats.recordLoss();
        stats.recordWin();

        assertEquals(4, stats.getGamesPlayed());
        assertEquals(3, stats.getGamesWon());
        assertEquals(1, stats.getGamesLost());
        assertEquals(75.0, stats.getWinPercentage());
    }

    @Test
    public void testReset() {
        stats.recordWin();
        stats.recordLoss();
        stats.reset();

        assertEquals(0, stats.getGamesPlayed());
        assertEquals(0, stats.getGamesWon());
        assertEquals(0, stats.getGamesLost());
        assertEquals(0.0, stats.getWinPercentage());
    }

    @Test
    public void testWinPercentageCalculation() {
        for (int i = 0; i < 7; i++) {
            stats.recordWin();
        }
        for (int i = 0; i < 3; i++) {
            stats.recordLoss();
        }

        assertEquals(10, stats.getGamesPlayed());
        assertEquals(7, stats.getGamesWon());
        assertEquals(3, stats.getGamesLost());
        assertEquals(70.0, stats.getWinPercentage(), 0.01);
    }
}
