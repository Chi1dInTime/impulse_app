package com.vasal.impulse.feature.today

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vasal.impulse.data.InMemoryTodayProgressStore
import com.vasal.impulse.data.InMemoryTaskStore
import com.vasal.impulse.data.TaskItem
import com.vasal.impulse.data.TaskStore
import com.vasal.impulse.data.TodayProgressState
import com.vasal.impulse.data.TodayProgressStore
import com.vasal.impulse.domain.AppInfo
import com.vasal.impulse.domain.DayProgress
import com.vasal.impulse.domain.DayProgressCalculator
import com.vasal.impulse.ui.theme.DayBlue
import com.vasal.impulse.ui.theme.DayGreen
import com.vasal.impulse.ui.theme.DayPurple
import com.vasal.impulse.ui.theme.ImpulseTheme
import com.vasal.impulse.ui.theme.WarmAmber
import com.vasal.impulse.ui.theme.WarmBlueContainer
import com.vasal.impulse.ui.theme.WarmGreenContainer
import com.vasal.impulse.ui.theme.WarmSurface
import kotlinx.coroutines.launch

private val demoToday = TodayUiState(
    impulse = QuestCardUiState(
        label = "Импульс дня",
        title = "Разобрать кухню",
        details = "5 минут",
        points = 10,
        support = "мало сил"
    ),
    dailyTask = QuestCardUiState(
        label = "Дело дня",
        title = "Обновить резюме",
        details = "Обычная версия: 20 минут",
        points = 35,
        support = "Лёгкая версия: открыть файл и поправить одну строку."
    ),
    extraQuests = listOf(
        ExtraQuestUiState(1001, "Прогулка 10 минут", "тело · легко", 8),
        ExtraQuestUiState(1002, "Постирать вещи", "дом · средне", 12),
        ExtraQuestUiState(1003, "Посмотреть фильм осознанно", "восстановление", 10)
    )
)

@Composable
fun TodayScreen(
    progressStore: TodayProgressStore,
    taskStore: TaskStore,
    modifier: Modifier = Modifier
) {
    val todayProgress by progressStore.todayProgress.collectAsStateWithLifecycle(
        initialValue = TodayProgressState()
    )
    val tasks by taskStore.tasks.collectAsStateWithLifecycle(initialValue = emptyList())
    val coroutineScope = rememberCoroutineScope()
    val dayProgress = DayProgressCalculator.calculate(todayProgress.points)
    val dailyTask = tasks.dailyTaskOrFallback()
    val extraQuests = tasks.extraQuestCards(todayProgress.completedExtraQuestIds)
    val todayState = demoToday.copy(
        dailyTask = dailyTask,
        extraQuests = extraQuests
    )

    TodayScreen(
        state = todayState,
        points = todayProgress.points,
        dayProgress = dayProgress,
        impulseCompleted = todayProgress.impulseCompleted,
        dailyTaskCompleted = todayProgress.dailyTaskCompleted,
        traceTitle = todayProgress.traceTitle,
        onCompleteImpulse = {
            coroutineScope.launch {
                progressStore.completeImpulse(demoToday.impulse.points)
            }
        },
        onCompleteDailyTask = {
            coroutineScope.launch {
                progressStore.completeDailyTask(
                    points = dailyTask.points,
                    title = dailyTask.title
                )
            }
        },
        onCompleteExtraQuest = { quest ->
            coroutineScope.launch {
                progressStore.completeExtraQuest(
                    taskId = quest.id,
                    title = quest.title,
                    points = quest.points
                )
            }
        },
        modifier = modifier
    )
}

@Composable
private fun TodayScreen(
    state: TodayUiState,
    points: Int,
    dayProgress: DayProgress,
    impulseCompleted: Boolean,
    dailyTaskCompleted: Boolean,
    traceTitle: String?,
    onCompleteImpulse: () -> Unit,
    onCompleteDailyTask: () -> Unit,
    onCompleteExtraQuest: (ExtraQuestUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        TodayHeader()
        DayProgressCard(
            points = points,
            progress = dayProgress,
            impulseCompleted = impulseCompleted,
            dailyTaskCompleted = dailyTaskCompleted
        )
        QuestActionCard(
            quest = state.impulse,
            containerColor = WarmAmber,
            primaryAction = if (impulseCompleted) "Готово" else "Сделано",
            secondaryAction = null,
            completed = impulseCompleted,
            completedLabel = "импульс выполнен",
            onPrimaryAction = onCompleteImpulse
        )
        QuestActionCard(
            quest = state.dailyTask,
            containerColor = WarmBlueContainer,
            primaryAction = if (dailyTaskCompleted) "Готово" else "Выполнено",
            secondaryAction = null,
            completed = dailyTaskCompleted,
            completedLabel = "дело дня выполнено",
            onPrimaryAction = onCompleteDailyTask
        )
        if (dailyTaskCompleted && traceTitle != null) {
            DayTraceCard(traceTitle = traceTitle)
        }
        ExtraQuestsSection(
            quests = state.extraQuests,
            onCompleteQuest = onCompleteExtraQuest
        )
    }
}

@Composable
private fun TodayHeader() {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = AppInfo.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Сегодня можно начать мягко.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.72f)
        )
    }
}

