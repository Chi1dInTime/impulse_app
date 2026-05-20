# Импульс

**Импульс** — нативное Android-приложение для мягкого запуска дня и видимого личного прогресса.

Это не строгий таск-менеджер и не система наказаний. Приложение должно помогать начать с маленького действия, выбрать главное дело дня, учитывать низкую энергию и показывать, что день уже сдвинулся.

## Основная идея

- `Импульс дня`: маленькое действие на 5-10 минут, чтобы день начал двигаться.
- `Дело дня`: главное запланированное действие на сегодня.
- `След дня`: запись в истории о том, чем день реально стал заметен.
- `Квест`: любое полезное повторяемое действие, за которое можно получать очки.
- `Награда`: ритуал, капсула, отдых или приятная отметка, а не разрешение жить.

## Планируемые экраны

Первый дизайн-направление сохранено в:

[docs/design/impulse-mobile-mockups.html](docs/design/impulse-mobile-mockups.html)

В приложении планируется нижнее меню:

- `Сегодня`: прогресс текущего дня, импульс дня, дело дня, быстрые дополнительные квесты.
- `Дела`: список квестов, добавление и редактирование дел, важность, сложность, энергия, очки.
- `Статистика`: следы дней, недельная карта, баланс категорий и очки.
- `Награды`: пользовательские награды, стоимость в очках, тип награды и история получения.

## Прогресс дня

На первом экране должен быть компактный прогресс текущего дня по очкам.

Шкала работает слоями:

1. Сначала шкала заполняется зелёным: минимальный разгон дня.
2. Когда зелёный уровень закрыт, поверх него от начала растёт синий слой: сильный день.
3. Когда синий уровень закрыт, поверх него от начала растёт фиолетовый слой: супер-продуктивный день.

В статистике недели дни должны окрашиваться по максимальному достигнутому уровню:

- зелёный: минимальный день закрыт;
- синий: день вышел за базовый уровень;
- фиолетовый: очень продуктивный день;
- пустой/нейтральный: день ещё без заметного следа.

## Current State

Iteration 0 creates the Android project skeleton:

- Kotlin Android project structure.
- Jetpack Compose + Material 3.
- One starter screen.
- One JVM unit test.
- One Compose UI smoke test.
- Gradle wrapper.
- Compose Compiler Gradle plugin setup for Kotlin 2.x.
- HTML design mockups for the first product direction.

The app has been manually opened on an Android emulator.

## Requirements

Install Android Studio with:

- Android SDK Platform 36.
- Android SDK Build-Tools 36.0.0.
- JDK included with Android Studio.

## How to Try on an Emulator

1. Open this repository folder in Android Studio.
2. Let Gradle sync finish.
3. Open `Tools -> Device Manager`.
4. Create or start an Android emulator.
5. Select the emulator as the run target.
6. Press Run.

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

## Useful Commands

```powershell
.\gradlew.bat test
.\gradlew.bat assembleDebug
.\gradlew.bat connectedAndroidTest
```

`connectedAndroidTest` requires an emulator or connected phone.

## Working Agreement

Every feature iteration should have:

- A GitHub issue.
- A separate branch named `codex/NN-short-feature-name`.
- Minimal implementation.
- Tests for changed behavior.
- A PR.
- Manual approval from the user before merge.
