package com.minesweeper.model;

/**
 * Represents a single cell in the Minesweeper game board.
 * Each cell can contain a mine, be flagged, or be revealed.
 */
public class Cell {
    private final int row;
    private final int col;
    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int adjacentMines;

    /**
     * Creates a new cell at the specified position.
     *
     * @param row The row position of the cell
     * @param col The column position of the cell
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.adjacentMines = 0;
    }

    // Getters and setters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    /**
     * Toggles the flagged state of this cell.
     */
    public void toggleFlag() {
        if (!isRevealed) {
            isFlagged = !isFlagged;
        }
    }

    @Override
    public String toString() {
        if (isFlagged) return "F";
        if (!isRevealed) return "?";
        if (isMine) return "*";
        return adjacentMines == 0 ? " " : String.valueOf(adjacentMines);
    }
}
