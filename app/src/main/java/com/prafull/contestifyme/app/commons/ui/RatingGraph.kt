package com.prafull.contestifyme.app.commons.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prafull.contestifyme.app.profileFeature.domain.model.UserRating
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RatingGraph(userRatings: List<UserRating>) {
    val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())
    val ratings = userRatings.map { it.newRating.toDouble() }
    val dates = userRatings.map { dateFormat.format(Date(it.ratingUpdateTimeSeconds * 1000L)) }

    LineChart(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp),
        data = listOf(
            Line(
                label = "User Ratings",
                values = ratings,
                color = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF00FF00),
                        Color(0xFF0000FF)
                    )
                ),
                drawStyle = DrawStyle.Stroke(width = 2.dp)
            )
        )
    )
}
/*
@Composable
fun RatingGraph(pointsData: List<Point>, modifier: Modifier = Modifier) {
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color.Blue)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(3)
        .backgroundColor(Color.Red)
        .labelAndAxisLinePadding(20.dp).build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        isZoomAllowed = false,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White,
    )
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}

@Preview
@Composable
fun RatingGraphPreview() {
    RatingGraph(
        pointsData = listOf(
            Point(1f, 1f),
            Point(2f, 2f),
            Point(3f, 3f),
            Point(4f, 4f),
            Point(5f, 5f),
            Point(6f, 6f),
            Point(7f, 7f),
            Point(8f, 8f),

            )
    )
}*/