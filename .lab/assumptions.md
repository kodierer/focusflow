# Assumptions — FocusFlow Lab

**Regel:** Zahlen nur hier oder aus belegter Recherche. Sonst `unknown`. Grok erfindet kein Budget.

## Budget & Aufwand (Mensch ausfüllen)

| Feld | Wert | Status |
|------|------|--------|
| Stundensatz Mensch (EUR) | TODO | TODO |
| Monats-/Projektbudget Labor (EUR, Cap) | TODO | TODO — **ohne Cap darf Finance kein teures `continue` empfehlen → `ask_human`** |
| Grok: Abo vorhanden | TODO ja/nein | TODO |
| Grok: API-Key vorhanden | TODO ja/nein | TODO |
| Copilot-Plan | TODO | TODO |
| Copilot Coding Agent darf auf dieses Repo | TODO ja/nein | TODO |
| Coding-Worker Standard | `github_copilot` | gesetzt |
| Coding-Worker Fallback | `grok_build` (nur wenn Copilot blockiert) | gesetzt |

## Produkt & Preis

| Feld | Wert | Status |
|------|------|--------|
| Produktart | TODO: Hobby / einmaliger Kauf / Abo | TODO |
| Preis-Annahme | TODO oder `unknown` | TODO |
| Ziel-ICP (ein Satz) | TODO | TODO |

## Reife-Schwellen (Vorschlag — Mensch darf ändern)

| Achse | Schwelle |
|-------|----------|
| functional | ≥ 4 von 6 Kernaufgaben durch Synth-Tester erfüllt |
| ux | Mittel ≥ 3.5 |
| fun / Wiederkehr | ≥ 3.0 bei Persona `casual_fun` |
| reliability / Blocker | keine offenen Blocker Tech-QA / Security |
| market_clarity | ICP + 1 Differenzmerkmal dokumentiert |
| risk | keine offenen kritischen Security-Findings ohne Mitigation |

## Human-Test

- Alle **2–3 Iterationen**, ODER vor `ship`, ODER bei Widerspruch Simulation vs. Bauchgefühl.
- Kalibrierung landet in `reports/human/`.

## Bekannt aus Repo (keine Marktgrößen)

| Claim | Status | Quelle |
|-------|--------|--------|
| Package `com.kodierer.focusflow` | known | HANDOFF |
| Website https://friedermossmann.de/ | known | HANDOFF |
| Privacy-Policy URL | known | HANDOFF |
| Closed Testing ≥12 echte Tester / ≥14 Tage | known | Play / Issues #2–#4 |
| README-Markt-/Retention-Zahlen | unknown | unsourced — nicht als Fakt nutzen |
