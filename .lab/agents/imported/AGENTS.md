# AGENTS.md — FocusFlow

## Team (Grok + Copilot + Cline)

- **Playbook:** [`docs/AGENT_TEAM.md`](docs/AGENT_TEAM.md) — Rollen, Gates, Parallel-Workflow.
- **Live status:** [`docs/AGENT_HANDOFF.md`](docs/AGENT_HANDOFF.md) — immer zuerst lesen und nach Launch-/Status-Änderungen aktualisieren.
- **You (human):** PO / Tech Lead / merge authority.
- **Cline:** Engineer only — implement, run tests, keep CI green.
- **Copilot:** pair programming + review pass (not self-approve of Cline’s own work).
- **Grok Bot:** orchestrator (Software-Lifecycle Standard 0–7), DevOps/Play/site; specialist Grok agents `FF-*` for PM/Arch/QA/Security/Review.

## CI must stay green

- PRs and pushes to `main` / `develop` run `.github/workflows/ci.yml` (`test`, `lintDebug`, `assembleDebug`).
- Do not merge with red CI.
- Release / Play: `.github/workflows/release-play.yml` — secrets only in GitHub Secrets.
- See `CI_CD_SETUP.md`.
