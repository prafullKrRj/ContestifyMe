package com.prafull.contestifyme.features.profileFeature.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.prafull.contestifyme.features.profileFeature.constants.ProfileConstants
import com.prafull.contestifyme.features.profileFeature.domain.model.UserSubmissions

@Composable
fun SubmissionsScreen(
    submissions: List<UserSubmissions>,
    onClickAction: (Int, Int) -> Unit = { contestId, id -> },
    onBackPress: () -> Unit = {}
) {
    BackHandler {
        onBackPress()
    }
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        submissions.forEach { userSubmission ->
            item {
                SubMissionCard(userSubmission) { contestId, id ->
                    onClickAction(contestId, id)
                }
            }
        }
    }
}


@Composable
fun SubMissionCard(
    userSubmission: UserSubmissions,
    onClickAction: (Int, Int) -> Unit = { contestId, id -> }
) {
    val cardColor = ProfileConstants.verdictsColors[userSubmission.verdict.uppercase()]?.first
    val textColor = ProfileConstants.verdictsColors[userSubmission.verdict.uppercase()]?.second
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onClickAction(userSubmission.contestId, userSubmission.id)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = cardColor ?: MaterialTheme.colorScheme.primaryContainer
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = userSubmission.index + ". " + userSubmission.name,
                color = textColor ?: MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = userSubmission.verdict.uppercase(),
                color = textColor ?: MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun PaginationButton(image: ImageVector, onClickAction: () -> Unit = {}) {
    FilledIconButton(
        onClick = {
            onClickAction()
        }
    ) {
        Icon(
            imageVector = image,
            contentDescription = "Next"
        )
    }
}