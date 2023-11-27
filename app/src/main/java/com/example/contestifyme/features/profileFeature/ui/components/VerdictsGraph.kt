package com.example.contestifyme.features.profileFeature.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.contestifyme.features.profileFeature.constants.ProfileConstants

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VerdictGraph(
    modifier: Modifier = Modifier,
    verdicts: HashMap<String, Int>,
    pieChartData: PieChartData,
    pieChartConfig: PieChartConfig
) {
    Column(modifier = modifier) {
    Text(
        text = "VERDICTS",
        fontSize = 22.sp,
        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
    PieChart(
        modifier = Modifier
            .width(400.dp)
            .height(400.dp),
        pieChartData,
        pieChartConfig
    )
    FlowRow {
        verdicts.keys.forEach {
            Card(
                Modifier
                    .padding(vertical = 8.dp)
                    .padding(end = 4.dp)
            ) {
                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .background(ProfileConstants.verdictsColors[it.uppercase()]!!.first)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = it, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = verdicts[it].toString(), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}