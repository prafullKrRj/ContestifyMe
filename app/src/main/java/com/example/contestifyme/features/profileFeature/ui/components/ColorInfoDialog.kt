package com.example.contestifyme.features.profileFeature.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.contestifyme.features.profileFeature.constants.ProfileConstants

@Composable
fun ColorInfoDialog(openDialog: () -> Unit) {
    AlertDialog(
        onDismissRequest = { openDialog() },
        icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
        text = {
            Column(modifier = Modifier) {

                LazyColumn {
                    item {
                        Text(
                            "Colors are based on the verdict of the submission."
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    ProfileConstants.colors.forEach { (key, value) ->
                        item {
                            Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                                Box(modifier = Modifier
                                    .size(25.dp)
                                    .clip(CircleShape)
                                    .background(value.first))
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(text = key)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog()
                }
            ) {
                Text("Ok")
            }
        }
    )
}