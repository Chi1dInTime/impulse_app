package com.vasal.impulse.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_progress")
data class DailyProgressEntity(
    @PrimaryKey val date: String,
    val points: Int,
    val impulseCompleted: Boolean,
    val dailyTaskCompleted: Boolean,
    val traceTitle: String?,
    val completedExtraQuestIds: String,
    val completedExtraQuestTitles: String
)
