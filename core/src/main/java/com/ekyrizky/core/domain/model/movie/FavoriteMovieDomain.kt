package com.ekyrizky.core.domain.model.movie

data class FavoriteMovieDomain(
        val id: Int?,
        val title: String?,
        val tagline: String?,
        val releaseYear: String?,
        val runtime: Int?,
        val voteAverage: Double?,
        val description: String?,
        val posterPath: String?,
        val backdropPath: String?,
)