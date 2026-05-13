package com.vasal.impulse

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class ImpulseAppTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun todaySkeletonShowsCoreLabels() {
        composeRule.onNodeWithText("Импульс").assertIsDisplayed()
        composeRule.onNodeWithText("Импульс дня").assertIsDisplayed()
        composeRule.onNodeWithText("Дело дня").assertIsDisplayed()
    }
}

