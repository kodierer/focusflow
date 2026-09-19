# BLOCKED — Virtual Product Lab Setup

**Status:** stopped pending human input  
**Date:** 2026-09-19 (Europe/Berlin)  
**Agent:** FF-PM / setup runner

## Question
The setup Auftrag text was truncated after the heading **„Ordnerstruktur (anlegen)“**. The concrete folder/file tree (and any schemas for `state.json`, agent output formats, persona files, finance/market/synthesis contracts) did not arrive.

## Options
1. **Paste the remainder** of the Auftrag (Ordnerstruktur + any schemas/templates) into chat or as a file; setup continues and completes `.lab/` + `SETUP_COMPLETE.md`.
2. **Provide a reference repo/gist** with the canonical `.lab/` tree; we copy that contract.
3. **Authorize a minimal inferred tree** (risk: inventing filenames/schemas beyond the named stubs) — not recommended under Lab hard rules.

## Recommendation
Choose **1**: paste the full Ordnerstruktur section. Bootstrap findings are already in `.lab/BOOTSTRAP.md`. Named stubs that were unambiguous in the visible Auftrag can wait in a minimal scaffold until the tree arrives.

## Already known from visible Auftrag (do not invent beyond this)
- Source of truth: `.lab/`
- Files/dirs named: `BOOTSTRAP.md`, `assumptions.md`, `state.json`, `outbox/` (`SETUP_COMPLETE.md`, `BLOCKED.md`), `agents/imported/`, `agents/registry.md`
- Loop: build → simulated testers → optional market → synthesis → finance → occasional humans → build
- Hard rules 1–9 as in the Auftrag
