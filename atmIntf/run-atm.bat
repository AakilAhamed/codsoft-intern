@echo off
setlocal

:: Set JavaFX SDK path
set PATH_TO_FX=C:\javafx-sdk-24.0.1\lib

:: Compile ATMAppFX.java
echo Compiling ATMAppFX.java...
javac --module-path "%PATH_TO_FX%" --add-modules javafx.controls ATMAppFX.java

:: Exit if compilation fails
if errorlevel 1 (
    echo Compilation failed. Please check your code.
    pause
    exit /b
)

:: Run the JavaFX application
echo Running ATMAppFX...
java --module-path "%PATH_TO_FX%" --add-modules javafx.controls ATMAppFX

pause
