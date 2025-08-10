# Maze Runner - Modern Edition

A modernized version of the classic Maze Runner game, rebuilt with JavaFX and modern Java development practices.

## 🚀 What's New

### Modern Architecture
- **JavaFX** instead of Swing for modern UI
- **Maven** build system for better dependency management
- **Java 17** for improved performance and features
- **Modern game loop** with proper frame timing
- **Entity Component System** for better game object management

### Enhanced Features
- **Smooth 60 FPS gameplay** with proper delta time
- **Modern audio system** using JavaFX Media
- **Improved collision detection**
- **Better resource management**
- **Modern logging** with SLF4J and Logback
- **Fullscreen support** and modern window management

## 🛠️ Prerequisites

- **Java 17** or higher
- **Maven 3.6** or higher
- **Git** (for cloning)

## 📦 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Maze-Runner
   ```

2. **Install Java 17+**
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
   - Set `JAVA_HOME` environment variable

3. **Install Maven**
   - Download from [Maven website](https://maven.apache.org/download.cgi)
   - Add to your PATH

## 🎮 Running the Game

### Option 1: Using Maven (Recommended)
```bash
# Compile and run
mvn clean javafx:run

# Or build and run JAR
mvn clean package
java -jar target/maze-runner-2.0.0.jar
```

### Option 2: Using IDE
1. Open the project in your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Import as Maven project
3. Run `MazeRunnerFX` class

## 🎯 Game Controls

| Key | Action |
|-----|--------|
| **WASD** or **Arrow Keys** | Move player |
| **Space** | Shoot |
| **R** | Reload weapon |
| **P** | Pause/Resume |
| **M** | Toggle music |
| **F11** | Toggle fullscreen |
| **ESC** | Pause game |

## 🏗️ Project Structure

```
Maze-Runner/
├── src/
│   └── game/
│       ├── Engine/           # Modern game engine
│       │   └── GameEngine.java
│       ├── Model/            # Game objects
│       │   ├── Player.java   # Modern player class
│       │   ├── Enemy.java
│       │   ├── Weapon.java   # Modern weapon system
│       │   └── ...
│       ├── View/             # UI components
│       │   ├── MazeRunnerFX.java  # Main JavaFX app
│       │   └── ...
│       └── Controller/       # Game logic
├── pom.xml                   # Maven configuration
├── README.md                 # This file
└── gametrack.mp3            # Background music
```

## 🔧 Building from Source

### Development Build
```bash
mvn clean compile
```

### Production Build
```bash
mvn clean package
```

### Run Tests
```bash
mvn test
```

## 📋 Dependencies

### Core Dependencies
- **JavaFX 17.0.2** - Modern UI framework
- **SLF4J 1.7.36** - Logging facade
- **Logback 1.2.11** - Logging implementation

### Test Dependencies
- **JUnit Jupiter 5.8.2** - Unit testing

## 🎨 Customization

### Adding New Weapons
1. Create weapon image in `src/game/View/`
2. Add weapon type in `Weapon.loadWeaponImage()`
3. Configure weapon stats in `MazeRunnerFX.initializeGame()`

### Modifying Game Settings
Edit constants in `MazeRunnerFX.java`:
- `GAME_WIDTH` / `GAME_HEIGHT` - Game window size
- `ENEMY_COUNT` - Number of enemies
- `TARGET_FPS` - Target frame rate

### Changing Maze Layout
Modify the maze array in `GameEngine.initializeMaze()`:
- `0` - Empty path
- `1` - Wall
- `2-6` - Border walls
- `7` - Obstacle
- `8` - Special item

## 🐛 Troubleshooting

### Common Issues

**Java not found**
```bash
# Check Java version
java -version

# Set JAVA_HOME if needed
export JAVA_HOME=/path/to/java17
```

**Maven not found**
```bash
# Check Maven version
mvn -version

# Add Maven to PATH if needed
export PATH=$PATH:/path/to/maven/bin
```

**Audio not working**
- Ensure `gametrack.mp3` is in the project root
- Check system audio settings
- Try running with `-Djavafx.media.unsupported=true`

**Performance issues**
- Reduce `TARGET_FPS` in `GameEngine.java`
- Close other applications
- Update graphics drivers

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Original game creators
- JavaFX team for the modern UI framework
- Maven team for the build system
- Open source community

## 📞 Support

For issues and questions:
1. Check the troubleshooting section
2. Search existing issues
3. Create a new issue with detailed information

---

**Enjoy the modernized Maze Runner experience!** 🎮
