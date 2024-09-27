package com.prafull.contestifyme.app.friendsFeature.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.friendsFeature.domain.FriendsRepo
import com.prafull.contestifyme.onboard.model.UserResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FriendsViewModel(
    private val context: Context
) : ViewModel(), KoinComponent {
    private val friendsRepo by inject<FriendsRepo>()

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
        viewModelScope.launch {
            friendsRepo.getFriends().collectLatest { response ->
                _friendsList.update {
                    response
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
            friendsRepo.addFriend(handle).collectLatest { response ->
                when (response) {
                    is BaseClass.Error -> {
                        Toast.makeText(context, "Error adding friend", Toast.LENGTH_SHORT).show()
                        loading = false
                        _showAddFriendDialog.update { false }
                    }

                    BaseClass.Initial -> {

                    }

                    BaseClass.Loading -> {
                        loading = true
                    }

                    is BaseClass.Success -> {
                        _friendsList.update {
                            BaseClass.Success(response.data)
                        }
                        loading = false
                        _showAddFriendDialog.update { false }
                    }
                }
            }
        }
    }
}
