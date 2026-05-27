# Add core app section shells

## Goal

Make the bottom navigation real and let the user see all main sections early.

## User Story

As the user, I want to tap `Сегодня`, `Дела`, `Статистика`, and `Награды`, so the app feels like a coherent product rather than a single isolated Today screen.

## Scope

- Make bottom navigation tabs selectable.
- Allow horizontal swipes between core sections.
- Add demo shell screens for Tasks, Stats, and Rewards.
- Keep Today as the default section.
- Tasks shell shows a preview list and add/edit affordance.
- Stats shell shows a demo week with green, blue, purple, and neutral days.
- Rewards shell shows custom reward previews and add/edit affordance.
- Remove overly explanatory helper copy from the Today progress card.
- Keep all section data static/demo-only.

## Out of Scope

- Real task editing.
- Real stats aggregation.
- Real reward claiming.
- Room persistence.
- Navigation Compose.
- Back-stack behavior.

## Implementation Notes

- Use local Compose state in `ImpulseApp` for selected bottom navigation section.
- Keep section shells in `feature/tasks`, `feature/stats`, and `feature/rewards`.
- Continue avoiding Navigation Compose until nested screens or back stack behavior are needed.

## Data Model Changes

None. All new section content is static demo data.

## UI Behavior

- `Сегодня` opens by default.
- Tapping `Дела` shows the Tasks shell.
- Tapping `Статистика` shows the Stats shell.
- Tapping `Награды` shows the Rewards shell.
- Swiping left or right moves between adjacent sections.
- Bottom navigation keeps the selected tab highlighted.

## Tests

- [x] Compose UI smoke test for Today labels
- [x] Compose UI test for switching section shells
- [ ] Manual emulator check

## Manual Acceptance Checklist

- [x] App builds
- [ ] App runs on emulator or phone
- [ ] All four bottom navigation tabs can be opened
- [ ] Each section communicates what it will become
- [ ] No section pretends to save data yet
- [ ] Copy does not shame the user

## Notes After Implementation

- Added selectable bottom navigation state in `ImpulseApp`.
- Added horizontal pager navigation for left/right swipes between sections.
- Added demo shell screens for `Дела`, `Статистика`, and `Награды`.
- Kept section content static/demo-only.
- Removed the long explanatory helper line from the Today progress card.
- Changed the Tasks add action to a compact `+`.
- Renamed `Неделя видна` to `Прогресс недели` and centered weekday labels.
- Addressed user feedback from manual emulator review.
- Verification:
  - `.\gradlew.bat test`
  - `.\gradlew.bat assembleDebug`
  - `.\gradlew.bat :app:compileDebugAndroidTestKotlin`
