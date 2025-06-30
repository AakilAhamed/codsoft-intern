@echo off
setlocal

:: Set JavaFX SDK path
set PATH_TO_FX=C:\javafx-sdk-24.0.1\lib

:: Compile the JavaFX game
echo Compiling GuessGameFX.java...
javac --module-path "%PATH_TO_FX%" --add-modules javafx.controls GuessGameFX.java

:: Check if compilation succeeded
if errorlevel 1 (
    echo ❌ Compilation failed.
    pause
    exit /b
)

:: Run the game
echo Running GuessGameFX...
java --module-path "%PATH_TO_FX%" --add-modules javafx.controls GuessGameFX

pause
