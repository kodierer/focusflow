# FocusFlow - Setup & Deployment Guide

##  Schnellee Übersicht

**FocusFlow** ist eine moderne Pomodoro-Timer App für Android mit diesen Highlights:

- ⏱️ Flexible Pomodoro-Timer (25/5 min, einstellbar)
-  Echtzeit-Statistiken & Session-Tracking
-  Wunderschönes Material 3 UI (Blau für Work, Grün für Break)
-  Lokale Datenspeicherung
- ⚡ Minimale Abhängigkeiten, Super schnell

---

##  Installation & Build

### Systemvoraussetzungen

```
✓ Java JDK 11 oder höher
✓ Android Studio 2024.2+
✓ Android SDK API 36
✓ Gradle 8.x+
✓ Windows 10/11 oder macOS/Linux
```

### Schritt-für-Schritt Setup

#### 1. **Java installieren** (falls nicht vorhanden)
```bash
# Windows: https://www.oracle.com/java/technologies/downloads/
# macOS: brew install java11
# Linux: sudo apt-get install openjdk-11-jdk
```

Dann JAVA_HOME setzen:
```bash
# Windows (PowerShell als Admin):
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-11", "Machine")

# macOS/Linux:
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
```

#### 2. **Android Studio öffnen**
```
File → Open → MyApplication (den Ordner auswählen)
Android Studio wird automatisch alle Dependencies downloaden
```

#### 3. **Projekt bauen**
```bash
# Via Android Studio: Build → Make Project
# Via Terminal:
cd MyApplication
./gradlew build
```

#### 4. **Auf Emulator/Gerät installieren**

**Emulator** (bevorzugt zum Testen):
- Android Studio: Tools → Device Manager → Create Virtual Device
- Wähle ein API 26+ Gerät
- Starten
- Run → Run 'app'

**Echtes Gerät**:
- USB-Debugging in Entwickleroptionen aktivieren
- Gerät anschließen
- Run → Run 'app'
- Gerät auswählen

---

##  Projektstruktur

```
MyApplication/
├── app/
│   ├── build.gradle.kts          # Dependencies & Build-Konfiguration
│   ├── src/
│   │   └── main/
│   │       ├── AndroidManifest.xml
│   │       ├── java/com/example/myapplication/
│   │       │   ├── MainActivity.kt           # App-Entry Point
│   │       │   ├── TimerViewModel.kt         # Timer-Logik & State
│   │       │   ├── ui/
│   │       │   │   ├── screens/
│   │       │   │   │   └── FocusTimerScreen.kt  # Komplette UI
│   │       │   │   └── theme/
│   │       │   │       ├── Color.kt
│   │       │   │       ├── Theme.kt
│   │       │   │       └── Type.kt
│   │       │   ├── utils/
│   │       │   │   └── HapticFeedback.kt    # Vibration-Effekte
│   │       │   └── data/
│   │       │       └── SessionRepository.kt  # Datenspeicherung
│   │       └── res/                          # Ressourcen (Icons, Strings, etc)
│   └── proguard-rules.pro
├── gradle/
│   └── libs.versions.toml         # Dependency-Versionen
├── build.gradle.kts               # Root Build-Config
├── settings.gradle.kts
├── gradle.properties
└── gradlew / gradlew.bat          # Gradle Wrapper
```

---

##  App-Nutzung

### Hauptbildschirm
1. **Timer anzeigen**: Große Uhr mit MM:SS Format
2. **Play/Pause**: Starte oder pausiere den Timer
3. **Reset**: Setzt den Timer auf die ursprüngliche Zeit
4. **Einstellungen**: Passe Arbeits- und Pausenzeit an
5. **Statistiken**: Sehe deine Sessions, Fokus-Zeit und Tagesstatistiken

### Workflow
```
1. "Start" drücken
2. Arbeite 25 Minuten (oder deine eingestellte Zeit)
3. Timer wird automatisch zur Pause
4. Gönne dir 5 Minuten Pause
5. Wiederhole!
6. Deine Statistiken aktualisieren sich in Echtzeit
```

---

##  Development Tipps

### Hot Reload aktivieren (Android Studio)
1. Run → Edit Configurations
2. "On build and deployment" → "Update app code and resources"
3. Jetzt: Build & Run = schneller Reload

### UI Preview (ohne Emulator)
```kotlin
// FocusTimerScreen.kt unten hinzufügen:
@Preview(showBackground = true)
@Composable
fun FocusTimerScreenPreview() {
    MyApplicationTheme {
        FocusTimerScreen(viewModel = TimerViewModel())
    }
}
```
Dann im Code-Editor: Split View (oben rechts) → Preview

### Debugging
```kotlin
// Logcat in Android Studio: View → Tool Windows → Logcat
// Interessante Logs schauen mit:
Log.d("FocusTimer", "Timer started")
```

---

## ⚙️ Konfiguration

### Timer-Einstellungen ändern (Default-Werte)
**File**: `TimerViewModel.kt`
```kotlin
data class TimerState(
    val workMinutes: Int = 25,      // ← Hier ändern
    val breakMinutes: Int = 5,      // ← Hier ändern
    // ...
)
```

### App-Name ändern
**File**: `res/values/strings.xml`
```xml
<string name="app_name">FocusFlow - Timer</string>  <!-- Hier ändern -->
```

