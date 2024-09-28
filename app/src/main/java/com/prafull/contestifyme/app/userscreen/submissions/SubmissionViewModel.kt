package com.prafull.contestifyme.app.userscreen.submissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.App
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.friendsFeature.domain.FriendsRepo
import com.prafull.contestifyme.app.profileFeature.domain.model.UserSubmissions
import com.prafull.contestifyme.app.profileFeature.domain.repositories.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SubmissionViewModel(
    var args: App.SubmissionScreen
) : ViewModel(), KoinComponent {

    private val profileRepo by inject<ProfileRepository>()
    private val friendRepo by inject<FriendsRepo>()

    private val _submissions = MutableStateFlow<BaseClass<List<UserSubmissions>>>(BaseClass.Loading)
    val submissions = _submissions.asStateFlow()

    init {
        getSubmissions()
    }

    private fun getSubmissions() = viewModelScope.launch(Dispatchers.IO) {

        if (args.isFriend) {
            friendRepo.getFriendData(args.handle).collectLatest { response ->
                when (response) {
                    is BaseClass.Error -> {
                        _submissions.update { BaseClass.Error(response.exception) }
                    }

                    BaseClass.Loading -> {
                        _submissions.update { BaseClass.Loading }
                    }

                    is BaseClass.Success -> {
                        _submissions.update {
                            BaseClass.Success(
                                response.data.userSubmissions
                            )
                        }
                    }
                }

            }
        } else {
            profileRepo.getUserInfo().collectLatest { response ->
                when (response) {
                    is BaseClass.Error -> {
                        _submissions.update { BaseClass.Error(response.exception) }
                    }

                    BaseClass.Loading -> {
                        _submissions.update { BaseClass.Loading }
                    }

                    is BaseClass.Success -> {
                        _submissions.update {
                            BaseClass.Success(
                                response.data.subMissionInfo
                            )
                        }
                    }
                }

            }
        }
    }

}