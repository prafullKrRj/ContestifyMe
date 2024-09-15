package com.prafull.contestifyme

import android.app.Application
import com.prafull.contestifyme.features.contestsFeature.di.contestModule
import com.prafull.contestifyme.features.friendsFeature.di.friendsModule
import com.prafull.contestifyme.features.problemsFeature.di.problemsModule
import com.prafull.contestifyme.features.profileFeature.di.profileModule
import com.prafull.contestifyme.startOnBoard.data.OnBoardContainer
import com.prafull.contestifyme.startOnBoard.data.OnBoardContainerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ContestifyApplication : Application() {
    lateinit var onBoardContainer: OnBoardContainer
    override fun onCreate() {
        super.onCreate()
        initialiseContainers()
        startKoin {
            androidContext(this@ContestifyApplication)
            androidLogger()
            modules(contestModule, profileModule, friendsModule, problemsModule)
        }
    }

    private fun initialiseContainers() {
        //  compareContainer = CompareContainerImpl(this)
        onBoardContainer = OnBoardContainerImpl(this)
    }
}