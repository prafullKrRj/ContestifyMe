package com.prafull.contestifyme.app.userscreen.submissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.profileFeature.domain.repositories.ProfileRepository
import com.prafull.contestifyme.network.model.UserSubmissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SubmissionViewModel : ViewModel(), KoinComponent {

    private val profileRepo by inject<ProfileRepository>()

    private val _submissions = MutableStateFlow<BaseClass<List<UserSubmissions>>>(BaseClass.Loading)
    val submissions = _submissions.asStateFlow()

    init {
        getSubmissions()
    }

    private fun getSubmissions() = viewModelScope.launch(Dispatchers.IO) {
        profileRepo.getUserSubmissions().collect { response ->
            _submissions.update { response }
        }
    }
}