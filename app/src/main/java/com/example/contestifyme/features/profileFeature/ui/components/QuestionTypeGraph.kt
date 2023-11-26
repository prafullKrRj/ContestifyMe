package com.example.contestifyme.features.profileFeature.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType

@Composable
fun QuestionTypeGraph(modifier: Modifier = Modifier) {
  /*  val barChartData = DataUtils.getBarChartData(
        listSize = 7,
        barChartType = BarChartType.HORIZONTAL,
        maxRange = 7,
        dataCategoryOptions = DataCategoryOptions()
    )
    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(barChartData.size - 1)
        .bottomPadding(40.dp)
        .axisLabelAngle(20f)
        .labelData { index -> barChartData[index].label }
        .build()

    val yAxisData = AxisData.Builder()
        .steps(25)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (7 / 2)).toString() }
        .build()

    BarChart(modifier = Modifier.height(350.dp), barChartData = BarChartData(barChartData, xAxisData, yAxisData))*/

}