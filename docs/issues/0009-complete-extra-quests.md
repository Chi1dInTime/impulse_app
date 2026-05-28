# 0009. Complete extra quests

## Goal

Let the user keep going after the impulse and daily task by completing extra saved quests on Today.

## Scope

- Show extra quests on Today from the saved task list.
- Let the user complete each extra quest once per day.
- Add extra quest points to the day total.
- Persist completed extra quest ids and titles.
- Show completed extra quests in Statistics day details.

## Current Behavior

- Today shows non-impulse and non-daily tasks in `Ещё можно`.
- New tasks default to `гибкое`, so they naturally appear as extra quests.
- User-created impulse tasks can also appear in `Ещё можно`; only the currently shown `Импульс дня` is excluded to avoid duplication.
- Each extra quest has a `Сделано` button.
- Completing an extra quest adds its saved points and disables that quest button.
- The day progress meter and weekly statistics update from the new total.
- Statistics day details list completed extra quests.
- The week trace list is collapsed under `Следы недели` by default so the selected day card stays primary.

## Verification

- [x] `./gradlew.bat test`
- [x] `./gradlew.bat assembleDebug`
- [x] `./gradlew.bat :app:compileDebugAndroidTestKotlin`

## Notes

This makes blue and purple week colors reachable through additional useful actions. It does not yet add replacement suggestions or a richer activity log table.
