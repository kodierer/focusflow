# Decide Rules

## SHIP
Wenn Schwellen laut `assumptions.md` erfüllt **UND** Finance `stop_ship` oder `continue` mit „Grenznutzen zu klein“ **UND** (`human_eval_due` false **ODER** letzte Human-Runde ohne neuen Blocker).

## KILL
Wenn 2 Iterationen ohne relevanten Score-Gewinn **ODER** Budget leer **ODER** Human widerspricht Simulation bei großem Pivot.

## SHRINK / continue_cheap
Weitermachen ja, aber Labor zu teuer/fett: **1** Fix, **3** Personas, **kein** Markt.

## HUMAN_EVAL
Wenn Iteration durch 2 teilbar **ODER** Simulation intern widersprüchlich **ODER** vor erstem ship.

## Change-Caps
- Normal: `max_changes_per_iteration` (3)
- continue_cheap: 1

## Budget
Ohne `cap_total` in assumptions/state: Finance empfiehlt kein teures continue → `ask_human`.
