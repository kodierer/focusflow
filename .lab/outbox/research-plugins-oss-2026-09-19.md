---
id: research-plugins-oss-2026-09-19
role: market_researcher+meta
status: done
created_at: 2026-09-19T12:28:27Z
---

# Recherche: freie Plugins & nützliche OSS-Repos

**Hinweis:** Empfehlungen sind Kandidaten mit Quellen. Keine Installationspflicht. Lizenzen vor Übernahme prüfen. Keine erfundenen Sterne-Ratings als Fakt.

## A) Editor / Agent-Plugins (kostenlos nutzbar / Marketplace)

| Kandidat | Nutzen für FocusFlow/Lab | Quelle | Hinweis |
|----------|--------------------------|--------|---------|
| **GitHub Pull Requests** (`GitHub.vscode-pull-request-github`) | Issues + PRs in VS Code/Cursor — Ticket-Spur | https://marketplace.visualstudio.com/items?itemName=GitHub.vscode-pull-request-github | Bereits empfohlen; Issues stecken in dieser Extension |
| **GitHub MCP Connector** (Cursor Plugin) | Issues/PRs/Actions aus dem Agent | Cursor SearchPlugins „GitHub“ id 48677658 | Bei uns bereits als `user-GitHub-xai` **connected** |
| **CodeRabbit** (Cursor Plugin) | PR-Reviews | SearchPlugins id 41762204 | **Nicht installiert**; ob Free-Tier reicht: unknown — vor Nutzung Pricing prüfen |
| Android-/Kotlin-Extensions (Marketplace) | Build/Logcat/Navigation | Web-Suche „Android Tools / DevKit VS Code“ | Nützlich für Cline/Mensch lokal; Exact free SKUs vor Ort prüfen |

**Grok-Bot / Cursor bereits connected:** GitHub + Play Console. Kein neuer Token nötig für GitHub-Arbeit der Agenten.

**Nicht empfohlen ohne Bedarf:** Clerk, Dynatrace, RevenueCat, Meta Quest, Scandit — passen nicht zum aktuellen Launch-Scope (Closed Testing / schlanker Timer).

## B) GitHub Open-Source (Compose Pomodoro / Fokus) — zum Lernen, nicht copy-paste

| Repo | Warum relevant | Lizenz (laut Listing) | Quelle |
|------|----------------|----------------------|--------|
| SultonUzDev/PomodoroFocusTimer | Compose, MVVM/MVI, Room/DataStore, Offline | unknown hier — LICENSE im Repo prüfen | https://github.com/SultonUzDev/PomodoroFocusTimer |
| vrn7712/Zon | Material 3, Room, Widgets, offline, MVVM+StateFlow | GPL-3.0 | https://github.com/vrn7712/Zon — **Achtung GPL** bei Code-Übernahme |
| adnanrangrej/Focus-Modes-App | ForegroundService-Timer, Clean Arch, Hilt/Room | MIT (README) | https://github.com/adnanrangrej/Focus-Modes-App |
| nichsedge/deepfocus | Compose M3, Room Stats, Media3, ForegroundService | MIT (README) | https://github.com/nichsedge/deepfocus |
| thebytearray/promodo | Minimal Compose Timer, DataStore | Apache-2.0 | https://github.com/thebytearray/promodo |

## C) Lab-Empfehlung (priorisiert)
1. Ticket-Workflow über **GitHub PR/Issues Extension** + bestehenden **GitHub-Connector** festziehen (Meta-Steward prüft Kommentare).
2. OSS nur als **Ideen/Referenz** (ForegroundService, Persistenz nach Process Death) — Launch-Freeze beachten; kein Feature-Gießkanne.
3. CodeRabbit erst nach Klärung Free vs. Paid.
4. GPL-Repos (Zon): nur Inspiration, keine ungeprüfte Code-Kopie.

## Unknowns
- Ob CodeRabbit Free-Tier für private Repos reicht
- Ob „Android Tools“ VS Code Extension im Cursor-Marketplace identisch/frei verfügbar


## Ticket
https://github.com/kodierer/focusflow/issues/9
