# Closed testing for Play Production access

## Why this matters

Google Play requires a **personal developer account** to complete closed testing with **at least 12 testers** for **at least 14 consecutive days** before you can request **Production** access.

As of **2026-09-19**, this account is still behind that gate: Production is **not** unlocked yet. Finish closed testing first, then apply.

## Steps

1. **Create a closed testing track** in Play Console (Testing → Closed testing).
2. **Choose countries / regions** for the closed track (match where your testers live).
3. **Build an email list** of testers (Google accounts). You need **≥ 12** people who will actually opt in.
4. **Recruit 12+ testers** — friends and family Google accounts work fine.
5. **Share opt-in links** from the closed track. **Each tester must open the link and opt in**; being on the email list alone is not enough.
6. **Create a closed release**
   - Prefer **versionCode ≥ 5** (recommended).
   - Context: Internal testing previously showed **versionCode 3**; CI had uploaded **4**. Use a higher code for the closed release so it is clearly ahead of Internal.
7. **Start the 14-day clock** once ≥ 12 testers have opted in and the release is available to them. Keep the closed track active for the full period.
8. **Apply for Production** only after the 14-day closed-testing requirement is satisfied in Console.

## Tip

Use friends/family Google accounts. Remind every person to complete opt-in via the Play Console link — silent list membership does not count toward the 12.

## Do not assume Production is ready

Until Console shows the closed-testing requirement met and Production access is granted, do **not** treat Production as unlocked. This doc does not claim Production is available today.
