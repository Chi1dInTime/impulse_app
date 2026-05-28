# 0008. Show weekly history and progress

## Goal

Make the `Статистика` screen reflect real saved daily progress instead of demo data.

## Scope

- Add a history flow to the day progress store.
- Read persisted daily progress from Room.
- Show the current week with neutral, green, blue, and purple day states.
- Show weekly points from saved days.
- Show saved day traces.
- Let the user tap a week day and see what happened that day.
- Keep empty days gentle and neutral.

## Current Behavior

- `Статистика` observes saved day history.
- Completing an impulse can color the current week day green if the minimum is reached.
- Completing `Дело дня` shows the saved `След дня` in statistics.
- Empty days remain neutral.
- Tapping a day shows its points, completed impulse/daily task, and trace if present.

## Verification

- [x] `./gradlew.bat test`
- [x] `./gradlew.bat assembleDebug`
- [x] `./gradlew.bat :app:compileDebugAndroidTestKotlin`

## Notes

This stage shows real saved progress, but does not yet implement deeper charts, category balance, custom date navigation, or editable history.
