package com.vasal.impulse.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryTaskStore(
    initialTasks: List<TaskItem> = defaultTaskItems
) : TaskStore {
    private val state = MutableStateFlow(initialTasks)
    private var nextId = (initialTasks.maxOfOrNull { it.id } ?: 0L) + 1L

    override val tasks: StateFlow<List<TaskItem>> = state.asStateFlow()

    override suspend fun seedDefaultsIfEmpty() {
        if (state.value.isEmpty()) {
            state.value = defaultTaskItems
            nextId = defaultTaskItems.maxOf { it.id } + 1L
        }
    }

    override suspend fun saveTask(draft: TaskDraft) {
        val normalized = draft.normalized()
        if (normalized.title.isBlank()) {
            return
        }

        state.update { current ->
            val id = normalized.id ?: nextId++
            val item = normalized.toItem(id)
            if (current.any { it.id == id }) {
                current.map { existing -> if (existing.id == id) item else existing }
            } else {
                current + item
            }
        }
    }
}

val defaultTaskItems = listOf(
    TaskItem(
        id = 1,
        title = "Разобрать кухню",
        description = "Пять минут без идеала.",
        category = "дом",
        durationMinutes = 5,
        difficulty = 1,
        importance = 2,
        energyCost = 1,
        points = 10,
        kind = "импульс"
    ),
    TaskItem(
        id = 2,
        title = "Обновить резюме",
        description = "Открыть файл и поправить один заметный блок.",
        category = "работа",
        durationMinutes = 20,
        difficulty = 3,
        importance = 5,
        energyCost = 3,
        points = 35,
        kind = "дело дня"
    ),
    TaskItem(
        id = 3,
        title = "Прогулка 10 минут",
        description = "Лёгкий выход наружу без спортивного героизма.",
        category = "тело",
        durationMinutes = 10,
        difficulty = 1,
        importance = 3,
        energyCost = 1,
        points = 8,
        kind = "гибкое"
    )
)

private fun TaskDraft.toItem(id: Long): TaskItem =
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
