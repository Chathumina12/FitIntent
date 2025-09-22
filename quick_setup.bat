@echo off
echo ========================================
echo    FitIntent Quick Setup Guide
echo ========================================
echo.

echo Checking requirements...
echo.

echo 1. Checking Java...
java -version > nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Java is installed
    java -version
) else (
    echo ❌ Java is NOT installed
    echo Please install Java JDK 11+ from: https://adoptium.net/
    echo Then restart this script.
    pause
    exit /b 1
)

echo.
echo 2. Checking Android SDK...
if exist "%USERPROFILE%\AppData\Local\Android\Sdk" (
    echo ✅ Android SDK found
    echo Updating local.properties...
    echo sdk.dir=%USERPROFILE%\AppData\Local\Android\Sdk > local.properties
) else (
    echo ❌ Android SDK is NOT installed
    echo.
    echo REQUIRED: Download Android Studio for SDK
    echo 1. Go to: https://developer.android.com/studio
    echo 2. Download and install Android Studio
    echo 3. Open it and download SDK components
    echo 4. Run this script again
    echo.
    set /p open="Open download page now? (y/n): "
    if /i "%open%"=="y" start https://developer.android.com/studio
    pause
    exit /b 1
)

echo.
echo 3. Building FitIntent APK...
echo This may take 5-10 minutes on first build...
echo.

gradlew.bat assembleDebug

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo    ✅ SUCCESS! APK BUILT SUCCESSFULLY!
    echo ========================================
    echo.
    echo APK Location: app\build\outputs\apk\debug\app-debug.apk
    echo File size:
    dir app\build\outputs\apk\debug\app-debug.apk | findstr app-debug.apk
    echo.
    echo 🚀 READY FOR TESTING!
    echo.
    echo Choose your testing method:
    echo 1. Online Emulator (Appetize.io)
    echo 2. Android Studio Emulator
    echo 3. Transfer APK to phone
    echo.
    set /p choice="Enter choice (1/2/3): "

    if "%choice%"=="1" (
        echo Opening Appetize.io...
        start https://appetize.io
        echo Upload this file: %cd%\app\build\outputs\apk\debug\app-debug.apk
    ) else if "%choice%"=="2" (
        echo Starting Android Studio for emulator...
        start "" "%USERPROFILE%\AppData\Local\Android\Sdk\..\..\Android Studio\bin\studio64.exe"
        echo 1. Create/Start a virtual device
        echo 2. Then install APK: adb install app\build\outputs\apk\debug\app-debug.apk
    ) else if "%choice%"=="3" (
        echo APK ready for transfer: app\build\outputs\apk\debug\app-debug.apk
        echo You can email this file or transfer via USB
    )

) else (
    echo.
    echo ❌ BUILD FAILED!
    echo Check the error messages above.
    echo Make sure Android SDK is properly installed.
)

echo.
pause