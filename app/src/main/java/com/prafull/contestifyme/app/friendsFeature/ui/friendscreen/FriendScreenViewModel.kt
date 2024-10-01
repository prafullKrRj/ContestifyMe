package com.prafull.contestifyme.app.friendsFeature.ui.friendscreen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.app.friendsFeature.domain.FriendRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FriendScreenViewModel(
    private val handle: String,
    val context: Context
) : ViewModel(), KoinComponent {

    var friendHandle by mutableStateOf(handle)

    private val repo: FriendRepo by inject()

    private val _friendState = MutableStateFlow<BaseClass<UserData>>(BaseClass.Loading)
    val friendState = _friendState.asStateFlow()

    init {
        getFriendDetails()
    }

    // only getting userInfos for main screen not ratingInfos and submissions
    fun getFriendDetails() = viewModelScope.launch(Dispatchers.IO) {
        repo.getSingleFriendData(handle).collectLatest { response ->
            if (response is BaseClass.Error) {
                showToast("Error occurred")
                repo.getFriendDataFromDb(handle)?.let { dbResponse ->
                    _friendState.update {
                        BaseClass.Success(
                            UserData(
                                handle = handle,
                                usersInfo = dbResponse.toUserResult(),
                                userRating = emptyList(),
                                userSubmissions = emptyList()
                            )
                        )
                    }
                }.run {
                    return@collectLatest
                }
            }
            _friendState.update {
                response
            }
        }
    }

    private suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}