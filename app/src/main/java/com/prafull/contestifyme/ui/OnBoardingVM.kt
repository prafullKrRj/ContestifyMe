package com.prafull.contestifyme.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.commons.Response
import com.prafull.contestifyme.startOnBoard.data.OnBoardRepository
import com.prafull.contestifyme.startOnBoard.network.model.userInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class OnBoardingVM(
    private val onBoardingRepository: OnBoardRepository
): ViewModel() {

    private val _onBoardState = MutableStateFlow<OnBoardingState>(OnBoardingState.Initial)
    val onBoardState = _onBoardState.asStateFlow()
    fun saveUser(handle: String) {
        viewModelScope.launch {
            try {
                val userInfo = onBoardingRepository.getUserValidation(handle).collect { resp ->
                    when (resp) {
                        is Response.Error -> {
                            _onBoardState.update {
                                OnBoardingState.Error(resp.exception.message ?: "Error")
                            }
                        }
                        Response.Loading -> {
                            _onBoardState.update {
                                OnBoardingState.Loading
                            }
                        }
                        is Response.Success -> {
                            _onBoardState.update {
                                OnBoardingState.Success(resp.data)
                            }
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.d("prafull", "saveUser: error http")
            } catch (e: IOException) {
                Log.d("prafull", "saveUser: error io")
            }
        }
    }
}

sealed class OnBoardingState {
    object Initial: OnBoardingState()
    object Loading: OnBoardingState()
    data class Success(val user: userInfo): OnBoardingState()
    data class Error(val error: String): OnBoardingState()
}