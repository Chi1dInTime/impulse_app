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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.vasal.impulse.data.RoomTodayProgressStore
import com.vasal.impulse.data.RoomTaskStore
import com.vasal.impulse.data.TaskStore
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
    val database = rememberImpulseDatabase()
    ImpulseApp(
        todayProgressStore = rememberRoomTodayProgressStore(database),
        taskStore = rememberRoomTaskStore(database)
    )
}

@Composable
fun ImpulseApp(
    todayProgressStore: TodayProgressStore,
    taskStore: TaskStore
) {
    val pagerState = rememberPagerState(pageCount = { AppSection.entries.size })
    val coroutineScope = rememberCoroutineScope()
    val selectedSection = AppSection.entries[pagerState.currentPage]

    LaunchedEffect(taskStore) {
        taskStore.seedDefaultsIfEmpty()
    }

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
                    taskStore = taskStore,
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
    taskStore: TaskStore,
    modifier: Modifier = Modifier
) {
    when (selectedSection) {
        AppSection.Today -> TodayScreen(
            progressStore = todayProgressStore,
            taskStore = taskStore,
            modifier = modifier
        )
        AppSection.Tasks -> TasksScreen(
            taskStore = taskStore,
            modifier = modifier
        )
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
                        text = section.label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1,
                        softWrap = false,
                        textAlign = TextAlign.Center
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
    val label: String
) {
    Today(label = "Сегодня"),
    Tasks(label = "Дела"),
    Stats(label = "Статистика"),
    Rewards(label = "Награды")
}

@Composable
private fun rememberImpulseDatabase(): ImpulseDatabase {
    val context = LocalContext.current.applicationContext
    return remember(context) {
        ImpulseDatabase.getInstance(context)
    }
}

@Composable
private fun rememberRoomTodayProgressStore(database: ImpulseDatabase): TodayProgressStore =
    remember(database) {
        RoomTodayProgressStore(dao = database.dailyProgressDao())
    }

@Composable
private fun rememberRoomTaskStore(database: ImpulseDatabase): TaskStore =
    remember(database) {
        RoomTaskStore(dao = database.questDao())
    }

@Preview(showBackground = true)
@Composable
private fun ImpulseAppPreview() {
    ImpulseTheme {
        ImpulseApp(
            todayProgressStore = com.vasal.impulse.data.InMemoryTodayProgressStore(),
            taskStore = com.vasal.impulse.data.InMemoryTaskStore()
        )
    }
}
