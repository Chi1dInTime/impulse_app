package com.vasal.impulse.data

import com.vasal.impulse.domain.QuestRecommender

data class TaskItem(
    val id: Long,
    val title: String,
    val description: String,
    val category: String,
    val durationMinutes: Int,
    val difficulty: Int,
    val importance: Int,
    val energyCost: Int,
    val points: Int,
    val kind: String
)

data class TaskDraft(
    val id: Long? = null,
    val title: String = "",
    val description: String = "",
    val category: String = TaskCategories.first(),
    val durationMinutes: Int = 10,
    val difficulty: Int = 1,
    val importance: Int = 3,
    val energyCost: Int = 1,
    val points: Int = QuestRecommender.recommend(
        importance = importance,
        difficulty = difficulty,
        energyCost = energyCost,
        durationMinutes = durationMinutes
    ).points,
    val kind: String = "гибкое"
)

val TaskCategories = listOf("дом", "работа", "тело", "восстановление", "админ")
val TaskKinds = listOf("импульс", "дело дня", "надо бы", "рутина", "гибкое")

fun TaskItem.toDraft(): TaskDraft =
    TaskDraft(
        id = id,
        title = title,
        description = description,
        category = category,
        durationMinutes = durationMinutes,
        difficulty = difficulty,
        importance = importance,
        energyCost = energyCost,
        points = points,
        kind = kind
    )

fun TaskDraft.normalized(): TaskDraft =
    copy(
        title = title.trim(),
        description = description.trim(),
        durationMinutes = durationMinutes.coerceIn(1, 240),
        difficulty = difficulty.coerceIn(1, 5),
        importance = importance.coerceIn(1, 5),
        energyCost = energyCost.coerceIn(1, 5),
        points = points.coerceIn(1, 250)
    )
