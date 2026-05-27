package com.vasal.impulse.data

import com.vasal.impulse.data.local.QuestDao
import com.vasal.impulse.data.local.QuestEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomTaskStore(
    private val dao: QuestDao,
    private val clock: () -> Long = { System.currentTimeMillis() }
) : TaskStore {
    override val tasks: Flow<List<TaskItem>> =
        dao.observeAll().map { quests -> quests.map { it.toItem() } }

    override suspend fun seedDefaultsIfEmpty() {
        if (dao.count() > 0) {
            return
        }
        val now = clock()
        dao.upsertAll(defaultTaskItems.map { it.toEntity(createdAt = now, updatedAt = now) })
    }

    override suspend fun saveTask(draft: TaskDraft) {
        val normalized = draft.normalized()
        if (normalized.title.isBlank()) {
            return
        }

        val now = clock()
        dao.upsert(
            QuestEntity(
                id = normalized.id ?: 0,
                title = normalized.title,
                description = normalized.description,
                category = normalized.category,
                durationMinutes = normalized.durationMinutes,
                difficulty = normalized.difficulty,
                importance = normalized.importance,
                energyCost = normalized.energyCost,
                points = normalized.points,
                kind = normalized.kind,
                createdAt = now,
                updatedAt = now
            )
        )
    }
}

private fun QuestEntity.toItem(): TaskItem =
    TaskItem(
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

private fun TaskItem.toEntity(createdAt: Long, updatedAt: Long): QuestEntity =
    QuestEntity(
        title = title,
        description = description,
        category = category,
        durationMinutes = durationMinutes,
        difficulty = difficulty,
        importance = importance,
        energyCost = energyCost,
        points = points,
        kind = kind,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
