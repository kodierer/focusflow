---
id: iter-00-synthesize-after-synth_test
iteration: 0
role: synthesizer
status: done
created_at: 2026-09-19T12:34:23Z
issue: https://github.com/kodierer/focusflow/issues/10
---

# Synthese — Synth-Test Runde 1

## Methode
Simuliert aus Spec + `TimerViewModel` / Screen-Dateien. **Kein Emulator.** Startbarkeit = unknown.

## Cluster (wie viele Personas)
| Friction | Personas | Schwere |
|----------|----------|---------|
| Timer überlebt Hintergrund/Kill nicht (kein FGS; nur Handler) | chaos (stark), power_user, newbie (indirekt) | hoch |
| Auto-Continue / toggle startet sofort | newbie, power_user, chaos | mittel |
| Stats/History flach vs. Konkurrenz | power_user | niedrig (Launch-Freeze) |
| Listing/Preis/Permissions unklar für Kauf | buyer | mittel (Docs/assumptions) |
| Live-UI-Klarheit Arbeit/Pause | newbie | mittel — braucht Gerätecheck |

## Max. 3 Changes (Vorschlag für nächstes Build — noch kein Auftrag)
1. **P0:** Laufenden Timer-State speichern/restoren (mindestens timeLeft + Session-Typ); mittelfristig ForegroundService — Chaos-Blocker.
2. **P1:** Auto-Continue entschärfen (Opt-in oder kurzer Delay) + klarere Arbeit/Pause-Labels.
3. **P1:** Buyer/Listing: Permission- und Nutzen-Klarheit dokumentieren; assumptions Preis TODO setzen — kein Feature-Code.

Nicht in die 3: große History/Widgets (HANDOFF / post-Production).

## Nächster Lab-Schritt
`synthesize` erledigt → Inbox für Finance/Decide oder Mensch bestätigt Build-Issue aus #10.
