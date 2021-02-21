package com.ekyrizky.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ekyrizky.moviecatalogue.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.vo.Resource

interface ContentDataSource {

    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)

    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean)
}