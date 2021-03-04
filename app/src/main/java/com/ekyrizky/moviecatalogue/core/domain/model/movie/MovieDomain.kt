package com.ekyrizky.moviecatalogue.core.domain.model.movie

data class MovieDomain(
        val id: Int?,
        val title: String?,
        val releaseYear: String?,
        val voteAverage: Double?,
        val description: String?,
        val posterPath: String?,
        val backdropPath: String?
)