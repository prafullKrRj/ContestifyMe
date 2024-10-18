package com.prafull.contestifyme.app.contestsFeature.contestListScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.contestifyme.R
import com.prafull.contestifyme.app.ContestRoutes
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.ui.SimpleTopAppBar
import com.prafull.contestifyme.app.commons.ui.getTime
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsEntity
import com.prafull.contestifyme.app.friendsFeature.ui.friendList.LoadingScreen

@Composable
fun ContestsScreen(
    viewModel: ContestsViewModel,
    navController: NavController
) {
    val contestState by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            SimpleTopAppBar(label = R.string.contest)
        },
    ) { paddingValues ->
        when (val state = contestState) {
            is BaseClass.Loading -> {
                LoadingScreen()
            }

            is BaseClass.Error -> {
                Headings(modifier = Modifier.padding(start = 16.dp), label = R.string.error)
            }

            is BaseClass.Success -> {
                ContestsMainScreen(
                    contest = state.data,
                    modifier = Modifier.padding(paddingValues),
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun Headings(modifier: Modifier = Modifier, label: Int) {
    Text(
        modifier = modifier,
        text = stringResource(id = label),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ContestsMainScreen(
    contest: List<ContestsEntity>,
    modifier: Modifier,
    navController: NavController
) {
    LazyColumn(modifier) {

        contest.filter {
            it.relativeTimeSeconds <= 0
        }.sortedBy {
            it.startTimeSeconds
        }.isNotEmpty().let {
            val upcomingContests = contest.filter {
                it.relativeTimeSeconds <= 0
            }.sortedBy {
                it.startTimeSeconds
            }
            item {
                Headings(
                    modifier = Modifier.padding(start = 16.dp),
                    label = R.string.upcoming_contests
                )
            }
            items(upcomingContests) {
                ContestItem(contest = it, navController = navController, false)
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
                ContestItem(contest = it, navController = navController, true)
            }
        }
    }
}

@Composable
fun ContestItem(
    contest: ContestsEntity,
    navController: NavController,
    clickableView: Boolean = true
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clickable(enabled = clickableView) {
                navController.navigate(
                    ContestRoutes.ContestScreen(contest.id.toString(), contest.name)
                )
            },
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
            ContestDetails(
                type = "Start Time",
                detail = "${startTime.hour}:${if (startTime.minute < 10) "0" + startTime.minute else startTime.minute}, ${startTime.dayOfMonth}-${startTime.monthValue}-${startTime.year}"
            )

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