package com.prafull.contestifyme

import android.app.Application
import com.prafull.contestifyme.features.contestsFeature.data.source.ContestsContainer
import com.prafull.contestifyme.features.contestsFeature.data.source.ContestsContainerImpl
import com.prafull.contestifyme.features.friendsFeature.data.source.FriendsContainer
import com.prafull.contestifyme.features.friendsFeature.data.source.FriendsContainerImpl
import com.prafull.contestifyme.features.problemsFeature.data.source.ProblemsContainer
import com.prafull.contestifyme.features.problemsFeature.data.source.ProblemsContainerImpl
import com.prafull.contestifyme.features.profileFeature.data.source.ProfileContainer
import com.prafull.contestifyme.features.profileFeature.data.source.ProfileContainerImpl
import com.prafull.contestifyme.startOnBoard.data.OnBoardContainer
import com.prafull.contestifyme.startOnBoard.data.OnBoardContainerImpl

class ContestifyApplication : Application(){
    lateinit var profileContainer: ProfileContainer
    lateinit var contestsContainer: ContestsContainer
 //   lateinit var compareContainer: CompareContainer
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
      //  compareContainer = CompareContainerImpl(this)
        friendsContainer = FriendsContainerImpl(this)
        problemsContainer = ProblemsContainerImpl(this)
        onBoardContainer = OnBoardContainerImpl(this)
    }
}