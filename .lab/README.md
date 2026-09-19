# Virtual Product Lab

Kleines Softwareprojekt in Schleifen verbessern — **über Dateien**, nicht über verlorene Chats.

Ablauf: Bauen → simulierte Tester (Personas) → optional Markt → Synthese → Finance entscheidet → gelegentlich echte Menschen → wieder bauen.

## Harte Regeln
1. Quelle der Wahrheit: dieses Verzeichnis `.lab/`.
2. Keine erfundenen Markt-/Umsatz-/Nutzerzahlen. Nur `assumptions.md` oder belegte Recherche; sonst `unknown`.
3. Max. 3 Produktänderungen pro Iteration; bei Finance `continue_cheap`/`shrink`: 1.
4. Stopp über Reife + Budget + stagnierende Scores (`graph/decide_rules.md`).
5. Sim-Tester sind Sensoren; echte Tester kalibrieren.
6. Copilot baut Code über Issues/PRs. Grok orchestriert, testet simuliert, priorisiert, finanziert. Nicht drei Coder am selben Ticket.
7. Agent-Output = Datei (JSON nach Schema und/oder kurze `.md`). Chat allein zählt nicht.
8. Produktivcode nur wenn `state.json` Phase/Auftrag Build erlaubt. Sonst nur `.lab/` und Docs.
9. Unklar → `outbox/` mit `BLOCKED` und stoppen.

## Wo liegt was
| Pfad | Zweck |
|------|--------|
| `state.json` | Iteration, Phase, Budget-Schätzung, Status |
| `assumptions.md` | Zahlen vom Menschen (Budget, Schwellen) |
| `spec.md` | Produktspec (Iteration 0 füllt) |
| `backlog.md` | Samen für Issues — Stories bleiben in GitHub Issues |
| `personas/` | Tester-Charaktere |
| `agents/` | Systemprompts + `imported/` Alt-Playbooks |
| `inbox/` | Aufträge (YAML-Kopf + Body) |
| `outbox/` | Ergebnisse |
| `reports/` | test / market / finance / human |
| `graph/` | loop, decide_rules, adapters |
| `schemas/` | JSON-Schemas |

## Phasen
`bootstrap` → `discovery`? → `spec` → `build` → (`synth_test` parallel Personas) → optional `market` → `synthesize` → `finance` → `decide` → weiter oder `ship`/`kill`/`blocked`/`human_eval`.

## Durchlauf starten
1. `state.json` lesen (Iteration, Phase, Status).
2. Auftrag als Inbox-Datei legen: `.lab/inbox/iter-{NN}-{role}-{kurzslug}.md` (Pflicht-YAML-Kopf).
3. Rolle aus `agents/*.md` ausführen; Output nach `outbox/` bzw. `reports/` laut Schema.
4. Inbox auf `done` setzen oder nach `inbox/_done/` verschieben.
5. Finance/Decide aktualisiert `state.json`.

## Schluss
`stop_ship` → Freeze + REPORT; `stop_kill` → STOP; `ask_human`/`blocked` → Mensch. Details: `graph/loop.md` und `graph/decide_rules.md`.

Coding-Worker Standard: **github_copilot** (Issue-Template `.github/ISSUE_TEMPLATE/lab-build.md`). Fallback nur wenn Copilot blockiert: `grok_build`.
