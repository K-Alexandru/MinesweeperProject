# Minesweeper - Modern Edition

A modern, improved implementation of the classic Minesweeper game built with Java and Swing.

## Features

### Code Improvements
- **MVC Architecture**: Clean separation of Model, View, and Controller components
- **Modern Java**: Built with Java 17 using modern language features (switch expressions, records-ready)
- **Configurable**: Easy-to-modify game settings through the `GameConfig` class
- **Well-Documented**: Comprehensive JavaDoc comments throughout the codebase
- **Resource Management**: Efficient image loading and caching system
- **Statistics Tracking**: Persistent game statistics across sessions

### Game Features
- **Classic Gameplay**: Traditional Minesweeper rules and mechanics
- **Customizable Board**: Configurable board size (5x5 to 30x30)
- **Difficulty Levels**: Three preset difficulty levels (Easy, Medium, Hard)
- **Visual Feedback**: Custom images for numbers, mines, and flags
- **Statistics**: Track games played, won, lost, and win percentage
- **Splash Screen**: Professional startup screen

## Project Structure

```
MinesweeperProject/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/minesweeper/
│   │   │       ├── config/
│   │   │       │   └── GameConfig.java          # Game configuration
│   │   │       ├── model/
│   │   │       │   ├── Cell.java                # Cell model
│   │   │       │   ├── MinesweeperBoard.java    # Game board model
│   │   │       │   └── GameStatistics.java      # Statistics tracking
│   │   │       ├── view/
│   │   │       │   ├── MinesweeperGUI.java      # Main game window
│   │   │       │   └── SplashScreen.java        # Startup splash screen
│   │   │       ├── controller/
│   │   │       │   └── MinesweeperController.java # Game controller
│   │   │       ├── util/
│   │   │       │   └── ResourceLoader.java      # Resource management
│   │   │       └── MinesweeperApplication.java  # Main entry point
│   │   └── resources/
│   │       └── *.png, *.jpg                     # Game images
│   └── test/
│       └── java/com/minesweeper/                # Unit tests
├── pom.xml                                       # Maven build configuration
└── README.md                                     # This file
```

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher (optional, for building from source)

### Running with Maven

```bash
# Compile and run
mvn clean compile exec:java -Dexec.mainClass="com.minesweeper.MinesweeperApplication"

# Or build an executable JAR
mvn clean package

# Then run the JAR
java -jar target/minesweeper.jar
```

### Running with Java directly

```bash
# Compile
javac -d target/classes -sourcepath src/main/java src/main/java/com/minesweeper/MinesweeperApplication.java

# Run
java -cp target/classes:src/main/resources com.minesweeper.MinesweeperApplication
```

## How to Play

1. **Left Click**: Reveal a cell
   - If it's a mine, you lose
   - If it's empty, adjacent cells may auto-reveal
   - Numbers indicate how many mines are adjacent

2. **Right Click**: Flag/unflag a cell as a mine
   - Use flags to mark cells you think contain mines
   - Flags prevent accidental left-clicks

3. **Objective**: Reveal all non-mine cells without clicking on any mines

4. **Win Condition**: All non-mine cells are revealed

## Configuration

You can customize the game by modifying `GameConfig.java`:

```java
// Board size (5-30)
public static final int DEFAULT_BOARD_SIZE = 20;

// Difficulty levels (mine density)
public static final double EASY_MINE_DENSITY = 0.05;    // 5% mines
public static final double MEDIUM_MINE_DENSITY = 0.15;  // 15% mines
public static final double HARD_MINE_DENSITY = 0.25;    // 25% mines

// UI settings
public static final int CELL_SIZE = 35;
public static final int WINDOW_WIDTH = 800;
public static final int WINDOW_HEIGHT = 800;
```

## Architecture Highlights

### Model-View-Controller Pattern
- **Model** (`model/`): Contains game logic and state
  - `Cell`: Represents individual cells
  - `MinesweeperBoard`: Manages the game board
  - `GameStatistics`: Tracks game statistics

- **View** (`view/`): Handles user interface
  - `MinesweeperGUI`: Main game window
  - `SplashScreen`: Startup screen

- **Controller** (`controller/`): Coordinates between model and view
  - `MinesweeperController`: Handles user actions and game state

### Key Design Patterns
- **Singleton**: `ResourceLoader` for efficient resource management
- **Observer**: `GameStateListener` interface for event handling
- **Factory**: Image loading and caching system

## Improvements Over Original

1. **Better Code Organization**: MVC pattern vs. monolithic design
2. **Type Safety**: Proper encapsulation and access modifiers
3. **Resource Management**: Cached image loading instead of repeated loading
4. **Error Handling**: Proper validation and error messages
5. **Configurability**: Centralized configuration vs. hard-coded values
6. **Documentation**: Comprehensive JavaDoc comments
7. **Modern Java**: Uses Java 17 features (switch expressions, var, etc.)
8. **Build System**: Maven for dependency management and building
9. **Testability**: Separated concerns make unit testing easier
10. **UI Improvements**: Better event handling and visual feedback

## Future Enhancements

Potential improvements that could be added:
- [ ] Multiple difficulty level menu
- [ ] High score persistence (file or database)
- [ ] Timer to track game duration
- [ ] Custom board size and mine count selector
- [ ] Sound effects
- [ ] Themes and skins
- [ ] Undo functionality
- [ ] Hint system
- [ ] Multiplayer support

## License

MIT License - See LICENSE file for details

## Credits

Original game concept: Microsoft Minesweeper
Modern implementation: Improved and modernized version with enhanced architecture and features
