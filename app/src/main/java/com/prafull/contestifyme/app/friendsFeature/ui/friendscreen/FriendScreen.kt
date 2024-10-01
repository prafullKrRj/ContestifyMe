package com.prafull.contestifyme.app.friendsFeature.ui.friendscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.contestifyme.R
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.ui.ErrorScreen
import com.prafull.contestifyme.app.friendsFeature.ui.friendList.LoadingScreen
import com.prafull.contestifyme.app.userscreen.UserProfileScreen
import com.prafull.contestifyme.app.userscreen.submissions.SubmissionCard
import com.prafull.contestifyme.goBackStack
import com.prafull.contestifyme.network.model.UserSubmissions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendScreen(viewModel: FriendScreenViewModel, navController: NavController) {
    val friendState by viewModel.friendState.collectAsState()
    val onBackClick = remember {
        {
            navController.goBackStack()
        }
    }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            2
        }
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = viewModel.friendHandle) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val response = friendState) {
                is BaseClass.Loading -> {
                    LoadingScreen()
                }

                is BaseClass.Success -> {
                    HorizontalPager(state = pagerState, Modifier.fillMaxSize()) {
                        when (it) {
                            0 -> {
                                UserProfileScreen(
                                    userData = response.data,
                                    modifier = Modifier
                                ) {
                                    scope.launch {
                                        pagerState.animateScrollToPage(1)
                                    }
                                }
                            }

                            1 -> {
                                FriendsSubmissionsList(
                                    submissions = response.data.userSubmissions,
                                    navController = navController
                                ) {
                                    scope.launch {
                                        pagerState.animateScrollToPage(0)
                                    }
                                }
                            }
                        }
                    }
                }

                is BaseClass.Error -> {
                    ErrorScreen {
                        viewModel.getFriendDetails()
                    }
                }

            }
        }
    }
}

@Composable
private fun FriendsSubmissionsList(
    submissions: List<UserSubmissions>,
    navController: NavController,
    toUserData: () -> Unit = {}
) {
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                FilledTonalButton(
                    onClick = toUserData
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_navigate_next_24),
                        modifier = Modifier.rotate(180f),
                        contentDescription = "toSubmissions"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "UserData")
                }
            }
        }
        items(submissions, key = {
            it.id
        }) {
            SubmissionCard(submission = it, navController = navController)
        }
    }
}