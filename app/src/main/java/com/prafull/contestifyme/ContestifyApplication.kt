package com.prafull.contestifyme

import android.app.Application
import com.prafull.contestifyme.app.contestsFeature.di.contestModule
import com.prafull.contestifyme.app.friendsFeature.di.friendsModule
import com.prafull.contestifyme.app.problemsFeature.di.problemsModule
import com.prafull.contestifyme.app.profileFeature.di.profileModule
import com.prafull.contestifyme.onboard.OnBoardApiService
import com.prafull.contestifyme.onboard.OnBoardingViewModel
import com.prafull.contestifyme.utils.Constants
import com.prafull.contestifyme.utils.managers.SharedPrefManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContestifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ContestifyApplication)
            androidLogger()
            modules(contestModule, profileModule, friendsModule, problemsModule, module {
                viewModel {
                    OnBoardingViewModel()
                }
                single<OnBoardApiService> {
                    Retrofit.Builder().baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(OnBoardApiService::class.java)
                }
                single {
                    SharedPrefManager(androidContext())
                }
            })
        }
    }
}