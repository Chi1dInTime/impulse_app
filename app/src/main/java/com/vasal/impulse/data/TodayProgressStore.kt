package com.vasal.impulse.data

import kotlinx.coroutines.flow.Flow

interface TodayProgressStore {
    val todayProgress: Flow<TodayProgressState>

    suspend fun completeImpulse(points: Int)
}
