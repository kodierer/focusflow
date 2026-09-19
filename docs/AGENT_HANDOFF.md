# Agent-Übergabe — FocusFlow

**Zweck:** Gemeinsame Wahrheit für **GitHub Copilot** (VS Code) und **Grok Bot**. Bei Session-Start lesen; nach relevanten Änderungen aktualisieren und committen.

**Stand:** 2026-09-19 (Europe/Berlin)  
**Repo:** `kodierer/focusflow` · Branch: `main`  
**Package:** `com.kodierer.focusflow`  
**Website:** https://friedermossmann.de/  
**Privacy:** https://kodierer.github.io/focusflow/privacy.html  
**Kontakt (Play):** joerg.frieder@gmail.com

---

## Rollen

|| Wer | Typische Aufgaben |
|-----|-------------------|
| **Copilot / Cline** | Code, Tests, Lint, lokale Builds, CI-grün halten |
| **Grok Bot** | Play Console, Drupal/Website, GitHub Issues/Wiki, Orchestrierung |
| **PO (Jörg-Frieder)** | Merge-Authority, Play-Submit, Tester-Mails |

Kein Live-Sync der Chats — dieses File + Issues/PRs sind die Brücke.

---

## Erledigt (2026-09-19)

- [x] Store-Listing / App-Inhalt-Deklarationen weitgehend
- [x] Privacy Policy live (GitHub Pages)
- [x] Kontakt-E-Mail Play: `joerg.frieder@gmail.com`
- [x] Website Play + GitHub About/README: `https://friedermossmann.de/`
- [x] Issues + Labels + Templates; Wiki Home / Closed-Testing / Play-Launch
- [x] friedermossmann.de: Drupal 10.6.2 neu; Frontpage Visitenkarte + FocusFlow (`/node/1`)
- [x] Docs: `docs/CLOSED_TESTING.md`, `docs/PLAY_LAUNCH_CHECKLIST.md`

---

## Offen (Priorität)

1. **Closed Testing:** ≥12 echte Google-Tester-Accounts → Gruppen/E-Mails in Play → Alpha-Release (1.1/v4 war draft, DE) **submitten** — keine Fake-Mails.
2. **≥14 Tage** Closed Testing erfüllen (persönliches Play-Konto).
3. **Production**-Zugang beantragen / Production-Release.
4. Optional: EN Store-Listing (#5).
5. Code-Bugs aus älterer Übergabe (`UEBERGABE_DOKUMENTATION.md`, Mai 2026) nur anfassen wenn noch reproduzierbar — Launch hat Vorrang vor Feature-Arbeit.

Issues: [#2](https://github.com/kodierer/focusflow/issues/2) Closed Testing · [#3](https://github.com/kodierer/focusflow/issues/3) Production · [#4](https://github.com/kodierer/focusflow/issues/4) Tester · [#5](https://github.com/kodierer/focusflow/issues/5) EN Listing

---

## Nicht tun

- Keine Secrets/Keystore-Passwörter in Git oder Chat
- Keine erfundenen Tester-Gmails
- Keine neuen Features vor Production-Launch (Scope: Listing/Compliance)

---

## Wie aktualisieren

1. Abschnitt **Stand** + Checkboxen anpassen.
2. Kurz **Letzte Änderung** unten ergänzen (Datum, Agent, 1 Zeile).
3. Commit z. B. `docs: update AGENT_HANDOFF`.

### Letzte Änderung

- 2026-09-19 — Grok Bot: Datei angelegt (Drupal live, Website in Play/GitHub, Closed Testing noch offen).
