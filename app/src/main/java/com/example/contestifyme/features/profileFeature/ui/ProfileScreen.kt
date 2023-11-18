@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.contestifyme.features.profileFeature.ui

import androidx.compose.material3.ElevatedCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily.Companion.SansSerif
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.contestifyme.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    handle: String
) {

    Scaffold (
        topBar = {
            ProfileAppBar {

            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(16.dp), contentPadding = paddingValues) {
            item {
                ProfileCard(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun ProfileCard (modifier: Modifier) {
    ElevatedCard {
        Column(
            modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Name",
                fontFamily = SansSerif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "Email",
                fontFamily = SansSerif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "Phone",
                fontFamily = SansSerif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "College",
                fontFamily = SansSerif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "Year",
                fontFamily = SansSerif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "Branch",
                fontFamily = SansSerif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun ProfileAppBar(settingClicked: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(painter = painterResource(id = R.drawable.logo2), contentDescription = null, Modifier.size(32.dp))
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = SansSerif
                )
            }

        },
        actions = {
            IconButton(onClick = {
                settingClicked()
            }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    )
}
