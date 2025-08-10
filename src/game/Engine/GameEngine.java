package game.Engine;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import game.Model.GameObject;
import game.Model.Player;
import game.Model.Enemy;
import game.Model.Wall;
import game.Model.Gift;
import game.Model.Bomb;
import game.Model.Armor;
import game.Model.Obstacle;
import game.Model.EndPoint;
import game.Model.Checkpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Modern Game Engine using JavaFX with proper game loop
 */
public class GameEngine {
    
    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private Pane gamePane;
    
    // Game state
    private boolean isRunning = false;
    private boolean isPaused = false;
    private long lastUpdateTime;
    
    // Game objects
    private Player player;
    private List<Enemy> enemies = new CopyOnWriteArrayList<>();
    private List<Wall> walls = new CopyOnWriteArrayList<>();
    private List<Gift> gifts = new CopyOnWriteArrayList<>();
    private List<Bomb> bombs = new CopyOnWriteArrayList<>();
    private List<Armor> armors = new CopyOnWriteArrayList<>();
    private List<Obstacle> obstacles = new CopyOnWriteArrayList<>();
    private EndPoint endPoint;
    private Checkpoint checkpoint;
    
    // Game settings
    private static final int TARGET_FPS = 60;
    private static final long TARGET_FRAME_TIME = 1_000_000_000L / TARGET_FPS;
    
    // Maze data
    private int[][] maze;
    
    public GameEngine(int width, int height) {
        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        this.gamePane = new Pane(canvas);
        
        initializeGameLoop();
        initializeMaze();
    }
    
