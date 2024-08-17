package com.merkost.lumi.di

import com.merkost.lumi.data.repository.MovieRepositoryImpl
import com.merkost.lumi.domain.repositories.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}