package com.example.contestifyme.features.profileFeature.ui

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
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.ui.components.ProfileCard
import com.example.contestifyme.features.profileFeature.ui.components.QuestionByIndexGraph
import com.example.contestifyme.features.profileFeature.ui.components.RankCard
import com.example.contestifyme.features.profileFeature.ui.components.RatingGraph
import com.example.contestifyme.features.profileFeature.ui.components.SubmissionsGraph
import com.example.contestifyme.features.profileFeature.ui.components.VerdictGraph

@Composable
fun FrontScreen(user: UserInfoEntity, viewModel: ProfileViewModel, swipeToSubmission: () -> Unit = {}) {
    val verdicts by rememberSaveable {
        mutableStateOf(viewModel.getVerdicts())
    }
    val solvedByTags by rememberSaveable {
        mutableStateOf(viewModel.getQuestionSolvedByTags())
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
        }
        item {
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
        item { 
            QuestionByTypeGraph(solvedByTags)
        }
        if (user.subMissionInfo.isNotEmpty()) {
            item {
                VerdictGraph(
                    verdicts = verdicts,
                    pieChartData = GetChartData.getVerdictsData(verdicts),
                    pieChartConfig = GetChartData.pieChartConfig()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            QuestionByIndexGraph(viewModel.getQuestionSolvedByIndexData())
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@Composable
fun QuestionByTypeGraph(questionSolvedByTags: HashMap<String, Int>) {

    Text(
        text = "Question Solved By Tags",
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 8.dp),
        textAlign = TextAlign.Center
    )
    Row(modifier = Modifier) {
        DonutPieChart(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            pieChartData = GetChartData.getQuestionSolvedByTagsData(questionSolvedByTags),
            pieChartConfig = PieChartConfig(
                labelVisible = false,
                labelFontSize = 42.sp,
                strokeWidth = 120f,
                labelColor = Color.Black,
                activeSliceAlpha = .9f,
                isAnimationEnable = true,
                backgroundColor = Color.Transparent,
            )
        )
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