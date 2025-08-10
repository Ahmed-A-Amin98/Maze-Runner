package game.View;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import game.Engine.GameEngine;
import game.Model.Player;
import game.Model.Enemy;
import game.Model.Wall;
import game.Model.Gift;
import game.Model.Bomb;
import game.Model.Armor;
import game.Model.Obstacle;
import game.Model.EndPoint;
import game.Model.Checkpoint;
import game.Model.Weapon;

import java.io.File;
import java.util.Random;

/**
 * Modern JavaFX-based Maze Runner Game
 */
public class MazeRunnerFX extends Application {
    
    private Stage primaryStage;
    private GameEngine gameEngine;
    private Player player;
    private MediaPlayer backgroundMusic;
    private boolean musicPlaying = false;
    
    // Game settings
    private static final int GAME_WIDTH = 900;
    private static final int GAME_HEIGHT = 690;
    private static final int ENEMY_COUNT = 8;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        try {
            // Initialize game engine
            gameEngine = new GameEngine(GAME_WIDTH, GAME_HEIGHT);
            
            // Set up the main scene
            Scene scene = new Scene(gameEngine.getGamePane(), GAME_WIDTH, GAME_HEIGHT);
            scene.setFill(javafx.scene.paint.Color.BLACK);
            
            // Set up key handling
            setupKeyHandling(scene);
            
            // Configure the stage
            primaryStage.setTitle("Maze Runner - Modern Edition");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            
            // Initialize game objects
            initializeGame();
            
            // Start background music
            startBackgroundMusic();
            
            // Show the stage
            primaryStage.show();
            
            // Start the game
            gameEngine.start();
            
        } catch (Exception e) {
            showError("Error starting game", e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void setupKeyHandling(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            KeyCode code = event.getCode();
            
            if (player != null) {
                player.handleKeyPress(code);
            }
            
            // Global key handling
            switch (code) {
                case ESCAPE:
                    pauseGame();
                    break;
                case P:
                    togglePause();
                    break;
                case M:
                    toggleMusic();
                    break;
                case F11:
                    toggleFullscreen();
                    break;
            }
        });
        
        scene.setOnKeyReleased((KeyEvent event) -> {
            if (player != null) {
                player.handleKeyRelease(event.getCode());
            }
        });
    }
    
    private void initializeGame() {
        // Create player
        player = new Player(150, 750); // Starting position
        gameEngine.setPlayer(player);
        
        // Add default weapon
        Weapon defaultWeapon = new Weapon("Pistol", 10, 15, 0.5);
        player.addWeapon(defaultWeapon);
        
        // Create enemies
        createEnemies();
        
        // Create walls and obstacles
        createWallsAndObstacles();
        
        // Create collectibles
        createCollectibles();
        
        // Create endpoint
        EndPoint endPoint = new EndPoint(837, 660);
        gameEngine.setEndPoint(endPoint);
        
        // Create checkpoint
        Checkpoint checkpoint = new Checkpoint(450, 450);
        gameEngine.setCheckpoint(checkpoint);
    }
    
    private void createEnemies() {
        Random random = new Random();
        int[][] maze = gameEngine.getMaze();
        
        for (int i = 0; i < ENEMY_COUNT; i++) {
            int x, y;
            do {
                x = random.nextInt(23);
                y = random.nextInt(29);
            } while (maze[x][y] != 0); // Only place on empty spaces
            
            Enemy enemy = new Enemy((y * 30) - 5, (x * 30) - 2);
            gameEngine.addEnemy(enemy);
        }
    }
    
    private void createWallsAndObstacles() {
        int[][] maze = gameEngine.getMaze();
        
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                int cell = maze[row][col];
                double x = col * 30.0;
                double y = row * 30.0;
                
                switch (cell) {
                    case 1: // Wall
                        Wall wall = new Wall(x, y);
                        gameEngine.addWall(wall);
                        break;
                    case 7: // Obstacle
                        Obstacle obstacle = new Obstacle(x, y);
                        gameEngine.addObstacle(obstacle);
                        break;
                }
            }
        }
    }
    
    private void createCollectibles() {
        Random random = new Random();
        int[][] maze = gameEngine.getMaze();
        
        // Create gifts
        for (int i = 0; i < 5; i++) {
            int x, y;
            do {
                x = random.nextInt(23);
                y = random.nextInt(29);
            } while (maze[x][y] != 0);
            
            Gift gift = new Gift((y * 30) - 5, (x * 30) - 2);
            gameEngine.addGift(gift);
        }
        
        // Create bombs
        for (int i = 0; i < 3; i++) {
            int x, y;
            do {
                x = random.nextInt(23);
                y = random.nextInt(29);
            } while (maze[x][y] != 0);
            
            Bomb bomb = new Bomb((y * 30) - 5, (x * 30) - 2);
            gameEngine.addBomb(bomb);
        }
        
        // Create armor
        for (int i = 0; i < 2; i++) {
            int x, y;
            do {
                x = random.nextInt(23);
                y = random.nextInt(29);
            } while (maze[x][y] != 0);
            
            Armor armor = new Armor((y * 30) - 5, (x * 30) - 2);
            gameEngine.addArmor(armor);
        }
    }
    
    private void startBackgroundMusic() {
        try {
            File musicFile = new File("gametrack.mp3");
            if (musicFile.exists()) {
                Media media = new Media(musicFile.toURI().toString());
                backgroundMusic = new MediaPlayer(media);
                backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
                backgroundMusic.setVolume(0.5);
                backgroundMusic.play();
                musicPlaying = true;
            }
        } catch (Exception e) {
            System.err.println("Could not load background music: " + e.getMessage());
        }
    }
    
    private void toggleMusic() {
        if (backgroundMusic != null) {
            if (musicPlaying) {
                backgroundMusic.pause();
                musicPlaying = false;
            } else {
                backgroundMusic.play();
                musicPlaying = true;
            }
        }
    }
    
    private void pauseGame() {
        if (gameEngine != null) {
            gameEngine.pause();
            showPauseDialog();
        }
    }
    
    private void togglePause() {
        if (gameEngine != null) {
            if (gameEngine.isPaused()) {
                gameEngine.resume();
            } else {
                gameEngine.pause();
            }
        }
    }
    
    private void toggleFullscreen() {
        primaryStage.setFullScreen(!primaryStage.isFullScreen());
    }
    
    private void showPauseDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game Paused");
            alert.setHeaderText("Game is paused");
            alert.setContentText("Press 'P' to resume or 'ESC' to quit");
            alert.showAndWait();
            gameEngine.resume();
        });
    }
    
    private void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    
    @Override
    public void stop() {
        if (gameEngine != null) {
            gameEngine.stop();
        }
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
