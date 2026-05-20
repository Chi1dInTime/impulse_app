# Impulse Android App Plan

## TLDR for Humans

We will build **Impulse** as an Android-first phone app. The first version is local-first: it works on the phone without accounts, servers, or cloud sync.

Core language:

- **Impulse of the day**: a small 5-10 minute starter action that helps the day begin.
- **Daily task**: the main planned action for today.
- **Trace of the day**: what the completed day shows in history, whether it matched the plan or was a good alternative.
- **Quest**: any useful activity that earns points and can later be classified by difficulty, importance, duration, and energy cost.

Working process:

- Every iteration is a small feature that can be built, installed, and tried on an Android emulator or phone.
- Every iteration gets its own GitHub issue, branch, tests, implementation notes, and PR.
- We merge to the main branch only after manual approval from the user.
- We keep choices popular, maintained, testable, and easy to extend.

Recommended Android stack:

- Kotlin.
- Jetpack Compose for UI.
- Material 3 for the design system.
- Android Architecture Components: ViewModel, StateFlow, repository pattern.
- Room for local structured storage.
- DataStore later for small settings.
- JUnit for pure logic tests.
- Room tests for persistence.
- Compose UI tests for interaction flows.
- Android Studio/Gradle build as the source of truth.

First playable milestones:

1. Android project skeleton with one Compose screen.
2. Today screen with layered day progress, impulse, daily task, and quest list.
3. Complete an Impulse of the day and earn points.
4. Save data locally with Room.
5. Complete a Daily task and generate a Trace of the day.
6. Quest list and editor with difficulty/importance/energy fields.
7. Weekly planning screen.
8. Alternative suggestions for low-energy days.
9. Progress/history screen with colored day levels.
10. Custom rewards and capsules.

## Detailed Instructions for Agents

### Product Intent

Impulse is not a guilt-based productivity app. Its job is to reduce friction, make small starts rewarding, and preserve evidence that the user is doing real things. The app should treat low-energy days as a normal state, not as a failure.

The first version should prioritize:

- A pleasant phone-first morning flow.
- Fast manual logging with one thumb.
- Visible progress.
- Flexible substitution.
- Minimal cognitive load.
- Offline reliability.

Do not build a generic task manager first. Build the motivational loop first.

### Current Design Direction

The current visual reference lives at:

- `docs/design/impulse-mobile-mockups.html`

The app should use four primary destinations in a bottom navigation bar:

- `Today`: day progress, impulse of the day, daily task, and quick extra quests.
- `Tasks`: quest list, adding/editing tasks, importance, difficulty, energy, and points.
- `Stats`: day traces, weekly overview, category balance, and point history.
- `Rewards`: custom user rewards, reward cost, reward type, and claimed reward history.

The first screen should show current day progress, not weekly progress. Weekly progress belongs in Stats.

Day progress should be a compact point-based layered meter:

1. Green fills from start to end for the minimum useful day.
2. After the green level is full, blue fills from the start on top of green for a stronger day.
3. After the blue level is full, purple fills from the start on top of blue for a very productive day.

The weekly Stats view should color each day by the highest reached day level:

- green: minimum useful day completed;
- blue: stronger day completed;
- purple: very productive day completed;
- neutral: no visible trace yet.

Rewards must be user-configurable. The user should be able to create and edit rewards with a title, type, cost in points, and description. Rewards remain celebratory markers, not permission gates.

### Vocabulary

Use these terms consistently in UI, code, issues, and tests:

- `Impulse`: the app name.
- `Impulse of the day`: a small starter action, usually 5-10 minutes.
- `Daily task`: the main planned action for a given day.
- `Trace of the day`: the historical summary of what actually made the day count.
- `Quest`: a reusable activity template or concrete activity instance.
- `Should-do`: a pool of useful tasks that are not necessarily tied to a day.
- `Routine`: small recurring chores or maintenance actions.
- `Alternative`: a replacement action offered when the planned daily task is too hard.
- `Reward`: a visual, ritual, or game-like acknowledgement; not a permission system.

