package com.vasal.impulse.data

data class TodayProgressState(
    val points: Int = InitialTodayPoints,
    val impulseCompleted: Boolean = false
)

const val InitialTodayPoints = 32
