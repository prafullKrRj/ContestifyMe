package com.prafull.contestifyme.app.userscreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafull.contestifyme.R
import com.prafull.contestifyme.app.App
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.app.commons.Utils
import com.prafull.contestifyme.app.commons.ui.ProfileCard
import com.prafull.contestifyme.network.model.UserRating
import com.prafull.contestifyme.network.model.UserSubmissions
import com.prafull.contestifyme.network.model.userinfo.UserResult
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getVerdictFrequency(userSubmissions: List<UserSubmissions>): Map<String, Int> {
    return userSubmissions.groupingBy { it.verdict }.eachCount()
}

@Composable
fun UserScreen(userData: UserData, navController: NavController) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0) {
        2
    }
    Column(Modifier.fillMaxSize()) {
        if (userData.userSubmissions.isNotEmpty()) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Tab(selected = pagerState.currentPage == 0, onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }) {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Tab(selected = pagerState.currentPage == 1, onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }) {
                    Text(
                        text = "Submissions",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            HorizontalPager(state = pagerState) {
                when (it) {
                    0 -> {
                        UserProfileScreen(userData = userData, modifier = Modifier)
                    }

                    1 -> {
                        SubmissionList(
                            submissions = userData.userSubmissions,
                            paddingValues = PaddingValues(),
                            navController = navController
                        )
                    }
                }

            }
        } else {
            UserProfileScreen(userData = userData, modifier = Modifier)
        }
    }
}

