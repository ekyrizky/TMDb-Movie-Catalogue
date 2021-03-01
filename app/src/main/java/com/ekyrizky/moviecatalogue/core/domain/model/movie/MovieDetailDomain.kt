package com.ekyrizky.moviecatalogue.core.domain.model.movie

data class MovieDetailDomain(
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