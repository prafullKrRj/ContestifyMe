@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.contestifyme.features.profileFeature.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.contestifyme.features.profileFeature.ui.components.ColorInfoDialog
import com.example.contestifyme.features.profileFeature.ui.components.ProfileAppBar
import kotlinx.coroutines.launch

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
                .padding(paddingValues = paddingValues)
        ) {
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


