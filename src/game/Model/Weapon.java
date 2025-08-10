
package game.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Modern Weapon class using JavaFX
 */
public class Weapon {
    
    private String name;
    private int damage;
    private int maxAmmo;
    private int currentAmmo;
    private double fireRate; // shots per second
    private double lastShotTime;
    private boolean isReloading = false;
    private double reloadTime = 2.0; // seconds
    private double reloadStartTime;
    
    private Image weaponImage;
    private double x, y;
    private double width = 30;
    private double height = 30;
    
    public Weapon(String name, int damage, int maxAmmo, double fireRate) {
        this.name = name;
        this.damage = damage;
        this.maxAmmo = maxAmmo;
        this.currentAmmo = maxAmmo;
        this.fireRate = fireRate;
        this.lastShotTime = 0;
        
        // Load weapon image based on name
        loadWeaponImage();
    }
    
    private void loadWeaponImage() {
        try {
            switch (name.toLowerCase()) {
                case "pistol":
                    weaponImage = new Image(getClass().getResourceAsStream("/game/View/Bullet1.png"));
                    break;
                case "rifle":
                    weaponImage = new Image(getClass().getResourceAsStream("/game/View/Bullet1.png"));
                    break;
                case "shotgun":
                    weaponImage = new Image(getClass().getResourceAsStream("/game/View/Bullet1.png"));
                    break;
                default:
                    weaponImage = new Image(getClass().getResourceAsStream("/game/View/Bullet1.png"));
                    break;
            }
        } catch (Exception e) {
            System.err.println("Could not load weapon image for " + name + ": " + e.getMessage());
        }
    }
    
    public boolean canShoot() {
        if (isReloading) return false;
        if (currentAmmo <= 0) return false;
        
        double currentTime = System.currentTimeMillis() / 1000.0;
        return (currentTime - lastShotTime) >= (1.0 / fireRate);
    }
    
    public void shoot() {
        if (!canShoot()) return;
        
        currentAmmo--;
        lastShotTime = System.currentTimeMillis() / 1000.0;
        
        // TODO: Create bullet projectile
        // This would be handled by the game engine
    }
    
    public void reload() {
        if (isReloading || currentAmmo == maxAmmo) return;
        
        isReloading = true;
        reloadStartTime = System.currentTimeMillis() / 1000.0;
    }
    
    public void update(double deltaTime) {
        if (isReloading) {
            double currentTime = System.currentTimeMillis() / 1000.0;
            if (currentTime - reloadStartTime >= reloadTime) {
                currentAmmo = maxAmmo;
                isReloading = false;
            }
        }
    }
    
    public void render(GraphicsContext gc) {
        if (weaponImage != null) {
            gc.drawImage(weaponImage, x, y, width, height);
        } else {
            // Fallback to rectangle drawing
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(x, y, width, height);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1);
            gc.strokeRect(x, y, width, height);
        }
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    
    public int getMaxAmmo() { return maxAmmo; }
    public void setMaxAmmo(int maxAmmo) { this.maxAmmo = maxAmmo; }
    
    public int getCurrentAmmo() { return currentAmmo; }
    public void setCurrentAmmo(int currentAmmo) { this.currentAmmo = currentAmmo; }
    
    public double getFireRate() { return fireRate; }
    public void setFireRate(double fireRate) { this.fireRate = fireRate; }
    
    public boolean isReloading() { return isReloading; }
    public void setReloading(boolean reloading) { isReloading = reloading; }
    
    public double getReloadTime() { return reloadTime; }
    public void setReloadTime(double reloadTime) { this.reloadTime = reloadTime; }
    
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    
    public Image getWeaponImage() { return weaponImage; }
    public void setWeaponImage(Image weaponImage) { this.weaponImage = weaponImage; }
    
    public String getAmmoStatus() {
        if (isReloading) {
            return "Reloading...";
        }
        return currentAmmo + "/" + maxAmmo;
    }
}