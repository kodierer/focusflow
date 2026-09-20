---
id: iter-00-decide-after-market
iteration: 0
role: decide
status: done
created_at: 2026-09-20T14:21:20Z
auto: true
---

# Decide (automatisch nach Market)

**Empfehlung:** `ask_human` — aber **klar gerichtet**, kein offenes Menü.

## Automatische Weiterleitung
Nächste „Abteilung“: **Closed Testing / Play-Ops** (Orchestrator + PO), nicht Engineer.

## Begründung
- Lab-Feature-Loop (#11/#13) hat den Reliability-Blocker gelöst.
- Market: Differenzierung = lokal/schlank/DE im Listing; ohne Store-Zugang kein ROI auf weitere Builds.
- Finance: kein Cap → kein teures continue; cheap Feature-Polish optional zurückstellen.

## Was der Mensch tun muss (eine Schiene)
1. Reale Tester organisieren / #4 vorantreiben.  
2. Alpha Closed Testing submitten (#2), wenn Tester da.  
3. Lab-Feature-Builds pausieren, bis Decide erneut `continue_cheap` sagt.

## Was automatisch schon erledigt ist
- Synth R2, Market Brief+Devil, Finance, dieses Decide, Pipeline-Vertrag.

## Nicht jetzt
- Neues lab-build für Auto-Continue oder Glyph.
