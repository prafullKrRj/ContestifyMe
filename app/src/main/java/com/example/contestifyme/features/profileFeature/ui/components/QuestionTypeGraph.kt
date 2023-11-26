package com.example.contestifyme.features.profileFeature.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestionByIndexGraph(data: HashMap<String, Int>) {
    val xData = data.keys.toList().filter {
        !it.last().isDigit()
    }.sorted()
    val yData = xData.map {
        data[it] ?: 0
    }
    Text(
        text = "Index Wise Solved",
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 8.dp),
        textAlign = TextAlign.Center
    )
    CustomVerticalBarChart(xData, yData)
}
