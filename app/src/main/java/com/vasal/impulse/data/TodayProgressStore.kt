package com.vasal.impulse.data

import kotlinx.coroutines.flow.Flow

interface TodayProgressStore {
    val todayProgress: Flow<TodayProgressState>
    val dayHistory: Flow<List<DayProgressHistoryItem>>

    suspend fun completeImpulse(points: Int)

    suspend fun completeDailyTask(points: Int, title: String)
}
