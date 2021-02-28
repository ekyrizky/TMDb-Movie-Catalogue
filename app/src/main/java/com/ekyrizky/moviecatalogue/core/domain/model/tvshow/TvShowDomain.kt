package com.ekyrizky.moviecatalogue.core.domain.model.tvshow

data class TvShowDomain(

        val id: Int,
        val title: String,
        val tagline: String,
        val releaseYear: String,
        val runtime: Int,
        val voteAverage: Double,
        val description: String,
        val posterPath: String,
        val backdropPath: String,
        val isFavorite: Boolean
)