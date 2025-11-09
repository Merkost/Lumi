package com.merkost.lumi.di

import androidx.room.Room
import com.merkost.lumi.data.local.database.AppDatabase
import com.merkost.lumi.data.repository.LocalMovieRepositoryImpl
import com.merkost.lumi.domain.repositories.LocalMovieRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    single { get<AppDatabase>().movieDao() }

    single<LocalMovieRepository> { LocalMovieRepositoryImpl(get()) }
}
