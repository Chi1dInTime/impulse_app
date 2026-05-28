package com.vasal.impulse.data

import com.vasal.impulse.data.local.DailyProgressDao
import com.vasal.impulse.data.local.DailyProgressEntity
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomTodayProgressStore(
    private val dao: DailyProgressDao,
    dateProvider: () -> LocalDate = { LocalDate.now() }
) : TodayProgressStore {
    private val todayKey = dateProvider().toString()

    override val todayProgress: Flow<TodayProgressState> =
        dao.observeByDate(todayKey).map { entity ->
            entity?.toState() ?: TodayProgressState()
        }
    override val dayHistory: Flow<List<DayProgressHistoryItem>> =
        dao.observeAll().map { entries -> entries.map { it.toHistoryItem() } }

    override suspend fun completeImpulse(points: Int) {
        val current = dao.getByDate(todayKey)
        if (current?.impulseCompleted == true) {
            return
        }

        val currentPoints = current?.points ?: InitialTodayPoints
        dao.upsert(
            DailyProgressEntity(
                date = todayKey,
                points = currentPoints + points,
                impulseCompleted = true,
                dailyTaskCompleted = current?.dailyTaskCompleted ?: false,
                traceTitle = current?.traceTitle,
                completedExtraQuestIds = current?.completedExtraQuestIds.orEmpty(),
                completedExtraQuestTitles = current?.completedExtraQuestTitles.orEmpty()
            )
        )
    }

    override suspend fun completeDailyTask(points: Int, title: String) {
        val current = dao.getByDate(todayKey)
        if (current?.dailyTaskCompleted == true) {
            return
        }

        val currentPoints = current?.points ?: InitialTodayPoints
        dao.upsert(
            DailyProgressEntity(
                date = todayKey,
                points = currentPoints + points,
                impulseCompleted = current?.impulseCompleted ?: false,
                dailyTaskCompleted = true,
                traceTitle = title,
                completedExtraQuestIds = current?.completedExtraQuestIds.orEmpty(),
                completedExtraQuestTitles = current?.completedExtraQuestTitles.orEmpty()
            )
        )
    }

    override suspend fun completeExtraQuest(taskId: Long, title: String, points: Int) {
        val current = dao.getByDate(todayKey)
        val completedIds = current?.completedExtraQuestIds.toIdSet()
        if (taskId in completedIds) {
            return
        }

        val currentPoints = current?.points ?: InitialTodayPoints
        val titles = current?.completedExtraQuestTitles.toTitleList()
        dao.upsert(
            DailyProgressEntity(
                date = todayKey,
                points = currentPoints + points,
                impulseCompleted = current?.impulseCompleted ?: false,
                dailyTaskCompleted = current?.dailyTaskCompleted ?: false,
                traceTitle = current?.traceTitle,
                completedExtraQuestIds = (completedIds + taskId).joinToString(","),
                completedExtraQuestTitles = (titles + title).joinToString("\n")
            )
        )
    }
}

private fun DailyProgressEntity.toState(): TodayProgressState =
    TodayProgressState(
        points = points,
        impulseCompleted = impulseCompleted,
        dailyTaskCompleted = dailyTaskCompleted,
        traceTitle = traceTitle,
        completedExtraQuestIds = completedExtraQuestIds.toIdSet(),
        completedExtraQuestTitles = completedExtraQuestTitles.toTitleList()
    )

private fun DailyProgressEntity.toHistoryItem(): DayProgressHistoryItem =
    DayProgressHistoryItem(
        date = date,
        points = points,
        impulseCompleted = impulseCompleted,
        dailyTaskCompleted = dailyTaskCompleted,
        traceTitle = traceTitle,
        completedExtraQuestTitles = completedExtraQuestTitles.toTitleList()
    )

private fun String?.toIdSet(): Set<Long> =
    orEmpty()
        .split(",")
        .mapNotNull { it.toLongOrNull() }
        .toSet()

private fun String?.toTitleList(): List<String> =
    orEmpty()
        .split("\n")
        .map { it.trim() }
        .filter { it.isNotBlank() }
