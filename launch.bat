@echo off
REM FocusFlow Launch Script for Windows
REM Automatisiert alle Schritte zum Live-Gehen

setlocal enabledelayedexpansion
SET "GREEN=[92m"
SET "YELLOW=[93m"
SET "RED=[91m"
SET "RESET=[0m"

echo.
echo %YELLOW%🚀 FocusFlow Launch Automation Script%RESET%
echo %YELLOW%========================================%RESET%
echo.

REM Check if gradlew exists
if not exist "gradlew.bat" (
    echo %RED%ERROR: gradlew.bat not found!%RESET%
    echo Please run this from the MyApplication root directory
    pause
    exit /b 1
)

REM Step 1: Check Git
echo %YELLOW%Step 1: Git Repository Setup%RESET%
git status >nul 2>&1
if errorlevel 1 (
    echo Initializing Git...
    git init
    git add .
    git commit -m "FocusFlow Initial Commit"
    echo %GREEN%✓ Git initialized%RESET%
) else (
    echo %GREEN%✓ Git already initialized%RESET%
)

REM Step 2: Build
echo.
echo %YELLOW%Step 2: Building Release Bundle%RESET%
echo This may take a few minutes...
call gradlew.bat bundleRelease

if errorlevel 1 (
    echo %RED%✗ Build failed! Check error messages above%RESET%
    pause
    exit /b 1
)

echo %GREEN%✓ Build successful!%RESET%
echo %GREEN%✓ Bundle location: app\build\outputs\bundle\release\app-release.aab%RESET%

REM Step 3: Done
echo.
echo %YELLOW%Step 3: Launch Information%RESET%
echo.
echo %GREEN%Your app is ready to release!%RESET%
echo.
echo Next steps:
echo   1. Go to: https://play.google.com/console
echo   2. Create new app "FocusFlow - Pomodoro Timer"
echo   3. Upload bundle: app\build\outputs\bundle\release\app-release.aab
echo   4. Fill in app details (see DEPLOYMENT_GUIDE.md)
echo   5. Submit for review!
echo.
echo %GREEN%Estimated time to live: 1-2 days!%RESET%
echo.

pause

