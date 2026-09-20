# Spec — FocusFlow

**Iteration 0 · aktualisiert nach Synth R2 · 2026-09-20**

## Problem
Menschen wollen fokussiert arbeiten, verlieren aber den Faden durch Ablenkung. FocusFlow ist eine schlanke Android-App: Pomodoro-ähnlicher Timer (Arbeit/Pause), Steuerung, Basis-Stats, Material-3-UI — lokal, ohne Backend.

## Nutzer (ICP — Annahme, Preis/Budget siehe assumptions TODO)
Erwachsene, die kurze Fokusblöcke am Android-Handy brauchen (Lernen, Freelancing, Büro). Primär DE-Listing; EN optional.

## Produkt heute
- Package `com.kodierer.focusflow`; TimerViewModel + Compose UI
- Persist/Restore nach Process-Death (#11/#12) — Human Kill-Test PASS
- ForegroundService + ongoing Notification (#13, ee1cbd7) — Statusleisten-Glyph wontfix / partial

## Kernaufgaben (max. 6)
1. App öffnen und ohne Anleitung Arbeitssession starten.
2. Pausieren und fortsetzen; Restzeit plausibel.
3. Reset; UI klar Arbeit vs. Pause.
4. Arbeits-/Pausendauer ändern (blockiert während Lauf).
5. Session-Zähler / Fokusminuten nachvollziehen.
6. Nach Prozessende/Hintergrund wieder öffnen ohne State-Bruch — **Human PASS**.

## Out of scope (aktuell)
- Statusleisten-Glyph-Nacharbeit; Cloud, Premium, Widgets, History-Graphen vor Production
- Marktzahlen ohne Quellen

## Unknowns
- Liegt #13-Commit `ee1cbd7` auf `origin/main`? (GitHub-main tip war ohne FGS-Datei)
- Budget-Cap / Preis in assumptions
- Closed-Testing-Tester (#4)
