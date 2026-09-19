# Adapters — Grok + Copilot

## Grok (Desktop / Bot)
- Liest `inbox/`, schreibt `outbox/` und Reports
- Nutzt Systemprompts aus `.lab/agents/`
- Orchestriert Phasen laut `state.json` + `graph/loop.md`
- Schätzt Token/Aufwand grob in `state.budget` nach Rollen

## GitHub Copilot Chat
- Mensch + Pair am Code
- **Kein zweiter Chef** — folgt Issue/ACs, orchestriert nicht die Lab-Phase

## GitHub Copilot Coding Agent
- Standard Coding-Worker (`github_copilot`)
- PM/Engineer-Brief erzeugt Issue aus `.github/ISSUE_TEMPLATE/lab-build.md`
- Assignee: Copilot; Ergebnis: PR
- Nach Merge: `state.repo_ref` aktualisieren, `changelog.md` Eintrag
- Copilot-Requests grob in `spent_copilot_requests_est` schätzen

## Grok Build
- Nur Fallback wenn Copilot blockiert (`grok_build`)
- Kein OpenHands anlegen, außer Mensch fordert es

## Cline
- Bleibt Engineer-Option laut Team-Playbook; nicht parallel denselben Ticket-Scope wie Copilot Coding Agent
