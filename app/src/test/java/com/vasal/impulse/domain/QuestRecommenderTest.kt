package com.vasal.impulse.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestRecommenderTest {
    @Test
    fun shortLowEnergyQuestIsImpulse() {
        val recommendation = QuestRecommender.recommend(
            importance = 2,
            difficulty = 1,
            energyCost = 1,
            durationMinutes = 5
        )

        assertEquals("импульс", recommendation.kind)
        assertTrue(recommendation.points >= 5)
    }

    @Test
    fun importantAndDifficultQuestIsDailyTask() {
        val recommendation = QuestRecommender.recommend(
            importance = 5,
            difficulty = 4,
            energyCost = 3,
            durationMinutes = 45
        )

        assertEquals("дело дня", recommendation.kind)
        assertEquals(53, recommendation.points)
    }

    @Test
    fun recommendationCoercesInput() {
        val recommendation = QuestRecommender.recommend(
            importance = 50,
            difficulty = 50,
            energyCost = 50,
            durationMinutes = 999
        )

        assertEquals("дело дня", recommendation.kind)
        assertTrue(recommendation.points <= 120)
    }
}
