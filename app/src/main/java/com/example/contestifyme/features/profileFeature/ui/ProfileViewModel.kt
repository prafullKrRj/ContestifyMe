package com.example.contestifyme.features.profileFeature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contestifyme.features.profileFeature.data.ProfileRepository
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val handle: String
): ViewModel() {

}
data class ProfileUiState(
    val user: List<UserInfoEntity> = emptyList(),
)