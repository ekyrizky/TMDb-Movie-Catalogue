package com.ekyrizky.core.domain.model.tvshow

data class TvShowDomain(
        val id: Int?,
        val title: String?,
        val releaseYear: String?,
        val voteAverage: Double?,
        val description: String?,
        val posterPath: String?,
        val backdropPath: String?
)