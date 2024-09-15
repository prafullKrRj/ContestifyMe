package com.prafull.contestifyme.features.problemsFeature.ui.components.sortComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.prafull.contestifyme.features.problemsFeature.problemsConstants.ProblemsConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDialog(previousType: Pair<Int, Int>, sortType: (Pair<Int, Int>) -> Unit) {
    var selected by remember { mutableStateOf(previousType) }
    var isRange by remember {
        mutableStateOf(false)
    }
    var sliderPosition by remember { mutableStateOf(800f..3500f) }

    AlertDialog(
        modifier = Modifier.padding(vertical = 24.dp),
        onDismissRequest = {
            sortType(previousType)
        },
        title = { Text(text = "Sort By") },
        text = {
            LazyColumn {
                item {
                    Row(modifier = Modifier) {
                        Checkbox(
                            checked = isRange,
                            onCheckedChange = {
                                isRange = !isRange
                            }
                        )
                        Text(text = "Range")
                    }
                    Text(text = "${sliderPosition.start.toInt()} - ${sliderPosition.endInclusive.toInt()}")
                    RangeSlider(
                        enabled = isRange,
                        modifier = Modifier,
                        steps = 26,
                        value = sliderPosition,
                        onValueChange = {
                            sliderPosition = it
                        },
                        valueRange = 800f..3500f,
                        onValueChangeFinished = {
                            selected = Pair(
                                sliderPosition.start.toInt(),
                                sliderPosition.endInclusive.toInt()
                            )
                        }
                    )
                }
                item {
                    Row {
                        Checkbox(
                            checked = !isRange,
                            onCheckedChange = {
                                isRange = !isRange
                            }
                        )
                        Text(text = "Rating")
                    }
                    ProblemsConstants.array.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                    selected = Pair(item, item)
                                },
                            verticalAlignment = CenterVertically
                        ) {
                            Checkbox(
                                enabled = !isRange,
                                checked = !isRange && selected == Pair(item, item),
                                onCheckedChange = { boolean ->
                                    if (!isRange && boolean) {
                                        selected = Pair(item, item)
                                    }
                                }
                            )
                            Text(text = "$item")
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                sortType(selected)
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                sortType(previousType)
            }) {
                Text(text = "Cancel")
            }
        }
    )
}