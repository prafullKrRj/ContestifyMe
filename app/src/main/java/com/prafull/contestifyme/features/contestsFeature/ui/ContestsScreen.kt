package com.prafull.contestifyme.features.contestsFeature.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contestifyme.R
import com.prafull.contestifyme.commons.ui.Headings
import com.prafull.contestifyme.commons.ui.SimpleTopAppBar
import com.prafull.contestifyme.commons.ui.getTime
import com.prafull.contestifyme.features.contestsFeature.data.local.ContestsEntity

@Composable
fun ContestsScreen(
    viewModel: ContestsViewModel = hiltViewModel()
) {
    val state: ContestUiState by viewModel.state.collectAsState()
    Scaffold (
        topBar = {
            SimpleTopAppBar (label = R.string.contest)
        }
    ){ paddingValues ->
        if (state.contests.isNotEmpty()) {
            ContestsMainScreen(contest = state.contests, modifier = Modifier.padding(paddingValues))
        } else {
            Headings(modifier = Modifier.padding(start = 16.dp), label = R.string.no_contests)
        }
    }
}

@Composable
fun ContestsMainScreen(contest: List<ContestsEntity>, modifier: Modifier) {
    LazyColumn(modifier) {
        item {
            Headings(modifier = Modifier.padding(start = 16.dp), label = R.string.upcoming_contests)
        }
        contest.filter {
            it.relativeTimeSeconds <= 0
        }.sortedBy {
            it.startTimeSeconds
        }.forEach {
            item {
                ContestItem(contest = it)
            }
        }
        item {
            Headings(modifier = Modifier.padding(start = 16.dp), label = R.string.past_contests)
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
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clickable { },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            val startTime = getTime(contest.startTimeSeconds.toLong())
            ContestDetails(type = "Name", detail = contest.name)
            ContestDetails(type = "Type", detail = contest.type)
            ContestDetails(type = "Phase", detail = contest.phase)
            ContestDetails(type = "Duration", detail = "${contest.durationSeconds / 3600} hours")
            ContestDetails(type = "Start Time", detail = "${startTime.hour}:${if (startTime.minute < 10) "0"+startTime.minute else startTime.minute}, ${startTime.dayOfMonth}-${startTime.monthValue}-${startTime.year}")

        }
    }
}
@Composable
fun ContestDetails(type: String = "", detail: String) {
    Row {
        Text(
            modifier = Modifier.weight(.2f),
            text = type,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
        )
        Text(
            modifier = Modifier.weight(.05f),
            text = "->",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
        )
        Text(
            modifier = Modifier.weight(.5f),
            text = detail,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
        )

    }
}