package com.example.contestifyme.features.profileFeature.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import com.example.contestifyme.features.profileFeature.constants.ProfileConstants
import com.example.contestifyme.features.profileFeature.ui.GetChartData

@OptIn(ExperimentalLayoutApi::class)
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
    FlowRow {
        questionSolvedByTags.keys.forEach {
            Card(
                Modifier
                    .padding(vertical = 8.dp)
                    .padding(end = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .background(ProfileConstants.solvedByTagsColor[it.lowercase()]!!)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = it, fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = questionSolvedByTags[it].toString(), fontSize = 12.sp)
                }
            }
        }
    }
}
