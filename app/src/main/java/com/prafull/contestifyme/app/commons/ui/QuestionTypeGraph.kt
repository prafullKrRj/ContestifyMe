package com.prafull.contestifyme.app.commons.ui

import androidx.compose.runtime.Composable

@Composable
fun QuestionByIndexGraph(data: HashMap<String, Int>) {
    val xData = data.keys.toList().filter {
        !it.last().isDigit()
    }.sorted()
    val yData = xData.map {
        data[it] ?: 0
    }
    CustomVerticalBarChart(xData, yData)
}
