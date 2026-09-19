# Spec — FocusFlow

**Iteration 0 · PM · 2026-09-19**

## Problem
Menschen wollen fokussiert arbeiten, verlieren aber den Faden durch Ablenkung. FocusFlow ist eine schlanke Android-App: Pomodoro-ähnlicher Timer (Arbeit/Pause), Steuerung, Basis-Stats, Material-3-UI — lokal, ohne Backend.

## Nutzer (ICP — Annahme, Preis/Budget siehe assumptions TODO)
Erwachsene, die kurze Fokusblöcke am Android-Handy brauchen (Lernen, Freelancing, Büro). Primär DE-Listing; EN optional.

## Produkt heute (aus README + Code)
- Package `com.kodierer.focusflow`; Einstieg `MainActivity` → `FocusTimerScreen`; Logik `TimerViewModel`; `data/` / `ui/` / `utils/`
- Arbeit/Pause mit Start/Pause/Reset; einstellbare Dauern; Session-/Fokuszeit-Anzeige; Compose + Material 3

## Kernaufgaben (max. 6) — Synth-Tester / Reife „functional“
1. App öffnen und **ohne Anleitung** eine Arbeitssession starten.
2. Laufenden Timer **pausieren und fortsetzen**; Restzeit bleibt plausibel.
3. Session **zurücksetzen**; UI zeigt klar Arbeit vs. Pause.
4. **Arbeits- und Pausendauer** in den Settings ändern (nicht während kritischem Lauf missverständlich).
5. **Session-Zähler / Fokusminuten** nach abgeschlossenen Blöcken nachvollziehen.
6. App **nach Prozessende/Hintergrund** wieder öffnen, ohne offensichtlichen State-Bruch (Chaos-Persona).

## Out of scope (aktuell)
- Neue Features vor Production (HANDOFF): Cloud-Sync, Premium, Integrationen, Push, Dark Mode, Langzeit-Graphen
- Markt-/Umsatzzahlen (unknown bis assumptions/Recherche)
- Produktivcode in Iteration 0

## Launch-Nebenbedingung (kein Feature-Sprint)
Closed Testing ≥12 echte Tester + Submit (#2/#4) → ≥14 Tage → Production (#3). Lab darf das nicht verdrängen.

## Unknowns
- App lokal/Emulator aktuell startbar? (Mensch prüfen)
- Persistenz der Stats über App-Kill hinweg: Umfang unknown ohne Testlauf
- Budget-Cap, Preis/Produktart, Copilot-Coding-Agent-Rechte: TODO in `assumptions.md`
- Differenzmerkmal vs. Konkurrenz: noch nicht belegt (`market_clarity`)
