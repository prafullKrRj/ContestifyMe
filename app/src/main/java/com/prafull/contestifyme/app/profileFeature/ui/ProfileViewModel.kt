package com.prafull.contestifyme.app.profileFeature.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.app.profileFeature.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@SuppressLint("MutableCollectionMutableState")
class ProfileViewModel(
    val context: Context
) : ViewModel(), KoinComponent {

    private val profileRepository: ProfileRepository by inject()

    private val _profileUiState = MutableStateFlow<BaseClass<UserData>>(BaseClass.Loading)
    val profileUiState = _profileUiState.asStateFlow()

    init {
        updateUserInfo()
    }

    fun updateUserInfo() {
        viewModelScope.launch {
            profileRepository.getUserInfo().collectLatest { response ->
                when (response) {
                    is BaseClass.Error -> {
                        when (response.exception) {
                            is java.io.IOException -> {
                                showToast("No internet connection.")
                            }

                            is retrofit2.HttpException -> {
                                Log.d(
                                    "ProfileViewModel",
                                    "updateUserInfo: ${response.exception.message()}"
                                )
                                showToast("An unexpected error occurred.")
                            }

                            else -> {
                                Log.d("ProfileViewModel", "updateUserInfo: ${response.exception}")
                                showToast("An unexpected error occurred.")
                            }
                        }
                        profileRepository.getUserInfoFromLocal().collectLatest { localResponse ->
                            _profileUiState.update { localResponse }
                        }
                    }

                    BaseClass.Loading -> {
                        _profileUiState.update { response }
                    }

                    is BaseClass.Success -> {
                        _profileUiState.update { response }
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}