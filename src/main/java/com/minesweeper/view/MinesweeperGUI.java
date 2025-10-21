package com.minesweeper.view;

import com.minesweeper.config.GameConfig;
import com.minesweeper.controller.MinesweeperController;
import com.minesweeper.model.Cell;
import com.minesweeper.util.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Main GUI window for the Minesweeper game.
 * Implements the view component of the MVC pattern.
 */
public class MinesweeperGUI extends JFrame implements MinesweeperController.GameStateListener {
    private final MinesweeperController controller;
    private final GameConfig config;
    private final ResourceLoader resourceLoader;

    private JPanel boardPanel;
    private JButton[][] cellButtons;
    private JTextField txtGamesPlayed;
    private JTextField txtGamesWon;
    private JTextField txtGamesLost;
    private JTextField txtMines;
    private JButton btnReset;

    /**
     * Creates the main game window.
     */
    public MinesweeperGUI(MinesweeperController controller) {
        super("Minesweeper - Modern Edition");
        this.controller = controller;
        this.config = controller.getConfig();
        this.resourceLoader = ResourceLoader.getInstance();
        this.controller.setGameStateListener(this);

        initializeUI();
    }

    /**
     * Initializes the user interface.
     */
    private void initializeUI() {
        setSize(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Create components
        createBoardPanel();
        createControlPanel();

        // Set background color
        getContentPane().setBackground(Color.PINK);

        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    /**
     * Creates the game board panel with cells.
     */
    private void createBoardPanel() {
        int boardSize = config.getBoardSize();
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        boardPanel.setBackground(Color.PINK);

        cellButtons = new JButton[boardSize][boardSize];

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                JButton button = createCellButton(row, col);
                cellButtons[row][col] = button;
                boardPanel.add(button);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a single cell button.
     */
    private JButton createCellButton(int row, int col) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(GameConfig.CELL_SIZE, GameConfig.CELL_SIZE));
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));

        // Add mouse listener for left and right clicks
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    controller.handleCellLeftClick(row, col);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    controller.handleCellRightClick(row, col);
                }
            }
        });

        return button;
    }

    /**
     * Creates the control panel with statistics and reset button.
     */
    private void createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(255, 100, 100));
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Games Played
        controlPanel.add(new JLabel("Games Played:"));
        txtGamesPlayed = new JTextField("0", 5);
        txtGamesPlayed.setEditable(false);
        controlPanel.add(txtGamesPlayed);

        // Games Won
        controlPanel.add(new JLabel("Games Won:"));
        txtGamesWon = new JTextField("0", 5);
        txtGamesWon.setEditable(false);
        controlPanel.add(txtGamesWon);

        // Games Lost
        controlPanel.add(new JLabel("Games Lost:"));
        txtGamesLost = new JTextField("0", 5);
        txtGamesLost.setEditable(false);
        controlPanel.add(txtGamesLost);

        // Total Mines
        controlPanel.add(new JLabel("Total Mines:"));
        txtMines = new JTextField(String.valueOf(controller.getBoard().getTotalMines()), 5);
        txtMines.setEditable(false);
        controlPanel.add(txtMines);

        // Reset Button
        btnReset = new JButton("New Game");
        btnReset.addActionListener(e -> {
            controller.resetGame();
            updateBoard();
        });
        controlPanel.add(btnReset);

        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the entire board display.
     */
    private void updateBoard() {
        int boardSize = config.getBoardSize();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                updateCellDisplay(row, col);
            }
        }
        updateStatistics();
        txtMines.setText(String.valueOf(controller.getBoard().getTotalMines()));
    }

    /**
     * Updates the display of a single cell.
     */
    private void updateCellDisplay(int row, int col) {
        Cell cell = controller.getCell(row, col);
        JButton button = cellButtons[row][col];

        if (cell.isFlagged()) {
            ImageIcon flagIcon = resourceLoader.loadScaledImage(
                ResourceLoader.FLAG, GameConfig.CELL_SIZE, GameConfig.CELL_SIZE);
            button.setIcon(flagIcon);
            button.setText("");
        } else if (cell.isRevealed()) {
            if (cell.isMine()) {
                // Show bomb
                ImageIcon bombIcon = resourceLoader.loadScaledImage(
                    controller.isGameWon() ? ResourceLoader.BOMB : ResourceLoader.RED_BOMB,
                    GameConfig.CELL_SIZE, GameConfig.CELL_SIZE);
                button.setIcon(bombIcon);
                button.setText("");
                button.setBackground(Color.RED);
            } else {
                // Show number or empty
                int adjacentMines = cell.getAdjacentMines();
                if (adjacentMines > 0) {
                    ImageIcon numberIcon = resourceLoader.getScaledNumberImage(
                        adjacentMines, GameConfig.CELL_SIZE, GameConfig.CELL_SIZE);
                    button.setIcon(numberIcon);
                    button.setText("");
                } else {
                    ImageIcon emptyIcon = resourceLoader.loadScaledImage(
                        ResourceLoader.EMPTY, GameConfig.CELL_SIZE, GameConfig.CELL_SIZE);
                    button.setIcon(emptyIcon);
                    button.setText("");
                }
                button.setEnabled(false);
            }
        } else {
            // Unrevealed cell
            button.setIcon(null);
            button.setText("");
            button.setBackground(UIManager.getColor("Button.background"));
            button.setEnabled(true);
        }
    }

    /**
     * Updates the statistics display.
     */
    private void updateStatistics() {
        txtGamesPlayed.setText(String.valueOf(controller.getStatistics().getGamesPlayed()));
        txtGamesWon.setText(String.valueOf(controller.getStatistics().getGamesWon()));
        txtGamesLost.setText(String.valueOf(controller.getStatistics().getGamesLost()));
    }

    // GameStateListener implementation
    @Override
    public void onGameWon() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                "Congratulations! You won!\n" +
                "Win Rate: " + String.format("%.1f%%", controller.getStatistics().getWinPercentage()),
                "Victory!",
                JOptionPane.INFORMATION_MESSAGE);
        });
    }

    @Override
    public void onGameLost() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                "Game Over! You hit a mine.\n" +
                "Win Rate: " + String.format("%.1f%%", controller.getStatistics().getWinPercentage()),
                "Game Over",
                JOptionPane.ERROR_MESSAGE);
        });
    }

    @Override
    public void onBoardUpdated() {
        updateBoard();
    }

    @Override
    public void onCellRevealed(int row, int col) {
        updateCellDisplay(row, col);
    }

    @Override
    public void onCellFlagged(int row, int col) {
        updateCellDisplay(row, col);
    }
}
