package com.prafull.contestifyme.onboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.onboard.model.UserInfo
import com.prafull.contestifyme.utils.Constants
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

class OnBoardingViewModel : ViewModel(), KoinComponent {

    private val _succeeded = MutableStateFlow(false)
    val succeeded = _succeeded.asStateFlow()

    private val apiService by inject<OnBoardApiService>()
    private val sharedPrefManager by inject<SharedPrefManager>()


    private val _loginState = MutableStateFlow<BaseClass<UserInfo>>(BaseClass.Initial)
    val loginState = _loginState.asStateFlow()

    var searchedId by mutableStateOf("")
    fun login(handle: String) {
        searchedId = handle
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.update { BaseClass.Loading }
            try {
                val user = apiService.getUserInfo(Constants.getUserInfo(handle))
                if (user.status == "FAILED") {
                    _loginState.update {
                        BaseClass.Error(
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
                        BaseClass.Success(user)
                    }
                }
            } catch (e: IOException) {
                _loginState.update {
                    BaseClass.Error(e)
                }
            } catch (e: HttpException) {
                _loginState.update {
                    BaseClass.Error(e)
                }
            } catch (e: Exception) {
                _loginState.update {
                    BaseClass.Error(e)
                }
            }
        }
    }
}

class UserNotFoundException() : Exception() {
    override val message: String
        get() = "User not found"
}