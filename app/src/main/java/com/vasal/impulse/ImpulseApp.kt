package com.vasal.impulse

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.vasal.impulse.domain.AppInfo
import com.vasal.impulse.feature.today.TodayScreen
import com.vasal.impulse.ui.theme.ImpulseTheme

@Composable
fun ImpulseApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = { ImpulseBottomBar() }
        ) { innerPadding ->
            TodayScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .semantics { contentDescription = AppInfo.firstScreenContentDescription }
            )
        }
    }
}

@Composable
private fun ImpulseBottomBar() {
    val destinations = listOf(
        BottomDestination(label = "Сегодня", marker = "С", selected = true),
        BottomDestination(label = "Дела", marker = "Д"),
        BottomDestination(label = "Статистика", marker = "С"),
        BottomDestination(label = "Награды", marker = "Н")
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = destination.selected,
                onClick = { },
                icon = { Text(destination.marker) },
                label = { Text(destination.label) }
            )
        }
    }
}

private data class BottomDestination(
    val label: String,
    val marker: String,
    val selected: Boolean = false
)

@Preview(showBackground = true)
@Composable
private fun ImpulseAppPreview() {
    ImpulseTheme {
        ImpulseApp()
    }
}
