package com.example.contestifyme.features.contestsFeature.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ListItem
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.contestifyme.features.contestsFeature.data.local.ContestDB
import com.example.contestifyme.features.contestsFeature.data.local.ContestsEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContestsScreen(
    viewModel: ContestsViewModel
) {

    val state: ContestUiState by viewModel.state.collectAsState()
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Contests",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
            })
        }
    ){ paddingValues ->
        if (state.contests.isNotEmpty()) {
            ContestsMainScreen(contest = state.contests, modifier = Modifier.padding(paddingValues))
        } else {
            Text(
                modifier = Modifier,
                text = "No Contests",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }

}

@Composable
fun ContestsMainScreen(contest: List<ContestsEntity>, modifier: Modifier) {
    LazyColumn(modifier) {
        item {
            Text(
                modifier = Modifier,
                text = "Upcoming Contests",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Start,
            )
        }
        contest.filter {
            it.relativeTimeSeconds <= 0
        }.sortedByDescending {
            it.startTimeSeconds
        }.forEach {
            item {
                ContestItem(contest = it)
            }
        }
        item {
            Text(
                modifier = Modifier,
                text = "Past Contests",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Start,
            )
        }
        contest.filter {
            it.relativeTimeSeconds > 0
        }.sortedByDescending {
            it.startTimeSeconds
        }.forEach {
            item {
                ContestItem(contest = it)
            }
        }
    }
}
@Composable
fun ContestItem(contest: ContestsEntity) {
    ListItem(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).clip(RoundedCornerShape(8.dp)),
        headlineContent = { Text(contest.name) },
        leadingContent = {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Info"
            )
        },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
    )
}