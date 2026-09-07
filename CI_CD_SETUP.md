# CI/CD Setup — FocusFlow

Kurzanleitung für GitHub Actions, Signing und Google Play (Internal Testing).

> **Hinweis:** Das frühere Workflow `build.yml` (nur Debug-APK, Tests übersprungen) wurde durch `ci.yml` ersetzt.

Play Console (nur Doku): Developer `8910419381670800139`, App `4973522678623873408`  
Package: `com.kodierer.focusflow`

---

## 1. Google Play Service Account

1. In der [Google Cloud Console](https://console.cloud.google.com/) ein Projekt wählen (oder anlegen).
2. **Google Play Android Developer API** aktivieren.
3. Service Account anlegen (Rollen in Cloud können minimal sein; der Zugriff kommt über Play Console).
4. JSON-Schlüssel herunterladen (einmalig).
5. In der [Play Console](https://play.google.com/console/) unter **Users and permissions** den Service Account einladen und Rechte für die App vergeben (mindestens Release zu Test-Tracks hochladen).
6. JSON-Inhalt als GitHub Secret speichern:

   - Name: `PLAY_SERVICE_ACCOUNT_JSON`
   - Wert: gesamter JSON-Text (Plaintext)

---

## 2. Keystore-Secrets (GitHub)

Lokalen Keystore **nicht** committen. Für CI:

```powershell
# PowerShell (Windows) — Base64 ohne Zeilenumbrüche
[Convert]::ToBase64String([IO.File]::ReadAllBytes("C:\Users\fmoss\AndroidStudioProjects\MyApplication\focusflow-release.jks"))
```

GitHub Repo → **Settings → Secrets and variables → Actions** — Secrets anlegen:

| Secret | Inhalt |
|--------|--------|
| `KEYSTORE_BASE64` | Base64 des `.jks` |
| `KEYSTORE_PASSWORD` | Keystore-Passwort |
| `KEY_ALIAS` | z. B. `focusflow` |
| `KEY_PASSWORD` | Key-Passwort |
| `PLAY_SERVICE_ACCOUNT_JSON` | Service-Account JSON |

Lokale Builds: `keystore.properties.example` nach `keystore.properties` kopieren und ausfüllen (Datei ist gitignored).

---

## 3. Passwörter rotieren (wichtig)

In `app/build.gradle.kts` lagen früher **Klartext**-Passwörter; `focusflow-release.jks` ist in der Git-Historie **bereits getrackt**.

Empfohlen:

1. Neuen Keystore erzeugen **oder** zumindest Passwörter ändern, sofern das Keystore-Format das hergibt / App-Signing über Play nutzen.
2. Alte Secrets aus der Historie entfernen (`git filter-repo` / BFG) — **nach** Backup.
3. Datei aus dem Index nehmen (wenn ihr committed): `git rm --cached focusflow-release.jks` (Datei bleibt lokal auf der Platte).
4. Neue Werte nur in GitHub Secrets / lokaler `keystore.properties`.

Bis die Historie bereinigt ist, Keystore und Passwörter als kompromittiert behandeln.

---

## 4. Workflows

### `ci.yml` — PR + Push auf `main` / `develop`

- JDK 21, `chmod +x gradlew`
- `./gradlew test lintDebug assembleDebug` (**Tests werden nicht übersprungen**)
- Optional: Debug-APK als Artifact

### `release-play.yml` — Tag `v*` oder manuell (`workflow_dispatch`)

- Läuft nur, wenn die Signing- und Play-Secrets gesetzt sind
- Keystore aus `KEYSTORE_BASE64` dekodieren
- `./gradlew bundleRelease`
- AAB als Artifact
- Upload auf Google Play Track **`internal`** via `r0adkll/upload-google-play@v1`

Erste Uploads bewusst nur Internal Testing — kein Production-Track aus CI.

---

## 5. versionCode / versionName erhöhen

In `app/build.gradle.kts` unter `defaultConfig`:

```kotlin
versionCode = 4        // immer erhöhen (ganze Zahl, monoton)
versionName = "1.2"    // Anzeigeversion
```

Play lehnt Uploads mit gleichem oder niedrigerem `versionCode` ab. Nach dem Bump: committen, taggen (`v1.2`) oder Workflow manuell starten.

---

## 6. Checkliste vor dem ersten Release-Lauf

- [ ] Service Account in Play Console verknüpft
- [ ] Alle fünf GitHub Secrets gesetzt
- [ ] `keystore.properties` lokal vorhanden (nicht committed)
- [ ] `*.jks` / `keystore.properties` in `.gitignore`
- [ ] CI auf `main` grün
- [ ] `versionCode` erhöht
- [ ] Tag `v*` pushen oder **Release to Play (internal)** manuell auslösen