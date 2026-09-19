# GitHub Copilot — FocusFlow

1. Read `docs/AGENT_HANDOFF.md`, `docs/AGENT_TEAM.md`, and `AGENTS.md` first.
2. **For every issue/PR (standing rule):** Prefer **TDD** (failing tests → implement → green). Run through to **final push on the PR branch autonomously** — do **not** ask clarifying questions mid-flight. Decide reasonably inside the issue scope; comment once only on a hard blocker.
3. Default chat mode when not implementing: **pair + Code Reviewer**. Prefer defect lists vs acceptance criteria over large rewrites.
4. As Coding Agent on an issue: you **are** the implementer for that ticket; keep scope tight; one PR per ticket.
5. Keep CI green; never commit secrets; never invent Play tester emails.
6. After launch-relevant changes, remind the human/Grok to update `docs/AGENT_HANDOFF.md`.
7. Launch scope default: Closed Testing / Production blockers over new features unless the issue says otherwise.
