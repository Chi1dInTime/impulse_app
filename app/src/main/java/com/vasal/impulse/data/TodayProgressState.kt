package com.vasal.impulse.data

data class TodayProgressState(
    val points: Int = InitialTodayPoints,
    val impulseCompleted: Boolean = false,
    val dailyTaskCompleted: Boolean = false,
    val traceTitle: String? = null
)

data class DayProgressHistoryItem(
    val date: String,
    val points: Int,
    val impulseCompleted: Boolean,
    val dailyTaskCompleted: Boolean,
    val traceTitle: String?
)

const val InitialTodayPoints = 32
