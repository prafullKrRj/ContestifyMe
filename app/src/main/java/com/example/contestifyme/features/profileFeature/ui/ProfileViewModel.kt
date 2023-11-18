package com.example.contestifyme.features.profileFeature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contestifyme.features.profileFeature.model.ProfileRepository
import com.example.contestifyme.ui.OnBoardingVM

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val hande: String
): ViewModel() {

}