package com.ekyrizky.moviecatalogue.core.domain.repository

import androidx.lifecycle.LiveData
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain

interface IContentRepository {

    fun getMovies(sort: String): LiveData<Resource<List<MovieDomain>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDomain>>

    fun getFavoriteMovies(): LiveData<List<MovieDomain>>

    fun setFavoriteMovie(movie: MovieDomain, state: Boolean)

    fun getTvShows(sort: String): LiveData<Resource<List<TvShowDomain>>>

    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowDomain>>

    fun getFavoriteTvShows(): LiveData<List<TvShowDomain>>

    fun setFavoriteTvShow(tvShow: TvShowDomain, state: Boolean)
}