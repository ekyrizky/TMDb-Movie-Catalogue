package com.ekyrizky.moviecatalogue.core.utils

import com.ekyrizky.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.PopularMoviesResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.PopularTvShowsResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain

object DataMapper {

    fun mapMoviesResponseToEntity(input: List<PopularMoviesResponse>):List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()

        input.map {
            val movies = MovieEntity(
                    id = it.id,
                    title = it.originalTitle,
                    tagline = "",
                    releaseYear = it.releaseDate,
                    runtime = 0,
                    voteAverage = it.voteAverage,
                    description = it.overview,
                    posterPath = it.posterPath,
                    backdropPath = it.backdropPath,
                    isFavorite = false
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapMTvShowsResponseToEntity(input: List<PopularTvShowsResponse>):List<TvShowEntity> {
        val tvShowList = ArrayList<TvShowEntity>()

        input.map {
            val tvShow = TvShowEntity(
                    id = it.id,
                    title = it.originalName,
                    tagline = "",
                    releaseYear = it.firstAirDate,
                    runtime = 0,
                    voteAverage = it.voteAverage,
                    description = it.overview,
                    posterPath = it.posterPath,
                    backdropPath = it.backdropPath,
                    isFavorite = false
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapMovieDetailResponseToEntity(input: MovieDetailResponse): MovieEntity =
            MovieEntity(
                    id = input.id,
                    title = input.originalTitle,
                    tagline = input.tagline,
                    releaseYear = input.releaseDate,
                    runtime = input.runtime,
                    voteAverage = input.voteAverage,
                    description = input.overview,
                    posterPath = input.posterPath,
                    backdropPath = input.backdropPath,
                    isFavorite = false
            )


    fun mapTvShowDetailResponseToEntity(input: TvShowDetailResponse): TvShowEntity =
            TvShowEntity(
                    id = input.id,
                    title = input.originalName,
                    tagline = input.tagline,
                    releaseYear = input.firstAirDate,
                    runtime = input.episodeRunTime.first(),
                    voteAverage = input.voteAverage,
                    description = input.overview,
                    posterPath = input.posterPath,
                    backdropPath = input.backdropPath,
                    isFavorite = false
            )

    fun mapMovieEntityToDomain(input: List<MovieEntity>): List<MovieDomain> =
            input.map {
                MovieDomain(
                        id = it.id,
                        title = it.title,
                        tagline = it.tagline,
                        releaseYear = it.releaseYear,
                        runtime = it.runtime,
                        voteAverage = it.voteAverage,
                        description = it.description,
                        posterPath = it.posterPath,
                        backdropPath = it.backdropPath,
                        isFavorite = it.isFavorite
                )
            }

    fun mapTvShowEntityToDomain(input: List<TvShowEntity>): List<TvShowDomain> =
            input.map {
                TvShowDomain(
                        id = it.id,
                        title = it.title,
                        tagline = it.tagline,
                        releaseYear = it.releaseYear,
                        runtime = it.runtime,
                        voteAverage = it.voteAverage,
                        description = it.description,
                        posterPath = it.posterPath,
                        backdropPath = it.backdropPath,
                        isFavorite = it.isFavorite
                )
            }

    fun mapMovieEntityDetailToDomain(input: MovieEntity): MovieDomain =
            MovieDomain(
                    id = input.id,
                    title = input.title,
                    tagline = input.tagline,
                    releaseYear = input.releaseYear,
                    runtime = input.runtime,
                    voteAverage = input.voteAverage,
                    description = input.description,
                    posterPath = input.posterPath,
                    backdropPath = input.backdropPath,
                    isFavorite = input.isFavorite
            )

    fun mapTvShowEntityDetailToDomain(input: TvShowEntity): TvShowDomain =
            TvShowDomain(
                    id = input.id,
                    title = input.title,
                    tagline = input.tagline,
                    releaseYear = input.releaseYear,
                    runtime = input.runtime,
                    voteAverage = input.voteAverage,
                    description = input.description,
                    posterPath = input.posterPath,
                    backdropPath = input.backdropPath,
                    isFavorite = input.isFavorite
            )


    fun mapMovieDomainToEntity(input: MovieDomain) = MovieEntity(
            id = input.id,
            title = input.title,
            tagline = input.tagline,
            releaseYear = input.releaseYear,
            runtime = input.runtime,
            voteAverage = input.voteAverage,
            description = input.description,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            isFavorite = input.isFavorite
    )

    fun mapTvShowDomainToEntity(input: TvShowDomain) = TvShowEntity(
            id = input.id,
            title = input.title,
            tagline = input.tagline,
            releaseYear = input.releaseYear,
            runtime = input.runtime,
            voteAverage = input.voteAverage,
            description = input.description,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            isFavorite = input.isFavorite
    )

}