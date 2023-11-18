package com.example.contestifyme.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contestifyme.startOnBoard.data.AppUser
import com.example.contestifyme.startOnBoard.data.OnBoardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class OnBoardingVM(
    private val onBoardingRepository: OnBoardRepository
): ViewModel() {

    val state: StateFlow<OnBoardingState> = onBoardingRepository.getUser().map {
        OnBoardingState(
            users = it
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OnBoardingState(
            users = emptyList()
        )
    )

    private suspend fun updateUser(handle: String) {
        viewModelScope.launch {
            onBoardingRepository.upsertUser(
                AppUser(
                    handle = handle,
                    boolean = true
                )
            )
        }
    }

    fun saveUser(handle: String) {
        viewModelScope.launch {
            try {
                val userInfo = onBoardingRepository.getUserValidation(handle)
                if (userInfo.status == "OK") {
                    updateUser(handle)
                }
                else {
                    Log.d("prafull", "saveUser: error")
                }
            } catch (e: HttpException) {
                Log.d("prafull", "saveUser: error http")
            } catch (e: IOException) {
                Log.d("prafull", "saveUser: error io")
            }
        }
    }
    fun getUser() : AppUser? {
        var appUser: AppUser? = null
        viewModelScope.launch {
            onBoardingRepository.getUser().map {
                appUser = it[0]
            }
        }
        return appUser
    }
}
data class OnBoardingState(
    var users: List<AppUser>
)