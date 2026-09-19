# SETUP_COMPLETE — Virtual Product Lab

**Datum:** 2026-09-19 (Europe/Berlin)  
**Branch:** `lab/bootstrap-vpl`

## Angelegt
- Komplette `.lab/`-Struktur (README, BOOTSTRAP, assumptions, state, spec-Platzhalter, backlog, changelog, registry)
- 7 Personas (+ Template); Runde 1 = newbie, power_user, chaos, buyer
- Agenten-Systemprompts + `agents/imported/`
- graph: loop, decide_rules, adapters
- schemas: test_report, market_brief, finance_report, task
- inbox inkl. `iter-00-pm-bootstrap.md` und `_done/`
- Root: Lab-Abschnitt in `AGENTS.md`; `.github/ISSUE_TEMPLATE/lab-build.md`

## 5 nächste Schritte (Mensch)
1. **Budget in `assumptions.md`:** Stundensatz, Cap, Grok/Copilot-Plan — sonst Finance → `ask_human`.
2. **Copilot Coding Agent prüfen:** Darf er auf `kodierer/focusflow` Issues/PRs?
3. **Erstes Build-Issue:** erst nach Iteration 0 Spec / Decide — Template `lab-build` nutzen; Launch-Issues #2/#4 haben weiter Vorrang.
4. **App startbar?** Lokal/Emulator verifizieren; wenn nein, Synth-Tester werten „nicht benutzbar“ (nicht Spec schönreden).
5. **Human-Tester bereit?** Closed Testing (#4); Kalibrierung alle 2–3 Iterationen.

## Stopp
Kein ungefragter Voll-Lauf. Nächster Prompt wenn du willst: „Starte Iteration 0 laut inbox.“
