package com.vasal.impulse

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.vasal.impulse.domain.AppInfo
import com.vasal.impulse.feature.rewards.RewardsScreen
import com.vasal.impulse.feature.stats.StatsScreen
import com.vasal.impulse.feature.tasks.TasksScreen
import com.vasal.impulse.feature.today.TodayScreen
import com.vasal.impulse.ui.theme.ImpulseTheme
import kotlinx.coroutines.launch

@Composable
fun ImpulseApp() {
    val pagerState = rememberPagerState(pageCount = { AppSection.entries.size })
    val coroutineScope = rememberCoroutineScope()
    val selectedSection = AppSection.entries[pagerState.currentPage]

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                ImpulseBottomBar(
                    selectedSection = selectedSection,
                    onSectionSelected = { section ->
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(section.ordinal)
                        }
                    }
                )
            }
        ) { innerPadding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .semantics { contentDescription = AppInfo.firstScreenContentDescription }
            ) { page ->
                AppSectionContent(
                    selectedSection = AppSection.entries[page],
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun AppSectionContent(
    selectedSection: AppSection,
    modifier: Modifier = Modifier
) {
    when (selectedSection) {
        AppSection.Today -> TodayScreen(modifier = modifier)
        AppSection.Tasks -> TasksScreen(modifier = modifier)
        AppSection.Stats -> StatsScreen(modifier = modifier)
        AppSection.Rewards -> RewardsScreen(modifier = modifier)
    }
}

@Composable
private fun ImpulseBottomBar(
    selectedSection: AppSection,
    onSectionSelected: (AppSection) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        AppSection.entries.forEach { section ->
            NavigationBarItem(
                selected = selectedSection == section,
                onClick = { onSectionSelected(section) },
                icon = { Text(section.marker) },
                label = { Text(section.label) }
            )
        }
    }
}

private enum class AppSection(
    val label: String,
    val marker: String
) {
    Today(label = "Сегодня", marker = "С"),
    Tasks(label = "Дела", marker = "Д"),
    Stats(label = "Статистика", marker = "С"),
    Rewards(label = "Награды", marker = "Н")
}

@Preview(showBackground = true)
@Composable
private fun ImpulseAppPreview() {
    ImpulseTheme {
        ImpulseApp()
    }
}