@Composable
fun UserProfileScreen(
    userData: UserData, modifier: Modifier
) {
    LazyColumn(
        modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item("rankCard") {
            RankCard(usersInfo = userData.usersInfo)
        }
        item("profileCard") {
            ProfileCard(modifier = Modifier, user = userData)
        }
        if (userData.userRating.isNotEmpty()) {
            item("ratingGraph") {
                Card(Modifier.fillMaxWidth()) {
                    UserRatingGraph(
                        userData.userRating, modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        } else {
            item {
                Text("No Ratings Found or No internet", modifier = Modifier.padding(8.dp))
            }
        }
        if (userData.userSubmissions.isNotEmpty()) {
            item("verdictsGraph") {
                Card(Modifier.fillMaxWidth()) {
                    UserVerdictsGraph(
                        userSubmissions = userData.userSubmissions,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            item("tagsGraph") {
                var expanded by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .clickable { expanded = !expanded }
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Tags")
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = if (expanded) "Collapse" else "Expand"
                                )
                            }
                        }
                        if (expanded) {
                            UserTagsDoughnutChart(
                                userSubmissions = userData.userSubmissions,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
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
                    QuestionSolvedByIndexColumnChart(
                        submissions = userData.userSubmissions,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        } else {
            item {
                Text("No Submissions Found or No internet", modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun AnimatedCard(modifier: Modifier = Modifier) {

}

@Composable
fun RankCard(usersInfo: UserResult) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElevatedCard(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Column(Modifier.padding(12.dp)) {
                usersInfo.rank?.let { it ->
                    Text(
                        text = it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                        color = Utils.getRankColor(it.lowercase()),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text = usersInfo.handle,
                    style = MaterialTheme.typography.titleLarge,
                    color = Utils.getRankColor(usersInfo.rank.toString().lowercase()),
                    fontWeight = FontWeight.W700
                )
                usersInfo.country?.let {
                    Text(
                        text = it
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionSolvedByIndexColumnChart(
    submissions: List<UserSubmissions>, modifier: Modifier = Modifier
) {
    val indexCounts = submissions.groupingBy { it.index }.eachCount()
    val horizontalScrollState = rememberScrollState()

    HeadingSection("Solved by Index", Modifier.padding(8.dp))
    val data = indexCounts.map { (index, count) ->
        Bars(
            label = index,
            values = listOf(
                Bars.Data(
                    label = "Indexes",
                    value = count.toDouble(),
                    color = Brush.radialGradient(listOf(Color(0xFF23af92), Color(0xFF2BC0A1)))
                )
            ),
        )
    }
    LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        item {
            ColumnChart(
                modifier = modifier
                    .fillMaxWidth()
                    .width((data.size * 40).dp)
                    .height(data.maxOf { it.values[0].value }.dp)
                    .padding(horizontal = 22.dp),
                data = data,
                barProperties = BarProperties(
                    spacing = 3.dp, thickness = 20.dp
                )
            )
        }
    }
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
        scaleAnimEnterSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        ),
        colorAnimEnterSpec = tween(300),
        colorAnimExitSpec = tween(300),
        scaleAnimExitSpec = tween(300),
        spaceDegreeAnimExitSpec = tween(300),
        style = Pie.Style.Stroke(42.dp)
    )
    FlowRow(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        tagsFrequency.forEach { (tag, frequency) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(doughnutData.find { it.label == tag }?.color ?: Color.Gray)
                )
                Text(
                    text = "$tag: $frequency", modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
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
            color = getColorForLanguage(
                language
            ),
            selectedColor = Color.Green
        )
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PieChart(
            modifier = modifier.size(200.dp),
            data = pieData,
            onPieClick = {
                val pieIndex = pieData.indexOf(it)
                pieData =
                    pieData.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
            },
            selectedScale = 1.2f,
            scaleAnimEnterSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
            ),
            colorAnimEnterSpec = tween(300),
            colorAnimExitSpec = tween(300),
            scaleAnimExitSpec = tween(300),
            spaceDegreeAnimExitSpec = tween(300),
            style = Pie.Style.Fill
        )
        Column {
            languageFrequency.forEach { (language, frequency) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(getColorForLanguage(language))
                    )
                    Text(
                        text = "$language: $frequency",
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
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
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
            scaleAnimEnterSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
            ),
            colorAnimEnterSpec = tween(300),
            colorAnimExitSpec = tween(300),
            scaleAnimExitSpec = tween(300),
            spaceDegreeAnimExitSpec = tween(300),
            style = Pie.Style.Fill
        )
        Column(modifier = Modifier) {
            verdictFrequency.forEach { (verdict, frequency) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(getColorForVerdict(verdict.uppercase()))
                    )
                    Text(
                        text = "$verdict: $frequency",
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun UserRatingGraph(userRatings: List<UserRating>, modifier: Modifier = Modifier) {
    HeadingSection("Rating Graph", Modifier.padding(8.dp))
    val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())
    val ratings = userRatings.map { it.newRating.toDouble() }
    val dates = userRatings.map { dateFormat.format(Date(it.ratingUpdateTimeSeconds * 1000L)) }
    Column {
        LineChart(
            animationDelay = 20,
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
                labels = dates, enabled = false
            ),
            indicatorProperties = HorizontalIndicatorProperties(enabled = true,
                count = 7,
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

@Composable
fun SubmissionList(
    submissions: List<UserSubmissions>, paddingValues: PaddingValues, navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (submissions.isNotEmpty()) {
            items(submissions, key = {
                "${it.id} ${it.contestId} ${it.index}"
            }) { submission ->
                SubmissionCard(submission, navController)
            }
        } else {
            item {
                Text(
                    text = "No submissions found",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SubmissionCard(submission: UserSubmissions, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = getColorForVerdict(submission.verdict).copy(alpha = 0.2f)
        )
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    App.WebViewScreen(
                        url = "https://codeforces.com/contest/${submission.contestId}/submission/${submission.id}",
                        heading = submission.name
                    )
                )
            }
            .padding(16.dp)

        ) {
            Text(
                text = submission.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoChip(
                    icon = ImageVector.vectorResource(id = R.drawable.baseline_timer_24),
                    text = "Time: ${submission.time}"
                )
                InfoChip(
                    icon = ImageVector.vectorResource(id = R.drawable.baseline_assignment_24),
                    text = "Verdict: ${submission.verdict}"
                )
            }
        }
    }
}

@Composable
fun InfoChip(icon: ImageVector, text: String) {
    Surface(
        shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text, style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

fun getColorForLanguage(language: String): Color {
    return languageColorMap[language]?.let { Color(it) } ?: getRandomMaterialColor()
}

// Data class definition
data class LangInfo(val name: String, val color: Int)

// Map of languages to hex colors
val languageColorMap = mapOf(
    "GNU C11" to 0x1976D2,
    "C++17 (GCC 7-32)" to 0x0097A7,
    "C++20 (GCC 13-64)" to 0x00796B,
    "C++23 (GCC 14-64, msys2)" to 0x388E3C,
    "C# 8" to 0x1565C0,
    "C# 10" to 0x0D47A1,
    "Mono C#" to 0x303F9F,
    "D" to 0x512DA8,
    "Go" to 0x1976D2,
    "Haskell" to 0x7B1FA2,
    "Java 21" to 0xC2185B,
    "Java 8" to 0xD32F2F,
    "Kotlin 1.7" to 0xFF5722,
    "Kotlin 1.9" to 0xE64A19,
    "OCaml" to 0xF57C00,
    "Delphi" to 0xFFA000,
    "FPC" to 0xFBC02D,
    "PascalABC.NET" to 0xAFB42B,
    "Perl" to 0x689F38,
    "PHP" to 0x388E3C,
    "Python 2" to 0x00796B,
    "Python 3" to 0x00695C,
    "PyPy 2" to 0x0097A7,
    "PyPy 3" to 0x00838F,
    "PyPy 3-64" to 0x006064,
    "Ruby 3" to 0xAD1457,
    "Rust 2021" to 0xBF360C,
    "Scala" to 0xD84315,
    "JavaScript" to 0xF4511E,
    "Node.js" to 0xE64A19,
    "Tcl" to 0x827717,
    "Io" to 0x33691E,
    "Pike" to 0x1B5E20,
    "Befunge" to 0x004D40,
    "Cobol" to 0x006064,
    "Factor" to 0x01579B,
    "Secret_171" to 0x0D47A1,
    "Roco" to 0x1A237E,
    "Ada" to 0x311B92,
    "Mysterious Language" to 0x4A148C,
    "FALSE" to 0x880E4F,
    "Picat" to 0xB71C1C,
    "GNU C++11 ZIP" to 0xBF360C,
    "Java 8 ZIP" to 0xD84315,
    "J" to 0xF4511E,
    "Q#" to 0xFF5722,
    "Text" to 0x6D4C41,
    "UnknownX" to 0x546E7A,
    "Secret 2021" to 0x263238
)

// List of LangInfo objects
val languages = listOf(
    LangInfo("GNU C11", 0x1976D2),
    LangInfo("C++17 (GCC 7-32)", 0x0097A7),
    LangInfo("C++20 (GCC 13-64)", 0x00796B),
    LangInfo("C++23 (GCC 14-64, msys2)", 0x388E3C),
    LangInfo("C# 8", 0x1565C0),
    LangInfo("C# 10", 0x0D47A1),
    LangInfo("Mono C#", 0x303F9F),
    LangInfo("D", 0x512DA8),
    LangInfo("Go", 0x1976D2),
    LangInfo("Haskell", 0x7B1FA2),
    LangInfo("Java 21", 0xC2185B),
    LangInfo("Java 8", 0xD32F2F),
    LangInfo("Kotlin 1.7", 0xFF5722),
    LangInfo("Kotlin 1.9", 0xE64A19),
    LangInfo("OCaml", 0xF57C00),
    LangInfo("Delphi", 0xFFA000),
    LangInfo("FPC", 0xFBC02D),
    LangInfo("PascalABC.NET", 0xAFB42B),
    LangInfo("Perl", 0x689F38),
    LangInfo("PHP", 0x388E3C),
    LangInfo("Python 2", 0x00796B),
    LangInfo("Python 3", 0x00695C),
    LangInfo("PyPy 2", 0x0097A7),
    LangInfo("PyPy 3", 0x00838F),
    LangInfo("PyPy 3-64", 0x006064),
    LangInfo("Ruby 3", 0xAD1457),
    LangInfo("Rust 2021", 0xBF360C),
    LangInfo("Scala", 0xD84315),
    LangInfo("JavaScript", 0xF4511E),
    LangInfo("Node.js", 0xE64A19),
    LangInfo("Tcl", 0x827717),
    LangInfo("Io", 0x33691E),
    LangInfo("Pike", 0x1B5E20),
    LangInfo("Befunge", 0x004D40),
    LangInfo("Cobol", 0x006064),
    LangInfo("Factor", 0x01579B),
    LangInfo("Secret_171", 0x0D47A1),
    LangInfo("Roco", 0x1A237E),
    LangInfo("Ada", 0x311B92),
    LangInfo("Mysterious Language", 0x4A148C),
    LangInfo("FALSE", 0x880E4F),
    LangInfo("Picat", 0xB71C1C),
    LangInfo("GNU C++11 ZIP", 0xBF360C),
    LangInfo("Java 8 ZIP", 0xD84315),
    LangInfo("J", 0xF4511E),
    LangInfo("Q#", 0xFF5722),
    LangInfo("Text", 0x6D4C41),
    LangInfo("UnknownX", 0x546E7A),
    LangInfo("Secret 2021", 0x263238)
)