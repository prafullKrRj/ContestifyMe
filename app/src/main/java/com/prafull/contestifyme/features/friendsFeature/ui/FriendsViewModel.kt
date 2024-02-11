package com.prafull.contestifyme.features.friendsFeature.ui

import androidx.lifecycle.ViewModel
import com.prafull.contestifyme.features.friendsFeature.domain.repositories.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor (
    private val friendsRepository: FriendsRepository
) : ViewModel() {

}