# FocusFlow – Uebergabe-Dokumentation

## 1) Projektkontext
- **App**: FocusFlow (Pomodoro Timer)
- **Repository**: https://github.com/kodierer/focusflow
- **Architektur**: Kotlin + Jetpack Compose + MVVM
- **Aktueller Stand**: Feature-Umfang ist implementiert; bestehende Statusdokumente sind vorhanden (`EXECUTIVE_SUMMARY.md`, `FINAL_STATUS.md`, `NEXT_STEPS.txt`).

## 2) Offene, priorisierte Punkte
> Priorisierung fuer die naechste uebernehmende Person. Diese Punkte sind als offene Risiken/Follow-ups zu behandeln.

1. **HIGH** – `VIBRATOR_SERVICE` modernisieren  
   - Datei: `app/src/main/java/com/example/myapplication/utils/HapticFeedback.kt`  
   - Status: Aktuell mit `@Suppress("DEPRECATION")`; auf modernes Vibrator-API fuer neue Android-Versionen konsolidieren.

2. **HIGH** – Deprecated-Usage in UI/Utilities vollstaendig gegenpruefen  
   - Einstieg: `SettingsScreen.kt`, `HapticFeedback.kt`  
   - Ziel: Sicherstellen, dass keine verbleibenden Deprecated-Stellen uebersehen wurden.

3. **MEDIUM** – Timer-Genauigkeit bei langen Sessions validieren  
   - Datei: `app/src/main/java/com/example/myapplication/TimerViewModel.kt` (`startTimer()`)  
   - Testfaelle: 25 / 60 / 120 Minuten, inkl. App im Vorder-/Hintergrund.

4. **MEDIUM** – Notification-Verhalten fuer Android 12+ (API 31+) absichern  
   - Datei: `app/src/main/java/com/example/myapplication/utils/NotificationHelper.kt`  
   - Fokus: Verhalten bei neuen Notification-/PendingIntent-Randfaellen und OEM-Unterschieden.

## 3) Konkreter Arbeitsablauf fuer die Uebernahme
1. Repository pullen und in Android Studio oeffnen.
2. JDK/SDK-Konfiguration pruefen.
3. Lokalen Build und Tests ausfuehren.
4. Danach die 4 priorisierten Punkte in obiger Reihenfolge bearbeiten.
5. Nach jedem Fix: Build + relevante Tests + manuelle Verifikation auf Emulator/Geraet.
6. Ergebnis kurz in `FINAL_STATUS.md` bzw. neuem Changelog-Abschnitt dokumentieren.

## 4) Relevante Einstiegsdateien
- `app/src/main/java/com/example/myapplication/TimerViewModel.kt`
- `app/src/main/java/com/example/myapplication/ui/screens/FocusTimerScreen.kt`
- `app/src/main/java/com/example/myapplication/ui/screens/SettingsScreen.kt`
- `app/src/main/java/com/example/myapplication/utils/HapticFeedback.kt`
- `app/src/main/java/com/example/myapplication/utils/NotificationHelper.kt`
- `.github/workflows/build.yml`
- `app/build.gradle.kts`

## 5) Build-/Test-Kommandos (Windows PowerShell)
```powershell
# Debug Build
./gradlew assembleDebug

# Unit Tests
./gradlew test

# Release Bundle (Play Store)
./gradlew bundleRelease

# Clean + Build
./gradlew clean build
```

## 6) Handover-Hinweise
- Bestehende Statusdokumente enthalten viel Detail-Historie; diese Datei ist als **kompakter Arbeits-Startpunkt** gedacht.
- Bei Konflikten zwischen Dokumenten gilt: **Code + aktueller CI-Status haben Vorrang**.
- Vor Release/Store-Upload sollten die 4 priorisierten Punkte aktiv verifiziert sein.
