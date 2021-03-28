package com.ekyrizky.favorite.utils

import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.moviecatalogue.model.movie.FavoriteMovie

object DataMapper {

    fun mapFavoriteMovieDomainToFavoriteMovie(input: List<FavoriteMovieDomain>): List<FavoriteMovie> =
        input.map {
            FavoriteMovie(
                id = it.id,
                title = it.title,
                tagline = it.tagline,
                releaseYear = it.releaseYear,
                runtime = it.runtime,
                voteAverage = it.voteAverage,
                description = it.description,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
            )
        }

    fun mapFavoriteMovieToFavoriteMovieDomain(input: List<FavoriteMovie>): List<FavoriteMovieDomain> =
        input.map {
            FavoriteMovieDomain(
                id = it.id,
                title = it.title,
                tagline = it.tagline,
                releaseYear = it.releaseYear,
                runtime = it.runtime,
                voteAverage = it.voteAverage,
                description = it.description,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
            )
        }
}