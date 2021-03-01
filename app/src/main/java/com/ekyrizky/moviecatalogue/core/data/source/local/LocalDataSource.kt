package com.ekyrizky.moviecatalogue.core.data.source.local

import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.MovieDetailEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.TvShowDetailEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.TvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.room.ContentDao
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.MOVIE
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.TV_SHOW
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.getSortedQuery
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val mContentDao: ContentDao) {

    companion object {
        private val INSTANCE: LocalDataSource? = null

        fun getInstance(contentDao: ContentDao): LocalDataSource =
                INSTANCE ?: LocalDataSource(contentDao)
    }

    fun getMovies(sort: String): Flow<List<MovieEntity>> = mContentDao.getMovies(getSortedQuery(sort, MOVIE))

    fun getMovieById(id: Int): Flow<MovieDetailEntity> = mContentDao.getMovieById(id)

    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>> = mContentDao.getFavoriteMovies()

    suspend fun insertMovies(movies: List<MovieEntity>) = mContentDao.insertMovies(movies)

    suspend fun insertMovieDetail(movies: MovieDetailEntity) = mContentDao.insertMovieDetail(movies)

    suspend fun insertFavoriteMovie(movies: FavoriteMovieEntity) = mContentDao.insertFavoriteMovie(movies)

    suspend fun checkFavoriteMovie(id: Int): Boolean = mContentDao.checkFavoriteMovie(id)

    suspend fun deleteFavoriteMovieById(id: Int) = mContentDao.deleteFavoriteMovieById(id)

    suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovieEntity) = mContentDao.deleteFavoriteMovie(favoriteMovie)

    suspend fun deleteAllFavoriteMovies() = mContentDao.deleteAllFavoriteMovies()

    fun getTvShows(sort: String): Flow<List<TvShowEntity>> = mContentDao.getTvShows(getSortedQuery(sort, TV_SHOW))

    fun getTvShowById(id: Int): Flow<TvShowDetailEntity> = mContentDao.getTvShowById(id)

    fun getFavoriteTvShows(): Flow<List<FavoriteTvShowEntity>> = mContentDao.getFavoriteTvShows()

    suspend fun insertTvShows(tvShows: List<TvShowEntity>) = mContentDao.insertTvShows(tvShows)

    suspend fun insertTvShowDetail(tvShows: TvShowDetailEntity) = mContentDao.insertTvShowDetail(tvShows)

    suspend fun insertFavoriteTvShow(tvShows: FavoriteTvShowEntity) = mContentDao.insertFavoriteTvShow(tvShows)

    suspend fun checkFavoriteTvShow(id: Int): Boolean = mContentDao.checkFavoriteTvShow(id)

    suspend fun deleteFavoriteTvShowById(id: Int) = mContentDao.deleteFavoriteTvShowById(id)

    suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) = mContentDao.deleteFavoriteTvShow(favoriteTvShow)

    suspend fun deleteAllFavoriteTvShows() = mContentDao.deleteAllFavoriteTvShows()
}