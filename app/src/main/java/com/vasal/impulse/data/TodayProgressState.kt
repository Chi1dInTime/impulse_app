package com.vasal.impulse.data

data class TodayProgressState(
    val points: Int = InitialTodayPoints,
    val impulseCompleted: Boolean = false,
    val dailyTaskCompleted: Boolean = false,
    val traceTitle: String? = null
)

const val InitialTodayPoints = 32
