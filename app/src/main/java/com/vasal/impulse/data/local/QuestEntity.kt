package com.vasal.impulse.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quests")
data class QuestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val category: String,
    val durationMinutes: Int,
    val difficulty: Int,
    val importance: Int,
    val energyCost: Int,
    val points: Int,
    val kind: String,
    val createdAt: Long,
    val updatedAt: Long
)
