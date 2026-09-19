# Loop (verbindlich)

```
bootstrap → discovery? → spec → build → tech_qa
  → synth_test (parallele Personas)
  → market (nicht jede Iteration; Finance darf skippen)
  → synthesize → finance → decide
       continue        → backlog → spec/build
       continue_cheap  → 1 Fix, 3 Personas, kein Markt
       human_eval      → interrupt, reports/human, Personas kalibrieren
       stop_ship       → Freeze, REPORT.md
       stop_kill       → STOP.md
       ask_human / blocked
```

## Phasen-Enum
`bootstrap | discovery | spec | build | synth_test | market | synthesize | finance | human_eval | decide | ship | kill | blocked`

## Runde 1 Synth-Tester
Nur: newbie, power_user, chaos, buyer.

## Coding
Issue aus `lab-build` Template → Copilot Coding Agent → PR → nach Merge `repo_ref` + `changelog.md`.
