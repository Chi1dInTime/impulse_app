package com.vasal.impulse.feature.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vasal.impulse.data.InMemoryTaskStore
import com.vasal.impulse.data.TaskCategories
import com.vasal.impulse.data.TaskDraft
import com.vasal.impulse.data.TaskItem
import com.vasal.impulse.data.TaskKinds
import com.vasal.impulse.data.TaskStore
import com.vasal.impulse.data.toDraft
import com.vasal.impulse.domain.QuestRecommendation
import com.vasal.impulse.domain.QuestRecommender
import com.vasal.impulse.ui.theme.DayGreen
import com.vasal.impulse.ui.theme.ImpulseTheme
import com.vasal.impulse.ui.theme.WarmBlueContainer
import com.vasal.impulse.ui.theme.WarmGreenContainer
import com.vasal.impulse.ui.theme.WarmSurface
import kotlinx.coroutines.launch

@Composable
fun TasksScreen(
    taskStore: TaskStore,
    modifier: Modifier = Modifier
) {
    val tasks by taskStore.tasks.collectAsStateWithLifecycle(initialValue = emptyList())
    var draft by remember { mutableStateOf<TaskDraft?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(taskStore) {
        taskStore.seedDefaultsIfEmpty()
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SectionHeader(
            title = "Дела",
            subtitle = "Квесты, рутина и то, что надо бы."
        )
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = WarmBlueContainer)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Список дел",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${tasks.size} сохранено",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.68f)
                    )
                }
                FloatingActionButton(
                    onClick = { draft = TaskDraft() },
                    containerColor = DayGreen,
                    contentColor = Color.White
                ) {
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        draft?.let { currentDraft ->
            TaskEditorCard(
                draft = currentDraft,
                onDraftChange = { draft = it },
                onCancel = { draft = null },
                onSave = {
                    coroutineScope.launch {
                        taskStore.saveTask(currentDraft)
                        draft = null
                    }
                }
            )
        }
        tasks.forEach { task ->
            TaskPreviewCard(
                task = task,
                onEdit = { draft = task.toDraft() }
            )
        }
    }
}

@Composable
private fun TaskEditorCard(
    draft: TaskDraft,
    onDraftChange: (TaskDraft) -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    val recommendation = QuestRecommender.recommend(
        importance = draft.importance,
        difficulty = draft.difficulty,
        energyCost = draft.energyCost,
        durationMinutes = draft.durationMinutes
    )

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = WarmGreenContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (draft.id == null) "Новое дело" else "Редактировать дело",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
                value = draft.title,
                onValueChange = { onDraftChange(draft.copy(title = it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Название") },
                singleLine = true
            )
            OutlinedTextField(
                value = draft.description,
                onValueChange = { onDraftChange(draft.copy(description = it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Описание") },
                minLines = 2
            )
            ChipSelector(
                label = "Тип",
                options = TaskKinds,
                selected = draft.kind,
                onSelected = { onDraftChange(draft.copy(kind = it)) }
            )
            ChipSelector(
                label = "Категория",
                options = TaskCategories,
                selected = draft.category,
                onSelected = { onDraftChange(draft.copy(category = it)) }
            )
            RecommendationCard(
                recommendation = recommendation,
                onApply = {
                    onDraftChange(
                        draft.copy(
                            kind = recommendation.kind,
                            points = recommendation.points
                        )
                    )
                }
            )
            NumberStepper(
                label = "Важность",
                value = draft.importance,
                range = 1..5,
                onValueChange = { onDraftChange(draft.copy(importance = it)) }
            )
            NumberStepper(
                label = "Сложность",
                value = draft.difficulty,
                range = 1..5,
                onValueChange = { onDraftChange(draft.copy(difficulty = it)) }
            )
            NumberStepper(
                label = "Энергия",
                value = draft.energyCost,
                range = 1..5,
                onValueChange = { onDraftChange(draft.copy(energyCost = it)) }
            )
            NumberStepper(
                label = "Минуты",
                value = draft.durationMinutes,
                range = 1..240,
                step = 5,
                onValueChange = { onDraftChange(draft.copy(durationMinutes = it)) }
            )
            NumberStepper(
                label = "Очки",
                value = draft.points,
                range = 1..250,
                onValueChange = { onDraftChange(draft.copy(points = it)) }
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onSave,
                    enabled = draft.title.isNotBlank()
                ) {
                    Text("Сохранить")
                }
                TextButton(onClick = onCancel) {
                    Text("Отмена")
                }
            }
        }
    }
}

@Composable
private fun RecommendationCard(
    recommendation: QuestRecommendation,
    onApply: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = WarmSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Рекомендация",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${recommendation.kind} · ${recommendation.points} очков",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = recommendation.reason,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.68f)
                )
            }
            TextButton(onClick = onApply) {
                Text("Применить")
            }
        }
    }
}

@Composable
private fun ChipSelector(
    label: String,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            options.forEach { option ->
                FilterChip(
                    selected = selected == option,
                    onClick = { onSelected(option) },
                    label = { Text(option) }
                )
            }
        }
    }
}

@Composable
private fun NumberStepper(
    label: String,
    value: Int,
    range: IntRange,
    step: Int = 1,
    onValueChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { onValueChange((value - step).coerceAtLeast(range.first)) },
                enabled = value > range.first
            ) {
                Text("-")
            }
            Text(
                text = "$value",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = { onValueChange((value + step).coerceAtMost(range.last)) },
                enabled = value < range.last
            ) {
                Text("+")
            }
        }
    }
}

@Composable
private fun TaskPreviewCard(
    task: TaskItem,
    onEdit: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = WarmSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                if (task.description.isNotBlank()) {
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.68f)
                    )
                }
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(task.kind, style = MaterialTheme.typography.labelMedium)
                    Text(task.category, style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = "важн. ${task.importance} · сложн. ${task.difficulty} · энерг. ${task.energyCost}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text("${task.durationMinutes} мин", style = MaterialTheme.typography.labelMedium)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "+${task.points}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = onEdit) {
                    Text("Изменить")
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.72f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksScreenPreview() {
    ImpulseTheme {
        TasksScreen(taskStore = InMemoryTaskStore())
    }
}
