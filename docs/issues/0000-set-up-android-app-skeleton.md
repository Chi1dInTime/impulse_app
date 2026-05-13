# Set up Android app skeleton

## Goal

Create the first native Android project skeleton for Impulse so future feature iterations have a working Compose foundation.

## User Story

As the user, I want to install or run the first app shell on an Android device/emulator so we can start adding one feature at a time.

## Scope

- Kotlin Android project structure.
- Jetpack Compose and Material 3 dependencies.
- Initial `MainActivity`.
- Initial Compose screen with app name, weekly progress placeholder, Impulse of the day, and Daily task placeholder.
- Basic theme and launcher placeholder icon.
- One JVM unit test.
- One Compose UI smoke test.
- README with phone testing instructions.

## Out of Scope

- Real persistence.
- Quest creation.
- Daily task completion behavior.
- GitHub remote issue/PR automation until the repository has a GitHub remote.

## Implementation Notes

- Start with one `app` module.
- Keep package-level modularity for `domain`, `feature`, `data`, and `ui` later.
- Use Android Gradle Plugin 9.1.0, matching current Android official release notes.
- Use Compose BOM 2026.04.01, matching current Android Compose documentation.

## Data Model Changes

None.

## UI Behavior

The app opens to a skeleton Today screen:

- App title: `Импульс`
- Progress placeholder.
- `Импульс дня` card.
- `Дело дня` card.

Buttons are placeholders until later iterations.

## Tests

- [x] Unit test for app metadata.
- [x] Compose UI smoke test for core labels.
- [ ] Local build/test run after Android Studio/JDK/SDK are available.

## Manual Acceptance Checklist

- [ ] App builds.
- [ ] App runs on emulator or phone.
- [ ] Initial screen is visible.
- [ ] Copy does not shame the user.

## Notes After Implementation

- Git was initialized locally.
- Current machine session does not expose Java, Gradle, or Android SDK in PATH yet.
- Open the project in Android Studio to sync dependencies and run the app.

