package com.vasal.impulse.data

import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class InMemoryTodayProgressStore(
    initialState: TodayProgressState = TodayProgressState(),
    private val dateProvider: () -> LocalDate = { LocalDate.now() }
) : TodayProgressStore {
    private val state = MutableStateFlow(initialState)

    override val todayProgress: StateFlow<TodayProgressState> = state.asStateFlow()
    override val dayHistory = state.map { current ->
        if (current.hasVisibleProgress()) {
            listOf(current.toHistoryItem(dateProvider().toString()))
        } else {
            emptyList()
        }
    }

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

private fun TodayProgressState.hasVisibleProgress(): Boolean =
    impulseCompleted || dailyTaskCompleted || traceTitle != null

private fun TodayProgressState.toHistoryItem(date: String): DayProgressHistoryItem =
    DayProgressHistoryItem(
        date = date,
        points = points,
        impulseCompleted = impulseCompleted,
        dailyTaskCompleted = dailyTaskCompleted,
        traceTitle = traceTitle
    )
