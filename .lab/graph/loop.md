# Loop (verbindlich)

```
bootstrap → discovery? → spec → build → tech_qa
  → synth_test (parallele Personas)
  → market (nicht jede Iteration; Finance darf skippen)
  → synthesize → finance → decide
       continue        → backlog → spec/build  [auto]
       continue_cheap  → 1 Fix, 3 Personas, kein Markt  [auto]
       human_eval      → interrupt, reports/human
       stop_ship       → Freeze, REPORT.md
       stop_kill       → STOP.md
       ask_human / blocked
```

## Zahnrad-Automatik
Siehe `pipeline.md`. Fertige Reports erzeugen die nächste Inbox **sofort**. Orchestrator führt weiter, bis Decide den Menschen braucht.

## Phasen-Enum
`bootstrap | discovery | spec | build | synth_test | market | synthesize | finance | human_eval | decide | ship | kill | blocked`

## Coding
Issue `lab-build` → Copilot Coding Agent (TDD, autonom) → PR → Merge → nächste Phase auto.
