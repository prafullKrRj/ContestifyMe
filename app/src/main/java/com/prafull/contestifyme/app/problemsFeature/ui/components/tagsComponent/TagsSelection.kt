package com.prafull.contestifyme.app.problemsFeature.ui.components.tagsComponent

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.prafull.contestifyme.app.problemsFeature.constants.ProblemsConstants

@Composable
fun TagsDialog(selectedTags: List<String>, updateList: (List<String>) -> Unit) {
    var selected: List<String> by remember {
        mutableStateOf(selectedTags)
    }
    AlertDialog(
        modifier = Modifier.padding(vertical = 24.dp),
        onDismissRequest = {
            updateList(selectedTags)
        },
        title = { Text(text = "Tags") },
        text = {
            LazyColumn {
                item {
                    ProblemsConstants.tags.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                    selected = if (selected.contains(item)) {
                                        selected.minus(item)
                                    } else {
                                        selected.plus(item)
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selected.contains(item),
                                onCheckedChange = {
                                    selected = if (selected.contains(item)) {
                                        selected.minus(item)
                                    } else {
                                        selected.plus(item)
                                    }
                                }
                            )
                            Text(text = item)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                updateList(selected)
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                updateList(selectedTags)
            }) {
                Text(text = "Cancel")
            }
        }
    )
}