Russian UI labels:

- App: `Импульс`
- Impulse of the day: `Импульс дня`
- Daily task: `Дело дня`
- Trace of the day: `След дня`
- Quest: `Квест`
- Should-do: `Надо бы`
- Routine: `Рутина`
- Alternative: `Замена`
- Reward: `Награда`

### Technical Baseline

Use a native Android architecture first. Avoid backend work until the product loop is proven.

Preferred stack:

- Language: Kotlin.
- UI: Jetpack Compose.
- Design system: Material 3 for Compose.
- Navigation: Navigation Compose, added when the second real screen appears.
- Architecture: unidirectional data flow from repositories/use cases to ViewModels to Compose UI state.
- Persistence: Room for quests, plans, logs, traces, and rewards.
- Settings: DataStore later for small preferences such as theme, first-run flags, and notification preferences.
- Async: Kotlin coroutines and Flow.
- Dependency injection: start with manual dependency wiring; add Hilt when repositories, database, and ViewModels become annoying to wire by hand.
- Dates: use `java.time` types and small helper functions. Add a dedicated date/time abstraction only when recurring schedules and calendar integration require it.

Why this baseline:

- Jetpack Compose is Google's modern Android UI toolkit.
- The Android architecture guide recommends layered architecture, unidirectional data flow, and state holders.
- Room is the recommended abstraction over SQLite for non-trivial structured local data.
- DataStore is suitable for small key-value or typed settings, not the main app database.
- Hilt is the standard Android dependency injection option when manual wiring becomes too noisy.
- Compose UI testing is designed for finding UI nodes, asserting state, and performing user actions.

Reference docs:

- Jetpack Compose: https://developer.android.com/develop/ui/compose/documentation
- Android app architecture: https://developer.android.com/topic/architecture
- Room: https://developer.android.com/room
- DataStore: https://developer.android.com/datastore
- Hilt: https://developer.android.com/training/dependency-injection/hilt-android
- Compose testing: https://developer.android.com/develop/ui/compose/testing

### Repository Workflow

Each feature iteration must follow this flow:

1. Create a GitHub issue before implementation.
2. Write the issue with:
   - Goal.
   - User story.
   - Scope.
   - Out of scope.
   - Data model changes.
   - UI behavior.
   - Test plan.
   - Manual acceptance checklist.
3. Create a new branch from the main branch.
4. Implement the smallest useful version.
5. Add or update tests in the same branch.
6. Run relevant tests and record the result in the issue or PR.
7. Open a PR with a concise implementation summary.
8. Build and install/run the local Android app so the feature can be tried by hand.
9. Wait for user approval: the user says `ок`.
10. Merge into the main branch only after approval.
11. Close the issue after merge.

Branch naming:

- Use `codex/NN-short-feature-name`.
- Example: `codex/01-android-skeleton`.

Commit style:

- Keep commits focused.
- Prefer one implementation commit per small iteration unless test/setup commits are clearer separately.
- Example: `Add Android project skeleton`.

PR style:

- Title: feature-focused and human-readable.
- Body:
  - What changed.
  - How to test manually on emulator/phone.
  - Automated tests run.
  - Screenshots or screen recordings when UI changed.
  - Linked issue.

Important local note:

- If Git CLI is unavailable in the current terminal, restart the terminal/Codex after Git installation or add Git to PATH.
- GitHub connector can help with GitHub issues and PR metadata, but local Android feature work still benefits from a working Git CLI.

### Android Project Shape

Use a single Android app module at first:

```text
app/
  src/main/
    java/.../impulse/
      MainActivity.kt
      ImpulseApp.kt
      core/
      data/
      domain/
      feature/today/
      feature/planning/
      feature/history/
      feature/rewards/
      ui/theme/
  src/test/
  src/androidTest/
```

Start modular inside packages, not Gradle modules. Split into multiple Gradle modules only when the codebase has enough size to justify the extra build and dependency complexity.

Layering:

