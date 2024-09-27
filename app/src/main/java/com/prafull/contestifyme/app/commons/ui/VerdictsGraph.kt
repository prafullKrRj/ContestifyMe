package com.prafull.contestifyme.app.commons.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VerdictGraph(
    verdicts: HashMap<String, Int>,
    pieChartData: PieChartData,
    pieChartConfig: PieChartConfig
) {
    Text(text = "Verdicts", fontSize = 20.sp)
    PieChart(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
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
                    /*  Box(
                          modifier = Modifier
                              .width(20.dp)
                              .height(20.dp)
                              .background(ProfileConstants.verdictsColors[it.uppercase()]!!.first)
                      )*/
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = it, fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = verdicts[it].toString(), fontSize = 12.sp)
                }
            }
        }
    }
}