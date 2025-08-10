@echo off
echo ========================================
echo    Maze Runner - Modern Edition
echo ========================================
echo.

echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo.
    echo Please install Java 17 or higher from:
    echo https://adoptium.net/
    echo.
    pause
    exit /b 1
)

echo Java found! Checking version...
java -version

echo.
echo Checking Maven installation...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo WARNING: Maven is not installed
    echo.
    echo To install Maven:
    echo 1. Download from https://maven.apache.org/download.cgi
    echo 2. Extract to a folder (e.g., C:\maven)
    echo 3. Add C:\maven\bin to your PATH
    echo.
    echo For now, you can run the game using an IDE:
    echo - IntelliJ IDEA: Import as Maven project
    echo - Eclipse: Import as Maven project
    echo - VS Code: Install Java extension pack
    echo.
    echo Or compile manually:
    echo javac -cp "path/to/javafx-sdk/lib/*" src/game/View/MazeRunnerFX.java
    echo.
    pause
    exit /b 1
)

echo Maven found! Building project...
echo.

echo Running: mvn clean javafx:run
mvn clean javafx:run

if %errorlevel% neq 0 (
    echo.
    echo Build failed. Please check the error messages above.
    echo.
    echo Common solutions:
    echo 1. Install Java 17 or higher
    echo 2. Install Maven 3.6 or higher
    echo 3. Check internet connection for dependencies
    echo.
    pause
    exit /b 1
)

echo.
echo Game completed successfully!
pause
