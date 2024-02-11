package com.prafull.contestifyme.features.friendsFeature.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.contestifyme.R
import com.prafull.contestifyme.commons.ui.SimpleTopAppBar

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FriendsListScreen(
    viewModel: FriendsViewModel,
    onFriendClicked: (String) -> Unit,
    addFriend: (String) -> Unit,
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    var addFriendField by rememberSaveable {
        mutableStateOf(false)
    }

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

        /* if ((uiState as FriendsUiState.Success).data.isEmpty()) {
                    Text(
                        text = "No friends added yet",
                        modifier = Modifier.padding(16.dp)
                    )
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
                        (uiState as FriendsUiState.Success).data.sortedByDescending {
                            it.rating
                        }.forEach { friend ->
                            item {
                                FriendItem(
                                    modifier = Modifier.animateEnterExit(
                                        enter = slideInHorizontally(
                                            animationSpec = spring(
                                                dampingRatio = DampingRatioLowBouncy,
                                                stiffness = StiffnessVeryLow
                                            ),
                                            initialOffsetX = { -it },
                                        )
                                    ),
                                    friend = friend){
                                    onFriendClicked(friend.handle)
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/
        /*  if (addFriendField) {
        AddFriendDialog(
            onDismiss = {
                addFriendField = false
            },
            onAddFriend = {
                addFriend(it)
                addFriendField = false
            }
        )
    }*/
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
/*
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
                .padding(8.dp)) {
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
                Text(text = "Active ${getFormattedTime(friend.lastOnlineTimeSeconds?.toLong())}", fontSize = 14.sp)
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
*/