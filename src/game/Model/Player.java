package game.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Modern Player class using JavaFX
 */
public class Player extends Rectangle {
    
    private double x, y;
    private double velocityX = 0;
    private double velocityY = 0;
    private double speed = 200.0; // pixels per second
    private int health = 100;
    private int maxHealth = 100;
    private int score = 0;
    private boolean hasArmor = false;
    private boolean isAlive = true;
    
    private Image playerImage;
    private double width = 30;
    private double height = 30;
    
    // Movement state
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    
    // Inventory
    private List<Weapon> weapons = new ArrayList<>();
    private int currentWeaponIndex = 0;
    
    public Player(double x, double y) {
        super(x, y, 30, 30);
        this.x = x;
        this.y = y;
        
        // Set up the rectangle properties
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setFill(Color.BLUE);
        setStroke(Color.WHITE);
        setStrokeWidth(2);
        
        // Load player image
        try {
            playerImage = new Image(getClass().getResourceAsStream("/game/View/player1.png"));
        } catch (Exception e) {
            System.err.println("Could not load player image: " + e.getMessage());
        }
    }
    
    public void update(double deltaTime) {
        if (!isAlive) return;
        
        // Update velocity based on input
        velocityX = 0;
        velocityY = 0;
        
        if (movingUp) velocityY = -speed;
        if (movingDown) velocityY = speed;
        if (movingLeft) velocityX = -speed;
        if (movingRight) velocityX = speed;
        
        // Normalize diagonal movement
        if (velocityX != 0 && velocityY != 0) {
            velocityX *= 0.707; // 1/sqrt(2)
            velocityY *= 0.707;
        }
        
        // Update position
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
        
        // Update rectangle position
        setX(x);
        setY(y);
        
        // Keep player in bounds (assuming 900x690 game area)
        if (x < 0) {
            x = 0;
            setX(x);
        }
        if (x > 870) {
            x = 870;
            setX(x);
        }
        if (y < 0) {
            y = 0;
            setY(y);
        }
        if (y > 660) {
            y = 660;
            setY(y);
        }
    }
    
    public void render(GraphicsContext gc) {
        if (!isAlive) return;
        
        if (playerImage != null) {
            gc.drawImage(playerImage, x, y, width, height);
        } else {
            // Fallback to rectangle drawing
            gc.setFill(Color.BLUE);
            gc.fillRect(x, y, width, height);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, width, height);
        }
        
        // Draw health bar
        drawHealthBar(gc);
    }
    
    private void drawHealthBar(GraphicsContext gc) {
        double barWidth = 30;
        double barHeight = 5;
        double barX = x;
        double barY = y - 10;
        
        // Background
        gc.setFill(Color.RED);
        gc.fillRect(barX, barY, barWidth, barHeight);
        
        // Health
        double healthPercent = (double) health / maxHealth;
        gc.setFill(Color.GREEN);
        gc.fillRect(barX, barY, barWidth * healthPercent, barHeight);
        
        // Border
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeRect(barX, barY, barWidth, barHeight);
    }
    
    public void handleKeyPress(KeyCode code) {
        switch (code) {
            case W:
            case UP:
                movingUp = true;
                break;
            case S:
            case DOWN:
                movingDown = true;
                break;
            case A:
            case LEFT:
                movingLeft = true;
                break;
            case D:
            case RIGHT:
                movingRight = true;
                break;
            case SPACE:
                shoot();
                break;
            case R:
                reload();
                break;
        }
    }
    
    public void handleKeyRelease(KeyCode code) {
        switch (code) {
            case W:
            case UP:
                movingUp = false;
                break;
            case S:
            case DOWN:
                movingDown = false;
                break;
            case A:
            case LEFT:
                movingLeft = false;
                break;
            case D:
            case RIGHT:
                movingRight = false;
                break;
        }
    }
    
    public void shoot() {
        if (!weapons.isEmpty() && currentWeaponIndex < weapons.size()) {
            Weapon weapon = weapons.get(currentWeaponIndex);
            if (weapon.canShoot()) {
                weapon.shoot();
                // TODO: Create bullet projectile
            }
        }
    }
    
    public void reload() {
        if (!weapons.isEmpty() && currentWeaponIndex < weapons.size()) {
            weapons.get(currentWeaponIndex).reload();
        }
    }
    
    public void takeDamage(int damage) {
        if (hasArmor) {
            damage = damage / 2; // Armor reduces damage by half
            hasArmor = false; // Armor breaks after one hit
        }
        
        health -= damage;
        if (health <= 0) {
            health = 0;
            isAlive = false;
        }
    }
    
    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }
    
    public void collectGift(Gift gift) {
        score += gift.getPoints();
        // TODO: Apply gift effects
    }
    
    public void collectArmor(Armor armor) {
        hasArmor = true;
        // TODO: Apply armor effects
    }
    
    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }
    
    public void switchWeapon(int index) {
        if (index >= 0 && index < weapons.size()) {
            currentWeaponIndex = index;
        }
    }
    
    // Getters and setters
    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public void setHealth(int health) { this.health = health; }
    
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    
    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) { isAlive = alive; }
    
    public boolean hasArmor() { return hasArmor; }
    public void setHasArmor(boolean hasArmor) { this.hasArmor = hasArmor; }
    
    public List<Weapon> getWeapons() { return weapons; }
    public Weapon getCurrentWeapon() { 
        return weapons.isEmpty() ? null : weapons.get(currentWeaponIndex); 
    }
    
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
}
