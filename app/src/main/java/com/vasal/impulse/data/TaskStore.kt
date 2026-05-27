package com.vasal.impulse.data

import kotlinx.coroutines.flow.Flow

interface TaskStore {
    val tasks: Flow<List<TaskItem>>

    suspend fun seedDefaultsIfEmpty()

    suspend fun saveTask(draft: TaskDraft)
}
