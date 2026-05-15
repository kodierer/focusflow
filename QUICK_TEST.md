# FocusFlow - Quick Start Testing Guide

## 🚀 Start Testing in 30 Seconds

### Prerequisites
- Android Studio installed
- JDK 21 configured
- Android SDK (API 36+)
- Pixel_10 emulator available

---

## ⚡ Quick Start Commands

### 1. Build & Deploy to Emulator
```powershell
cd C:\Users\fmoss\AndroidStudioProjects\MyApplication

# Start emulator in background
$env:ANDROID_HOME = "C:\Users\fmoss\AppData\Local\Android\Sdk"
& "$env:ANDROID_HOME\emulator\emulator" -avd Pixel_10 -no-window -no-audio &

# Wait a few seconds for emulator to boot
Start-Sleep -Seconds 10

# Build and install
.\gradlew assembleDebug --no-daemon

# Install on device
$env:ANDROID_HOME\platform-tools\adb install -r app\build\outputs\apk\debug\app-debug.apk

# Launch app
$env:ANDROID_HOME\platform-tools\adb shell am start -n com.example.myapplication/.MainActivity
```

### 2. One-Liner for Full Build & Deploy
```powershell
cd C:\Users\fmoss\AndroidStudioProjects\MyApplication; .\gradlew assembleDebug -x test --no-daemon; $env:ANDROID_HOME = "C:\Users\fmoss\AppData\Local\Android\Sdk"; & "$env:ANDROID_HOME\platform-tools\adb" install -r app\build\outputs\apk\debug\app-debug.apk; & "$env:ANDROID_HOME\platform-tools\adb" shell am start -n com.example.myapplication/.MainActivity
```

---

## 🧪 Testing Checklist

### Timer Display
- [ ] App shows "25:00" for focus session
- [ ] Timer display is clearly visible
- [ ] Text is white on dark background

### Timer Controls
- [ ] FAB (white button) is visible at bottom-right
- [ ] Clicking FAB starts countdown
- [ ] Timer decreases by 1 second each second
- [ ] Display updates in real-time

### Timer Pause/Resume
- [ ] Clicking FAB again pauses timer
- [ ] Icon changes from "Play" to "Pause"
- [ ] Clicking again resumes from paused time

### Timer Reset
- [ ] Reset button (circular arrow) resets to 25:00
- [ ] Resets only when timer is paused
- [ ] Countdown resumes from 25:00

### Session Switch
- [ ] After focus session completes (25:00 → 0:00), switches to break
- [ ] Break shows "5:00"
- [ ] Timer auto-starts after session switch
- [ ] Can pause manually

### Settings Screen
- [ ] Navigation drawer opens on swipe/menu
- [ ] Settings option available
- [ ] Can adjust timer durations
- [ ] Changes persist on app restart

---

## 📊 Performance Metrics

### Expected Timings
| Operation | Time |
|-----------|------|
| Build | 14-20s (local) |
| Install | 2-5s |
| App Launch | 3-5s |
| Timer Start | <100ms |

### Memory Usage
- Debug Build Size: ~18.7 MB
- Runtime RAM: ~60-100 MB

---

## 🔍 View Logs

### Real-time Logs
```powershell
$env:ANDROID_HOME = "C:\Users\fmoss\AppData\Local\Android\Sdk"
& "$env:ANDROID_HOME\platform-tools\adb" logcat
```

### App-specific Logs
```powershell
$env:ANDROID_HOME\platform-tools\adb logcat | findstr "myapplication"
```

### Filter by Component
```powershell
$env:ANDROID_HOME\platform-tools\adb logcat | findstr "MainActivity\|TimerViewModel\|FocusTimerScreen"
```

---

## 📱 Device Information

```powershell
# List connected devices
$env:ANDROID_HOME\platform-tools\adb devices

# Device properties
$env:ANDROID_HOME\platform-tools\adb shell getprop ro.build.version.release
$env:ANDROID_HOME\platform-tools\adb shell getprop ro.build.version.sdk
```

---

## 🛠️ Troubleshooting

### Device Not Connected
```powershell
# Restart ADB
$env:ANDROID_HOME\platform-tools\adb kill-server
$env:ANDROID_HOME\platform-tools\adb devices
```

### Build Fails
```powershell
# Clean build
.\gradlew clean assembleDebug --no-daemon

# Reset gradle cache
Remove-Item .gradle -Recurse -Force
```

### App Won't Install
```powershell
# Uninstall first
$env:ANDROID_HOME\platform-tools\adb uninstall com.example.myapplication

# Then install
$env:ANDROID_HOME\platform-tools\adb install app\build\outputs\apk\debug\app-debug.apk
```

### App Crashes on Launch
```powershell
# Check logs
$env:ANDROID_HOME\platform-tools\adb logcat -d | grep "FATAL\|Exception\|Error" | tail -20
```

---

## 📸 Taking Screenshots

### Screenshot to File
```powershell
$env:ANDROID_HOME = "C:\Users\fmoss\AppData\Local\Android\Sdk"
& "$env:ANDROID_HOME\platform-tools\adb" shell screencap -p /sdcard/screenshot.png
& "$env:ANDROID_HOME\platform-tools\adb" pull /sdcard/screenshot.png "C:\Users\fmoss\Desktop\screenshot.png"
```

---

## 🌐 GitHub Actions Testing

### Trigger Workflow
1. Push code: `git push origin main`
2. Go to: `https://github.com/kodierer/focusflow/actions`
3. Find "Build FocusFlow APK" workflow
4. Check status and logs
5. Download APK from Artifacts (if successful)

### Expected Success Indicators
- ✅ "BUILD SUCCESSFUL" in logs
- ✅ APK size ~18MB
- ✅ Artifact "app-debug-apk" available
- ✅ Green checkmark on workflow

---

## 💾 File Cleanup

### Remove Build Artifacts
```powershell
.\gradlew clean
```

### Clear Android Cache
```powershell
Remove-Item -Path "C:\Users\fmoss\.android\*" -Recurse -Force
```

---

## 📞 Support Resources

- **Build Issues**: Check `gradle.properties` for memory settings
- **App Crashes**: View `adb logcat` output
- **Deployment Issues**: Review `DEPLOYMENT_READY.md`
- **Feature Questions**: See `APP_READY.md`

---

**Last Updated**: May 15, 2026  
**Build System**: Gradle 9.4.1 + JDK 21  
**Target SDK**: Android 15 (API 36)

