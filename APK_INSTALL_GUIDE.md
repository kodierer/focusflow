#  FOCUSFLOW APK - 5 MINUTEN INSTALL GUIDE

## ⚡ SCHNELLSTART - Wähle deine Variante

---

##  OPTION 1: Auf ECHTEM ANDROID-GERÄT (über USB) ⭐ EASIEST

### **Voraussetzungen:**
- Android Smartphone/Tablet
- USB-Kabel
- USB-Debugging aktiviert (in Entwickleroptionen)
- APK von GitHub herunterladen

### **Schritte:**

#### 1️⃣ **APK herunterladen**
```
1. https://github.com/kodierer/focusflow/actions öffnen
2. Neuesten grünen ✅ Build klicken
3. Scroll down zu "Artifacts"
4. "app-release-apk" downloaden
5. Datei: app-release.apk
```

#### 2️⃣ **Android-Gerät vorbereiten**
```
1. USB-Kabel anstecken
2. Auf Android: Einstellungen → System → Entwickleroptionen
3. "USB-Debugging" aktivieren
4. "Immer erlauben" klicken
```

#### 3️⃣ **APK installieren (Terminal)**
```powershell
# In PowerShell:
adb install C:\Users\fmoss\Downloads\app-release.apk

# Oder exakter Pfad zu deinen Downloads
adb install "C:\Users\fmoss\Downloads\app-release.apk"
```

#### 4️⃣ **Fertig! **
```
App erscheint automatisch auf deinem Handy
→ FocusFlow öffnen
→ TESTEN! 
```

**Zeit: ~2 Minuten**

---

## ️ OPTION 2: Auf ANDROID EMULATOR (virtuell)

### **Voraussetzungen:**
- Android Studio installiert
- Emulator konfiguriert (oder neu erstellen)
- APK herunterladen

### **Schritte:**

#### 1️⃣ **Android Studio öffnen + Emulator starten**
```
1. Android Studio starten
2. Tools → Device Manager
3. Emulator starten (z.B. "Pixel 6")
4. Warten bis vollständig geladen
```

#### 2️⃣ **APK downloaden** (wie oben)
```
GitHub Actions → app-release.apk
```

#### 3️⃣ **APK via adb installieren**
```powershell
# Emulator muss bereits laufen!

adb install "C:\Users\fmoss\Downloads\app-release.apk"

# Oder wenn mehrere Devices:
adb devices  # Alle anzeigen
adb -s emulator-5554 install app-release.apk
```

#### 4️⃣ **App im Emulator starten**
```
1. Emulator-Screen: Alle Apps aufrufen
2. "FocusFlow" suchen
3. Klicken
4. APP LÄUFT! 
```

**Zeit: ~5 Minuten**

---

##  OPTION 3: Direkt über Android Studio (Gui)

### **Wenn du Android Studio verwendest:**

#### 1️⃣ **Projekt öffnen**
```
Android Studio → File → Open
→ C:\Users\fmoss\AndroidStudioProjects\MyApplication
```

#### 2️⃣ **Emulator/Gerät auswählen**
```
Top Bar: Select Run Device
→ Emulator oder angeschlossenes Gerät auswählen
```

#### 3️⃣ **Run drücken**
```
Run → Run 'app'
Oder: Grüner Play-Button (top-bar)
```

#### 4️⃣ **Warten + Geniessen**
```
App wird kompiliert → Installiert → LÄUFT! 
(Braucht 2-3 Min zum Bauen)
```

**Zeit: ~3-5 Minuten** (+ Build-Zeit)

---

##  NACH INSTALLATION: APP TESTEN

### **Hauptscreen:**
```
[START] Button                    → Timer startet
[PAUSE] Button                    → Timer pausiert
[RESET] Button                    → Timer reset

Timer zeigt:                      MM:SS Format
- Blau Hintergrund               = Work Session (25 min)
- Grün Hintergrund               = Break Session (5 min)
```

### **Einstellungen**
```
Scroll down:
- Arbeitsdauer: 10-60 min (+ / - Buttons)
- Pausendauer: 1-30 min (+ / - Buttons)
```

### **Statistiken**
```
3 Cards unten:
-  Sessions (Sitzungen abgeschlossen)
- ⏱️ Fokus Zeit (Minuten fokussiert heute)
-  Heute (Stunden fokussiert)
```

### **Test-Workflow:**
```
1. "Start" drücken → Timer läuft ✅
2. "Pause" drücken → Timer pausiert ✅
3. "Start" drücken → Timer läuft weiter ✅
4. "Reset" drücken → Timer reset auf 25:00 ✅
5. "+" Button → Work-Zeit erhöhen ✅
6. "-" Button → Work-Zeit reduzieren ✅
```

---

## ⚠️ TROUBLESHOOTING

### **"adb command not found"**
```
Lösung: adb ist nicht im PATH
→ Füge C:\Users\fmoss\AppData\Local\Android\Sdk\platform-tools zum PATH hinzu

Oder: Vollständiger Pfad:
C:\Users\fmoss\AppData\Local\Android\Sdk\platform-tools\adb install app-release.apk
```

### **"Device not found"**
```
Emulator:
→ Stelle sicher Emulator läuft (Device Manager öffnen)

Echtes Gerät:
→ USB-Kabel fest angesteckt?
→ USB-Debugging aktiviert?
→ Klick "Always allow" auf dem Gerät?

Prüfen:
adb devices
(Sollte dein Gerät/Emulator anzeigen)
```

### **"Installation failed: [REASON]"**
```
Häufige Gründe:
- App ist bereits installiert → vorher deinstallieren
- Nicht genug Speicher
- APK-Datei korrupt

Lösungen:
adb uninstall com.example.myapplication
(Dann neu installieren)
```

### **"App crasht beim Start"**
```
1. Logcat öffnen (Android Studio → View → Logcat)
2. App öffnen
3. Fehler-Details anschauen
4. Suche nach "Exception" oder "Error"

Häufig: Fehlende Permissions
→ App neu starten
→ Alle Permissions genehmigen
```

---

##  INSTALLATION METHODE VERGLEICH

| Methode | Zeit | Einfachheit | Gerät |
|---------|------|-------------|-------|
| **Option 1: USB-Gerät** | 2 min | ⭐⭐⭐⭐⭐ | Echtes Phone |
| **Option 2: Emulator (adb)** | 5 min | ⭐⭐⭐⭐ | Virtual |
| **Option 3: Android Studio** | 5 min | ⭐⭐⭐ | Beide |

**Empfehlung**: Option 1 (echtes Gerät) ist am schnellsten! ⚡

---

## ✅ CHECKLISTE

- [ ] GitHub Actions APK herunterladen
- [ ] Gerät/Emulator bereit
- [ ] APK installieren (adb oder Android Studio)
- [ ] App öffnen
- [ ] Timer testen (Start/Pause/Reset)
- [ ] Einstellungen testen
- [ ] Statistiken prüfen
- [ ]  Fertig!

---

##  GLÜCKWUNSCH!

Deine **FocusFlow App** läuft jetzt! 

Der nächste Schritt ist: **Google Play Store Upload**

Siehe: DEPLOYMENT_GUIDE.md

---

**Geschätzte Testzeit: 5 Minuten ⏱️**

Viel Spaß beim Testen! 
