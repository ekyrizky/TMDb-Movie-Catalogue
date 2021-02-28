package com.ekyrizky.moviecatalogue.core.data.source.local

import androidx.lifecycle.LiveData
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.room.ContentDao
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.MOVIE
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.TV_SHOW
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.getSortedQuery

class LocalDataSource (private val mContentDao: ContentDao) {

    companion object {
        private val INSTANCE: LocalDataSource? = null

        fun getInstance(contentDao: ContentDao): LocalDataSource =
                INSTANCE ?: LocalDataSource(contentDao)
    }

    fun getMovies(sort: String): LiveData<List<MovieEntity>> = mContentDao.getMovies(getSortedQuery(sort, MOVIE))

    fun getMovieById(id: Int): LiveData<MovieEntity> = mContentDao.getMovieById(id)

    fun getFavoriteMovies(): LiveData<List<MovieEntity>> = mContentDao.getFavoriteMovies()

    fun insertMovies(movies: List<MovieEntity>) = mContentDao.insertMovies(movies)

    fun updateMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        mContentDao.updateMovie(movie)
    }

    fun getTvShows(sort: String): LiveData<List<TvShowEntity>> = mContentDao.getTvShows(getSortedQuery(sort, TV_SHOW))

    fun getTvShowById(id: Int): LiveData<TvShowEntity> = mContentDao.getTvShowById(id)

    fun getFavoriteTvShows(): LiveData<List<TvShowEntity>> = mContentDao.getFavoriteTvShows()

    fun insertTvShows(tvShows: List<TvShowEntity>) = mContentDao.insertTvShows(tvShows)

    fun updateTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFavorite = newState
        mContentDao.updateTvShow(tvShow)
    }
}