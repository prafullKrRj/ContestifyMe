package com.prafull.contestifyme.app.friendsFeature.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.friendsFeature.domain.FriendData
import com.prafull.contestifyme.app.friendsFeature.domain.FriendsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FriendScreenViewModel(
    private val handle: String
) : ViewModel(), KoinComponent {

    private val repo: FriendsRepo by inject()
    var friendHandle by mutableStateOf(handle)

    private val _friendState = MutableStateFlow<BaseClass<FriendData>>(BaseClass.Loading)
    val friendState = _friendState.asStateFlow()

    init {
        getFriendDetails()
    }

    fun getFriendDetails() = viewModelScope.launch(Dispatchers.IO) {
        repo.getFriendData(handle).collectLatest { response ->
            _friendState.update { response }
        }
    }
}