package com.vasal.impulse.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryTodayProgressStore(
    initialState: TodayProgressState = TodayProgressState()
) : TodayProgressStore {
    private val state = MutableStateFlow(initialState)

    override val todayProgress: StateFlow<TodayProgressState> = state.asStateFlow()

    override suspend fun completeImpulse(points: Int) {
        state.update { current ->
            if (current.impulseCompleted) {
                current
            } else {
                current.copy(
                    points = current.points + points,
                    impulseCompleted = true
                )
            }
        }
    }

    override suspend fun completeDailyTask(points: Int, title: String) {
        state.update { current ->
            if (current.dailyTaskCompleted) {
                current
            } else {
                current.copy(
                    points = current.points + points,
                    dailyTaskCompleted = true,
                    traceTitle = title
                )
            }
        }
    }
}
