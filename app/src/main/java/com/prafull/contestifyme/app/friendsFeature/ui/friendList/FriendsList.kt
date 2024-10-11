package com.prafull.contestifyme.app.friendsFeature.ui.friendList

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prafull.contestifyme.R
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.Utils
import com.prafull.contestifyme.app.commons.ui.ErrorScreen
import com.prafull.contestifyme.app.friendsFeature.FriendsRoutes
import com.prafull.contestifyme.app.settings.AlertBox
import com.prafull.contestifyme.network.model.userinfo.UserResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendListScreen(viewModel: FriendsViewModel, navController: NavController) {
    val friends by viewModel.friendsList.collectAsState()
    val context = LocalContext.current
    val showAddFriendDialog by viewModel.showAddFriendDialog.collectAsState()
    val selectedFriends by viewModel.selectedFriends.collectAsState()
    var showDeleteAllFriendsDialog by remember { mutableStateOf(false) }
    var isSelecting by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "Friends", style = MaterialTheme.typography.headlineSmall)
        }, actions = {
            if (isSelecting) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Delete")
                    IconButton(onClick = {
                        showDeleteAllFriendsDialog = false
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete All friends"
                        )
                    }
                }
            }

        }, navigationIcon = {
            if (isSelecting) {
                IconButton(onClick = {
                    viewModel.clearSelected()
                    isSelecting = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Remove selected friends"
                    )
                }
            }
        })
    }, floatingActionButton = {
        if (!isSelecting) {
            FloatingActionButton(onClick = {
                viewModel.showAddFriendDialog()
            }, shape = CircleShape) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Friend")
            }
        }
    }) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = {
                        navController.navigate(FriendsRoutes.CompareScreen)
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_compare_24),
                            contentDescription = "toCompareScreenIcon"
                        )
                    }
                }
            }
            when (val response = friends) {
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
                    val friendsList = response.data
                    if (friendsList.isEmpty()) {
                        item {
                            NoFriendsScreen()
                        }
                    } else {
                        if (isSelecting) {
                            item {
                                Row(
                                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = selectedFriends.size == friendsList.size,
                                            onCheckedChange = {
                                                if (it) {
                                                    viewModel.addAllFriendsToSelectedList(
                                                        friendsList
                                                    )
                                                } else {
                                                    viewModel.clearSelected()
                                                }
                                            })
                                        Text(text = "Select All")
                                    }
                                }
                            }
                        }
                        items(friendsList, key = {
                            it.handle
                        }) { friend ->
                            FriendItem(
                                info = friend,
                                navController = navController,
                                context,
                                isSelecting = isSelecting,
                                selected = selectedFriends.contains(friend),
                                onNoteToggled = {
                                    if (!isSelecting) isSelecting = true
                                    viewModel.toggleSelectedFriends(friend)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    if (showDeleteAllFriendsDialog) {
        AlertBox(title = "Delete All",
            text = "Do you want to delete selected Friends friends",
            onConfirm = {
                viewModel.deleteAllFriends()
                isSelecting = false
                showDeleteAllFriendsDialog = false
            }, onDismiss = {
                showDeleteAllFriendsDialog = false
            })
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

@Composable
fun NoFriendsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_friends),
                contentDescription = "No Friends"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No Friends Yet",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Add some friends to see them here!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendItem(
    info: UserResult,
    navController: NavController,
    context: Context,
    isSelecting: Boolean,
    selected: Boolean,
    onNoteToggled: () -> Unit = {}
) {
    Card(
        Modifier.fillMaxWidth(),
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
                .combinedClickable(
                    onClick = {
                        if (isSelecting) {
                            onNoteToggled()
                        } else {
                            navController.navigate(FriendsRoutes.FriendScreen(info.handle))
                        }
                    },
                    onLongClick = {
                        onNoteToggled()
                    },
                    enabled = true
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Utils.getRankColor(info.rank))
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
                    text = info.handle, style = MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = info.rank ?: "Unranked",
                        style = MaterialTheme.typography.bodySmall,
                        color = Utils.getRankColor(info.rank)
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
            if (isSelecting) {
                Checkbox(checked = selected, onCheckedChange = {
                    onNoteToggled()
                })
            }
        }
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