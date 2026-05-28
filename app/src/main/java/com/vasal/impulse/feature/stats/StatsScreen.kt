package com.vasal.impulse.feature.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vasal.impulse.data.DayProgressHistoryItem
import com.vasal.impulse.data.InMemoryTodayProgressStore
import com.vasal.impulse.data.TodayProgressState
import com.vasal.impulse.data.TodayProgressStore
import com.vasal.impulse.domain.DayProgressCalculator
import com.vasal.impulse.domain.DayProgressLevel
import com.vasal.impulse.ui.theme.DayBlue
import com.vasal.impulse.ui.theme.DayGreen
import com.vasal.impulse.ui.theme.DayPurple
import com.vasal.impulse.ui.theme.ImpulseTheme
import com.vasal.impulse.ui.theme.WarmGreenContainer
import com.vasal.impulse.ui.theme.WarmSurface
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun StatsScreen(
    progressStore: TodayProgressStore,
    modifier: Modifier = Modifier
) {
    val history by progressStore.dayHistory.collectAsStateWithLifecycle(initialValue = emptyList())
    val week = currentWeek(history)
    val weekPoints = week.sumOf { it.points }
    var selectedDay by remember(week) {
        mutableStateOf(week.firstOrNull { it.date == LocalDate.now().toString() } ?: week.first())
    }
    val traces = history
        .filter { it.traceTitle != null }
        .sortedByDescending { it.date }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SectionHeader(
            title = "Статистика",
            subtitle = "Следы дней и недельный ритм."
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Прогресс недели",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "$weekPoints очков",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    week.forEach { day ->
                        DayLevelBox(
                            day = day,
                            selected = selectedDay.date == day.date,
                            onClick = { selectedDay = day }
                        )
                    }
                }
            }
        }
        DayDetailsCard(selectedDay)
        if (traces.isEmpty()) {
            EmptyTraceCard()
        } else {
            traces.forEach { trace ->
                TracePreview(trace)
            }
        }
    }
}

@Composable
private fun DayLevelBox(
    day: WeekDay,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = day.color
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color ?: MaterialTheme.colorScheme.surface.copy(alpha = 0.72f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.label,
            color = if (color == null) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f) else Color.White,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Bold
        )
    }
}

@Composable
private fun DayDetailsCard(day: WeekDay) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "День: ${day.label}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${day.points} очков",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            val entry = day.entry
            if (entry == null) {
                Text(
                    text = "Пока ничего не записано.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.68f)
                )
            } else {
                if (entry.impulseCompleted) {
                    Text("Импульс дня выполнен", style = MaterialTheme.typography.bodyMedium)
                }
                if (entry.dailyTaskCompleted) {
                    Text("Дело дня выполнено", style = MaterialTheme.typography.bodyMedium)
                }
                Text(
                    text = entry.traceTitle ?: "След дня пока без названия",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun EmptyTraceCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = WarmSurface)
    ) {
        Text(
            text = "Следов пока нет",
            modifier = Modifier.padding(14.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun TracePreview(trace: DayProgressHistoryItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = WarmSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dayLabel(trace.date),
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(WarmGreenContainer)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = trace.traceTitle ?: "День стал видимым",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${trace.points} очков · ${traceSubtitle(trace)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
                )
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

private fun currentWeek(history: List<DayProgressHistoryItem>): List<WeekDay> {
    val byDate = history.associateBy { it.date }
    val start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    return (0L..6L).map { offset ->
        val date = start.plusDays(offset)
        val entry = byDate[date.toString()]
        WeekDay(
            date = date.toString(),
            label = labelFor(date.dayOfWeek),
            points = entry?.points ?: 0,
            color = entry?.points?.let(::colorForPoints),
            entry = entry
        )
    }
}

private fun colorForPoints(points: Int): Color? =
    when (DayProgressCalculator.calculate(points).level) {
        DayProgressLevel.Neutral -> null
        DayProgressLevel.Minimum -> DayGreen
        DayProgressLevel.Strong -> DayBlue
        DayProgressLevel.Super -> DayPurple
    }

private fun traceSubtitle(trace: DayProgressHistoryItem): String =
    when {
        trace.dailyTaskCompleted -> "дело дня выполнено"
        trace.impulseCompleted -> "импульс выполнен"
        else -> "день сохранён"
    }

private fun dayLabel(date: String): String =
    runCatching { labelFor(LocalDate.parse(date).dayOfWeek) }.getOrElse { "День" }

private fun labelFor(dayOfWeek: DayOfWeek): String =
    when (dayOfWeek) {
        DayOfWeek.MONDAY -> "Пн"
        DayOfWeek.TUESDAY -> "Вт"
        DayOfWeek.WEDNESDAY -> "Ср"
        DayOfWeek.THURSDAY -> "Чт"
        DayOfWeek.FRIDAY -> "Пт"
        DayOfWeek.SATURDAY -> "Сб"
        DayOfWeek.SUNDAY -> "Вс"
    }

private data class WeekDay(
    val date: String,
    val label: String,
    val points: Int,
    val color: Color?,
    val entry: DayProgressHistoryItem?
)

@Preview(showBackground = true)
@Composable
private fun StatsScreenPreview() {
    ImpulseTheme {
        StatsScreen(
            progressStore = InMemoryTodayProgressStore(
                initialState = TodayProgressState(
                    points = 67,
                    impulseCompleted = true,
                    dailyTaskCompleted = true,
                    traceTitle = "Обновить резюме"
                )
            )
        )
    }
}
