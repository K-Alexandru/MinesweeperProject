package com.minesweeper.model;

import com.minesweeper.config.GameConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MinesweeperBoard class.
 */
public class MinesweeperBoardTest {
    private GameConfig config;
    private MinesweeperBoard board;

    @BeforeEach
    public void setUp() {
        config = new GameConfig(10, 0.1); // 10x10 board with 10% mines
        board = new MinesweeperBoard(config);
    }

    @Test
    public void testBoardInitialization() {
        assertNotNull(board);
        assertEquals(10, board.getBoardSize());
        assertFalse(board.isGameOver());
        assertFalse(board.isGameWon());
    }

    @Test
    public void testCellCreation() {
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                Cell cell = board.getCell(row, col);
                assertNotNull(cell);
                assertEquals(row, cell.getRow());
                assertEquals(col, cell.getCol());
                assertFalse(cell.isRevealed());
                assertFalse(cell.isFlagged());
            }
        }
    }

    @Test
    public void testMinesPlaced() {
        int mineCount = 0;
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                if (board.getCell(row, col).isMine()) {
                    mineCount++;
                }
            }
        }
        assertEquals(board.getTotalMines(), mineCount);
        assertTrue(mineCount > 0, "At least some mines should be placed");
    }

    @Test
    public void testAdjacentMinesCounting() {
        // Test that adjacent mine counts are calculated
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                Cell cell = board.getCell(row, col);
                if (!cell.isMine()) {
                    int count = board.countAdjacentMines(row, col);
                    assertTrue(count >= 0 && count <= 8);
                    assertEquals(count, cell.getAdjacentMines());
                }
            }
        }
    }

    @Test
    public void testFlagToggle() {
        Cell cell = board.getCell(0, 0);
        assertFalse(cell.isFlagged());

        board.toggleFlag(0, 0);
        assertTrue(cell.isFlagged());

        board.toggleFlag(0, 0);
        assertFalse(cell.isFlagged());
    }

    @Test
    public void testRevealNonMineCell() {
        // Find a non-mine cell
        Cell nonMineCell = null;
        int row = 0, col = 0;
        for (int r = 0; r < board.getBoardSize(); r++) {
            for (int c = 0; c < board.getBoardSize(); c++) {
                if (!board.getCell(r, c).isMine()) {
                    nonMineCell = board.getCell(r, c);
                    row = r;
                    col = c;
                    break;
                }
            }
            if (nonMineCell != null) break;
        }

        if (nonMineCell != null) {
            boolean result = board.revealCell(row, col);
            assertTrue(result);
            assertTrue(nonMineCell.isRevealed());
        }
    }

    @Test
    public void testCannotRevealFlaggedCell() {
        // Find a non-mine cell
        for (int r = 0; r < board.getBoardSize(); r++) {
            for (int c = 0; c < board.getBoardSize(); c++) {
                if (!board.getCell(r, c).isMine()) {
                    board.toggleFlag(r, c);
                    int revealedBefore = board.getRevealedCells();
                    board.revealCell(r, c);
                    assertEquals(revealedBefore, board.getRevealedCells());
                    return;
                }
            }
        }
    }
}