- `data`: Room entities, DAOs, database, repository implementations.
- `domain`: models, scoring, classification, suggestion, trace-generation logic.
- `feature/*`: ViewModels and Compose screens.
- `ui/theme`: Material theme, colors, type, reusable UI components.
- `core`: date helpers, result wrappers, common utilities.

### Product Model

Start with these entities. Keep fields explicit and boring.

#### Quest

A reusable thing the user can do.

Fields:

- `id`
- `title`
- `description`
- `category`: `body | home | work | recovery | social | admin | fun`
- `defaultDurationMinutes`
- `points`
- `difficulty`: `1-5`
- `importance`: `1-5`
- `energyCost`: `1-5`
- `kind`: `impulse | daily | should_do | routine | flexible`
- `isArchived`
- `createdAt`
- `updatedAt`

Future behavior:

- A quest can be automatically suggested as an impulse, daily task, intermediate task, or alternative based on difficulty, importance, duration, and energy.

#### DailyPlan

A plan for a calendar date.

Fields:

- `id`
- `date`
- `dailyTaskQuestId`
- `impulseQuestId`
- `status`: `planned | adjusted | completed | skipped`
- `notes`
- `createdAt`
- `updatedAt`

#### ActivityLog

An actual completed action.

Fields:

- `id`
- `questId`
- `date`
- `completedAt`
- `pointsEarned`
- `durationMinutes`
- `completionType`: `impulse | daily_task | alternative | routine | manual`
- `energyBefore`: `low | medium | high | unknown`
- `note`

#### DayTrace

A generated or user-edited historical summary for one date.

Fields:

- `id`
- `date`
- `title`
- `sourceActivityLogId`
- `plannedDailyTaskQuestId`
- `resultType`: `as_planned | alternative | rescued | empty`
- `pointsTotal`
- `categories`
- `createdAt`
- `updatedAt`

#### Reward

A reward is not permission to live. It is a ritual, visual unlock, capsule, or chosen pleasant marker.

Fields:

- `id`
- `title`
- `description`
- `costPoints`
- `kind`: `ritual | capsule | visual | experience | rest`
- `status`: `available | claimed | archived`
- `isCustom`
- `createdAt`
- `updatedAt`

Users must be able to add and edit their own rewards. Seeded rewards are examples only, not fixed product rules.

### Scoring Principles

The first scoring formula should be simple:

- User-set base points per quest.
- Suggested default points can be derived from importance, difficulty, and duration.
- Completing the daily task gives a bonus.
- Completing the impulse gives a small startup bonus.
- Completing an alternative gives full respect, but the trace should record that it was an alternative.

Day progress should map total points to three positive levels:

- green: the minimum useful day is complete;
- blue: the user went beyond the minimum with stronger or additional actions;
- purple: the user had a very productive day, usually with important or difficult actions.

The exact thresholds can start as constants and become configurable later.

Avoid punishment mechanics:

- No broken streak shame.
- No red failure walls.
- No "you are behind" language.
- Prefer "change route", "save the day", "lighter version".

Reward language:

- Good: `Награда открыта`, `Можно забрать капсулу`, `Неделя стала видимой`.
- Avoid: `Ты заслужил право`, `Нельзя без очков`, `Провалено`.

### Phone UX Principles

Morning screen flow:

1. Show today's point progress first.
2. Offer one `Impulse of the day`.
3. After completion, give points, visual feedback, and a short encouraging line.
4. Show the `Daily task`.
5. Offer lighter versions or alternatives.
6. Keep other quests visible but secondary.

Phone-specific rules:

- Primary actions must be thumb-friendly.
- Do not require typing for the first morning win.
- Make completion possible in one tap plus optional note.
- Use bottom navigation for the four core destinations: Today, Tasks, Stats, Rewards.
- Prefer bottom sheets for quick actions and alternatives.
- Keep progress visible without making the screen feel like a dashboard spreadsheet.
- Support dark theme early because the app may be opened right after waking up.

Task management flow:

