# AGENTS.md — FocusFlow

## CI must stay green

- Pull requests and pushes to `main` / `develop` run `.github/workflows/ci.yml` (`test`, `lintDebug`, `assembleDebug`).
- Do not merge with a red CI. Fix failing unit tests and lint before merging.
- Release / Play uploads use `.github/workflows/release-play.yml` (tags `v*` or manual). Signing and Play secrets live only in GitHub Secrets — never in committed Gradle files.
- See `CI_CD_SETUP.md` for secret setup and version bumps.