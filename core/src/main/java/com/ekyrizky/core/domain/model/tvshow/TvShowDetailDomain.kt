package com.ekyrizky.core.domain.model.tvshow

data class TvShowDetailDomain(
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