1. Show a searchable list of quests/tasks.
2. Let the user add and edit tasks outside the morning flow.
3. Each task should expose importance, difficulty, energy cost, duration, points, and kind.
4. The Today screen can use this data for suggestions, but should not become the main editing surface.

Weekly planning flow:

1. Pick daily tasks for the week.
2. Maintain a `Should-do` pool.
3. Maintain a routine/impulse pool.
4. Let the app suggest reasonable placement.
5. Let the user override everything.

History flow:

1. Show a week as human-readable traces, not only charts.
2. Color week days by the best day level reached: green, blue, purple, or neutral.
3. Show category balance.
4. Show points and rewards as secondary reinforcement.
5. Let the user open a day and see what actually happened.

Rewards flow:

1. Show available points.
2. Show custom user rewards.
3. Let the user add/edit reward title, type, description, and point cost.
4. Let the user claim rewards as rituals or visual markers.
5. Avoid language that implies the user is not allowed to rest or enjoy something without earning it.

Design direction:

- Quiet, useful, warm.
- Native Android feel.
- Dense enough for repeated daily use.
- No guilt aesthetics.
- Stable dimensions for progress widgets, daily cards, and quick actions.
- Cards are for individual items, not every section.

### Iteration Plan

#### Iteration 0: Repository and Android Tooling Setup

Goal:

- Create the native Android app skeleton and make build/test commands work.

Scope:

- Initialize a Kotlin Android project.
- Add Jetpack Compose and Material 3.
- Add one `MainActivity`.
- Add a basic Compose theme.
- Add one smoke UI screen.
- Add JUnit test setup.
- Add Compose UI test setup.
- Add README with Android Studio and Gradle commands.

Manual acceptance:

- The app builds.
- The app runs on an Android emulator or connected phone.
- The initial screen renders.
- Test commands pass.

Automated tests:

- One JVM unit test.
- One Compose UI smoke test.

Issue title:

- `Set up Android app skeleton`

#### Iteration 1: Today Screen Shell

Goal:

- Make the first real phone screen visible with demo data.

Scope:

- Today screen layout.
- Compact layered day progress meter.
- Impulse of the day card.
- Daily task card.
- Secondary quest list.
- Bottom navigation shell with Today, Tasks, Stats, and Rewards labels.
- Demo data only, no persistence yet.

Manual acceptance:

- User can open the app and understand the morning flow.
- Day progress feels like today's momentum, not weekly history.
- Screen feels usable on a phone viewport.
- No real action needs to persist yet.

Automated tests:

- Compose UI test for main labels.
- Screenshot or emulator smoke check if available.

Issue title:

- `Add Today screen shell`

#### Iteration 2: Complete Impulse of the Day

Goal:

- Let the user complete a small starter action and receive immediate feedback.

Scope:

- Complete button for impulse.
- Points update in UI state.
- Visual completion state.
- Temporary in-memory activity log.
- Encouraging completion message.

Manual acceptance:

- Tapping complete marks the impulse done.
- Points increase.
- The day feels started.

Automated tests:

- Unit test for scoring helper.
- ViewModel state test.
- Compose UI test for completing impulse.

Issue title:

- `Complete impulse of the day`

#### Iteration 3: Local Persistence with Room

Goal:

- Preserve quests, plans, and logs across app restarts.

Scope:

- Add Room database.
- Add entities and DAOs for quests, daily plans, and activity logs.
- Add repository interface and implementation.
- Seed default quests only on first run.
- Add a debug-only reset action if useful.

Manual acceptance:

- Complete an impulse, restart the app, and see it remains completed.

Automated tests:

- DAO tests.
- Repository tests.
- ViewModel persistence test where practical.

Issue title:

- `Persist local activity data with Room`

#### Iteration 4: Daily Task Completion

Goal:

- Let the user complete the main planned action for the day.

Scope:

- Daily task completion button.
- Daily task points and bonus.
- Day status update.
- First version of day trace generation.

Manual acceptance:

- Completing the daily task creates a visible trace for today.

Automated tests:

- Scoring tests for daily bonus.
- Trace generation tests.
- Compose UI test for completing daily task.

Issue title:

- `Complete daily task and create day trace`

#### Iteration 5: Task List and Editor

Goal:

- Let the user manage reusable quests and tasks.

Scope:

- Add Tasks screen.
- Add quest/task list with search or category filters.
- Add task creation and editing.
- Fields: title, category, duration, difficulty, importance, energy cost, points, and kind.
- Keep persistence if Room already exists by this iteration.

Manual acceptance:

- User can create a task and see it in the task list.
- User can edit importance and difficulty.
- Today screen remains focused on action, not task administration.

Automated tests:

- Quest validation tests.
- ViewModel task list tests.
- Compose UI create/edit task flow.

Issue title:

- `Add task list and editor`

#### Iteration 6: Quest Classification

Goal:

- Classify quests using difficulty, importance, duration, and energy.

Scope:

- Simple recommendation label: impulse/daily/intermediate/alternative.
- Suggested default points.
- Suggested use cases for Today screen.

Manual acceptance:

- User can see whether a quest is better as an impulse, daily task, intermediate task, or alternative.

Automated tests:

- Classification helper tests.
- Scoring default tests.
- Compose UI classification label smoke test.

Issue title:

- `Classify quests by effort and value`

#### Iteration 7: Weekly Planning

Goal:

- Let the user plan the main actions for the week.

Scope:

- Add planning screen.
- Seven day slots.
- Assign existing quests as daily tasks.
- Show `Should-do` and routine pools.
- Keep editing minimal: select from existing quests first.

Manual acceptance:

- User can assign a daily task to each day of the week.
- Today screen reflects today's selected task.

Automated tests:

- Date/week helper tests.
- ViewModel planning state tests.
- Compose UI test for assigning a task.

Issue title:

- `Plan daily tasks for the week`

#### Iteration 8: Alternatives and Lighter Versions

Goal:

- Help the user adapt when the planned daily task is too hard.

Scope:

- Energy selector: low/medium/high.
- Alternative suggestions from quest pool.
- Bottom sheet for replacements.
- Complete an alternative instead of the planned daily task.
- Trace records `alternative`.

Manual acceptance:

- User can say energy is low and choose a replacement.
- The app records that the day was counted by alternative.

Automated tests:

- Suggestion helper tests.
- Trace result tests.
- Compose UI alternative completion flow.

Issue title:

- `Suggest alternatives for low-energy days`

#### Iteration 9: History and Progress

Goal:

- Make progress visible across days and weeks.

Scope:

- History screen.
- Week summary.
- Colored day levels: green, blue, purple, neutral.
- Day traces list.
- Category balance.
- Points over time.

Manual acceptance:

- User can see what made each recent day count.
- User can distinguish minimum, strong, and very productive days by color.
- Empty days are shown gently.

Automated tests:

- Aggregation helper tests.
- ViewModel tests for summary states.
- Compose UI history navigation flow.

Issue title:

- `Show weekly history and progress`

#### Iteration 10: Rewards

Goal:

- Add configurable rewarding loops without turning rewards into permission.

Scope:

- Reward list.
- Add and edit custom rewards.
- Reward fields: title, description, cost, kind.
- Claim reward with points.
- Reward kinds: ritual, capsule, visual, experience, rest.
- Claimed rewards history.
- Simple capsule reveal animation.

Manual acceptance:

- User can create or edit a reward.
- User can earn points and claim a reward.
- Copy makes it clear rewards are celebratory, not restrictive.

Automated tests:

- Reward validation tests.
- Reward affordability tests.
- Claiming tests.
- Compose UI reward create/edit and claim flow.

Issue title:

- `Add custom rewards and capsules`

#### Iteration 11: Local Schedule Preview

Goal:

- Prepare for future calendar integration without external accounts yet.

Scope:

- Calendar-style weekly view.
- Scheduled activities as local events.
- Today screen can mention upcoming local events.

Manual acceptance:

- User can add a planned activity time and see it affect today's suggestions.