    private void initializeGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isRunning || isPaused) return;
                
                long deltaTime = now - lastUpdateTime;
                if (deltaTime >= TARGET_FRAME_TIME) {
                    update(deltaTime);
                    render();
                    lastUpdateTime = now;
                }
            }
        };
    }
    
    private void initializeMaze() {
        // Initialize the maze array (same as original)
        maze = new int[][] {
            {6,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,0,1,1,1,0,1,0,0,0,0,0,1,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0,2},
            {3,0,0,0,0,0,1,0,1,1,1,0,0,1,0,1,0,1,0,1,1,1,1,1,0,0,0,1,0,2},
            {3,0,1,1,0,1,1,0,0,0,1,0,0,1,0,0,7,1,0,0,0,0,0,0,0,0,0,1,0,2},
            {3,0,1,0,0,0,1,0,1,1,1,1,0,1,1,1,0,0,0,1,1,1,1,1,0,1,0,1,0,2},
            {3,0,1,0,1,1,1,0,1,0,0,1,0,8,0,0,0,1,1,1,1,1,0,0,0,1,0,1,0,2},
            {3,0,1,0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,1,0,1,0,1,0,1,0,0,0,2},
            {3,0,1,0,1,1,1,0,1,0,1,0,0,1,0,1,0,1,1,1,0,1,0,1,0,1,1,0,0,2},
            {3,0,1,0,1,0,0,0,1,0,1,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,1,1,0,2},
            {3,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,0,1,1,1,0,1,0,1,0,0,0,1,0,2},
            {3,8,0,0,1,0,0,0,0,0,0,0,0,1,0,1,1,1,0,1,0,1,0,1,1,1,1,1,1,2},
            {3,0,0,1,1,1,1,1,0,1,1,0,0,1,0,0,0,1,0,1,1,1,0,0,0,0,0,0,0,2},
            {3,0,0,0,0,1,0,0,0,0,1,1,1,1,1,1,0,1,0,0,0,0,0,1,1,0,1,0,0,2},
            {3,0,1,0,0,1,1,1,0,0,0,0,0,1,0,0,0,1,0,1,0,1,0,1,0,0,1,1,0,2},
            {3,0,1,0,0,0,0,1,0,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,0,0,2},
            {3,1,1,1,1,0,1,1,0,0,1,0,0,8,0,1,0,0,0,1,0,0,0,0,1,0,1,1,1,2},
            {3,0,1,1,0,0,1,0,0,0,1,0,1,1,0,0,0,0,1,1,1,1,0,1,1,0,0,0,0,2},
            {3,0,0,1,0,0,1,1,1,0,1,0,0,1,1,1,1,0,1,0,0,1,0,1,0,0,1,0,0,2},
            {3,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,1,0,0,1,0,1,0,1,1,0,0,2},
            {3,0,1,1,1,0,1,1,0,0,1,0,0,1,0,1,1,1,1,1,0,1,0,1,0,1,0,0,0,2},
            {3,0,0,0,1,0,1,0,0,1,1,0,1,1,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,2},
            {3,0,1,1,1,0,0,0,1,1,1,0,0,1,0,1,1,1,0,0,0,1,1,1,0,1,1,1,0,2},
            {3,0,0,0,0,0,0,1,1,1,1,1,0,8,0,0,0,1,1,1,0,1,0,0,0,0,0,1,0,2},
            {6,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6}
        };
    }
    
    public void start() {
        if (!isRunning) {
            isRunning = true;
            lastUpdateTime = System.nanoTime();
            gameLoop.start();
        }
    }
    
    public void stop() {
        isRunning = false;
        gameLoop.stop();
    }
    
    public void pause() {
        isPaused = true;
    }
    
    public void resume() {
        isPaused = false;
    }
    
    private void update(long deltaTime) {
        double deltaSeconds = deltaTime / 1_000_000_000.0;
        
        // Update player
        if (player != null) {
            player.update(deltaSeconds);
        }
        
        // Update enemies
        enemies.forEach(enemy -> enemy.update(deltaSeconds));
        
        // Update other game objects
        bombs.forEach(bomb -> bomb.update(deltaSeconds));
        gifts.forEach(gift -> gift.update(deltaSeconds));
        
        // Check collisions
        checkCollisions();
        
        // Remove dead objects
        cleanupDeadObjects();
    }
    
    private void render() {
        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        // Render background
        renderBackground();
        
        // Render game objects
        walls.forEach(wall -> wall.render(gc));
        gifts.forEach(gift -> gift.render(gc));
        bombs.forEach(bomb -> bomb.render(gc));
        armors.forEach(armor -> armor.render(gc));
        obstacles.forEach(obstacle -> obstacle.render(gc));
        
        if (checkpoint != null) {
            checkpoint.render(gc);
        }
        
        if (endPoint != null) {
            endPoint.render(gc);
        }
        
        enemies.forEach(enemy -> enemy.render(gc));
        
        if (player != null) {
            player.render(gc);
        }
    }
    
    private void renderBackground() {
        // Render maze background
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                int cell = maze[row][col];
                double x = col * 30.0;
                double y = row * 30.0;
                
                switch (cell) {
                    case 0: // Path
                        gc.setFill(javafx.scene.paint.Color.BLACK);
                        break;
                    case 1: // Wall
                        gc.setFill(javafx.scene.paint.Color.GRAY);
                        break;
                    case 2: // Right border
                        gc.setFill(javafx.scene.paint.Color.DARKGRAY);
                        break;
                    case 3: // Left border
                        gc.setFill(javafx.scene.paint.Color.DARKGRAY);
                        break;
                    case 4: // Top border
                        gc.setFill(javafx.scene.paint.Color.DARKGRAY);
                        break;
                    case 5: // Top corner
                        gc.setFill(javafx.scene.paint.Color.DARKGRAY);
                        break;
                    case 6: // Corner
                        gc.setFill(javafx.scene.paint.Color.DARKGRAY);
                        break;
                    default:
                        gc.setFill(javafx.scene.paint.Color.BLACK);
                        break;
                }
                
                gc.fillRect(x, y, 30, 30);
            }
        }
    }
    
    private void checkCollisions() {
        if (player == null) return;
        
        // Check player-enemy collisions
        enemies.forEach(enemy -> {
            if (player.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                player.takeDamage(10);
                if (player.getHealth() <= 0) {
                    gameOver();
                }
            }
        });
        
        // Check player-gift collisions
        gifts.forEach(gift -> {
            if (player.getBoundsInParent().intersects(gift.getBoundsInParent())) {
                player.collectGift(gift);
                gifts.remove(gift);
            }
        });
        
        // Check player-bomb collisions
        bombs.forEach(bomb -> {
            if (player.getBoundsInParent().intersects(bomb.getBoundsInParent())) {
                player.takeDamage(bomb.getDamage());
                bombs.remove(bomb);
                if (player.getHealth() <= 0) {
                    gameOver();
                }
            }
        });
        
        // Check player-armor collisions
        armors.forEach(armor -> {
            if (player.getBoundsInParent().intersects(armor.getBoundsInParent())) {
                player.collectArmor(armor);
                armors.remove(armor);
            }
        });
        
        // Check player-endpoint collision
        if (endPoint != null && player.getBoundsInParent().intersects(endPoint.getBoundsInParent())) {
            gameWon();
        }
    }
    
    private void cleanupDeadObjects() {
        enemies.removeIf(enemy -> !enemy.isAlive());
        bombs.removeIf(bomb -> bomb.isExploded());
    }
    
    private void gameOver() {
        stop();
        // TODO: Show game over screen
    }
    
    private void gameWon() {
        stop();
        // TODO: Show victory screen
    }
    
    // Getters and setters
    public Pane getGamePane() {
        return gamePane;
    }
    
    public Canvas getCanvas() {
        return canvas;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    public void addWall(Wall wall) {
        walls.add(wall);
    }
    
    public void addGift(Gift gift) {
        gifts.add(gift);
    }
    
    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }
    
    public void addArmor(Armor armor) {
        armors.add(armor);
    }
    
    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }
    
    public void setEndPoint(EndPoint endPoint) {
        this.endPoint = endPoint;
    }
    
    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }
    
    public int[][] getMaze() {
        return maze;
    }
}
