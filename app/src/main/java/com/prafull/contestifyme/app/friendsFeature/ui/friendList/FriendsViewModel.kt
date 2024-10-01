package com.prafull.contestifyme.app.friendsFeature.ui.friendList

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.friendsFeature.domain.FriendRepo
import com.prafull.contestifyme.network.model.userinfo.UserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FriendsViewModel(
    private val context: Context
) : ViewModel(), KoinComponent {

    private val repo: FriendRepo by inject()
    private val _friendsList = MutableStateFlow<BaseClass<List<UserResult>>>(BaseClass.Loading)
    val friendsList = _friendsList.asStateFlow()

    private val _showAddFriendDialog = MutableStateFlow(false)
    val showAddFriendDialog = _showAddFriendDialog.asStateFlow()

    // for adding friend
    var loading by mutableStateOf(false)

    init {
        getFriendsList()
    }

    fun getFriendsList() {
        _friendsList.update {
            BaseClass.Loading
        }
        viewModelScope.launch {
            repo.updateFriends().collectLatest { response ->
                when (response) {
                    is BaseClass.Success -> {
                        _friendsList.update {
                            BaseClass.Success(response.data.map { it.toUserResult() })
                        }
                    }

                    is BaseClass.Error -> {
                        showToast("Error occurred")
                        _friendsList.update {
                            BaseClass.Success(
                                repo.getFriendsDataFromDB().map { it.toUserResult() }
                            )
                        }
                    }

                    BaseClass.Loading -> {
                        _friendsList.update {
                            BaseClass.Loading
                        }
                    }
                }
            }
        }
    }

    fun showAddFriendDialog() {
        _showAddFriendDialog.update { showAddFriendDialog.value.not() }
    }

    fun addFriend(handle: String) {
        loading = true
        viewModelScope.launch {
            repo.insertFriend(handle).collectLatest { response ->
                when (response) {
                    is BaseClass.Error -> {
                        showToast("Unable to add friend")
                        showAddFriendDialog()
                        loading = false
                    }

                    BaseClass.Loading -> {
                        loading = true
                    }

                    is BaseClass.Success -> {
                        _friendsList.update { BaseClass.Success(response.data.map { it.toUserResult() }) }
                        showAddFriendDialog()
                        loading = false
                    }
                }

            }
        }
    }

    private suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
