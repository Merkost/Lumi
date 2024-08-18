package com.merkost.lumi.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    val adult: Boolean = true,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    val budget: Int = 0,
    val genres: List<Genre> = emptyList(),
    val homepage: String?,
    val id: Int = 0,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double = 0.0,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry> = emptyList(),
    @SerialName("release_date") val
    releaseDate: String?,
    val revenue: Int = 0,
    val runtime: Int = 0,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage> = emptyList(),
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean = true,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0
)

@Serializable
data class Genre(
    val id: Int = 0, val name: String?
)

@Serializable
data class ProductionCompany(
    val id: Int = 0,
    @SerialName("logo_path")
    val logoPath: String?,
    val name: String?,
    @SerialName("origin_country")
    val originCountry: String?
)

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String?,
    val name: String?
)

@Serializable
data class SpokenLanguage(
    @SerialName("english_name")
    val englishName: String?,
    @SerialName("iso_639_1")
    val iso6391: String?,
    val name: String?
)
