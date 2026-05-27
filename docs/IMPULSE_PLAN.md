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
3. Clickable app sections shell: Today, Tasks, Stats, Rewards.
4. Complete an Impulse of the day and earn points in temporary state.
5. Save local activity data with Room.
6. Persistent task list and editor with difficulty, importance, energy, and points.
7. Complete a Daily task and generate a Trace of the day.
8. Stats screen with colored weekly levels and day traces.
9. Custom rewards and capsules.
10. Weekly planning screen.
11. Alternative suggestions for low-energy days.
12. Local schedule preview.
13. Gentle reminders.
14. Android polish.

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
- Navigation: start simple for the first shell; add Navigation Compose when real section navigation, nested editors, or back-stack behavior appear.
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

The plan is intentionally ordered so the user sees the product shape early. First we make the app feel like a whole app, then make the Today loop interactive, then persist data, then fill each section with real behavior.

#### Iteration 0: Repository and Android Tooling Setup

Status:

- Implemented.

Goal:

- Create the native Android app skeleton and make build/test commands work.

Scope:

- Kotlin Android project structure.
- Jetpack Compose and Material 3.
- One `MainActivity`.
- Basic Compose theme.
- One smoke UI screen.
- JUnit test setup.
- Compose UI test setup.
- Gradle wrapper and README instructions.

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

Status:

- Implemented on branch `codex/01-today-screen-shell`.

Goal:

- Make the first real Today screen visible with demo data.

Scope:

- Today screen layout.
- Compact layered day progress meter.
- Impulse of the day card.
- Daily task card.
- Secondary quest list.
- Bottom navigation labels for Today, Tasks, Stats, and Rewards.
- Demo data only, no persistence yet.

Manual acceptance:

- User can open the app and understand the morning flow.
- Day progress feels like today's momentum, not weekly history.
- Screen feels usable on a phone viewport.
- No real action needs to persist yet.

Automated tests:

- Compose UI test for main labels.
- Android build and JVM test.

Issue title:

- `Add Today screen shell`

#### Iteration 2: Core App Sections Shell

Goal:

- Make the bottom navigation real and let the user see all main sections early.

Scope:

- Make `Today`, `Tasks`, `Stats`, and `Rewards` selectable.
- Add demo shell screens for Tasks, Stats, and Rewards.
- Tasks shell shows a preview list and add/edit affordance, but no real editing yet.
- Stats shell shows a demo week with green, blue, purple, and neutral days.
- Rewards shell shows demo custom rewards and add/edit affordance, but no real claiming yet.
- Remove overly explanatory copy from the Today progress card.
- Keep all section data static/demo-only.

Manual acceptance:

- User can tap every bottom navigation item.
- Every section communicates what it will become.
- The app feels like a coherent product, not a single isolated Today screen.
- No section pretends to save data yet.

Automated tests:

- Compose UI smoke tests for navigation labels.
- Compose UI tests for switching between section shells.

Issue title:

- `Add core app section shells`

#### Iteration 3: Complete Impulse of the Day

Goal:

- Let the user complete a small starter action and receive immediate feedback.

Scope:

- Complete button for impulse.
- Points update in temporary UI state.
- Layered day progress updates from points.
- Visual completion state for the impulse card.
- Short encouraging completion message.
- Temporary in-memory activity log.

Out of scope:

- App restart persistence.
- Real task editing.
- Daily task completion.

Manual acceptance:

- Tapping complete marks the impulse done.
- Points increase.
- The day progress meter changes.
- The day feels started.

Automated tests:

- Unit test for day progress level calculation.
- UI state test for completing an impulse.
- Compose UI test for the impulse completion flow.

Issue title:

- `Complete impulse of the day`

#### Iteration 4: Local Persistence with Room

Goal:

- Preserve the first useful local app state across restarts.

Scope:

- Add Room database.
- Add entities and DAOs for quests, daily plans, activity logs, day traces, and rewards.
- Add repository interfaces and local implementations.
- Seed default quests and starter rewards only on first run.
- Persist completed impulse state and points for the current day.
- Add a debug-only reset action if useful.

Manual acceptance:

- Complete an impulse, restart the app, and see the day state remains completed.
- Seed data appears once and does not duplicate on restart.

Automated tests:

- DAO tests.
- Repository tests.
- Migration/schema smoke test if applicable.
- ViewModel or state-holder persistence test where practical.

Issue title:

- `Persist local activity data with Room`

#### Iteration 5: Tasks Screen and Editor

Goal:

- Let the user manage reusable quests and tasks in the Tasks section.

Scope:

- Persistent task list.
- Add task flow.
- Edit task flow.
- Fields: title, description, category, duration, difficulty, importance, energy cost, points, and kind.
- Basic validation.
- Today can still use seeded/demo selections until planning is implemented.

Manual acceptance:

- User can create a task.
- User can edit importance and difficulty.
- User can return to the task list and see saved changes after restart.
- Today screen remains focused on action, not administration.

Automated tests:

- Quest validation tests.
- DAO/repository tests for task creation and update.
- Compose UI tests for create/edit flow.

Issue title:

- `Add task list and editor`

#### Iteration 6: Quest Classification and Point Defaults

Goal:

- Use task metadata to make quests easier to reason about and suggest later.

Scope:

