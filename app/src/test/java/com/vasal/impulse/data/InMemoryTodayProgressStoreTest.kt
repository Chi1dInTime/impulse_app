package com.vasal.impulse.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class InMemoryTodayProgressStoreTest {
    @Test
    fun completeImpulseAddsPointsOnlyOnce() = runBlocking {
        val store = InMemoryTodayProgressStore()

        store.completeImpulse(points = 10)
        store.completeImpulse(points = 10)

        assertEquals(
            TodayProgressState(points = 42, impulseCompleted = true),
            store.todayProgress.first()
        )
    }

    @Test
    fun completeDailyTaskAddsPointsAndTraceOnlyOnce() = runBlocking {
        val store = InMemoryTodayProgressStore()

        store.completeDailyTask(points = 35, title = "Обновить резюме")
        store.completeDailyTask(points = 35, title = "Обновить резюме")

        assertEquals(
            TodayProgressState(
                points = 67,
                dailyTaskCompleted = true,
                traceTitle = "Обновить резюме"
            ),
            store.todayProgress.first()
        )
    }

    @Test
    fun completeExtraQuestAddsPointsAndTitleOnlyOnce() = runBlocking {
        val store = InMemoryTodayProgressStore()

        store.completeExtraQuest(taskId = 3, title = "Прогулка 10 минут", points = 8)
        store.completeExtraQuest(taskId = 3, title = "Прогулка 10 минут", points = 8)

        assertEquals(
            TodayProgressState(
                points = 40,
                completedExtraQuestIds = setOf(3),
                completedExtraQuestTitles = listOf("Прогулка 10 минут")
            ),
            store.todayProgress.first()
        )
    }
}
