@echo off
echo ========================================
echo    FitIntent App Testing Script
echo ========================================
echo.

echo Step 1: Building APK...
call gradlew.bat assembleDebug
if %errorlevel% neq 0 (
    echo BUILD FAILED!
    pause
    exit /b 1
)

echo.
echo ✅ BUILD SUCCESSFUL!
echo.
echo APK Location: app\build\outputs\apk\debug\app-debug.apk
echo.

echo Options for testing:
echo 1. Install on connected device/emulator (if available)
echo 2. Use online emulator (Appetize.io)
echo 3. Manual APK transfer
echo.

set /p choice="Choose option (1/2/3): "

if "%choice%"=="1" (
    echo Checking for connected devices...
    adb devices
    echo Installing APK...
    call gradlew.bat installDebug
    if %errorlevel% equ 0 (
        echo ✅ APP INSTALLED SUCCESSFULLY!
        echo Starting app...
        adb shell am start -n com.campus.fitintent/.activities.SplashActivity
    ) else (
        echo ❌ Installation failed. Make sure emulator/device is connected.
    )
) else if "%choice%"=="2" (
    echo Opening Appetize.io for online testing...
    start https://appetize.io
    echo Upload the APK file: app\build\outputs\apk\debug\app-debug.apk
) else if "%choice%"=="3" (
    echo APK built successfully!
    echo Location: %cd%\app\build\outputs\apk\debug\app-debug.apk
    echo You can now:
    echo - Email this file to yourself
    echo - Upload to cloud drive
    echo - Use online emulator
)

echo.
pause