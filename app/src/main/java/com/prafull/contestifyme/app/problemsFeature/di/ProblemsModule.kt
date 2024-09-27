package com.prafull.contestifyme.app.problemsFeature.di

import androidx.room.Room
import com.prafull.contestifyme.app.problemsFeature.data.local.ProblemsDao
import com.prafull.contestifyme.app.problemsFeature.data.local.ProblemsDatabase
import com.prafull.contestifyme.app.problemsFeature.data.remote.ProblemsApiService
import com.prafull.contestifyme.app.problemsFeature.data.repositories.ProblemsRepositoryImpl
import com.prafull.contestifyme.app.problemsFeature.domain.repositories.ProblemsRepository
import com.prafull.contestifyme.app.problemsFeature.ui.ProblemsViewModel
import com.prafull.contestifyme.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val problemsModule = module {
    viewModel { ProblemsViewModel(androidContext()) }
    single<ProblemsApiService> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProblemsApiService::class.java)
    }

    single<ProblemsDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            ProblemsDatabase::class.java,
            "problems_database"
        ).build()
    }

    single<ProblemsDao> {
        get<ProblemsDatabase>().problemsDao()
    }

    single<ProblemsRepository> {
        ProblemsRepositoryImpl()
    }
}