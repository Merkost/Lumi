package com.merkost.lumi.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val averageRating: Double,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    val releaseDate: String?,
    val isFavorite: Boolean = false,
    val isWatched: Boolean = false,
    val isToWatch: Boolean = false,
    val addedAt: Long = System.currentTimeMillis()
)
