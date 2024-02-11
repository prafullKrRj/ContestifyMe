package com.prafull.contestifyme

import android.app.Application
import com.prafull.contestifyme.features.profileFeature.data.source.ProfileContainer
import com.prafull.contestifyme.features.profileFeature.data.source.ProfileContainerImpl
import com.prafull.contestifyme.startOnBoard.data.OnBoardContainer
import com.prafull.contestifyme.startOnBoard.data.OnBoardContainerImpl
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ContestifyApplication : Application(){
    lateinit var profileContainer: ProfileContainer
    lateinit var onBoardContainer: OnBoardContainer
    override fun onCreate() {
        super.onCreate()
        initialiseContainers()
    }
    private fun initialiseContainers() {
        profileContainer = ProfileContainerImpl(this)
      //  compareContainer = CompareContainerImpl(this)
        onBoardContainer = OnBoardContainerImpl(this)
    }
}