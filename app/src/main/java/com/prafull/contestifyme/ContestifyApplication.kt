package com.prafull.contestifyme

import android.app.Application
import com.prafull.contestifyme.startOnBoard.data.OnBoardContainer
import com.prafull.contestifyme.startOnBoard.data.OnBoardContainerImpl
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ContestifyApplication : Application(){
    lateinit var onBoardContainer: OnBoardContainer
    override fun onCreate() {
        super.onCreate()
        initialiseContainers()
    }
    private fun initialiseContainers() {
      //  compareContainer = CompareContainerImpl(this)
        onBoardContainer = OnBoardContainerImpl(this)
    }
}