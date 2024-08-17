package com.merkost.lumi.di

import com.merkost.lumi.presentation.viewmodels.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MoviesViewModel)
}