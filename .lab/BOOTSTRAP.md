# Virtual Product Lab — BOOTSTRAP

**Repo:** `kodierer/focusflow` · **Branch scanned:** `main` @ `230633ae` · **Date:** 2026-09-19 (Europe/Berlin)  
**Scanner:** FF-PM (Grok) via GitHub API (no local clone)

## What exists

### Product / stack
- Android Pomodoro timer app **FocusFlow** (`com.kodierer.focusflow`)
- **Kotlin** + Jetpack Compose + Material 3; Gradle (`build.gradle.kts`, `settings.gradle.kts`, `app/`)
- Target: Android API 24+
- Package / site: Website https://friedermossmann.de/ · Privacy on GitHub Pages (`docs/privacy.html`)

### Agent / orchestration (keep — do not replace)
- `AGENTS.md` — Grok + Copilot + Cline roles
- `docs/AGENT_TEAM.md` — Software-Lifecycle Standard 0–7, role matrix
- `docs/AGENT_HANDOFF.md` — live handoff (Closed Testing / Production priority)
- `.github/copilot-instructions.md` — Copilot pair + reviewer mode
- Grok specialists already exist outside repo: Orchestrator `fuckedup`, `FF-PM`, `FF-Architect`, `FF-QA`, `FF-Security`, `FF-Reviewer`; Engineer = Cline; Pair/Review = Copilot

### GitHub / CI
- `.github/workflows/` (CI + release-play per AGENTS.md)
- `.github/ISSUE_TEMPLATE/`
- Open launch issues: #2 Closed Testing, #3 Production, #4 Testers, #5 EN Listing
- Convention: User Stories + ACs live in **GitHub Issues**, not chat dumps

### Docs / launch
- `docs/CLOSED_TESTING.md`, `docs/PLAY_LAUNCH_CHECKLIST.md`, `docs/WIKI_HOME.md`
- Many root status/playstore markdowns (historical); launch scope currently prefers compliance over new features

### Tests
- Documented in `TEST_SUITE.md`, `TESTS_COMPLETE.txt`, `QUICK_TEST.md`
- CI runs `test`, `lintDebug`, `assembleDebug` (per AGENTS.md)

### Not present
- No `.lab/` directory yet
- No LangGraph / Virtual Product Lab contract files yet

## What to adopt into `.lab/`
1. **Import (copy, do not delete originals)** into `.lab/agents/imported/`:
   - `AGENTS.md`
   - `docs/AGENT_TEAM.md`
   - `docs/AGENT_HANDOFF.md`
   - `.github/copilot-instructions.md`
2. Link them from `.lab/agents/registry.md` (Grok FF-* + Cline + Copilot + Orchestrator).
3. Wire lab loop to existing Issues (#2–#5) for build tickets; Copilot/Cline implement via Issues/PRs only when lab `auftrag` = `build`.
4. Seed `.lab/assumptions.md` with **unknown** for market/revenue metrics (README contains unverified market claims — do not treat as facts).
5. Current product phase context: Play Closed Testing / Production before feature sprints (see HANDOFF).

## Explicit non-goals for bootstrap
- Do not change app product code in this setup PR unless `auftrag` is `build` (it is not).
- Do not invent market sizes, revenue, or user counts.
- Do not erase existing agent playbooks.


## Importiert nach `.lab/agents/imported/`
| Datei | Original |
|-------|----------|
| `AGENTS.md` | `/AGENTS.md` (Root wird zusätzlich um Lab-Kurzvertrag ergänzt) |
| `AGENT_TEAM.md` | `/docs/AGENT_TEAM.md` |
| `AGENT_HANDOFF.md` | `/docs/AGENT_HANDOFF.md` |
| `copilot-instructions.md` | `/.github/copilot-instructions.md` |

Bestehende Grok-Rollen (FF-*, Orchestrator), Cline und Copilot bleiben — Lab verdrahtet sie, ersetzt sie nicht.
