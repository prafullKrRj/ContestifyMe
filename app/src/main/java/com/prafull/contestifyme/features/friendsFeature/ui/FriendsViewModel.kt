package com.prafull.contestifyme.features.friendsFeature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.commons.Resource
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.prafull.contestifyme.features.friendsFeature.domain.repositories.FriendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FriendsViewModel() : ViewModel(), KoinComponent {
    private val friendsRepository: FriendsRepository by inject()
    private val _friends: StateFlow<List<FriendsDataEntity>> = friendsRepository.getFromDatabase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList<FriendsDataEntity>()
        )

    private val _uiState: MutableStateFlow<Resource<List<FriendsDataEntity>>> =
        MutableStateFlow(Resource.Initial)
    val uiState: StateFlow<Resource<List<FriendsDataEntity>>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            friendsRepository.getFromInternet().collect { resp ->
                when (resp) {
                    is Resource.Error -> {
                        _uiState.update {
                            Resource.Success(
                                _friends.value
                            )
                        }
                    }

                    Resource.Initial -> {
                        _uiState.update {
                            Resource.Initial
                        }
                    }

                    Resource.Loading -> {
                        _uiState.update {
                            Resource.Loading
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            Resource.Success(resp.data.map {
                                it.toFriendsDataEntity()
                            })
                        }
                    }
                }
            }
        }
    }

    val addFriendState: MutableStateFlow<Resource<Boolean>> = MutableStateFlow(Resource.Initial)
    fun addFriend(handle: String) {
        try {
            viewModelScope.launch {
                friendsRepository.getSingleFriendFromInternet(handle)
                    .collect { resp ->
                        when (resp) {
                            is Resource.Error -> {
                                addFriendState.update {
                                    Resource.Error(resp.exception)
                                }
                            }

                            Resource.Initial -> {
                                addFriendState.update {
                                    Resource.Initial
                                }

                            }

                            Resource.Loading -> {
                                addFriendState.update {
                                    Resource.Loading
                                }
                            }

                            is Resource.Success -> {
                                addFriendState.update {
                                    Resource.Success(true)

                                }
                            }
                        }
                    }
            }
        } catch (e: Exception) {
            addFriendState.update {
                Resource.Error(e)
            }
        }
    }

    fun getFromInternet() {
        viewModelScope.launch {
            friendsRepository.getFromInternet().collect { resp ->
                when (resp) {
                    is Resource.Error -> {
                        _uiState.update {
                            Resource.Success(
                                _friends.value
                            )
                        }
                    }

                    Resource.Initial -> {
                        _uiState.update {
                            Resource.Initial
                        }
                    }

                    Resource.Loading -> {
                        _uiState.update {
                            Resource.Loading
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            Resource.Success(resp.data.map {
                                it.toFriendsDataEntity()
                            })
                        }
                    }
                }
            }
        }
    }
}