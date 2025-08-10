# Migration Guide: From Swing to JavaFX

This guide explains the modernization changes made to transform the original Swing-based Maze Runner game into a modern JavaFX application.

## 🔄 What Changed

### 1. **Build System Migration**
**Before:** Ant build system with NetBeans project structure
```xml
<!-- Old: build.xml -->
<project name="game" default="default" basedir=".">
    <import file="nbproject/build-impl.xml"/>
</project>
```

**After:** Maven build system with modern dependency management
```xml
<!-- New: pom.xml -->
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>17.0.2</version>
        </dependency>
    </dependencies>
</project>
```

### 2. **UI Framework Migration**
**Before:** Swing/AWT components
```java
// Old: Swing-based main class
public class MazeRunner {
    public static void main(String args[]) {
        Welcome w = new Welcome();
        w.setVisible(true);
    }
}
```

**After:** JavaFX Application
```java
// New: JavaFX-based main class
public class MazeRunnerFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(gameEngine.getGamePane(), 900, 690);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
```

### 3. **Game Engine Modernization**
**Before:** Timer-based game loop
```java
// Old: Swing Timer
Timer mainTimer = new Timer(10, this);
mainTimer.start();

@Override
public void actionPerformed(ActionEvent e) {
    // Game update logic
}
```

**After:** Modern game loop with delta time
```java
// New: JavaFX AnimationTimer
private void initializeGameLoop() {
    gameLoop = new AnimationTimer() {
        @Override
        public void handle(long now) {
            long deltaTime = now - lastUpdateTime;
            if (deltaTime >= TARGET_FRAME_TIME) {
                update(deltaTime);
                render();
                lastUpdateTime = now;
            }
        }
    };
}
```

### 4. **Audio System Migration**
**Before:** javazoom library
```java
// Old: javazoom audio
import javazoom.jl.player.Player;
FileInputStream fl = new FileInputStream(sound);
Player player = new Player(fl);
player.play();
```

**After:** JavaFX Media
```java
// New: JavaFX Media
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

Media media = new Media(musicFile.toURI().toString());
MediaPlayer backgroundMusic = new MediaPlayer(media);
backgroundMusic.play();
```

## 📁 File Structure Changes

### New Files Created
```
src/game/Engine/GameEngine.java          # Modern game engine
src/game/Model/Player.java               # Modern player class
src/game/View/MazeRunnerFX.java          # JavaFX main application
pom.xml                                  # Maven configuration
README.md                                # Modern documentation
.gitignore                               # Version control exclusions
run-game.bat                             # Windows launcher
setup.bat                                # Setup script
MIGRATION_GUIDE.md                       # This guide
```

### Files Modified
```
src/game/Model/Weapon.java               # Updated for JavaFX
```

### Files Kept (for reference)
```
src/game/View/MazeRunner.java            # Original Swing version
build.xml                                # Original Ant build
nbproject/                               # Original NetBeans config
```

## 🚀 How to Use the Modernized Version

### Option 1: Using Maven (Recommended)
```bash
# Install dependencies and run
mvn clean javafx:run

# Build executable JAR
mvn clean package
java -jar target/maze-runner-2.0.0.jar
```

### Option 2: Using IDE
1. Import as Maven project
2. Run `MazeRunnerFX` class
3. Enjoy modern gameplay!

### Option 3: Using provided scripts
```bash
# Windows
setup.bat      # Install dependencies
run-game.bat   # Run the game
```

## 🔧 Key Improvements

### Performance
- **60 FPS** consistent frame rate
- **Delta time** based updates for smooth movement
- **Efficient rendering** with JavaFX Canvas
- **Better memory management**

### User Experience
- **Modern UI** with JavaFX styling
- **Fullscreen support** (F11)
- **Music controls** (M key)
- **Pause functionality** (P key)
- **Smooth animations**

### Development Experience
- **Maven dependency management**
- **Modern logging** with SLF4J
- **Better project structure**
- **IDE-friendly** configuration
- **Version control** ready

### Code Quality
- **Separation of concerns** (Engine/Model/View)
- **Thread-safe** collections
- **Proper resource management**
- **Modern Java features** (lambdas, streams)

## 🔄 Migration Steps for Developers

### 1. **Update Development Environment**
```bash
# Install Java 17+
# Install Maven 3.6+
# Install modern IDE (IntelliJ IDEA, Eclipse, VS Code)
```

### 2. **Import Project**
```bash
# Clone repository
git clone <repository-url>
cd Maze-Runner

# Import as Maven project in IDE
# Or use command line
mvn clean compile
```

### 3. **Run Modern Version**
```bash
# Run with Maven
mvn javafx:run

# Or run in IDE
# Right-click MazeRunnerFX.java → Run
```

### 4. **Customize Game**
- Modify `GameEngine.java` for game mechanics
- Update `Player.java` for player behavior
- Edit `MazeRunnerFX.java` for UI changes
- Add new weapons in `Weapon.java`

## 🐛 Troubleshooting Migration

### Common Issues

**"JavaFX not found"**
```bash
# Solution: Maven handles JavaFX dependencies
mvn clean compile
```

**"Old Swing code not working"**
```bash
# Solution: Use new JavaFX classes
# Replace Swing imports with JavaFX equivalents
```

**"Audio not playing"**
```bash
# Solution: Check gametrack.mp3 exists
# Use JavaFX Media instead of javazoom
```

**"Build errors"**
```bash
# Solution: Ensure Java 17+ and Maven 3.6+
java -version
mvn -version
```

## 📚 Learning Resources

- [JavaFX Documentation](https://openjfx.io/)
- [Maven Guide](https://maven.apache.org/guides/)
- [Java 17 Features](https://openjdk.java.net/projects/jdk/17/)
- [Game Development Patterns](https://gameprogrammingpatterns.com/)

## 🤝 Contributing to Modernization

1. **Fork the repository**
2. **Create feature branch**
3. **Follow modern Java conventions**
4. **Add tests for new features**
5. **Update documentation**
6. **Submit pull request**

---

**The modernized Maze Runner is ready for the future!** 🎮✨
