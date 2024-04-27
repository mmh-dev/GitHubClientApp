package com.murod.githubclient.di

import androidx.room.Room
import com.murod.githubclient.database.RepoDatabase
import com.murod.githubclient.database.RepoRepository
import com.murod.githubclient.network.RetrofitService
import com.murod.githubclient.viewmodels.RepoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            RepoDatabase::class.java,
            "repo_db"
        ).build()
    }

    single { RepoRepository(get()) }

    single {get<RepoDatabase>().repoDao() }

    single {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RetrofitService::class.java)
    }

    viewModel { RepoViewModel(get(),get()) }
}
