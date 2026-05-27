# 0004. Persist local activity data with Room

## Goal

Add the first local persistence layer so the app remembers today's progress after restart.

## Scope

- Add Room and KSP to the Android project.
- Create a `daily_progress` table for today's points and impulse completion flag.
- Add a small store interface so UI can use either Room or an in-memory implementation.
- Connect the `Сегодня` screen to the persisted store.
- Keep UI tests deterministic by using the in-memory store.

## Current Behavior

- The app stores today's impulse completion in Room through `RoomTodayProgressStore`.
- The `Сегодня` screen observes a `Flow<TodayProgressState>`.
- Completing the impulse adds points once and disables the action.
- Tests use `InMemoryTodayProgressStore`, so previous emulator data does not affect them.
- The active bottom navigation item is visually highlighted and covered by UI test semantics.

## Verification

- [x] `./gradlew.bat test`
- [x] `./gradlew.bat assembleDebug`
- [x] `./gradlew.bat :app:compileDebugAndroidTestKotlin`

## Notes

This iteration intentionally persists only the current day state. Task editing, reward editing, richer activity logs, and statistics history remain separate planned steps.
