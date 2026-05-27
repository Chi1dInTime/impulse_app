# 0007. Complete daily task and create day trace

## Goal

Let the Today screen complete the main `Дело дня` and show the first visible `След дня`.

## Scope

- Persist daily task completion in the current day state.
- Add points for the daily task once.
- Store a first trace title for the day.
- Show a `След дня` card after the daily task is completed.
- Keep impulse completion independent from daily task completion.
- Use the saved `дело дня` from the task list instead of hardcoded demo points.
- Hide replacement buttons until the replacement flow is implemented.

## Current Behavior

- `Выполнено` on `Дело дня` adds the task points.
- The daily task card becomes completed and disabled.
- The progress message changes to `След дня уже появился`.
- A `След дня` card appears with the completed task title.
- The state is persisted through Room and survives restart.
- If the user edits points for the saved `дело дня`, Today uses the edited value.
- `Замена` is not shown yet because the replacement flow is a later iteration.

## Verification

- [x] `./gradlew.bat test`
- [x] `./gradlew.bat assembleDebug`
- [x] `./gradlew.bat :app:compileDebugAndroidTestKotlin`

## Notes

This is still the first simple trace. A richer history screen, alternatives, notes, and category summaries remain later iterations.
