# AGENTS.md — FocusFlow

## Shared handoff (Copilot + Grok Bot)

- **Read first:** [`docs/AGENT_HANDOFF.md`](docs/AGENT_HANDOFF.md) — current status, next steps, role split.
- After meaningful work (Play, site, launch blockers, or code that changes launch status), **update that file** and commit.
- Older snapshot: `UEBERGABE_DOKUMENTATION.md` (May 2026 bugs) — verify before fixing; launch docs in `docs/` take precedence for Play work.

## CI must stay green

- Pull requests and pushes to `main` / `develop` run `.github/workflows/ci.yml` (`test`, `lintDebug`, `assembleDebug`).
- Do not merge with a red CI. Fix failing unit tests and lint before merging.
- Release / Play uploads use `.github/workflows/release-play.yml` (tags `v*` or manual). Signing and Play secrets live only in GitHub Secrets — never in committed Gradle files.
- See `CI_CD_SETUP.md` for secret setup and version bumps.
