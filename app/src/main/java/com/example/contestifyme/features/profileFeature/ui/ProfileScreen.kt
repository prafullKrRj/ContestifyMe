@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.contestifyme.features.profileFeature.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily.Companion.SansSerif
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.contestifyme.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    handle: String
) {
    val state: ProfileUiState by viewModel.profileUiState.collectAsState()
    println(state.user)
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()
    Scaffold (
        topBar = {
            ProfileAppBar {

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
    }
}


@OptIn(ExperimentalMaterial3Api::class)
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
