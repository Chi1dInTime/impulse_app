# Complete impulse of the day

## Goal

Let the user complete the `Импульс дня` and receive immediate feedback in the Today screen.

## User Story

As the user, I want to tap `Сделано` on the impulse card and see that the day has started, so the app rewards a small first action without requiring typing or planning.

## Scope

- Add temporary in-memory completion state for the impulse.
- Add points to today's total after completion.
- Update the layered day progress meter from the new points total.
- Disable the impulse completion and replacement buttons after completion.
- Show a compact completion state in the impulse card.
- Add day progress calculation logic and unit tests.
- Keep all state in memory for now.

## Out of Scope

- Room persistence.
- Daily task completion.
- Task editing.
- Real activity log storage.
- Rewards claiming.

## Implementation Notes

- Use `rememberSaveable` in `TodayScreen` for temporary UI state.
- Add a pure `DayProgressCalculator` in `domain` so progress thresholds are testable.
- Keep copy short and non-shaming.

## Data Model Changes

No persistent data model changes.

Temporary UI state:

- `impulseCompleted`
- current points derived from base demo points plus impulse points
- `DayProgress` derived from current points

## UI Behavior

- Before completion, the impulse button says `Сделано`.
- After tapping it:
  - the impulse card shows `Готово`;
  - the support chip says `импульс выполнен`;
  - day points increase;
  - the layered progress meter updates;
  - Today copy says the day has started moving.

## Tests

- [x] Unit tests for day progress calculation
- [x] Compose UI test for completing impulse
- [ ] Manual emulator check

## Manual Acceptance Checklist

- [x] App builds
- [ ] App runs on emulator or phone
- [ ] Tapping `Сделано` updates the impulse card
- [ ] Points increase
- [ ] Day progress meter updates
- [ ] Copy does not shame the user

## Notes After Implementation

- Added `DayProgressCalculator` with unit tests.
- Added temporary `rememberSaveable` state for impulse completion.
- Completing the impulse increases demo points from 32 to 42.
- The impulse card changes to completed state after tapping `Сделано`.
- Verification:
  - `.\gradlew.bat test`
  - `.\gradlew.bat assembleDebug`
  - `.\gradlew.bat :app:compileDebugAndroidTestKotlin`
