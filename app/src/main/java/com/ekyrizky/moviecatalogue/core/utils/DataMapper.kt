package com.ekyrizky.moviecatalogue.core.utils

import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.MovieDetailEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.TvShowDetailEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.TvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.PopularMoviesResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.PopularTvShowsResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.moviecatalogue.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain

object DataMapper {

    fun mapMoviesResponseToEntity(input: List<PopularMoviesResponse>):List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()

        input.map {
            val movies = MovieEntity(
                    id = it.id,
                    title = it.originalTitle,
                    releaseYear = it.releaseDate,
                    voteAverage = it.voteAverage,
                    description = it.overview,
                    posterPath = it.posterPath,
                    backdropPath = it.backdropPath,
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapTvShowsResponseToEntity(input: List<PopularTvShowsResponse>):List<TvShowEntity> {
        val tvShowList = ArrayList<TvShowEntity>()

        input.map {
            val tvShow = TvShowEntity(
                    id = it.id,
                    title = it.originalName,
                    releaseYear = it.firstAirDate,
                    voteAverage = it.voteAverage,
                    description = it.overview,
                    posterPath = it.posterPath,
                    backdropPath = it.backdropPath,
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapMovieDetailResponseToEntity(input: MovieDetailResponse): MovieDetailEntity =
            MovieDetailEntity(
                    id = input.id,
                    title = input.originalTitle,
                    tagline = input.tagline,
                    releaseYear = input.releaseDate,
                    runtime = input.runtime,
                    voteAverage = input.voteAverage,
                    description = input.overview,
                    posterPath = input.posterPath,
                    backdropPath = input.backdropPath,
            )


    fun mapTvShowDetailResponseToEntity(input: TvShowDetailResponse): TvShowDetailEntity =
            TvShowDetailEntity(
                    id = input.id,
                    title = input.originalName,
                    tagline = input.tagline,
                    releaseYear = input.firstAirDate,
                    runtime = input.episodeRunTime.first(),
                    voteAverage = input.voteAverage,
                    description = input.overview,
                    posterPath = input.posterPath,
                    backdropPath = input.backdropPath,
            )

    fun mapMovieEntityToDomain(input: List<MovieEntity>): List<MovieDomain> =
            input.map {
                MovieDomain(
                        id = it.id,
                        title = it.title,
                        releaseYear = it.releaseYear,
                        voteAverage = it.voteAverage,
                        description = it.description,
                        posterPath = it.posterPath,
                        backdropPath = it.backdropPath,
                )
            }

    fun mapTvShowEntityToDomain(input: List<TvShowEntity>): List<TvShowDomain> =
            input.map {
                TvShowDomain(
                        id = it.id,
                        title = it.title,
                        releaseYear = it.releaseYear,
                        voteAverage = it.voteAverage,
                        description = it.description,
                        posterPath = it.posterPath,
                        backdropPath = it.backdropPath,
                )
            }

    fun mapMovieDetailEntityToDomain(input: MovieDetailEntity?): MovieDetailDomain =
            MovieDetailDomain(
                    id = input?.id,
                    title = input?.title,
                    tagline = input?.tagline,
                    releaseYear = input?.releaseYear,
                    runtime = input?.runtime,
                    voteAverage = input?.voteAverage,
                    description = input?.description,
                    posterPath = input?.posterPath,
                    backdropPath = input?.backdropPath,
            )

    fun mapTvShowDetailEntityToDomain(input: TvShowDetailEntity?): TvShowDetailDomain =
            TvShowDetailDomain(
                    id = input?.id,
                    title = input?.title,
                    tagline = input?.tagline,
                    releaseYear = input?.releaseYear,
                    runtime = input?.runtime,
                    voteAverage = input?.voteAverage,
                    description = input?.description,
                    posterPath = input?.posterPath,
                    backdropPath = input?.backdropPath,
            )

    fun mapFavoriteMovieEntityToDomain(input: List<FavoriteMovieEntity>): List<FavoriteMovieDomain> =
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

    fun mapFavoriteTvShowEntityToDomain(input: List<FavoriteTvShowEntity>): List<FavoriteTvShowDomain> =
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

    fun mapDetailMovieDomainToFavoriteEntity(input: MovieDetailDomain): FavoriteMovieEntity =
            FavoriteMovieEntity(
                    id = input.id,
                    title = input.title,
                    tagline = input.tagline,
                    releaseYear = input.releaseYear,
                    runtime = input.runtime,
                    voteAverage = input.voteAverage,
                    description = input.description,
                    posterPath = input.posterPath,
                    backdropPath = input.backdropPath,
        )

    fun mapDetailTvShowDomainToFavoriteEntity(input: TvShowDetailDomain): FavoriteTvShowEntity =
            FavoriteTvShowEntity(
                    id = input.id,
                    title = input.title,
                    tagline = input.tagline,
                    releaseYear = input.releaseYear,
                    runtime = input.runtime,
                    voteAverage = input.voteAverage,
                    description = input.description,
                    posterPath = input.posterPath,
                    backdropPath = input.backdropPath,
            )

    fun mapFavoriteMovieDomainToEntity(input: FavoriteMovieDomain): FavoriteMovieEntity =
            FavoriteMovieEntity(
                    id = input.id,
                    title = input.title,
                    tagline = input.tagline,
                    releaseYear = input.releaseYear,
                    runtime = input.runtime,
                    voteAverage = input.voteAverage,
                    description = input.description,
                    posterPath = input.posterPath,
                    backdropPath = input.backdropPath,
            )

    fun mapFavoriteTvShowDomainToEntity(input: FavoriteTvShowDomain): FavoriteTvShowEntity =
            FavoriteTvShowEntity(
                    id = input.id,
                    title = input.title,
                    tagline = input.tagline,
                    releaseYear = input.releaseYear,
                    runtime = input.runtime,
                    voteAverage = input.voteAverage,
                    description = input.description,
                    posterPath = input.posterPath,
                    backdropPath = input.backdropPath,
            )
}