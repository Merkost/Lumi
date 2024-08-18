package com.merkost.lumi.di

import com.merkost.lumi.presentation.viewmodels.MoviesViewModel
import com.merkost.lumi.presentation.viewmodels.MovieDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MoviesViewModel)
    viewModelOf(::MovieDetailsViewModel)
}