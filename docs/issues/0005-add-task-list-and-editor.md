# 0005. Add task list and editor

## Goal

Turn the `Дела` section into a real persistent task list.

## Scope

- Add a `quests` Room table.
- Add DAO/store abstractions for reusable tasks.
- Seed starter tasks once when the list is empty.
- Show the saved task list in `Дела`.
- Let the user add and edit a task.
- Include title, description, category, kind, duration, difficulty, importance, energy, and points.

## Current Behavior

- The `Дела` screen reads tasks from `TaskStore`.
- The real app uses Room through `RoomTaskStore`.
- Tests and previews use `InMemoryTaskStore`.
- The green `+` opens an inline task editor.
- Saved tasks appear in the list and can be opened again with `Изменить`.

## Verification

- [x] `./gradlew.bat test`
- [x] `./gradlew.bat assembleDebug`
- [x] `./gradlew.bat :app:compileDebugAndroidTestKotlin`

## Notes

This iteration keeps task editing inline inside the `Дела` screen. Dedicated navigation, deletion, search, and deeper planning behavior can come after the task model settles.
