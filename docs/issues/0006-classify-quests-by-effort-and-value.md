# 0006. Classify quests by effort and value

## Goal

Help the task editor suggest a useful task type and point value from task metadata.

## Scope

- Add a pure `QuestRecommender`.
- Suggest quest kind from duration, difficulty, importance, and required strength.
- Suggest points from the same fields.
- Show the recommendation inside the `Дела` editor with the UI label `Силы`.
- Let the user apply the recommendation manually.
- Keep manual kind and points edits possible.

## Current Behavior

- New tasks start with a calculated default point value.
- The editor shows `Рекомендация` with suggested kind and points.
- The user can press `Применить` or keep their own values.

## Verification

- [x] `./gradlew.bat test`
- [x] `./gradlew.bat assembleDebug`
- [x] `./gradlew.bat :app:compileDebugAndroidTestKotlin`

## Notes

The recommendation is intentionally simple and transparent. It should be useful enough to reduce manual tuning, but not controlling.
