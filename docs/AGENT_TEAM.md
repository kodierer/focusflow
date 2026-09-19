# Agenten-Entwickler-Team — FocusFlow

**Modus:** Software-Lifecycle **Standard (Phasen 0–7)**; Full (8–9) bei Bedarf.  
**Mensch:** Jörg-Frieder = **PO + Tech Lead + Merge-/Production-Authority**. Kein Agent ersetzt das.

**Brücke:** [`docs/AGENT_HANDOFF.md`](AGENT_HANDOFF.md) + Issues/PRs — kein Live-Chat-Sync zwischen Grok, Copilot und Cline.

---

## Tool-Zuordnung (parallel)

| Rolle | Wer | Wo | Darf | Darf nicht |
|-------|-----|-----|------|------------|
| **Orchestrator** | Grok Bot (`fuckedup`) | Grok Bot Chat | Phasen anstoßen, Gates, Handoff pflegen, Arbeit verteilen | Eigenen Code ungeprüft mergen |
| **Product Manager** | Grok: `FF-PM` | eigener Chat | What/Why, Stories, ACs, Out-of-scope | Dateinamen, Frameworks, Code |
| **Architect** | Grok: `FF-Architect` | eigener Chat | Module, Interfaces, ADRs, Trade-offs | Feature-Code |
| **Engineer** | **Cline** (primär) | VS Code | Implementieren, lokale Builds, Tests schreiben, CI grün | Architektur still ändern; Secrets committen |
| **Pair / Suggest** | **GitHub Copilot** | VS Code | Autocomplete, kleine Fixes, Test-Skizzen, Diff-Fragen | Allein Gate „Review PASS“ für eigenen Cline-Diff |
| **QA** | Grok: `FF-QA` (+ Cline führt Tests aus) | Grok / VS Code | ACs → Testfälle; Fail → zurück an Engineer | Features erfinden |
| **Security Reviewer** | Grok: `FF-Security` | eigener Chat | Auth, Secrets, Abuse, Deps | Features bauen |
| **Code Reviewer** | Grok: `FF-Reviewer` **oder** Copilot Chat (Review-Pass) | Grok / VS Code | Unabhängiger Check vs PRD/Arch/Tests | Eigenen Implementierungs-Diff freigeben, den man selbst geschrieben hat |
| **DevOps** | Grok Bot + Cline (YAML) | Grok / VS Code | CI, Play/Release-Workflows, Hosting-Orchestrierung | Produkt-Features |
| **Tech Writer** | Grok Bot oder Copilot | — | README, Changelog, Store-Texte | Ungeprüfte Claims |

**Separationsregel:** Implementierer ≠ Reviewer. Cline-Code → Review durch `FF-Reviewer` und/oder Copilot-Review-Pass, nicht „Cline sagt LGTM“.

---

## Parallel-Workflow (Alltag)

1. **Du** gibst Ziel / Issue (oder Orchestrator schlägt Phase vor).
2. **Orchestrator (Grok)** setzt Phase + Gate, aktualisiert `AGENT_HANDOFF.md`.
3. Bei Need: **FF-PM** / **FF-Architect** liefern Artefakt → du (oder Orchestrator) Gate PASS/FAIL.
4. **Cline:** „lies `docs/AGENT_HANDOFF.md` + Issue #N und implementiere …“ (YOLO/auto-approve wie gewünscht).
5. **Copilot:** parallel Vorschläge; am Ende explizit *Review* des Diffs anfordern.
6. **FF-QA** / **FF-Security** / **FF-Reviewer:** kurze, getrennte Passes.
7. **Du** mergst / Play-Submit.

Prompts für VS Code (Kopiervorlage):
```text
Cline: Read docs/AGENT_HANDOFF.md and docs/AGENT_TEAM.md. You are Engineer only.
Implement <ticket>. Do not change architecture silently. Keep CI green.
```

```text
Copilot: Read docs/AGENT_HANDOFF.md. Act as Code Reviewer on the current diff.
List defects vs ACs; do not rewrite the whole feature unless asked.
```

---

## Phasen-Gates (Kurz)

| Phase | Gate | Typischer Owner |
|-------|------|-----------------|
| 0 Discovery | messbares Problem, Scope | FF-PM + du |
| 1 Product | Stories mit ACs | FF-PM |
| 2 Architecture | ADRs, Interfaces | FF-Architect |
| 3 Design | implementierbare Contracts | FF-Architect |
| 4 Implementation | folgt Arch/Contracts | Cline |
| 5 Tests | Happy + Failures | FF-QA + Cline |
| 6 Security | keine offenen Kritischen | FF-Security |
| 7 Review | Approve oder Defect-Liste | FF-Reviewer / Copilot |
| 8–9 DevOps/Obs | optional Full | Grok + Cline |

Max. 3 Iterationen Tests/Security/Review, dann Eskalation an dich.

---

## FocusFlow jetzt (Launch-Scope)

Aktuell **kein Feature-Sprint** — Closed Testing / Production. Orchestrator + DevOps (Grok) führen; Cline nur bei Code-/CI-Blockern; Copilot Review bei Diffs.

Details: `AGENT_HANDOFF.md`.
