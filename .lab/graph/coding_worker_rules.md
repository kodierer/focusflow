# Coding-Worker Rules (alle Issues)

Gilt für GitHub Copilot Coding Agent, Cline und Fallback `grok_build`.

1. **TDD:** zuerst Tests (rot), dann Code (grün), dann kleines Refactoring.
2. **Autonomie:** Issue → Branch → Tests/Code → lokal/CI grün → Push auf PR-Branch **ohne** Zwischenfragen an den Menschen.
3. **Entscheidungen:** innerhalb Issue-Ziel und Out-of-scope selbst treffen; in PR-Beschreibung kurz begründen.
4. **Hard-Blocker:** einmal im Issue/PR melden und stoppen (Secrets fehlen, CI-Infra down, widersprüchliche ACs).
5. **Ein Worker pro Ticket** — kein zweiter Implementierer parallel.
6. Quelle: `AGENTS.md`, `.github/copilot-instructions.md`, Issue-Template `lab-build`.