- Classification helper for impulse, daily task, intermediate task, alternative, routine, and flexible quest.
- Suggested default points from importance, difficulty, duration, and energy cost.
- Show a recommendation label in the task editor.
- Let the user override points manually.

Manual acceptance:

- Creating or editing a task shows a useful recommendation.
- The recommendation feels helpful, not controlling.
- User can still override values.

Automated tests:

- Classification helper tests.
- Default point calculation tests.
- Compose UI smoke test for recommendation labels.

Issue title:

- `Classify quests by effort and value`

#### Iteration 7: Daily Task Completion and Day Trace

Goal:

- Let the user complete the main planned action and create the first real trace of the day.

Scope:

- Daily task completion button.
- Daily task points and bonus.
- Day status update.
- First version of day trace generation.
- Trace stores whether the result matched the planned task.

Manual acceptance:

- Completing the daily task creates a visible trace for today.
- The day progress meter updates.
- Copy stays encouraging and non-shaming.

Automated tests:

- Scoring tests for daily bonus.
- Trace generation tests.
- Repository tests for saved traces.
- Compose UI test for completing daily task.

Issue title:

- `Complete daily task and create day trace`

#### Iteration 8: Stats Screen with Real Progress

Goal:

- Make progress visible across days and weeks using persisted logs and traces.

Scope:

- Real Stats screen.
- Week summary.
- Colored day levels: green, blue, purple, neutral.
- Day traces list.
- Category balance.
- Points over time.
- Empty days shown gently.

Manual acceptance:

- User can see what made recent days count.
- User can distinguish minimum, strong, and very productive days by color.
- Empty days are understandable without shame.

Automated tests:

- Aggregation helper tests.
- ViewModel/state tests for summary states.
- Compose UI tests for Stats screen states.

Issue title:

- `Show weekly history and progress`

#### Iteration 9: Custom Rewards and Capsules

Goal:

- Add configurable rewards without turning rewards into permission.

Scope:

- Rewards screen backed by persisted data.
- Add and edit custom rewards.
- Reward fields: title, description, cost, kind.
- Claim reward with points.
- Reward kinds: ritual, capsule, visual, experience, rest.
- Claimed rewards history.
- Simple capsule reveal or claim celebration.

Manual acceptance:

- User can create or edit a reward.
- User can claim a reward with points.
- Copy makes it clear rewards are celebratory, not restrictive.

Automated tests:

- Reward validation tests.
- Reward affordability tests.
- Claiming tests.
- Compose UI reward create/edit and claim flow.

Issue title:

- `Add custom rewards and capsules`

#### Iteration 10: Weekly Planning

Goal:

- Let the user plan the main actions for the week.

Scope:

- Planning screen.
- Seven day slots.
- Assign existing quests as daily tasks.
- Show `Should-do` and routine pools.
- Today screen reflects today's selected task.
- Keep editing minimal: select from existing quests first.

Manual acceptance:

- User can assign a daily task to each day of the week.
- Today screen reflects today's selected task.
- The planning flow feels optional, not mandatory.

Automated tests:

- Date/week helper tests.
- Planning repository tests.
- ViewModel/state tests for weekly planning.
- Compose UI test for assigning a task.

Issue title:

- `Plan daily tasks for the week`

#### Iteration 11: Alternatives and Lighter Versions

Goal:

- Help the user adapt when the planned daily task is too hard.

Scope:

- Energy selector: low, medium, high.
- Alternative suggestions from quest pool.
- Bottom sheet for replacements.
- Complete an alternative instead of the planned daily task.
- Trace records `alternative` or `rescued`.

Manual acceptance:

- User can say energy is low and choose a replacement.
- The app records that the day counted by alternative.
- The app does not frame the change as failure.

Automated tests:

- Suggestion helper tests.
- Trace result tests.
- Compose UI alternative completion flow.

Issue title:

- `Suggest alternatives for low-energy days`

#### Iteration 12: Local Schedule Preview

Goal:

- Prepare for future calendar integration without external accounts yet.

Scope:

- Calendar-style weekly view.
- Scheduled activities as local events.
- Today screen can mention upcoming local events.
- Local-only editing.

Manual acceptance:

- User can add a planned activity time and see it affect today's suggestions.

Automated tests:

- Schedule helper tests.
- Repository tests for local events.
- Compose UI event creation flow.

Issue title:

- `Add local schedule preview`

#### Iteration 13: Gentle Reminders

Goal:

- Add reminders without becoming annoying.

Scope:

- Local notification permission flow.
- Reminder settings.
- Morning impulse reminder.
- Optional daily task reminder.
- DataStore for notification preferences.
- Gentle reminder copy.

Manual acceptance:

- User can enable and disable reminders.
- Reminder copy feels supportive.
- The app works normally if notification permission is denied.

Automated tests:

- Settings repository tests.
- ViewModel/state tests for notification preferences.

Issue title:

- `Add gentle local reminders`

#### Iteration 14: Android Polish

Goal:

- Make the app feel good as a daily phone app.

Scope:

- App icon polish.
- Dark theme polish.
- Empty states.
- Accessibility pass.
- Reduced explanatory copy where the UI can speak for itself.
- Small haptics for completed actions if appropriate.
- Backup/export discussion if local data becomes valuable.

Manual acceptance:

- Primary flows feel polished on phone.
- Screen reader labels exist for primary actions.
- Dark theme is usable.
- Text is concise and does not crowd the cards.

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
