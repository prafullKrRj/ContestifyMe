package com.example.contestifyme.features.profileFeature.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.contestifyme.commons.GetChartData
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.ui.components.DetailsDialog
import com.example.contestifyme.features.profileFeature.ui.components.ProfileCard
import com.example.contestifyme.features.profileFeature.ui.components.QuestionByIndexGraph
import com.example.contestifyme.features.profileFeature.ui.components.QuestionByTypeGraph
import com.example.contestifyme.features.profileFeature.ui.components.RankCard
import com.example.contestifyme.features.profileFeature.ui.components.RatingGraph
import com.example.contestifyme.features.profileFeature.ui.components.SubmissionsGraph
import com.example.contestifyme.features.profileFeature.ui.components.VerdictGraph

@SuppressLint("MutableCollectionMutableState")
@Composable
fun FrontScreen(user: UserInfoEntity, swipeToSubmission: () -> Unit = {}) {
    val verdicts by rememberSaveable {
        mutableStateOf(GetChartData.getVerdicts(user))
    }
    val solvedByTags by rememberSaveable {
        mutableStateOf(GetChartData.getQuestionSolvedByTags(user))
    }
    var showQuestionSolvedByTagsDialog by remember {
        mutableStateOf(false)
    }
    var showQuestionSolvedByIndexDialog by remember {
        mutableStateOf(false)
    }
    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            ToSubmissionsScreenButton {
                swipeToSubmission()
            }
        }
        item {
            RankCard(
                modifier = Modifier.fillMaxWidth(),
                rank = "${user.rank}",
                handle = user.handle,
                country = "India"
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileCard(modifier = Modifier.fillMaxWidth(), user = user)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            RatingGraph()
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            SubmissionsGraph()
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (user.subMissionInfo.isNotEmpty()) {
            item {
                Divider(Modifier.fillMaxWidth())
                VerdictGraph(
                    verdicts = verdicts,
                    pieChartData = GetChartData.getVerdictsData(verdicts),
                    pieChartConfig = GetChartData.pieChartConfig()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        item {

            ShowGraphButtons(showQuestionSolvedByTagsDialog = {
                showQuestionSolvedByTagsDialog = true
            }, showQuestionSolvedByIndexDialog = {
                showQuestionSolvedByIndexDialog = true
            })
        }
    }
    if (showQuestionSolvedByTagsDialog) {
        DetailsDialog(content = {
            QuestionByTypeGraph(solvedByTags)
        }) {
            showQuestionSolvedByTagsDialog = false
        }
    }
    if (showQuestionSolvedByIndexDialog) {
        DetailsDialog(content = {
            QuestionByIndexGraph(GetChartData.getQuestionSolvedByIndexData(user))
        }) {
            showQuestionSolvedByIndexDialog = false
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

@Composable
fun ShowGraphButtons(showQuestionSolvedByTagsDialog: () -> Unit, showQuestionSolvedByIndexDialog: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            showQuestionSolvedByTagsDialog()
        }) {
            Text(
                modifier = Modifier,
                text = "Tags Wise Solved",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
        }
        Button(onClick = {
            showQuestionSolvedByIndexDialog()
        }) {
            Text(
                modifier = Modifier,
                text = "Index Wise Solved",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
        }
    }
}