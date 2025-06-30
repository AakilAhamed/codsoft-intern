@echo off
setlocal

:: 📁 Update this path to where your JavaFX SDK is located
set PATH_TO_FX=C:\javafx-sdk-24.0.1\lib

:: 🔧 Compile the JavaFX source file
echo 🛠️ Compiling GradeCalculatorFX.java...
javac --module-path "%PATH_TO_FX%" --add-modules javafx.controls GradeCalculatorFX.java

:: ❌ Stop if compilation fails
if errorlevel 1 (
    echo ❌ Compilation failed.
    pause
    exit /b
)

:: 🚀 Run the JavaFX application
echo 🎯 Launching GradeCalculatorFX...
java --module-path "%PATH_TO_FX%" --add-modules javafx.controls GradeCalculatorFX

pause
