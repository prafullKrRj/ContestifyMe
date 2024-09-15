package com.prafull.contestifyme.features.friendsFeature.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.contestifyme.R
import com.prafull.contestifyme.commons.Resource
import com.prafull.contestifyme.commons.ui.ErrorScreen
import com.prafull.contestifyme.commons.ui.SimpleTopAppBar
import com.prafull.contestifyme.features.friendsFeature.FriendsConstants.getFormattedTime
import com.prafull.contestifyme.features.friendsFeature.FriendsConstants.getRatingColor
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDataEntity

@Composable
fun FriendsListScreen(
    viewModel: FriendsViewModel,
    onFriendClicked: (String) -> Unit
) {
    var addFriendField by rememberSaveable {
        mutableStateOf(false)
    }
    val uiState by viewModel.uiState.collectAsState()
    val addFriendState by viewModel.addFriendState.collectAsState()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SimpleTopAppBar(label = R.string.friends)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { addFriendField = true }, shape = CircleShape) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Friend"
                )
            }
        }
    ) { paddingValues ->
        when (uiState) {
            is Resource.Error -> {
                ErrorScreen {
                    viewModel.getFromInternet()
                }
            }

            Resource.Initial -> {
                LoadingScreen()
            }

            Resource.Loading -> {
                LoadingScreen()
            }

            is Resource.Success -> {
                SuccessScreen(
                    friendsData = (uiState as Resource.Success<List<FriendsDataEntity>>).data,
                    paddingValues = paddingValues,
                    onFriendClicked = {
                        onFriendClicked(it)
                    }
                )
            }
        }
    }
    if (addFriendField) {
        AddFriendDialog(
            onDismiss = {
                addFriendField = false
            },
            onAddFriend = { newHandle ->
                viewModel.addFriend(newHandle)
                when (addFriendState) {
                    is Resource.Success -> {
                        addFriendField = false
                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            context,
                            "Error Adding Friend: ${(addFriendState as Resource.Error).exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        Toast.makeText(context, "Loading....", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}

@Composable
fun SuccessScreen(
    friendsData: List<FriendsDataEntity>,
    paddingValues: PaddingValues,
    onFriendClicked: (String) -> Unit
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(
                dampingRatio = DampingRatioLowBouncy,
            )
        ),
        exit = fadeOut()
    ) {
        LazyColumn(contentPadding = paddingValues) {
            friendsData.forEach { friend ->
                item {
                    FriendItem(
                        modifier = Modifier,
                        friend = friend,
                        onFriendClicked = {
                            onFriendClicked(friend.handle)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddFriendDialog(
    onDismiss: () -> Unit,
    onAddFriend: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Add Friend")
        },
        text = {
            Column {
                Text(text = "Enter your friend's handle")
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("handle") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAddFriend(text)
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun FriendItem(modifier: Modifier, friend: FriendsDataEntity, onFriendClicked: () -> Unit) {
    ElevatedCard(modifier = modifier
        .padding(vertical = 4.dp, horizontal = 8.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable {
            onFriendClicked()
        }
    ) {

        Row(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(modifier = Modifier.weight(.2f)) {
                FriendImage(
                    modifier = Modifier,
                    data = friend.titlePhoto,
                    borderColor = getRatingColor(friend.rating ?: 0)
                )
            }
            Column(modifier = Modifier.weight(.6f)) {
                Text(text = friend.handle)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Active ${getFormattedTime(friend.lastOnlineTimeSeconds?.toLong())}",
                    fontSize = 14.sp
                )
            }
            Column(modifier = Modifier.weight(.2f)) {
                Text(
                    text = friend.rating.toString(), modifier = Modifier.padding(8.dp),
                    color = getRatingColor(friend.rating ?: 0),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun FriendImage(modifier: Modifier, data: String?, borderColor: Color) {
    AsyncImage(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .border(width = 2.dp, color = borderColor, shape = CircleShape),
        model = ImageRequest.Builder(LocalContext.current).data(data).crossfade(true).build(),
        contentDescription = null,
    )
}
