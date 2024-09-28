package com.prafull.contestifyme.app.userscreen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.prafull.contestifyme.app.App
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.app.commons.ui.ProfileCard
import com.prafull.contestifyme.app.profileFeature.domain.model.UserRating
import com.prafull.contestifyme.app.profileFeature.domain.model.UserSubmissions
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.RowChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getVerdictFrequency(userSubmissions: List<UserSubmissions>): Map<String, Int> {
    return userSubmissions.groupingBy { it.verdict }.eachCount()
}

@Composable
fun UserProfileScreen(userData: UserData, modifier: Modifier, navController: NavController) {
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item("rankCard") {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedCard(Modifier.weight(.6f)) {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        userData.usersInfo.rank?.let { Text(text = it) }
                        userData.usersInfo.handle?.let { Text(text = it) }
                        userData.usersInfo.country?.let { Text(text = it) }
                    }
                }
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.weight(.4f))
                } else {
                    TextButton(
                        onClick = {
                            isLoading = true
                            scope.launch(Dispatchers.Default) {
                                val submissions = Gson().toJson(userData.userSubmissions)
                                withContext(Dispatchers.Main) {
                                    isLoading = false
                                    navController.navigate(App.SubmissionScreen(submissions))
                                }
                            }
                        },
                        modifier = Modifier.weight(.4f),
                        enabled = !isLoading
                    ) {
                        Text(text = "Submissions")
                    }
                }
            }
        }
        item("profileCard") {
            ProfileCard(modifier = Modifier, user = userData)
        }
        item("ratingGraph") {
            Card(Modifier.fillMaxWidth()) {
                UserRatingGraph(userData.userRating, modifier = Modifier.padding(vertical = 8.dp))
            }
        }
        item("verdictsGraph") {
            Card(Modifier.fillMaxWidth()) {
                UserVerdictsGraph(
                    userSubmissions = userData.userSubmissions,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        item("tagsGraph") {
            Card(Modifier.fillMaxWidth()) {
                UserTagsDoughnutChart(
                    userSubmissions = userData.userSubmissions,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        item("languagesGraph") {
            Card(Modifier.fillMaxWidth()) {
                UserLanguagesGraph(
                    userSubmissions = userData.userSubmissions,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        item("indexGraph") {
            Card(Modifier.fillMaxWidth()) {
                QuestionSolvedByIndexRowChart(
                    submissions = userData.userSubmissions,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun QuestionSolvedByIndexRowChart(
    submissions: List<UserSubmissions>, modifier: Modifier = Modifier
) {
    val indexCounts = submissions.groupingBy { it.index }.eachCount()
    HeadingSection("Solved by Index", Modifier.padding(8.dp))
    val data = indexCounts.map { (index, count) ->
        Bars(
            label = index,
            values = listOf(
                Bars.Data(
                    value = count.toDouble(),
                    color = Brush.radialGradient(listOf(Color(0xFF23af92), Color(0xFF2BC0A1)))
                )
            ),
        )
    }

    RowChart(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 22.dp),
        data = data,
        barProperties = BarProperties(
            spacing = 3.dp, thickness = 20.dp
        )
    )
}

fun getTagsFrequency(userSubmissions: List<UserSubmissions>): Map<String, Int> {
    val map: Map<String, Int> = userSubmissions.flatMap { it.tags }.groupingBy { it }.eachCount()
    return map
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserTagsDoughnutChart(userSubmissions: List<UserSubmissions>, modifier: Modifier = Modifier) {
    HeadingSection("Solved by Tags", Modifier.padding(8.dp))
    val tagsFrequency = getTagsFrequency(userSubmissions)
    var doughnutData = tagsFrequency.map { (tag, frequency) ->
        Pie(
            label = tag,
            data = frequency.toDouble(),
            color = getRandomMaterialColor(),
            selectedColor = Color.Green
        )
    }
    PieChart(
        modifier = modifier
            .size(200.dp)
            .padding(horizontal = 8.dp),
        data = doughnutData,
        onPieClick = {
            println("Pie Clicked: $it")
            val pieIndex = doughnutData.indexOf(it)
            doughnutData =
                doughnutData.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
        },
        selectedScale = 1.2f,
        scaleAnimEnterSpec = spring<Float>(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        ),
        colorAnimEnterSpec = tween(300),
        colorAnimExitSpec = tween(300),
        scaleAnimExitSpec = tween(300),
        spaceDegreeAnimExitSpec = tween(300),
        style = Pie.Style.Stroke(42.dp)
    )
    FlowRow(modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        tagsFrequency.forEach { (tag, frequency) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(doughnutData.find { it.label == tag }?.color ?: Color.Gray)
                )
                Text(
                    text = "$tag: $frequency",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }/*
    Row(modifier = modifier.fillMaxWidth()) {
        PieChart(
            modifier = Modifier.size(200.dp),
            data = doughnutData,
            onPieClick = {
                println("Pie Clicked: $it")
                val pieIndex = doughnutData.indexOf(it)
                doughnutData =
                    doughnutData.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
            },
            selectedScale = 1.2f,
            scaleAnimEnterSpec = spring<Float>(
                dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
            ),
            colorAnimEnterSpec = tween(300),
            colorAnimExitSpec = tween(300),
            scaleAnimExitSpec = tween(300),
            spaceDegreeAnimExitSpec = tween(300),
            style = Pie.Style.Stroke(42.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            tagsFrequency.forEach { (tag, frequency) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(doughnutData.find { it.label == tag }?.color ?: Color.Gray)
                    )
                    Text(
                        text = "$tag: $frequency",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }*/
}

fun getRandomMaterialColor(): Color {
    val colors = listOf(
        Color(0xFFB71C1C),
        Color(0xFF880E4F),
        Color(0xFF4A148C),
        Color(0xFF311B92),
        Color(0xFF1A237E),
        Color(0xFF0D47A1),
        Color(0xFF01579B),
        Color(0xFF006064),
        Color(0xFF004D40),
        Color(0xFF1B5E20),
        Color(0xFF33691E),
        Color(0xFF827717),
        Color(0xFFF57F17),
        Color(0xFFFF6F00),
        Color(0xFFE65100),
        Color(0xFFBF360C),
        Color(0xFF3E2723),
        Color(0xFF212121),
        Color(0xFF263238)
    )
    return colors.random()
}

fun getLanguageFrequency(userSubmissions: List<UserSubmissions>): Map<String, Int> {
    return userSubmissions.groupingBy { it.programmingLanguage }.eachCount()
}

@Composable
fun UserLanguagesGraph(userSubmissions: List<UserSubmissions>, modifier: Modifier = Modifier) {
    HeadingSection("Languages Used", Modifier.padding(8.dp))
    val languageFrequency = getLanguageFrequency(userSubmissions)
    var pieData = languageFrequency.map { (language, frequency) ->
        Pie(
            label = language,
            data = frequency.toDouble(),
            color = getRandomMaterialColor(),
            selectedColor = Color.Green
        )
    }
    PieChart(
        modifier = modifier.size(200.dp),
        data = pieData,
        onPieClick = {
            println("Pie Clicked: $it")
            val pieIndex = pieData.indexOf(it)
            pieData =
                pieData.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
        },
        selectedScale = 1.2f,
        scaleAnimEnterSpec = spring<Float>(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        ),
        colorAnimEnterSpec = tween(300),
        colorAnimExitSpec = tween(300),
        scaleAnimExitSpec = tween(300),
        spaceDegreeAnimExitSpec = tween(300),
        style = Pie.Style.Fill
    )
}

@Composable
fun UserVerdictsGraph(userSubmissions: List<UserSubmissions>, modifier: Modifier = Modifier) {
    HeadingSection("Verdict Frequency", Modifier.padding(8.dp))
    val verdictFrequency = getVerdictFrequency(userSubmissions)
    var pieData = verdictFrequency.map { (verdict, frequency) ->
        Pie(
            label = verdict,
            data = frequency.toDouble(),
            color = getColorForVerdict(verdict.uppercase()),
            selectedColor = Color.Green
        )
    }
    PieChart(
        modifier = modifier.size(200.dp),
        data = pieData,
        onPieClick = {
            println("Pie Clicked: $it")
            val pieIndex = pieData.indexOf(it)
            pieData =
                pieData.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
        },
        selectedScale = 1.2f,
        scaleAnimEnterSpec = spring<Float>(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        ),
        colorAnimEnterSpec = tween(300),
        colorAnimExitSpec = tween(300),
        scaleAnimExitSpec = tween(300),
        spaceDegreeAnimExitSpec = tween(300),
        style = Pie.Style.Fill
    )
}

@Composable
fun UserRatingGraph(userRatings: List<UserRating>, modifier: Modifier = Modifier) {
    HeadingSection("Rating Graph", Modifier.padding(8.dp))
    val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())
    val ratings = userRatings.map { it.newRating.toDouble() }
    val dates = userRatings.map { dateFormat.format(Date(it.ratingUpdateTimeSeconds * 1000L)) }
    Column {
        LineChart(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 22.dp),
            data = listOf(
                Line(
                    label = "Rating", values = ratings, color = SolidColor(Color(0xFF23af92)),
                    firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                    secondGradientFillColor = Color.Transparent,
                )
            ),
            labelProperties = LabelProperties(
                labels = dates, enabled = true
            ),
            indicatorProperties = HorizontalIndicatorProperties(
                enabled = true,
                count = 5,
                contentBuilder = { indicator ->
                    indicator.toInt().toString()
                })
        )
    }
}

@Composable
private fun HeadingSection(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, style = MaterialTheme.typography.headlineMedium, modifier = modifier
    )
}

fun getColorForVerdict(verdict: String): Color {
    return when (verdict.uppercase()) {
        "OK" -> Color(0xFF4CAF50)  // Green
        "ACCEPTED" -> Color(0xFF4CAF50)  // Green
        "PARTIAL" -> Color(0xFFFFC107)  // Amber
        "FAILED" -> Color(0xFFF44336)  // Red
        "COMPILATION_ERROR" -> Color(0xFFFF5722)  // Deep Orange
        "RUNTIME_ERROR" -> Color(0xFFE91E63)  // Pink
        "WRONG_ANSWER" -> Color(0xFFFF9800)  // Orange
        "PRESENTATION_ERROR" -> Color(0xFF9C27B0)  // Purple
        "TIME_LIMIT_EXCEEDED" -> Color(0xFF2196F3)  // Blue
        "MEMORY_LIMIT_EXCEEDED" -> Color(0xFF3F51B5)  // Indigo
        "IDLENESS_LIMIT_EXCEEDED" -> Color(0xFF00BCD4)  // Cyan
        "SECURITY_VIOLATED" -> Color(0xFF795548)  // Brown
        "CRASHED" -> Color(0xFF607D8B)  // Blue Grey
        "INPUT_PREPARATION_CRASHED" -> Color(0xFF9E9E9E)  // Grey
        "CHALLENGED" -> Color(0xFFCDDC39)  // Lime
        "SKIPPED" -> Color(0xFF03A9F4)  // Light Blue
        "TESTING" -> Color(0xFF009688)  // Teal
        "REJECTED" -> Color(0xFF673AB7)  // Deep Purple
        else -> Color(0xFF000000)  // Black for unknown verdicts
    }
}
