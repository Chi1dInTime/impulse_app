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
                impulseCompleted = true
            )
        )
    }
}

private fun DailyProgressEntity.toState(): TodayProgressState =
    TodayProgressState(
        points = points,
        impulseCompleted = impulseCompleted
    )
