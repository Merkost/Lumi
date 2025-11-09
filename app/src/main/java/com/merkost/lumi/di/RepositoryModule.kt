package com.merkost.lumi.di

import com.merkost.lumi.data.repository.ConfigurationRepositoryImpl
import com.merkost.lumi.data.repository.MovieRepositoryImpl
import com.merkost.lumi.domain.repositories.ConfigurationRepository
import com.merkost.lumi.domain.repositories.MovieRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<ConfigurationRepository> { ConfigurationRepositoryImpl(get(), androidContext()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}