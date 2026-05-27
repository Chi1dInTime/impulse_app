package com.vasal.impulse.feature.stats

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vasal.impulse.ui.theme.DayBlue
import com.vasal.impulse.ui.theme.DayGreen
import com.vasal.impulse.ui.theme.DayPurple
import com.vasal.impulse.ui.theme.ImpulseTheme
import com.vasal.impulse.ui.theme.WarmGreenContainer
import com.vasal.impulse.ui.theme.WarmSurface

private val demoDays = listOf(
    WeekDay("Пн", DayGreen),
    WeekDay("Вт", DayBlue),
    WeekDay("Ср", null),
    WeekDay("Чт", DayPurple),
    WeekDay("Пт", DayGreen),
    WeekDay("Сб", null),
    WeekDay("Вс", null)
)

@Composable
fun StatsScreen(modifier: Modifier = Modifier) {
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
                        text = "74 очка",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    demoDays.forEach { day ->
                        DayLevelBox(day)
                    }
                }
            }
        }
        TracePreview("Вт", "Резюме сдвинулось", "поправлена структура · +35")
        TracePreview("Пн", "День начался с кухни", "импульс + прогулка · +18")
        TracePreview("Вс", "Лёгкая версия тоже считается", "фильм осознанно · +12")
    }
}

@Composable
private fun DayLevelBox(day: WeekDay) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(day.color ?: MaterialTheme.colorScheme.surface.copy(alpha = 0.72f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.label,
            color = if (day.color == null) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f) else Color.White,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TracePreview(day: String, title: String, subtitle: String) {
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
                text = day,
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(WarmGreenContainer)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
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

private data class WeekDay(
    val label: String,
    val color: Color?
)

@Preview(showBackground = true)
@Composable
private fun StatsScreenPreview() {
    ImpulseTheme {
        StatsScreen()
    }
}
