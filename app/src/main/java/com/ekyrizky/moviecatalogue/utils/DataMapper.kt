package com.ekyrizky.moviecatalogue.utils

import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.core.domain.model.movie.MovieDomain
import com.ekyrizky.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.model.movie.FavoriteMovie
import com.ekyrizky.moviecatalogue.model.movie.Movie
import com.ekyrizky.moviecatalogue.model.movie.MovieDetail
import com.ekyrizky.moviecatalogue.model.tvshow.FavoriteTvShow
import com.ekyrizky.moviecatalogue.model.tvshow.TvShow
import com.ekyrizky.moviecatalogue.model.tvshow.TvShowDetail

object DataMapper {

    fun mapMovieDomainToMovie(input: List<MovieDomain>?): List<Movie> {
        val movieList = ArrayList<Movie>()

        input?.map {
            val movies = Movie(
                id = it.id,
                title = it.title,
                releaseYear = it.releaseYear,
                voteAverage = it.voteAverage,
                description = it.description,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapMovieToMovieDomain(input: List<Movie>?): List<MovieDomain> {
        val movieList = ArrayList<MovieDomain>()

        input?.map {
            val movies = MovieDomain(
                id = it.id,
                title = it.title,
                releaseYear = it.releaseYear,
                voteAverage = it.voteAverage,
                description = it.description,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapTvShowDomainToTvShow(input: List<TvShowDomain>?):List<TvShow> {
        val tvShowList = ArrayList<TvShow>()

        input?.map {
            val tvShow = TvShow(
                id = it.id,
                title = it.title,
                releaseYear = it.releaseYear,
                voteAverage = it.voteAverage,
                description = it.description,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapTvShowToTvShowDomain(input: List<TvShow>?):List<TvShowDomain> {
        val tvShowList = ArrayList<TvShowDomain>()

        input?.map {
            val tvShow = TvShowDomain(
                id = it.id,
                title = it.title,
                releaseYear = it.releaseYear,
                voteAverage = it.voteAverage,
                description = it.description,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapMovieDetailDomainToMovieDetail(input: MovieDetailDomain?): MovieDetail =
        MovieDetail(
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

    fun mapMovieDetailToMovieDetailDomain(input: MovieDetail?): MovieDetailDomain =
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

    fun mapTvShowDetailDomainToTvShowDetail(input: TvShowDetailDomain?): TvShowDetail =
        TvShowDetail(
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

    fun mapTvShowDetailToTvShowDetailDomain(input: TvShowDetail): TvShowDetailDomain =
        TvShowDetailDomain(
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