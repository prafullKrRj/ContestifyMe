package com.prafull.contestifyme.onboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.network.model.userinfo.UsersInfo
import com.prafull.contestifyme.utils.managers.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import java.io.IOException

sealed interface OnBoardingState {
    data object Loading : OnBoardingState
    data object Empty : OnBoardingState
    data class Success(val user: UsersInfo) : OnBoardingState
    data class Error(val e: Throwable) : OnBoardingState
}

class OnBoardingViewModel : ViewModel(), KoinComponent {

    private val _succeeded = MutableStateFlow(false)
    val succeeded = _succeeded.asStateFlow()

    private val apiService by inject<OnBoardApiService>()
    private val sharedPrefManager by inject<SharedPrefManager>()


    private val _loginState = MutableStateFlow<OnBoardingState>(
        OnBoardingState.Empty
    )
    val loginState = _loginState.asStateFlow()

    var searchedId by mutableStateOf("")
    fun login(handle: String) {
        searchedId = handle
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.update { OnBoardingState.Loading }
            try {
                val user = apiService.getUser(handle)
                Log.d("OnBoardingViewModel", "login: $user")
                if (user.status == "FAILED") {
                    _loginState.update {
                        OnBoardingState.Error(
                            UserNotFoundException()
                        )
                    }
                } else {
                    _loginState.update {
                        sharedPrefManager.setLoginUserHandle(handle)
                        sharedPrefManager.setLoggedIn(true)
                        _succeeded.update {
                            true
                        }
                        user.let { OnBoardingState.Success(it) }
                    }
                }
            } catch (e: IOException) {
                _loginState.update {
                    OnBoardingState.Error(e)
                }
            } catch (e: HttpException) {
                _loginState.update {
                    OnBoardingState.Error(e)
                }
            } catch (e: Exception) {
                _loginState.update {
                    OnBoardingState.Error(e)
                }
            }
        }
    }
}

class UserNotFoundException() : Exception() {
    override val message: String
        get() = "User not found"
}