package com.ekyrizky.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ekyrizky.moviecatalogue.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.data.source.local.room.ContentDao
import com.ekyrizky.moviecatalogue.utils.SortUtils.MOVIE
import com.ekyrizky.moviecatalogue.utils.SortUtils.TV_SHOW
import com.ekyrizky.moviecatalogue.utils.SortUtils.getSortedQuery

class LocalDataSource (private val mContentDao: ContentDao) {

    companion object {
        private val INSTANCE: LocalDataSource? = null

        fun getInstance(contentDao: ContentDao): LocalDataSource =
                INSTANCE ?: LocalDataSource(contentDao)
    }

    fun getMovies(sort: String): DataSource.Factory<Int, MovieEntity> = mContentDao.getMovies(getSortedQuery(sort, MOVIE))

    fun getMovieById(id: Int): LiveData<MovieEntity> = mContentDao.getMovieById(id)

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = mContentDao.getFavoriteMovies()

    fun insertMovies(movies: List<MovieEntity>) = mContentDao.insertMovies(movies)

    fun updateMovie(movie: MovieEntity, newState: Boolean) {
        movie.favorited = newState
        mContentDao.updateMovie(movie)
    }

    fun getTvShows(sort: String): DataSource.Factory<Int, TvShowEntity> = mContentDao.getTvShows(getSortedQuery(sort, TV_SHOW))

    fun getTvShowById(id: Int): LiveData<TvShowEntity> = mContentDao.getTvShowById(id)

    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> = mContentDao.getFavoriteTvShows()

    fun insertTvShows(tvShows: List<TvShowEntity>) = mContentDao.insertTvShows(tvShows)

    fun updateTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.favorited = newState
        mContentDao.updateTvShow(tvShow)
    }
}