package com.vasal.impulse.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class InMemoryTaskStoreTest {
    @Test
    fun saveTaskAddsNewTask() = runBlocking {
        val store = InMemoryTaskStore(initialTasks = emptyList())

        store.saveTask(TaskDraft(title = "Позвонить врачу", importance = 4, difficulty = 2))

        val tasks = store.tasks.first()
        assertEquals(1, tasks.size)
        assertEquals("Позвонить врачу", tasks.single().title)
        assertEquals(4, tasks.single().importance)
        assertEquals(2, tasks.single().difficulty)
    }

    @Test
    fun saveTaskUpdatesExistingTask() = runBlocking {
        val store = InMemoryTaskStore(
            initialTasks = listOf(defaultTaskItems.first())
        )

        store.saveTask(defaultTaskItems.first().toDraft().copy(title = "Кухня 5 минут"))

        val tasks = store.tasks.first()
        assertEquals(1, tasks.size)
        assertEquals("Кухня 5 минут", tasks.single().title)
    }

    @Test
    fun blankTitleIsIgnored() = runBlocking {
        val store = InMemoryTaskStore(initialTasks = emptyList())

        store.saveTask(TaskDraft(title = "   "))

        assertTrue(store.tasks.first().isEmpty())
    }
}
