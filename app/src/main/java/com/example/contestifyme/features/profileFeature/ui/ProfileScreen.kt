@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.contestifyme.features.profileFeature.ui

import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily.Companion.SansSerif
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.contestifyme.R
import com.example.contestifyme.R.string.app_name
import com.example.contestifyme.features.profileFeature.constants.ProfileConstants
import kotlinx.coroutines.launch
import com.example.contestifyme.R.string.submissions as submissions1

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {
    val state: ProfileUiState by viewModel.profileUiState.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()
    var colorInformationDialog by remember {
        mutableStateOf(false)
    }
    Scaffold (
        topBar = {
            ProfileAppBar(page = pagerState.currentPage) {
                if (pagerState.currentPage == 1) {
                    colorInformationDialog = true
                }
            }
        }
    ) { paddingValues ->

        Column (
            Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)) {
             if (state.user.isNotEmpty()) {
                 HorizontalPager(pageCount = 2, state = pagerState, userScrollEnabled = true) {
                    when (it) {
                        0 -> FrontScreen(state.user[0]) {
                            scope.launch { pagerState.animateScrollToPage(1) }
                        }
                        1 -> SubmissionsScreen(state.user[0].subMissionInfo,
                            nextSubMissions = {
                                      viewModel.next()
                            },
                            previousSubMissions = {
                                viewModel.previous()
                            })
                    }
                 }
             }
        }
        if (colorInformationDialog) {
            ColorInfoDialog(openDialog = {
                colorInformationDialog = false
            })
        }
    }
}

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
                            Row (verticalAlignment = CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAppBar(page: Int, iconClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(painter = painterResource(id = R.drawable.logo2), contentDescription = null, Modifier.size(32.dp))
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text =
                    if (page == 0) stringResource(id = app_name) else stringResource(id = submissions1),
                    maxLines = 1, overflow = TextOverflow.Ellipsis, fontFamily = SansSerif,
                )
            }
        },
        actions = {
            IconButton(onClick = {
                iconClick()
            }) {
                Icon(
                    imageVector = if (page == 0) Icons.Default.Settings else Icons.Default.Info,
                    contentDescription = "App Info or Settings"
                )
            }
        }
    )
}
