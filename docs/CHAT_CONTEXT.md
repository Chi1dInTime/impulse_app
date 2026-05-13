# Chat Context for Impulse App

This file preserves the useful context from the original Codex chat so a new Codex workspace opened at `C:\Users\vasal\Documents\impulse_app` can continue smoothly.

## Product Idea

The app is called **Impulse** / `Импульс`.

It is an Android-first motivation companion for a user with low/unstable motivation. It should help start the day, choose one meaningful action, adapt when energy is low, and make progress visible without guilt.

The app should not feel like a strict productivity tool. It should feel like a small external motivational system:

- reduce friction before action;
- reward small starts;
- preserve evidence that the user is doing real things;
- avoid shame, punishment, or broken-streak pressure;
- support low-energy days with substitutions.

## Core Vocabulary

Use these terms consistently:

- `Импульс дня` / Impulse of the day: a small 5-10 minute starter action for the morning.
- `Дело дня` / Daily task: the main planned action for today.
- `След дня` / Trace of the day: the historical record of what actually made the day count.
- `Квест` / Quest: any useful reusable activity that can earn points.
- `Надо бы` / Should-do: useful tasks not necessarily tied to a specific day.
- `Рутина` / Routine: small recurring chores or maintenance tasks.
- `Замена` / Alternative: a replacement action when the planned daily task feels too hard.
- `Награда` / Reward: a ritual, visual unlock, capsule, or celebration, not permission to live.

Important product distinction:

- In the present: the user works with `Дело дня`.
- In history/statistics: the completed result becomes `След дня`.
- `Импульс дня` is specifically the starter action, not the main daily task.

## Intended Morning Flow

The user wakes up and opens the app.

The app shows:

1. Recent progress from the last days/week.
2. A small `Импульс дня`, such as cleaning the kitchen for 5 minutes or paying a bill.
3. Immediate feedback after completion: points, checkmark, encouraging copy.
4. Today's `Дело дня`.
5. Other possible quests.
6. Alternatives if the user does not want or cannot do the planned daily task.

The user should be able to start without typing.

## Weekly Planning Concept

The app should support weekly planning with three layers:

- important `Дела дня`;
- a pool of `Надо бы`;
- small routine/impulse quests.

In the future, every quest should have difficulty, importance, duration, and energy cost. The app can then classify whether a quest is good as:

- an impulse;
- a daily task;
- an intermediate task;
- an alternative.

## Rewards

Points and rewards are not permissions. The user can buy coffee or rest without earning it. Rewards should make pleasant things feel more ritualized and satisfying.

Good reward types:

- visual progress;
- weekly cards;
- capsules;
- rituals;
- experiences;
- rest markers;
- gentle gamification.

Avoid copy like:

- `Ты провалил день.`
- `Ты отстал.`
- `Нельзя без очков.`
- `Серия сломана.`

Prefer:

- `День уже начал двигаться.`
- `Можно выбрать замену.`
- `Сегодня хватит легкой версии.`
- `Дело дня выполнено.`

## Technical Direction

The plan was initially written as a web/PWA plan, then changed by the user to native Android.

Current technical direction:

- Native Android app.
- Kotlin.
- Jetpack Compose.
- Material 3.
- Android Architecture Components.
- Room for local persistence later.
- DataStore later for small settings.
- JUnit for JVM tests.
- Compose UI tests for interaction flows.

Primary plan file:

- `docs/IMPULSE_PLAN.md`

Local issue document for Iteration 0:

- `docs/issues/0000-set-up-android-app-skeleton.md`

## Repository State

Current local path:

- `C:\Users\vasal\Documents\impulse_app`

GitHub repository:

- `https://github.com/Chi1dInTime/impulse_app`

Current branch:

- `codex/00-android-skeleton`

Current commit:

- `a9a21b5 Set up Android app skeleton`

Remote:

- `origin https://github.com/Chi1dInTime/impulse_app.git`

The branch `codex/00-android-skeleton` has been pushed to GitHub.

## Current Implementation

Iteration 0 has been implemented as an Android skeleton.

Files added include:

- `.gitignore`
- `README.md`
- `settings.gradle.kts`
- `build.gradle.kts`
- `gradle/libs.versions.toml`
- `app/build.gradle.kts`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/vasal/impulse/MainActivity.kt`
- `app/src/main/java/com/vasal/impulse/ImpulseApp.kt`
- `app/src/main/java/com/vasal/impulse/domain/AppInfo.kt`
- `app/src/main/java/com/vasal/impulse/ui/theme/Color.kt`
- `app/src/main/java/com/vasal/impulse/ui/theme/Theme.kt`
- Android resource files for strings, theme, colors, and placeholder launcher icon.
- `app/src/test/java/com/vasal/impulse/domain/AppInfoTest.kt`
- `app/src/androidTest/java/com/vasal/impulse/ImpulseAppTest.kt`
- `docs/IMPULSE_PLAN.md`
- `docs/issues/0000-set-up-android-app-skeleton.md`

The initial screen is a Compose skeleton with:

- title `Импульс`;
- weekly progress placeholder;
- `Импульс дня` card;
- `Дело дня` card;
- placeholder button.

## Verification Status

Git status after push was clean:

- `codex/00-android-skeleton...origin/codex/00-android-skeleton`

Automated Android tests and build have **not** been run yet because the current environment did not expose:

- `java`;
- `gradle`;
- Android SDK.

The user should install/open Android Studio, sync Gradle, and run:

```powershell
.\gradlew.bat test
.\gradlew.bat assembleDebug
.\gradlew.bat connectedAndroidTest
```

If the Gradle wrapper is missing, open/sync the project in Android Studio first.

## GitHub Issue and PR Status

GitHub connector can see the repo and the user:

- user login: `Chi1dInTime`;
- repo: `Chi1dInTime/impulse_app`;
- permissions showed admin/push metadata access.

However, creating GitHub issue and PR through the connector failed with:

- `403 Resource not accessible by integration`

This likely means the GitHub App integration lacks Issues/Pull Requests write permissions for this repository.

Until fixed:

- create issue/PR manually on GitHub, or
- update GitHub App permissions/install settings, then retry.

The code branch itself was pushed successfully.

## Folder Rename Context

Original folder:

- `C:\Users\vasal\Documents\New project`

The user renamed it to:

- `C:\Users\vasal\Documents\impulse_app`

The original chat/workspace may still point to the old folder at the UI level. New Codex sessions should be opened directly from `C:\Users\vasal\Documents\impulse_app`.

## Next Suggested Step

Open a new Codex workspace at:

- `C:\Users\vasal\Documents\impulse_app`

Then:

1. Check git status.
2. Open the project in Android Studio.
3. Let Gradle sync.
4. Fix any version/tooling issues from the initial Android skeleton.
5. Run build/tests.
6. If successful, ask the user to try it on phone/emulator.
7. After user says `ок`, establish `main` and close/merge the first iteration according to the workflow.

If GitHub issue/PR permissions are fixed, create the real GitHub issue and PR for Iteration 0 from the existing local issue document.

