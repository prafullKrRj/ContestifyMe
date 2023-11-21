package com.example.contestifyme.features.problemsFeature.ui.components.sortComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
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
import com.example.contestifyme.features.problemsFeature.problemsConstants.ProblemsConstants

@Composable
fun SortDialog(previousType: Int, sortType: (Int) -> Unit) {
    var selected by remember { mutableStateOf(previousType) }
    AlertDialog(
        modifier = Modifier.padding(vertical = 24.dp),
        onDismissRequest = {
            sortType(previousType)
        },
        title = { Text(text = "Sort By") },
        text = {
            LazyColumn {
                item {
                    ProblemsConstants.array.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                           selected = item
                                },
                            verticalAlignment = CenterVertically
                        ) {
                            Checkbox(
                                checked = selected == item,
                                onCheckedChange = { boolean ->
                                    if (boolean) {
                                        selected = item
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