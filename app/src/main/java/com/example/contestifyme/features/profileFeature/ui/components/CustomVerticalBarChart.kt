package com.example.contestifyme.features.profileFeature.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomVerticalBarChart(xData: List<String>, yData: List<Int>) {
    val scrollState = rememberScrollState()
    val oneQuestionHeight: Double = 300.0/yData.max()
    Column(modifier = Modifier
        .padding(20.dp)
        .horizontalScroll(scrollState)
        .fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            yData.forEachIndexed { index, it ->
                Column(modifier = Modifier
                    .padding(2.dp)
                    .height(350.dp)
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .width(35.dp)
                        .height((it * oneQuestionHeight).dp)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        , contentAlignment = Alignment.BottomCenter) {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = "${(it)}",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = xData[index],
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}