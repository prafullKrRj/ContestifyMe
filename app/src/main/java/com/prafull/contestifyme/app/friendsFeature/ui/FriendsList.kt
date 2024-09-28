package com.prafull.contestifyme.app.friendsFeature.ui

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.ui.ErrorScreen
import com.prafull.contestifyme.app.friendsFeature.FriendsRoutes
import com.prafull.contestifyme.onboard.model.UserResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendListScreen(viewModel: FriendsViewModel, navController: NavController) {
    val friends by viewModel.friendsList.collectAsState()
    val context = LocalContext.current
    val showAddFriendDialog by viewModel.showAddFriendDialog.collectAsState()
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "Friends", style = MaterialTheme.typography.headlineSmall)
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.showAddFriendDialog()
        }, shape = CircleShape) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Friend")
        }
    }) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (friends) {
                is BaseClass.Error -> {
                    item {
                        ErrorScreen {
                            viewModel.getFriendsList()
                        }
                    }
                }

                BaseClass.Loading -> {
                    item {
                        LoadingScreen()
                    }
                }

                is BaseClass.Success -> {
                    val friendsList = (friends as BaseClass.Success).data
                    items(friendsList, key = {
                        it.handle!!
                    }) { friend ->
                        FriendItem(info = friend, navController = navController, context)
                    }
                }
            }
        }
    }
    if (showAddFriendDialog) {
        var handle by remember {
            mutableStateOf("")
        }
        AlertDialog(onDismissRequest = {
            if (!viewModel.loading) {
                viewModel.showAddFriendDialog()
            }
        }, confirmButton = {
            TextButton(onClick = {
                viewModel.addFriend(handle)
            }, enabled = handle.isNotEmpty() && !viewModel.loading) {
                Text(text = "Add Friend")
            }
        }, text = {
            Box(contentAlignment = Alignment.Center) {
                OutlinedTextField(value = handle, onValueChange = {
                    handle = it
                }, label = {
                    Text(text = "Enter handle")
                }, singleLine = true, enabled = !viewModel.loading)
                if (viewModel.loading) {
                    CircularProgressIndicator()
                }
            }
        }, title = {
            Text(text = "Add Friend")
        }, dismissButton = {
            TextButton(onClick = {
                viewModel.showAddFriendDialog()
            }, enabled = !viewModel.loading) {
                Text(text = "Cancel")
            }
        })
    }
}

fun formatLastActiveTime(lastOnlineTimeSeconds: Int?): String {
    return if (lastOnlineTimeSeconds != null) {
        val date = java.util.Date(lastOnlineTimeSeconds.toLong() * 1000)
        val format = java.text.SimpleDateFormat("dd MMM yyyy, HH:mm", java.util.Locale.getDefault())
        format.format(date)
    } else {
        "N/A"
    }
}

@Composable
fun FriendItem(info: UserResult, navController: NavController, context: Context) {
    Card(
        Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(FriendsRoutes.FriendScreen(info.handle!!))
                }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(getRankColor(info.rank))
                    .padding(3.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(info.avatar).build(),
                    contentDescription = "friend_image",
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = info.handle!!,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = info.rank ?: "Unranked",
                        style = MaterialTheme.typography.bodySmall,
                        color = getRankColor(info.rank)
                    )
                    Text(
                        text = "Rating: ${info.rating ?: "N/A"}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = "Last Active: ${formatLastActiveTime(info.lastOnlineTimeSeconds)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
fun FriendItemPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sampleUserResult = UserResult(
        avatar = "https://example.com/avatar.jpg",
        contribution = 100,
        country = "Country",
        firstName = "First",
        friendOfCount = 50,
        handle = "sampleHandle",
        lastOnlineTimeSeconds = 1620000000,
        maxRank = "grandmaster",
        maxRating = 2500,
        organization = "Organization",
        rank = "master",
        rating = 2400,
        registrationTimeSeconds = 1600000000,
        titlePhoto = "https://example.com/titlePhoto.jpg"
    )
    FriendItem(info = sampleUserResult, navController = navController, context = context)
}


fun getRankColor(rank: String?): Color {
    return when (rank) {
        "legendary grandmaster" -> Color(0xFFAA0000)     // Dark Red
        "international grandmaster" -> Color(0xFFFF3333) // Darker Red
        "grandmaster" -> Color(0xFFFF7777)              // Red
        "international master" -> Color(0xFFFFBB55)     // Orange with slight variation
        "master" -> Color(0xFFFFCC88)                   // Orange
        "candidate master" -> Color(0xFFFF88FF)         // Violet
        "expert" -> Color(0xFFAAAAFF)                   // Blue
        "specialist" -> Color(0xFF77DDBB)               // Cyan
        "pupil" -> Color(0xFF77FF77)                    // Green
        "newbie" -> Color(0xFFCCCCCC)                   // Gray
        else -> Color.Red                               // Default Red for unspecified ranks
    }
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}