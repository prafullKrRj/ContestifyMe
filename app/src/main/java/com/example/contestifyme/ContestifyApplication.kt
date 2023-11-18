package com.example.contestifyme

import android.app.Application
import com.example.contestifyme.features.compareFeature.data.CompareContainer
import com.example.contestifyme.features.compareFeature.data.CompareContainerImpl
import com.example.contestifyme.features.contestsFeature.data.ContestsContainer
import com.example.contestifyme.features.contestsFeature.data.ContestsContainerImpl
import com.example.contestifyme.features.friendsFeature.data.FriendsContainer
import com.example.contestifyme.features.friendsFeature.data.FriendsContainerImpl
import com.example.contestifyme.features.problemsFeature.data.ProblemsContainer
import com.example.contestifyme.features.problemsFeature.data.ProblemsContainerImpl
import com.example.contestifyme.features.profileFeature.data.ProfileContainer
import com.example.contestifyme.features.profileFeature.data.ProfileContainerImpl
import com.example.contestifyme.ui.data.OnBoardContainer
import com.example.contestifyme.ui.data.OnBoardContainerImpl

class ContestifyApplication : Application(){
    lateinit var profileContainer: ProfileContainer
    lateinit var contestsContainer: ContestsContainer
    lateinit var compareContainer: CompareContainer
    lateinit var friendsContainer: FriendsContainer
    lateinit var problemsContainer: ProblemsContainer
    lateinit var onBoardContainer: OnBoardContainer
    override fun onCreate() {
        super.onCreate()
        initialiseContainers()
    }
    private fun initialiseContainers() {
        profileContainer = ProfileContainerImpl(this)
        contestsContainer = ContestsContainerImpl(this)
        compareContainer = CompareContainerImpl(this)
        friendsContainer = FriendsContainerImpl(this)
        problemsContainer = ProblemsContainerImpl(this)
        onBoardContainer = OnBoardContainerImpl(this)
    }
}