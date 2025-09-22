@echo off
echo ========================================
echo    FitIntent Simple Build Script
echo ========================================
echo.

echo Checking for Java...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java JDK 11 or later from:
    echo https://adoptium.net/
    pause
    exit /b 1
)

echo.
echo Downloading Gradle wrapper...
if not exist "gradle\wrapper\gradle-wrapper.jar" (
    echo Downloading gradle-wrapper.jar...
    curl -L -o gradle\wrapper\gradle-wrapper.jar https://github.com/gradle/gradle/raw/v8.0.0/gradle/wrapper/gradle-wrapper.jar
    if %errorlevel% neq 0 (
        echo Failed to download Gradle wrapper
        echo Please check your internet connection
        pause
        exit /b 1
    )
)

echo.
echo Building FitIntent APK...
gradlew.bat assembleDebug --info --stacktrace

if %errorlevel% equ 0 (
    echo.
    echo ✅ BUILD SUCCESSFUL!
    echo.
    echo APK Location: app\build\outputs\apk\debug\app-debug.apk
    echo File size:
    dir app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo 🚀 Ready for testing!
    echo.
    echo Testing options:
    echo 1. Online Emulator: https://appetize.io
    echo 2. Upload APK file from: %cd%\app\build\outputs\apk\debug\app-debug.apk
    echo.
    set /p open="Open Appetize.io for testing? (y/n): "
    if /i "%open%"=="y" start https://appetize.io
) else (
    echo.
    echo ❌ BUILD FAILED!
    echo Please check the error messages above.
)

echo.
pause