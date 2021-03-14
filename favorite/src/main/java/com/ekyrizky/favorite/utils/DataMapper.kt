package com.ekyrizky.favorite.utils

import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.moviecatalogue.model.movie.FavoriteMovie
import com.ekyrizky.moviecatalogue.model.tvshow.FavoriteTvShow

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

    fun mapFavoriteTvShowDomainToFavoriteTvShow(input: List<FavoriteTvShowDomain>): List<FavoriteTvShow> =
        input.map {
            FavoriteTvShow(
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

    fun mapFavoriteTvShowToFavoriteTvShowDomain(input: List<FavoriteTvShow>): List<FavoriteTvShowDomain> =
        input.map {
            FavoriteTvShowDomain(
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