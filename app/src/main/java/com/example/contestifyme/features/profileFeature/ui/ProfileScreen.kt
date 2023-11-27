package com.example.contestifyme.features.profileFeature.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.ui.components.ColorInfoDialog
import com.example.contestifyme.features.profileFeature.ui.components.ProfileAppBar
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {
    val state: ProfileUiState by viewModel.profileUiState.collectAsState()
    if (state.user.isNotEmpty()) {
        Log.d("praf", "${state.user[0].subMissionInfo}")
    }
    val navHostController: NavHostController = rememberNavController()
    var url by rememberSaveable {
        mutableStateOf("https://www.codeforces.com/")
    }
    Column (
        Modifier
            .fillMaxSize()
    ) {
        NavHost(navController = navHostController, startDestination = "main") {
            composable("main") {
                MainProfileScreen(
                    user = state.user
                ) {
                    url = it
                    navHostController.navigate("answer")
                }
            }
            composable("answer") {
                SubmissionAnswer(
                    url = url
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainProfileScreen(
    user: List<UserInfoEntity>,
    navigate: (String) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    )
    val scope = rememberCoroutineScope()
    var colorInformationDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
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
            if (user.isNotEmpty()) {
                HorizontalPager(
                    pageCount = 2,
                    state = pagerState,
                    userScrollEnabled = true
                ) {
                    when (it) {
                        0 -> FrontScreen(user = user[0]) {
                            scope.launch { pagerState.animateScrollToPage(1) }
                        }

                        1 -> SubmissionsScreen(user[0].subMissionInfo,
                            onClickAction = { contestId, id ->
                                navigate("https://codeforces.com/contest/$contestId/submission/$id")
                            },
                            onBackPress = {
                                scope.launch { pagerState.animateScrollToPage(0) }
                            }
                        )
                    }
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