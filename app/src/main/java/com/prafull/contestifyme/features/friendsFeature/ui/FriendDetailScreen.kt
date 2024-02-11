package com.prafull.contestifyme.features.friendsFeature.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.prafull.contestifyme.features.profileFeature.domain.model.UserSubmissions
import com.prafull.contestifyme.features.profileFeature.ui.SubMissionCard


@SuppressLint("MutableCollectionMutableState")
@Composable
fun FriendsDetailScreen(navHostController: NavHostController, handle: String,  viewModel: FriendsViewModel) {
 /*   val user = viewModel.getFriend(handle).toUserInfoEntity()
    val verdicts by rememberSaveable {
        mutableStateOf(GetChartData.getVerdicts(user))
    }
    val solvedByTags by rememberSaveable {
        mutableStateOf(GetChartData.getQuestionSolvedByTags(user))
    }
    var showQuestionSolvedByTagsDialog by remember {
        mutableStateOf(false)
    }
    var showQuestionSolvedByIndexDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                label = R.string.friends,
                navIcon = Icons.Filled.ArrowBack,
                navIconClicked = {
                    navHostController.popBackStack()
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = paddingValues.calculateTopPadding()
                    + paddingValues.calculateBottomPadding()
        )) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                RankCard(
                    modifier = Modifier.fillMaxWidth(),
                    rank = "${user.rank}",
                    handle = user.handle,
                    country = user.country ?: "",
                )
                Spacer(modifier = Modifier.height(8.dp))
                ProfileCard(modifier = Modifier, user = user)
            }
            item {
                RatingGraph()
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                SubmissionsGraph()
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (GetChartData.getVerdictsData(verdicts).isNotNull() && verdicts.isNotEmpty()) {
                item {
                    Divider(Modifier.fillMaxWidth())
                    VerdictGraph(
                        verdicts = verdicts,
                        pieChartData = GetChartData.getVerdictsData(verdicts),
                        pieChartConfig = GetChartData.pieChartConfig()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                ShowGraphButtons(showQuestionSolvedByTagsDialog = {
                    showQuestionSolvedByTagsDialog = true
                }, showQuestionSolvedByIndexDialog = {
                    showQuestionSolvedByIndexDialog = true
                })
            }
            item {
                PastSubMissions(submissions = user.subMissionInfo)
            }
        }
    }
    if (showQuestionSolvedByTagsDialog) {
        DetailsDialog(content = {
            QuestionByTypeGraph(solvedByTags)
        }) {
            showQuestionSolvedByTagsDialog = false
        }
    }
    if (showQuestionSolvedByIndexDialog) {
        DetailsDialog(content = {
            QuestionByIndexGraph(GetChartData.getQuestionSolvedByIndexData(user))
        }) {
            showQuestionSolvedByIndexDialog = false
        }
    }
    */
}
@Composable
fun PastSubMissions(submissions: List<UserSubmissions>) {
    Column(
        modifier = Modifier,
    ) {
        Text(
            text = "Past Submissions",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            if (submissions.size > 7) {
                repeat(7) {
                    SubMissionCard(userSubmission = submissions[it], onClickAction = {
                            _,
                            _ ->
                    })
                }
            } else {
                repeat(submissions.size) {
                    SubMissionCard(userSubmission = submissions[it], onClickAction = {
                            _, _ ->
                    })
                }
            }
        }
    }
}
/*
fun FriendsDataEntity.toUserInfoEntity(): UserInfoEntity {
    return UserInfoEntity(
        handle = handle,
        rank = rank,
        country = country,
        rating = rating,
        maxRating = maxRating,
        avatar = avatar,
        titlePhoto = titlePhoto,
        contribution = contribution,
        lastOnlineTimeSeconds = lastOnlineTimeSeconds,
        registrationTimeSeconds = registrationTimeSeconds,
        maxRank = maxRank,
        name = name,
        ratingInfo = ratingInfo,
        subMissionInfo = subMissionInfo,
        friendOfCount = friendOfCount,
        organization = organization
    )
}*/