Automated tests:

- Schedule helper tests.
- Compose UI event creation flow.

Issue title:

- `Add local schedule preview`

#### Iteration 12: Notifications and Reminders

Goal:

- Add gentle reminders without becoming annoying.

Scope:

- Local notification permission flow.
- Reminder settings.
- Morning impulse reminder.
- Optional daily task reminder.
- DataStore for notification preferences.

Manual acceptance:

- User can enable/disable reminders.
- Reminder copy is gentle.

Automated tests:

- Settings repository tests.
- ViewModel tests for notification preference state.

Issue title:

- `Add gentle local reminders`

#### Iteration 13: Android Polish

Goal:

- Make the app feel good as a daily phone app.

Scope:

- App icon placeholder.
- Dark theme polish.
- Empty states.
- Accessibility pass.
- Small haptics for completed actions if appropriate.
- Backup/export discussion if local data becomes valuable.

Manual acceptance:

- Primary flows feel polished on phone.
- Screen reader labels exist for primary actions.
- Dark theme is usable.

Automated tests:

- Compose UI smoke tests for main destinations.
- Build test.

Issue title:

- `Polish Android daily-use experience`

### Issue Template

Use this body for each GitHub issue:

```md
## Goal

## User Story

As the user, I want ...

## Scope

## Out of Scope

## Implementation Notes

## Data Model Changes

## UI Behavior

## Tests

- [ ] Unit tests
- [ ] Room/repository tests where persistence changes
- [ ] ViewModel tests where state changes
- [ ] Compose UI tests where user behavior changes

## Manual Acceptance Checklist

- [ ] App builds
- [ ] App runs on emulator or phone
- [ ] Feature can be tried by hand
- [ ] Edge state is understandable
- [ ] Copy does not shame the user

## Notes After Implementation
```

### PR Template

Use this body for each PR:

```md
## Summary

## Linked Issue

Closes #

## Manual Test

## Automated Tests

## Screenshots / Recording

## Follow-ups
```

### Testing Rules

Every feature must include tests proportional to risk:

- Pure logic gets JVM unit tests.
- Room schema, DAO, and repository behavior get persistence tests.
- ViewModel state transitions get ViewModel tests.
- User interactions get Compose UI tests.
- UI-only shell changes still get at least a smoke UI test.

Required checks before asking the user to approve:

- `./gradlew test` or Windows equivalent `.\gradlew test`.
- `./gradlew assembleDebug` or Windows equivalent `.\gradlew assembleDebug`.
- Relevant `connectedAndroidTest` or targeted Compose UI test when the feature changes user interaction and an emulator/device is available.
- Manual run on emulator or connected phone.

### Accessibility and Tone Rules

UI copy must be direct and kind.

Prefer:

- `День уже начал двигаться.`
- `Можно выбрать замену.`
- `Сегодня хватит легкой версии.`
- `Дело дня выполнено.`

Avoid:

- `Ты провалил день.`
- `Ты отстал.`
- `Нельзя получить награду.`
- `Серия сломана.`

Accessibility baseline:

- Buttons have clear text or content descriptions.
- Color is never the only state indicator.
- Progress components have text values.
- Touch targets are comfortably tappable.
- Dynamic font sizes do not break the main flow.
- Screen reader order is sensible for the Today screen.

### Open Decisions to Revisit Later

Do not block early implementation on these:

- Whether to add cloud sync.
- Whether to add authentication.
- Whether to integrate Google Calendar.
- Whether rewards should have budgets or spending caps.
- Whether notifications should be scheduled by time, habit, or context.
- Whether to use AI suggestions.
- Whether to add widgets.
- Whether to make an iOS app later.

### Definition of Done for Each Iteration

An iteration is done when:

- Issue exists and describes the implementation.
- Branch exists.
- Feature works locally on Android emulator or phone.
- Tests are present and passing or failures are explicitly documented.
- PR exists with test notes.
- User has tried it or reviewed it.
- User says `ок`.
- Branch is merged to main.
- Issue is closed.
