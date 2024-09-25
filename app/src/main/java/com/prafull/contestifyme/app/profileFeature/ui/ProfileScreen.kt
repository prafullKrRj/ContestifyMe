package com.prafull.contestifyme.app.profileFeature.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contestifyme.R
import com.prafull.contestifyme.app.commons.ui.ColorInfoDialog
import com.prafull.contestifyme.app.friendsFeature.ui.LoadingScreen
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavController) {
    val state: ProfileUiState by viewModel.profileUiState.collectAsState()
    val navHostController: NavHostController = rememberNavController()
    var url by rememberSaveable {
        mutableStateOf("https://www.codeforces.com/")
    }
    val dbData = viewModel.dataFromDb.collectAsState().value.user
    val context: Context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
    ) {
        NavHost(navController = navHostController, startDestination = "main") {
            composable("main") {
                when (state) {
                    is ProfileUiState.Loading -> {
                        LoadingScreen()
                    }

                    is ProfileUiState.Success -> {
                        MainProfileScreen(
                            viewModel,
                            user = (state as ProfileUiState.Success).user
                        ) {
                            url = it
                            navHostController.navigate("answer")
                        }
                    }

                    is ProfileUiState.Error -> {
                        MainProfileScreen(viewModel = viewModel, user = dbData, navigate = {
                            url = it
                            navHostController.navigate("answer")
                        })
                    }

                    else -> {
                        Toast.makeText(context, "Error In Loading Refresh", Toast.LENGTH_SHORT)
                            .show()
                        MainProfileScreen(viewModel, user = dbData, navigate = {
                            url = it
                            navHostController.navigate("answer")

                        })
                    }
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

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MainProfileScreen(
    viewModel: ProfileViewModel,
    user: List<UserInfoEntity>,
    navigate: (String) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        2
    }
    var colorInformationDialog by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    var refreshing by remember {
        mutableStateOf(false)
    }

    fun refresh() = scope.launch {
        refreshing = true
        viewModel.updateUserInfo()
        delay(1500)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.logo2),
                        contentDescription = null,
                        Modifier.size(32.dp)
                    )
                    Text(
                        text =
                        if (pagerState.currentPage == 0) stringResource(id = R.string.app_name) else stringResource(
                            id = R.string.submissions
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily.SansSerif,
                    )
                }
            }, actions = {
                IconButton(onClick = { colorInformationDialog = true }) {
                    Icon(
                        imageVector = if (pagerState.currentPage == 0) Icons.Default.Settings else Icons.Default.Info,
                        contentDescription = "Info"
                    )
                }
            })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .pullRefresh(refreshState)
        ) {
            if (user.isNotEmpty()) {
                HorizontalPager(
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

        if (colorInformationDialog) {
            ColorInfoDialog(openDialog = {
                colorInformationDialog = false
            })
        }
    }
}