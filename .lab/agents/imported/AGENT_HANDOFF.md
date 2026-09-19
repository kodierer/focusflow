# Agent-Übergabe — FocusFlow

**Zweck:** Gemeinsame Wahrheit für **Grok Bot**, **GitHub Copilot** und **Cline**. Bei Session-Start lesen; nach relevanten Änderungen aktualisieren und committen.

**Team-Playbook:** [`docs/AGENT_TEAM.md`](AGENT_TEAM.md)  
**Stand:** 2026-09-19 (Europe/Berlin)  
**Repo:** `kodierer/focusflow` · Branch: `main`  
**Package:** `com.kodierer.focusflow`  
**Website:** https://friedermossmann.de/  
**Privacy:** https://kodierer.github.io/focusflow/privacy.html  
**Kontakt (Play):** joerg.frieder@gmail.com

---

## Rollen (Kurz)

| Wer | Rolle |
|-----|--------|
| **Du (Jörg-Frieder)** | PO, Tech Lead, Merge & Production |
| **Grok Bot (`fuckedup`)** | Orchestrator, DevOps/Play/Website |
| **Grok `FF-PM` / `FF-Architect` / `FF-QA` / `FF-Security` / `FF-Reviewer`** | Phasen-Spezialisten (eigene Chats) |
| **Cline** | Engineer (Code, Tests ausführen, CI) |
| **Copilot** | Pair + unabhängiger Review-Pass |

Kein Live-Sync der Chats — dieses File + Issues/PRs + `AGENT_TEAM.md` sind die Brücke.

---

## Erledigt (2026-09-19)

- [x] Store-Listing / App-Inhalt-Deklarationen weitgehend
- [x] Privacy Policy live (GitHub Pages)
- [x] Kontakt-E-Mail Play: `joerg.frieder@gmail.com`
- [x] Website Play + GitHub About/README: `https://friedermossmann.de/`
- [x] Issues + Labels + Templates; Wiki Home / Closed-Testing / Play-Launch
- [x] friedermossmann.de: Drupal 10.6.2 neu; Frontpage Visitenkarte + FocusFlow (`/node/1`)
- [x] Docs: `docs/CLOSED_TESTING.md`, `docs/PLAY_LAUNCH_CHECKLIST.md`
- [x] Agent-Handoff + Copilot-Instructions + Agent-Team-Playbook

---

## Offen (Priorität)

1. **Closed Testing:** ≥12 echte Google-Tester-Accounts → Play → Alpha **submitten** — keine Fake-Mails.
2. **≥14 Tage** Closed Testing (persönliches Play-Konto).
3. **Production**-Zugang / Production-Release.
4. Optional: EN Store-Listing (#5).
5. Alte Code-Bugs (`UEBERGABE_DOKUMENTATION.md`) nur wenn noch reproduzierbar — Launch hat Vorrang.

Issues: [#2](https://github.com/kodierer/focusflow/issues/2) · [#3](https://github.com/kodierer/focusflow/issues/3) · [#4](https://github.com/kodierer/focusflow/issues/4) · [#5](https://github.com/kodierer/focusflow/issues/5)

---

## Nicht tun

- Keine Secrets/Keystore-Passwörter in Git oder Chat
- Keine erfundenen Tester-Gmails
- Keine neuen Features vor Production-Launch (Scope: Listing/Compliance)
- Implementierer ≠ Reviewer (siehe `AGENT_TEAM.md`)

---

## Wie aktualisieren

1. Abschnitt **Stand** + Checkboxen anpassen.
2. **Letzte Änderung** unten ergänzen (Datum, Agent, 1 Zeile).
3. Commit z. B. `docs: update AGENT_HANDOFF`.

### Letzte Änderung

- 2026-09-19 — Grok Bot: Agent-Team-Playbook; Handoff auf Grok+Copilot+Cline erweitert.
