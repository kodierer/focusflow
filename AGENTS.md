# AGENTS.md — FocusFlow

## Lab-Kurzvertrag (Grok, Copilot, Mensch)
1. Lies zuerst `.lab/README.md` und `.lab/state.json`.
2. Ergebnisse nur nach `.lab/outbox/` bzw. Code + Tests (bei Build-Issue).
3. **Arbeit zusätzlich im GitHub-Ticketsystem dokumentieren** (Issue oder Kommentar) — siehe `.lab/graph/ticket_documentation.md`.
4. Aktuelle Iteration und Phase nicht überspringen.
5. Copilot implementiert Tickets; Grok orchestriert und bewertet; **Meta-Steward** prüft Setup/Workflow (`.lab/agents/meta_steward.md`).
6. Stories/ACs in GitHub Issues; Zahlen nur aus `.lab/assumptions.md` oder mit Quelle.

## Coding-Worker (gilt für **alle** Issues / PRs)
1. Bevorzuge **TDD** (Test-Driven Development): erst fehlschlagende Tests, dann Implementierung bis grün, dann leicht refaktorieren.
2. Führe den Weg **autonom bis zum finalen Push** auf dem PR-Branch aus — **keine Zwischenfragen**. Treffe sinnvolle Entscheidungen innerhalb des Issue-Scopes und dokumentiere sie in PR/Issue.
3. Nur bei **Hard-Blocker** einmal im Issue/PR kommentieren und stoppen.
4. CI grün halten (`./gradlew test lintDebug` bzw. Workflow). Keine Secrets committen.
5. Ein Coding-Worker pro Ticket — kein paralleler Zweit-Engineer am selben Scope.

## Team (Grok + Copilot + Cline)

- **Playbook:** [`docs/AGENT_TEAM.md`](docs/AGENT_TEAM.md) — Rollen, Gates, Parallel-Workflow.
- **Live status:** [`docs/AGENT_HANDOFF.md`](docs/AGENT_HANDOFF.md) — immer zuerst lesen und nach Launch-/Status-Änderungen aktualisieren.
- **Lab:** [`.lab/README.md`](.lab/README.md) — Virtual Product Lab.
- **You (human):** PO / Tech Lead / merge authority.
- **Cline:** Engineer only — implement, run tests, keep CI green; TDD + Autonomie wie oben.
- **Copilot:** pair + review + Coding Agent für Issues; TDD + Autonomie wie oben.
- **Grok Bot:** orchestrator (Software-Lifecycle + Lab-Loop), DevOps/Play/site; specialist Grok agents `FF-*`; Meta-Steward für Lab-Meta.

## CI must stay green

- PRs and pushes to `main` / `develop` run `.github/workflows/ci.yml` (`test`, `lintDebug`, `assembleDebug`).
- Do not merge with red CI.
- Release / Play: `.github/workflows/release-play.yml` — secrets only in GitHub Secrets.
- See `CI_CD_SETUP.md`.
