package com.prafull.contestifyme.app.contestsFeature.di

import androidx.room.Room
import com.prafull.contestifyme.app.contestsFeature.contestListScreen.ContestsViewModel
import com.prafull.contestifyme.app.contestsFeature.contestScreen.ContestViewModel
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestDB
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsDao
import com.prafull.contestifyme.app.contestsFeature.data.remote.ContestsApiService
import com.prafull.contestifyme.app.contestsFeature.data.repositories.ContestsRepositoryImpl
import com.prafull.contestifyme.app.contestsFeature.domain.repositories.ContestsRepository
import com.prafull.contestifyme.utils.Constants.BASE_URL
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
    viewModel {
        ContestViewModel(
            get()
        )
    }
}