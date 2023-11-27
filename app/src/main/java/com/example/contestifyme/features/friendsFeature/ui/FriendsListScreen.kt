package com.example.contestifyme.features.friendsFeature.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.contestifyme.features.friendsFeature.data.local.FriendsDataEntity

@Composable
fun FriendsListScreen(
    friends: List<FriendsDataEntity>,
    onFriendClicked: (String) -> Unit,
    addFriend: (String) -> Unit,
    state: FriendsUiState
) {
    var addFriendField by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            FriendsAppBar {
                addFriendField = true
            }
        }
    ) { paddingValues ->
        if (friends.isEmpty()) {
            Text(
                text = "No friends added yet",
                modifier = Modifier.padding(16.dp)
            )
        }
        LazyColumn(contentPadding = paddingValues) {
            friends.sortedByDescending {
                it.rating
            }.forEach { friend ->
                item {
                    FriendItem(friend = friend) {
                        onFriendClicked(friend.handle)
                    }
                }
            }
        }
    }
    if (addFriendField) {
        AddFriendDialog(
            onDismiss = {
                addFriendField = false
            },
            onAddFriend = {
                addFriend(it)
                addFriendField = false
            }
        )
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
fun FriendItem(friend: FriendsDataEntity, onFriendClicked: () -> Unit) {
    ElevatedCard(modifier = Modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable {
            onFriendClicked()
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = friend.handle,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        }
        Text(
            text = "Rating: ${friend.rating}",
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsAppBar(
    addFriend: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Friends",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = {
                addFriend()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Friend"
                )
            }
        }
    )
}