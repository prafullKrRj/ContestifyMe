package com.example.contestifyme.features.friendsFeature.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import co.yml.charts.common.extensions.isNotNull
import com.example.contestifyme.commons.GetChartData
import com.example.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.model.UserSubmissions
import com.example.contestifyme.features.profileFeature.ui.ShowGraphButtons
import com.example.contestifyme.features.profileFeature.ui.SubMissionCard
import com.example.contestifyme.features.profileFeature.ui.components.DetailsDialog
import com.example.contestifyme.features.profileFeature.ui.components.ProfileCard
import com.example.contestifyme.features.profileFeature.ui.components.QuestionByIndexGraph
import com.example.contestifyme.features.profileFeature.ui.components.QuestionByTypeGraph
import com.example.contestifyme.features.profileFeature.ui.components.RankCard
import com.example.contestifyme.features.profileFeature.ui.components.RatingGraph
import com.example.contestifyme.features.profileFeature.ui.components.SubmissionsGraph
import com.example.contestifyme.features.profileFeature.ui.components.VerdictGraph

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsDetailScreen(navHostController: NavHostController, friendInfo: FriendsDataEntity) {
    val user = friendInfo.toUserInfoEntity()
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
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = user.handle,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) {paddingValues ->
        LazyColumn(contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = paddingValues.calculateTopPadding()
                        + paddingValues.calculateBottomPadding()
        )) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                RankCard(
                    modifier = Modifier.fillMaxWidth(),
                    rank = "${friendInfo.rank}",
                    handle = friendInfo.handle,
                    country = friendInfo.country ?: "",
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
                        contestId, id ->
                    })
                }
            } else {
                repeat(submissions.size) {
                    SubMissionCard(userSubmission = submissions[it], onClickAction = {
                            contestId, id ->
                    })
                }
            }
        }
    }
}
fun FriendsDataEntity.toUserInfoEntity(): UserInfoEntity {
    return UserInfoEntity(
        handle = handle,
        rank = rank,
        country = country,
        rating = rating,
        maxRating = maxRating,
        friendOfCount = friendOfCount,
        avatar = avatar,
        titlePhoto = titlePhoto,
        contribution = contribution,
        organization = organization,
        lastOnlineTimeSeconds = lastOnlineTimeSeconds,
        registrationTimeSeconds = registrationTimeSeconds,
        maxRank = maxRank,
        name = name,
        ratingInfo = ratingInfo,
        subMissionInfo = subMissionInfo,
    )
}