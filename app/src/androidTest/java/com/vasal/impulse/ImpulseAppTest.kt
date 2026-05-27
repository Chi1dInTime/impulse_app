package com.vasal.impulse

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.vasal.impulse.data.InMemoryTaskStore
import com.vasal.impulse.data.InMemoryTodayProgressStore
import com.vasal.impulse.ui.theme.ImpulseTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ImpulseAppTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        composeRule.setContent {
            ImpulseTheme {
                ImpulseApp(
                    todayProgressStore = InMemoryTodayProgressStore(),
                    taskStore = InMemoryTaskStore()
                )
            }
        }
    }

    @Test
    fun todayScreenShowsShellLabels() {
        composeRule.onNodeWithText("Импульс").assertIsDisplayed()
        composeRule.onNodeWithText("Прогресс дня").assertIsDisplayed()
        composeRule.onNodeWithText("Импульс дня").assertIsDisplayed()
        composeRule.onNodeWithText("Дело дня").assertIsDisplayed()
        composeRule.onNodeWithText("Ещё можно").assertIsDisplayed()
        composeRule.onNodeWithText("Сегодня").assertIsDisplayed()
        composeRule.onNodeWithText("Дела").assertIsDisplayed()
        composeRule.onNodeWithText("Статистика").assertIsDisplayed()
        composeRule.onNodeWithText("Награды").assertIsDisplayed()
    }

    @Test
    fun bottomNavigationSwitchesSectionShells() {
        composeRule.onNodeWithText("Дела").performClick()
        composeRule.onNodeWithContentDescription("Раздел Дела").assertIsSelected()
        composeRule.onNodeWithText("Список дел").assertIsDisplayed()

        composeRule.onNodeWithText("Статистика").performClick()
        composeRule.onNodeWithContentDescription("Раздел Статистика").assertIsSelected()
        composeRule.onNodeWithText("Прогресс недели").assertIsDisplayed()

        composeRule.onNodeWithText("Награды").performClick()
        composeRule.onNodeWithContentDescription("Раздел Награды").assertIsSelected()
        composeRule.onNodeWithText("Настрой свои награды под себя.").assertIsDisplayed()

        composeRule.onNodeWithText("Сегодня").performClick()
        composeRule.onNodeWithContentDescription("Раздел Сегодня").assertIsSelected()
        composeRule.onNodeWithText("Прогресс дня").assertIsDisplayed()
    }

    @Test
    fun completingImpulseUpdatesTodayState() {
        composeRule.onNodeWithText("32 очка").assertIsDisplayed()

        composeRule.onNodeWithText("Сделано").performClick()

        composeRule.onNodeWithText("42 очка").assertIsDisplayed()
        composeRule.onNodeWithText("Готово").assertIsDisplayed()
        composeRule.onNodeWithText("импульс выполнен").assertIsDisplayed()
        composeRule.onNodeWithText("День уже начал двигаться").assertIsDisplayed()
    }

    @Test
    fun tasksSectionCreatesTask() {
        composeRule.onNodeWithText("Дела").performClick()

        composeRule.onNodeWithText("+").performClick()
        composeRule.onNodeWithText("Название").performTextInput("Позвонить врачу")
        composeRule.onNodeWithText("Сохранить").performClick()

        composeRule.onNodeWithText("Позвонить врачу").assertIsDisplayed()
    }
}
