package com.vasal.impulse

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.vasal.impulse.data.RoomTodayProgressStore
import com.vasal.impulse.data.TodayProgressStore
import com.vasal.impulse.data.local.ImpulseDatabase
import com.vasal.impulse.domain.AppInfo
import com.vasal.impulse.feature.rewards.RewardsScreen
import com.vasal.impulse.feature.stats.StatsScreen
import com.vasal.impulse.feature.tasks.TasksScreen
import com.vasal.impulse.feature.today.TodayScreen
import com.vasal.impulse.ui.theme.DayGreen
import com.vasal.impulse.ui.theme.ImpulseTheme
import com.vasal.impulse.ui.theme.WarmGreenContainer
import kotlinx.coroutines.launch

@Composable
fun ImpulseApp() {
    ImpulseApp(todayProgressStore = rememberRoomTodayProgressStore())
}

@Composable
fun ImpulseApp(todayProgressStore: TodayProgressStore) {
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
                    todayProgressStore = todayProgressStore,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun AppSectionContent(
    selectedSection: AppSection,
    todayProgressStore: TodayProgressStore,
    modifier: Modifier = Modifier
) {
    when (selectedSection) {
        AppSection.Today -> TodayScreen(
            progressStore = todayProgressStore,
            modifier = modifier
        )
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
            val selected = selectedSection == section
            NavigationBarItem(
                modifier = Modifier.semantics {
                    contentDescription = "Раздел ${section.label}"
                },
                selected = selected,
                onClick = { onSectionSelected(section) },
                icon = {
                    Text(
                        text = section.marker,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                label = {
                    Text(
                        text = section.label,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DayGreen,
                    selectedTextColor = DayGreen,
                    indicatorColor = WarmGreenContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.58f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.58f)
                )
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

@Composable
private fun rememberRoomTodayProgressStore(): TodayProgressStore {
    val context = LocalContext.current.applicationContext
    return remember(context) {
        RoomTodayProgressStore(
            dao = ImpulseDatabase.getInstance(context).dailyProgressDao()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ImpulseAppPreview() {
    ImpulseTheme {
        ImpulseApp()
    }
}
