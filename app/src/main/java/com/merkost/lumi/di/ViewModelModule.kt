package com.merkost.lumi.di

import com.merkost.lumi.presentation.viewmodels.FavoritesViewModel
import com.merkost.lumi.presentation.viewmodels.MoviesViewModel
import com.merkost.lumi.presentation.viewmodels.MovieDetailsViewModel
import com.merkost.lumi.presentation.viewmodels.SearchViewModel
import com.merkost.lumi.presentation.viewmodels.ToWatchViewModel
import com.merkost.lumi.presentation.viewmodels.WatchedViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MoviesViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::WatchedViewModel)
    viewModelOf(::ToWatchViewModel)
}