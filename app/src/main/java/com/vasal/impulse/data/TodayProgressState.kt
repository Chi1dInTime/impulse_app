package com.vasal.impulse.data

data class TodayProgressState(
    val points: Int = InitialTodayPoints,
    val impulseCompleted: Boolean = false,
    val dailyTaskCompleted: Boolean = false,
    val traceTitle: String? = null,
    val completedExtraQuestIds: Set<Long> = emptySet(),
    val completedExtraQuestTitles: List<String> = emptyList()
)

data class DayProgressHistoryItem(
    val date: String,
    val points: Int,
    val impulseCompleted: Boolean,
    val dailyTaskCompleted: Boolean,
    val traceTitle: String?,
    val completedExtraQuestTitles: List<String>
)

const val InitialTodayPoints = 32