@Composable
private fun DayProgressCard(
    points: Int,
    progress: DayProgress,
    impulseCompleted: Boolean,
    dailyTaskCompleted: Boolean
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = WarmGreenContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SectionTitle(
                title = "Прогресс дня",
                trailing = "$points очка"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = dayProgressMessage(
                        impulseCompleted = impulseCompleted,
                        dailyTaskCompleted = dailyTaskCompleted
                    ),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = progress.pointsUntilNextLevel?.let { "ещё +$it" } ?: "супер",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.End
                )
            }
            LayeredDayMeter(
                minimumProgress = progress.minimumProgress,
                strongProgress = progress.strongProgress,
                superProgress = progress.superProgress
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("минимум", style = MaterialTheme.typography.labelSmall)
                Text("сверх", style = MaterialTheme.typography.labelSmall)
                Text("супер", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

private fun dayProgressMessage(
    impulseCompleted: Boolean,
    dailyTaskCompleted: Boolean
): String =
    when {
        dailyTaskCompleted -> "След дня уже появился"
        impulseCompleted -> "День уже начал двигаться"
        else -> "День ждёт первого движения"
    }

@Composable
private fun LayeredDayMeter(
    minimumProgress: Float,
    strongProgress: Float,
    superProgress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(14.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            .semantics {
                contentDescription = "Слоёная шкала прогресса дня"
            }
    ) {
        MeterLayer(progress = minimumProgress, color = DayGreen)
        MeterLayer(progress = strongProgress, color = DayBlue)
        MeterLayer(progress = superProgress, color = DayPurple)
    }
}

@Composable
private fun MeterLayer(progress: Float, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth(progress.coerceIn(0f, 1f))
            .height(14.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(color)
    )
}

@Composable
private fun QuestActionCard(
    quest: QuestCardUiState,
    containerColor: Color,
    primaryAction: String,
    secondaryAction: String?,
    completed: Boolean,
    completedLabel: String,
    onPrimaryAction: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SectionTitle(title = quest.label, trailing = quest.details)
            Text(
                text = quest.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LabelChip(text = "+${quest.points} очков", strong = true)
                LabelChip(text = if (completed) completedLabel else quest.support)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onPrimaryAction,
                    enabled = !completed
                ) {
                    Text(primaryAction)
                }
                if (secondaryAction != null) {
                    OutlinedButton(
                        onClick = { },
                        enabled = !completed
                    ) {
                        Text(secondaryAction)
                    }
                }
            }
        }
    }
}

@Composable
private fun DayTraceCard(traceTitle: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = WarmSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SectionTitle(title = "След дня", trailing = "сохранён")
            Text(
                text = traceTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Главное дело стало видимым в истории дня.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.68f)
            )
        }
    }
}

@Composable
private fun ExtraQuestsSection(
    quests: List<ExtraQuestUiState>,
    onCompleteQuest: (ExtraQuestUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SectionTitle(title = "Ещё можно", trailing = "${quests.size} квеста")
        quests.forEach { quest ->
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
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                            text = quest.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = quest.subtitle,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "+${quest.points}",
                            style = MaterialTheme.typography.labelLarge,
                            color = DayGreen,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = { onCompleteQuest(quest) },
                            enabled = !quest.completed
                        ) {
                            Text(if (quest.completed) "Готово" else "Сделано")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String, trailing: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = trailing,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
        )
    }
}

@Composable
private fun LabelChip(text: String, strong: Boolean = false) {
    Text(
        text = text,
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(
                if (strong) {
                    MaterialTheme.colorScheme.surface
                } else {
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.58f)
                }
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        style = MaterialTheme.typography.labelMedium,
        fontWeight = if (strong) FontWeight.Bold else FontWeight.Normal,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (strong) 1f else 0.72f)
    )
}

private data class TodayUiState(
    val impulse: QuestCardUiState,
    val dailyTask: QuestCardUiState,
    val extraQuests: List<ExtraQuestUiState>
)

private data class QuestCardUiState(
    val label: String,
    val title: String,
    val details: String,
    val points: Int,
    val support: String
)

private data class ExtraQuestUiState(
    val id: Long,
    val title: String,
    val subtitle: String,
    val points: Int,
    val completed: Boolean = false
)

private fun List<TaskItem>.dailyTaskOrFallback(): QuestCardUiState =
    firstOrNull { it.kind == "дело дня" }
        ?.toDailyQuestCard()
        ?: demoToday.dailyTask

private fun TaskItem.toDailyQuestCard(): QuestCardUiState =
    QuestCardUiState(
        label = "Дело дня",
        title = title,
        details = "$durationMinutes минут",
        points = points,
        support = "важн. $importance · сложн. $difficulty · сил $energyCost"
    )

private fun List<TaskItem>.extraQuestCards(completedIds: Set<Long>): List<ExtraQuestUiState> =
    filter { it.kind != "дело дня" && it.title != demoToday.impulse.title }
        .take(4)
        .map { task ->
            ExtraQuestUiState(
                id = task.id,
                title = task.title,
                subtitle = "${task.category} · сложн. ${task.difficulty} · сил ${task.energyCost}",
                points = task.points,
                completed = task.id in completedIds
            )
        }
        .ifEmpty { demoToday.extraQuests }

@Preview(showBackground = true)
@Composable
private fun TodayScreenPreview() {
    ImpulseTheme {
        TodayScreen(
            progressStore = InMemoryTodayProgressStore(),
            taskStore = InMemoryTaskStore()
        )
    }
}
