package com.prafull.contestifyme.app.userscreen.submissions

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.contestifyme.R
import com.prafull.contestifyme.app.App
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.ui.ErrorScreen
import com.prafull.contestifyme.app.friendsFeature.ui.friendList.LoadingScreen
import com.prafull.contestifyme.app.userscreen.getColorForVerdict
import com.prafull.contestifyme.goBackStack
import com.prafull.contestifyme.network.model.UserSubmissions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Submissions(viewModel: SubmissionViewModel, navController: NavController) {
    val state by viewModel.submissions.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Submissions") }, navigationIcon = {
            IconButton(onClick = navController::goBackStack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }) { paddingValues ->
        when (state) {
            is BaseClass.Loading -> {
                LoadingScreen()
            }

            is BaseClass.Success -> {
                val submissions = (state as BaseClass.Success).data
                SubmissionList(submissions, paddingValues, navController)
            }

            is BaseClass.Error -> {
                ErrorScreen {

                }
            }
        }
    }
}

@Composable
fun SubmissionList(
    submissions: List<UserSubmissions>, paddingValues: PaddingValues, navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (submissions.isNotEmpty()) {
            items(submissions, key = {
                "${it.id} ${it.contestId} ${it.index}"
            }) { submission ->
                SubmissionCard(submission, navController)
            }
        } else {
            item {
                Text(
                    text = "No submissions found",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SubmissionCard(submission: UserSubmissions, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = getColorForVerdict(submission.verdict).copy(alpha = 0.2f)
        )
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    App.WebViewScreen(
                        url = "https://codeforces.com/contest/${submission.contestId}/submission/${submission.id}",
                        heading = submission.name
                    )
                )
            }
            .padding(16.dp)

        ) {
            Text(
                text = submission.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoChip(
                    icon = ImageVector.vectorResource(id = R.drawable.baseline_timer_24),
                    text = "Time: ${submission.time}"
                )
                InfoChip(
                    icon = ImageVector.vectorResource(id = R.drawable.baseline_assignment_24),
                    text = "Verdict: ${submission.verdict}"
                )
            }
        }
    }
}

@Composable
fun InfoChip(icon: ImageVector, text: String) {
    Surface(
        shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text, style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
