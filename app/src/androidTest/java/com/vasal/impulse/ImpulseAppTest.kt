package com.vasal.impulse

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ImpulseAppTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

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
        composeRule.onNodeWithText("Список дел").assertIsDisplayed()

        composeRule.onNodeWithText("Статистика").performClick()
        composeRule.onNodeWithText("Прогресс недели").assertIsDisplayed()

        composeRule.onNodeWithText("Награды").performClick()
        composeRule.onNodeWithText("Настрой свои награды под себя.").assertIsDisplayed()

        composeRule.onNodeWithText("Сегодня").performClick()
        composeRule.onNodeWithText("Прогресс дня").assertIsDisplayed()
    }
}
