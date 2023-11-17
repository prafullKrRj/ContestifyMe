package com.example.contestifyme

import android.app.Application
import com.example.contestifyme.compareFeature.data.CompareContainer
import com.example.contestifyme.compareFeature.data.CompareContainerImpl
import com.example.contestifyme.contestsFeature.data.ContestsContainer
import com.example.contestifyme.contestsFeature.data.ContestsContainerImpl
import com.example.contestifyme.friendsFeature.data.FriendsContainer
import com.example.contestifyme.friendsFeature.data.FriendsContainerImpl
import com.example.contestifyme.problemsFeature.data.ProblemsContainer
import com.example.contestifyme.problemsFeature.data.ProblemsContainerImpl
import com.example.contestifyme.profileFeature.data.ProfileContainer
import com.example.contestifyme.profileFeature.data.ProfileContainerImpl

class ContestifyApplication : Application(){
    lateinit var profileContainer: ProfileContainer
    lateinit var contestsContainer: ContestsContainer
    lateinit var compareContainer: CompareContainer
    lateinit var friendsContainer: FriendsContainer
    lateinit var problemsContainer: ProblemsContainer
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
    }
}