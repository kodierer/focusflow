---
id: iter-00-pm-bootstrap
iteration: 0
role: pm
persona: null
parent: null
priority: P0
status: open
input_files:
  - README.md
  - .lab/BOOTSTRAP.md
  - .lab/state.json
  - .lab/assumptions.md
  - .lab/agents/imported/AGENT_HANDOFF.md
output_schema: null
output_file: .lab/outbox/iter-00-pm-bootstrap.md
budget_hint: low
---

# Ziel
Produkt aus README/Code in `spec.md` auf **max. 1 Seite** gießen. Max. **6 Kernaufgaben** definieren. Lücken als `unknowns`.

# Kontext
Lab-Setup ist neu. Launch-Scope (Closed Testing) hat Vorrang vor Feature-Bau laut HANDOFF. Keine Marktzahlen erfinden.

# Verbote
- Produktivcode ändern
- Budget/Preise erfinden
- Mehr als Spec + Backlog-Iteration-0 anfassen
- „Voll durchlaufen“ ohne expliziten Menschen-Auftrag

# Definition of Done
- [ ] `spec.md` gefüllt (Problem, Nutzer, ≤6 Kernaufgaben, Out-of-scope, unknowns)
- [ ] `backlog.md` Iteration 0 aktualisiert
- [ ] `state.json`: `phase` = `spec`, `status` sinnvoll, kurze `notes`
- [ ] Outbox-Datei geschrieben; diese Inbox `done` oder nach `_done/`
