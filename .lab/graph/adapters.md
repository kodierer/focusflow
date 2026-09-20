# Adapters — Grok + Copilot

## Grok (Desktop / Bot)
- Liest `inbox/`, schreibt `outbox/` und Reports
- Nutzt Systemprompts aus `.lab/agents/`
- Orchestriert Phasen laut `state.json` + `graph/loop.md` + **`pipeline.md` (Zahnrad)**
- Schätzt Token/Aufwand grob in `state.budget` nach Rollen
- **Nach fertigem Report sofort nächste Rolle** — kein Chat-OK

## GitHub Copilot Chat
- Mensch + Pair am Code
- **Kein zweiter Chef**

## GitHub Copilot Coding Agent
- Standard Coding-Worker; TDD + autonom bis Push
- Nach Merge: Orchestrator dreht nächstes Zahnrad (tech_qa/synth)

## Ticket-Spur
- Jede Rolle dokumentiert in GitHub Issues — `ticket_documentation.md`

## Meta-Steward
- `agents/meta_steward.md` — prüft ob Handoffs hängen geblieben sind
