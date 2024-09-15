package com.prafull.contestifyme.features.problemsFeature.di

import androidx.room.Room
import com.prafull.contestifyme.constants.Constants
import com.prafull.contestifyme.features.problemsFeature.data.local.ProblemsDao
import com.prafull.contestifyme.features.problemsFeature.data.local.ProblemsDatabase
import com.prafull.contestifyme.features.problemsFeature.data.remote.ProblemsApiService
import com.prafull.contestifyme.features.problemsFeature.data.repositories.ProblemsRepositoryImpl
import com.prafull.contestifyme.features.problemsFeature.domain.repositories.ProblemsRepository
import com.prafull.contestifyme.features.problemsFeature.ui.ProblemsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val problemsModule = module {
    viewModel { ProblemsViewModel() }
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