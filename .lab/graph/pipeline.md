# Pipeline-Automatik (Zahnrad-Prinzip)

## Prinzip
Jede fertige Outbox/Report-Datei **ist** der Auftrag an die nächste Rolle. Der Mensch wird nur bei `ask_human` / `blocked` / `human_eval` / Merge-Authority unterbrochen.

## Pflicht nach jedem Rollen-Output
1. Report/Outbox schreiben (Schema einhalten) + Issue-Kommentar.
2. `state.json`: `phase` auf **nächste** Phase setzen; `notes` = ein Satz Auftrag.
3. Inbox-Datei für die **nächste** Rolle anlegen (`status: open`) mit Links auf Inputs.
4. Vorherige Inbox nach `_done/` bzw. `status: done`.
5. Orchestrator (Grok `fuckedup` / FF-PM) **sofort** die nächste Rolle ausführen oder anstoßen — **ohne** auf Chat-OK zu warten, außer Decide = `ask_human`.

## Übergabe-Kette
| Fertig | Nächste Rolle | Artefakt |
|--------|---------------|----------|
| spec | synth_test **oder** build (laut Decide) | inbox |
| build + merge | tech_qa | inbox |
| tech_qa OK | synth_test | inbox |
| synth_test | synthesize | automatisch |
| synthesize | market *oder* finance (Finance darf market skippen) | inbox |
| market | finance | automatisch |
| finance | decide | automatisch |
| decide continue/cheap | engineer_brief → lab-build Issue → Copilot | Issue |
| decide ask_human | outbox ASK_HUMAN.md + stop | Mensch |
| decide stop_ship/kill | REPORT/STOP.md | Mensch bestätigt |

## Coding-Worker
TDD + autonom bis Push (`coding_worker_rules.md`). Nach PR-Merge: Orchestrator setzt phase weiter (tech_qa/synth).

## Verbot
Zwischenfragen im Chat für den Normalpfad. Chat nur Status/Links.
