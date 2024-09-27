package com.prafull.contestifyme.app.friendsFeature.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.GetChartData
import com.prafull.contestifyme.app.commons.ui.ErrorScreen
import com.prafull.contestifyme.app.commons.ui.ProfileCard
import com.prafull.contestifyme.app.commons.ui.QuestionByIndexGraph
import com.prafull.contestifyme.app.commons.ui.QuestionByTypeGraph
import com.prafull.contestifyme.app.commons.ui.RankCard
import com.prafull.contestifyme.app.commons.ui.RatingGraph
import com.prafull.contestifyme.app.commons.ui.SubmissionsGraph
import com.prafull.contestifyme.app.friendsFeature.domain.FriendData
import com.prafull.contestifyme.app.profileFeature.ui.ExpandCard
import com.prafull.contestifyme.goBackStack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendScreen(viewModel: FriendScreenViewModel, navController: NavController) {
    val friendState by viewModel.friendState.collectAsState()
    val onBackClick = remember {
        {
            navController.goBackStack()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = viewModel.friendHandle) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
            when (friendState) {
                is BaseClass.Loading -> {
                    LoadingScreen()
                }

                is BaseClass.Success -> {
                    FriendSuccessScreen(friendData = (friendState as BaseClass.Success<FriendData>).data)
                }

                is BaseClass.Error -> {
                    ErrorScreen {
                        viewModel.getFriendDetails()
                    }
                }

                BaseClass.Initial -> TODO()
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun FriendSuccessScreen(friendData: FriendData) {
    val solvedByTags by rememberSaveable {
        mutableStateOf(GetChartData.getQuestionSolvedByTags(friendData.userSubmissions))
    }
    var showQuestionSolvedByTags by remember {
        mutableStateOf(false)
    }
    var showQuestionSolvedByIndex by remember {
        mutableStateOf(false)
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item("rank") {
            RankCard(
                modifier = Modifier.fillMaxWidth(),
                rank = "${friendData.usersInfo.rank}",
                handle = friendData.handle,
                country = friendData.usersInfo.country ?: "Unknown"
            )
        }
        item {
            ProfileCard(modifier = Modifier.fillMaxWidth(), user = friendData.toUserData())
        }
        item("ratingGraph") {
            RatingGraph()
        }
        item {
            HorizontalDivider(Modifier.height(1.dp))
        }
        item("submissionGraph") {
            SubmissionsGraph()
        }
        item {
            HorizontalDivider(Modifier.height(1.dp))
        }

        item {
            HorizontalDivider()
        }
        item("questionByType") {
            ExpandCard(
                expanded = showQuestionSolvedByTags,
                title = "Questions Solved by Tags",
                content = {
                    QuestionByTypeGraph(solvedByTags)
                }, onExpand = {
                    showQuestionSolvedByTags = !showQuestionSolvedByTags
                })
        }
        item {
            HorizontalDivider()
        }
        item("questionByIndex") {
            ExpandCard(
                expanded = showQuestionSolvedByIndex,
                title = "Questions Solved by index",
                content = {
                    QuestionByIndexGraph(GetChartData.getQuestionSolvedByIndexData(friendData.userSubmissions))
                }) {
                showQuestionSolvedByIndex = !showQuestionSolvedByIndex
            }
        }
    }
}