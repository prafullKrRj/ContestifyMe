package com.prafull.contestifyme.features.contestsFeature.di

import androidx.room.Room
import com.prafull.contestifyme.constants.Constants.BASE_URL
import com.prafull.contestifyme.features.contestsFeature.data.local.ContestDB
import com.prafull.contestifyme.features.contestsFeature.data.local.ContestsDao
import com.prafull.contestifyme.features.contestsFeature.data.remote.ContestsApiService
import com.prafull.contestifyme.features.contestsFeature.data.repositories.ContestsRepositoryImpl
import com.prafull.contestifyme.features.contestsFeature.domain.repositories.ContestsRepository
import com.prafull.contestifyme.features.contestsFeature.ui.ContestsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val contestModule = module {
    single<ContestsApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ContestsApiService::class.java)
    }

    single<ContestDB> {
        Room.databaseBuilder(
            androidApplication(),
            ContestDB::class.java,
            "contests_db"
        ).build()
    }

    single<ContestsDao> {
        get<ContestDB>().contestsDao()
    }

    single<ContestsRepository> {
        ContestsRepositoryImpl()
    }
    viewModel { ContestsViewModel() }
}