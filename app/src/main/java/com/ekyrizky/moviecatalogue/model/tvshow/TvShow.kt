package com.ekyrizky.moviecatalogue.model.tvshow

data class TvShow(
    val id: Int?,
    val title: String?,
    val releaseYear: String?,
    val voteAverage: Double?,
    val description: String?,
    val posterPath: String?,
    val backdropPath: String?
)