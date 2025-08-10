@echo off
echo ========================================
echo    Maze Runner - Setup Script
echo ========================================
echo.

echo This script will help you set up the development environment
echo for the modernized Maze Runner game.
echo.

echo Checking current Java installation...
java -version >nul 2>&1
if %errorlevel% equ 0 (
    echo ✓ Java is installed
    java -version
) else (
    echo ✗ Java is not installed
    echo.
    echo Please install Java 17 or higher:
    echo 1. Go to https://adoptium.net/
    echo 2. Download Eclipse Temurin JDK 17
    echo 3. Run the installer
    echo 4. Restart this script after installation
    echo.
    pause
    exit /b 1
)

echo.
echo Checking Maven installation...
mvn -version >nul 2>&1
if %errorlevel% equ 0 (
    echo ✓ Maven is installed
    mvn -version
) else (
    echo ✗ Maven is not installed
    echo.
    echo Installing Maven...
    echo 1. Downloading Maven...
    
    REM Try to download Maven using PowerShell
    powershell -Command "& {Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip' -OutFile 'maven.zip'}"
    
    if exist maven.zip (
        echo 2. Extracting Maven...
        powershell -Command "& {Expand-Archive -Path 'maven.zip' -DestinationPath '.' -Force}"
        del maven.zip
        
        echo 3. Setting up environment...
        setx MAVEN_HOME "%CD%\apache-maven-3.9.5"
        setx PATH "%PATH%;%CD%\apache-maven-3.9.5\bin"
        
        echo ✓ Maven installed successfully!
        echo Please restart your command prompt and run this script again.
        echo.
        pause
        exit /b 0
    ) else (
        echo ✗ Failed to download Maven
        echo.
        echo Please install Maven manually:
        echo 1. Go to https://maven.apache.org/download.cgi
        echo 2. Download apache-maven-3.9.5-bin.zip
        echo 3. Extract to C:\maven
        echo 4. Add C:\maven\bin to your PATH
        echo.
        pause
        exit /b 1
    )
)

echo.
echo ✓ All dependencies are installed!
echo.
echo You can now run the game using:
echo   run-game.bat
echo.
echo Or build the project using:
echo   mvn clean package
echo.
echo Enjoy the modernized Maze Runner!
echo.
pause