### Farben anpassen
**File**: `ui/screens/FocusTimerScreen.kt`
```kotlin
val backgroundColor by animateColorAsState(
    targetValue = if (state.isWorkSession) {
        Color(0xFF1E88E5)  // Blau (hex) ← Hier ändern
    } else {
        Color(0xFF43A047)  // Grün (hex) ← Hier ändern
    },
    // ...
)
```

---

##  Deployment zum Google Play Store

### Pre-Deployment Checkliste
- [ ] Versionsnummer erhöht in `build.gradle.kts` (versionCode, versionName)
- [ ] Minify aktiviert für Release (`minifyEnabled = true`)
- [ ] App-Icon erstellt (512x512px)
- [ ] Google Play Store Account erstellt
- [ ] Privacy Policy website

### Signing & Release Build
```bash
# 1. Keystore erstellen (erste mal nur)
keytool -genkey -v -keystore focus-timer.keystore \
    -keyalg RSA -keysize 2048 -validity 10000 \
    -alias focusflowkey

# 2. build.gradle.kts updaten:
# signingConfigs { ... } Sektion
# buildTypes release { signingConfig ... }

# 3. Release APK bauen
./gradlew assembleRelease

# 4. Bundle für Play Store (empfohlen)
./gradlew bundleRelease
```

### Play Store Upload
1. https://play.google.com/console
2. "Create app"
3. App-Details ausfüllen (Beschreibung, Screenshot, etc)
4. Release → Roller out release
5. Bundle/APK hochladen
6. Freigeben!

---

##  Marketing & Growth Hacks

### Launch-Strategie
1. **Beta-Testing**: 20-30 Beta-Tester via Google Play Console
2. **Reviews**: Aktiv um 5-Sterne-Reviews bitten (nach Session 10)
3. **Beschreibung**: SEO-optimiert mit Keywords: "Pomodoro", "Timer", "Produktivität"
4. **Screenshot**: Zeige Statistiken-Feature (sehr attraktiv)
5. **Social**: Twitter, Reddit, ProductHunt posten

### ASO (App Store Optimization)
- **Title**: "FocusFlow - Pomodoro & Produktivitäts-Timer"
- **Subtitle**: "25min Fokus Sessions mit Statistiken"
- **Keywords**: pomodoro, timer, produktivität, fokus, arbeits-timer
- **Description**: Highlight Statistiken & Material Design

### Monetarisierung

**Option 1: Banner Ads (Google AdMob)**
```kotlin
// build.gradle.kts (dependencies)
implementation("com.google.android.gms:play-services-ads:22.6.0")
```

**Option 2: Premium Version (Freemium)**
- Google Play Billing Library
- In-App Purchase für "Unlock Premium"
- Features: Werbe-frei, erweiterte Stats, Dark Mode

---

##  Troubleshooting

### "JAVA_HOME is not set"
```bash
# Windows PowerShell (als Admin):
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-11", "Machine")
# Dann PowerShell neu starten

# macOS/Linux:
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
```

### "Gradle build failed"
```bash
# 1. Gradle cache löschen
rm -rf ~/.gradle/caches

# 2. Gradle wrapper neu downloaden
./gradlew wrapper --gradle-version=8.5

# 3. Nochmal bauen
./gradlew clean build
```

### "Emulator laggt"
- KVM/Hyper-V aktivieren (Virtualisierung in BIOS)
- Weniger RAM für Emulator zuteilen
- oder: Echtes Gerät verwenden

### "Build error: Symbol not found"
- Android Studio: File → Invalidate Caches and Restart
- Oder: gradlew clean build

---

##  Weitere Ressourcen

- **Android Docs**: https://developer.android.com/
- **Jetpack Compose**: https://developer.android.com/jetpack/compose
- **Kotlin Docs**: https://kotlinlang.org/docs/
- **Material Design 3**: https://m3.material.io/

---

##  Nächste Feature-Ideen

### Short-term (1-2 Wochen)
- [ ] Notifications bei Timer-Ende
- [ ] Sounds (Klingel-Sound)
- [ ] Dark Mode
- [ ] Settings Screen auslagern

### Medium-term (3-6 Wochen)
- [ ] Graphischer Report (Statistiken-Charts)
- [ ] Weekly/Monthly Übersicht
- [ ] Timer-History
- [ ] Anpassbare Sounds & Vibrationen

### Long-term (2+ Monate)
- [ ] Cloud Sync (Firebase)
- [ ] Social Features (Freunde, Challenges)
- [ ] Integration mit Kalender/Tasks
- [ ] AI-powered Focus-Empfehlungen
- [ ] Musik-Integration (Spotify, Focus-Musik)

---

## ⚡ Performance Tips

```kotlin
// Lazy Loading von Composables
LazyColumn {
    items(100) { index ->
        // Nur visible items rendern
    }
}

// State hoisting für bessere Performance
val state by viewModel.state.collectAsState()

// Compose Preview für schnelleres Testen
@Preview
@Composable
fun MyScreenPreview() { ... }
```

---

##  Support & Community

- **GitHub Issues**: (wenn du es open-source machen willst)
- **Email**: support@focusflowapp.com (später)
- **Discord**: Fokus-Nerd Community (später)

---

**Viel Erfolg beim Launchen! **

Diese App hat das Zeug zum Hit! Lass uns die Welt produktiver machen! 

---

*Last updated: 2026-05-15*
