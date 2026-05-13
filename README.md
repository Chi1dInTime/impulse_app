# Импульс

Native Android app for a small motivational loop:

- `Импульс дня`: a 5-10 minute starter action.
- `Дело дня`: the main planned action for today.
- `След дня`: what the completed day shows in history.
- `Квест`: any useful activity that can earn points.

## Current State

Iteration 0 creates the Android project skeleton:

- Kotlin Android project structure.
- Jetpack Compose + Material 3.
- One starter screen.
- One JVM unit test.
- One Compose UI smoke test.

## Requirements

Install Android Studio with:

- Android SDK Platform 36.
- Android SDK Build-Tools 36.0.0.
- JDK 17. Android Studio includes a compatible JDK.

The current terminal session may need a restart after installing Android Studio or Git so `java`, `gradle`, and `git` are visible in `PATH`.

## How to Try on a Phone

1. Install Android Studio.
2. Open this repository folder in Android Studio.
3. Let Gradle sync finish.
4. Connect your Android phone by USB.
5. On the phone, enable Developer options:
   - Settings -> About phone -> tap Build number 7 times.
   - Settings -> System -> Developer options -> enable USB debugging.
6. Approve the USB debugging prompt on the phone.
7. In Android Studio, select the phone as the run target.
8. Press Run.

Alternative: create an Android emulator in Android Studio Device Manager and run the app there.

## Useful Commands

After Android Studio/Gradle are available:

```powershell
.\gradlew.bat test
.\gradlew.bat assembleDebug
.\gradlew.bat connectedAndroidTest
```

If the Gradle wrapper is not present yet, open the project in Android Studio first and use its Gradle sync/setup tooling.

## Working Agreement

Every feature iteration should have:

- A GitHub issue.
- A separate branch named `codex/NN-short-feature-name`.
- Minimal implementation.
- Tests for changed behavior.
- A PR.
- Manual approval from the user before merge.

