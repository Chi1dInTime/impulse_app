# Add Today screen shell

## Goal

Build the first real Today screen shell using demo data and the approved design direction.

## User Story

As the user, I want to open the app and immediately see today's momentum, a small impulse, the daily task, and a few extra options, so the app feels useful before persistence and real planning are implemented.

## Scope

- Replace the placeholder starter screen with a Today screen shell.
- Add compact layered day progress using demo points.
- Add `Impulse of the day`, `Daily task`, and `More possible quests` sections.
- Add a bottom navigation shell with `Today`, `Tasks`, `Stats`, and `Rewards`.
- Keep all data static/demo-only.
- Update smoke tests for the visible labels.

## Out of Scope

- Real navigation between tabs.
- Room persistence.
- ViewModel state.
- Real scoring.
- Completing quests.
- Creating or editing tasks/rewards.

## Implementation Notes

- Keep `ImpulseApp` as the root Compose entry point.
- Put Today UI in `feature/today`.
- Use existing Material 3 dependencies.
- Avoid adding Navigation Compose until destinations become real screens.

## Data Model Changes

None. UI state is local demo data only.

## UI Behavior

- Today opens by default.
- The day progress card shows one layered meter:
  - green for minimum useful day progress;
  - blue over green for stronger day progress;
  - purple over blue for very productive day progress.
- Bottom navigation labels are visible but inactive except for the selected Today tab.

## Tests

- [x] JVM unit test for app metadata
- [x] Compose UI smoke test for Today labels added
- [x] Compose UI test source compiles

## Manual Acceptance Checklist

- [x] App builds
- [x] App runs on emulator or phone
- [x] Today screen matches the approved design direction
- [x] Layered day progress is visible and compact
- [x] Bottom navigation is visible
- [x] Copy does not shame the user

## Notes After Implementation

- Added `feature/today/TodayScreen.kt` with static demo data.
- Added compact layered day progress with green, blue, and purple layers.
- Added bottom navigation shell with visible core destinations.
- Updated app metadata strings to readable Russian.
- Verification:
  - `.\gradlew.bat test`
  - `.\gradlew.bat assembleDebug`
  - `.\gradlew.bat :app:compileDebugAndroidTestKotlin`
- Manual emulator check passed by the user.
- Follow-up: reduce explanatory copy in cards, especially the day progress helper text.
