package com.vasal.impulse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vasal.impulse.domain.AppInfo
import com.vasal.impulse.ui.theme.ImpulseTheme

@Composable
fun ImpulseApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold { innerPadding ->
            TodaySkeletonScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .semantics { contentDescription = AppInfo.firstScreenContentDescription }
            )
        }
    }
}

@Composable
private fun TodaySkeletonScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = AppInfo.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Сегодня можно начать с маленького движения.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.72f)
        )

        ProgressPreviewCard()
        StarterCard()
        DailyTaskCard()
    }
}

@Composable
private fun ProgressPreviewCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Прогресс недели",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "0 следов пока. Скоро здесь появится карта недели.")
        }
    }
}

@Composable
private fun StarterCard() {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Импульс дня",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "Разобрать кухню 5 минут")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = { }) {
                    Text("Сделано")
                }
            }
        }
    }
}

@Composable
private fun DailyTaskCard() {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Дело дня",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "Запланируем первое важное дело на следующей итерации.")
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Если сил мало, приложение предложит замену.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.68f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImpulseAppPreview() {
    ImpulseTheme {
        ImpulseApp()
    }
}

