package com.ekyrizky.moviecatalogue.model.movie

data class Movie(
    val id: Int?,
    val title: String?,
    val releaseYear: String?,
    val voteAverage: Double?,
    val description: String?,
    val posterPath: String?,
    val backdropPath: String?
)