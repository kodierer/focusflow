# Play launch checklist (FocusFlow)

Checklist mapped to PRD stories **S1–S4**. Use this while finishing Console work and the first Production request.

## Store listing (S1–S4 discovery / presentation)

- [x] Draft listing prepared for **de-DE**: short/full texts, app icon, feature graphic, **4 screenshots**
- [ ] Contact email still open — set the public contact email in Play Console before Production

## Privacy

- [x] Privacy Policy URL: https://kodierer.github.io/focusflow/privacy.html

## App content declarations

- [ ] App content / questionnaire declarations — **in progress** in Play Console (complete before Production)

## Closed testing gate

- [ ] Closed testing track live with ≥ 12 opted-in testers for ≥ 14 days  
  See [CLOSED_TESTING.md](./CLOSED_TESTING.md). Production access for this personal account depends on this gate (state as of 2026-09-19: not yet unlocked).

## Production release

- [ ] Ship Production via **`.github/workflows/release-play.yml`** or promote a successful closed/internal build in Console

## CI / release automation notes

- CI is green after **desugaring** fixes.
- **`release-play`** workflow exists in the repo.
- Required Play / signing **secrets are set** for the workflow.

## Parallel work for humans

Play Console UI steps are largely **serial** (one form/screen at a time). Docs, code, and CI can run **in parallel** while someone works through Console. Coordinate so listing, privacy URL, declarations, and closed testing stay consistent with the build you promote.
