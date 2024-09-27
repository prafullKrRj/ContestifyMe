package com.prafull.contestifyme.app.profileFeature.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prafull.contestifyme.app.commons.GetChartData
import com.prafull.contestifyme.app.commons.ui.ProfileCard
import com.prafull.contestifyme.app.commons.ui.QuestionByIndexGraph
import com.prafull.contestifyme.app.commons.ui.QuestionByTypeGraph
import com.prafull.contestifyme.app.commons.ui.RankCard
import com.prafull.contestifyme.app.commons.ui.RatingGraph
import com.prafull.contestifyme.app.commons.ui.SubmissionsGraph
import com.prafull.contestifyme.app.commons.ui.VerdictGraph
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity

@SuppressLint("MutableCollectionMutableState")
@Composable
fun FrontScreen(user: UserInfoEntity, swipeToSubmission: () -> Unit = {}) {
    val verdicts by rememberSaveable {
        mutableStateOf(GetChartData.getVerdictsFromUserEntity(user))
    }
    val solvedByTags by rememberSaveable {
        mutableStateOf(GetChartData.getQuestionSolvedByTags(user.subMissionInfo))
    }
    var showQuestionSolvedByTags by remember {
        mutableStateOf(false)
    }
    var showQuestionSolvedByIndex by remember {
        mutableStateOf(false)
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item("submissions") {
            ToSubmissionsScreenButton {
                swipeToSubmission()
            }
        }
        item("rank") {
            RankCard(
                modifier = Modifier.fillMaxWidth(),
                rank = "${user.rank}",
                handle = user.handle,
                country = "India"
            )
        }
        item {
            ProfileCard(modifier = Modifier.fillMaxWidth(), user = user.toUserData())
        }
        item("ratingGraph") {
            RatingGraph()
        }
        item {
            HorizontalDivider(Modifier.height(1.dp))
        }
        item("submissionGraph") {
            SubmissionsGraph()
        }
        item {
            HorizontalDivider(Modifier.height(1.dp))
        }
        item("submissionVerdicts") {
            if (user.subMissionInfo.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                    ), modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        VerdictGraph(
                            verdicts = verdicts,
                            pieChartData = GetChartData.getVerdictsData(verdicts),
                            pieChartConfig = GetChartData.pieChartConfig()
                        )
                    }
                }
            }
        }
        item {
            HorizontalDivider()
        }
        item("questionByType") {
            ExpandCard(
                expanded = showQuestionSolvedByTags,
                title = "Questions Solved by Tags",
                content = {
                    QuestionByTypeGraph(solvedByTags)
                }, onExpand = {
                    showQuestionSolvedByTags = !showQuestionSolvedByTags
                })
        }
        item {
            HorizontalDivider()
        }
        item("questionByIndex") {
            ExpandCard(
                expanded = showQuestionSolvedByIndex,
                title = "Questions Solved by index",
                content = {
                    QuestionByIndexGraph(GetChartData.getQuestionSolvedByIndexData(user.subMissionInfo))
                }) {
                showQuestionSolvedByIndex = !showQuestionSolvedByIndex
            }
        }
    }
}

@Composable
fun ExpandCard(
    expanded: Boolean,
    title: String,
    content: @Composable () -> Unit,
    onExpand: () -> Unit
) {
    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title, style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onExpand) {
                    Icon(
                        imageVector = if (!expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = "Expand"
                    )
                }
            }
            if (expanded) {
                content()
            }
        }
    }
}

@Composable
fun ToSubmissionsScreenButton(swipeToSubmission: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        FilledIconButton(onClick = { swipeToSubmission() }) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}