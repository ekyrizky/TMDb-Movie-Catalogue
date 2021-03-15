package com.ekyrizky.core.data.source.local

import com.ekyrizky.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.core.data.source.local.entity.movie.MovieDetailEntity
import com.ekyrizky.core.data.source.local.entity.movie.MovieEntity
import com.ekyrizky.core.data.source.local.entity.movie.PopularMovieEntity
import com.ekyrizky.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.core.data.source.local.entity.tvshow.PopularTvShowEntity
import com.ekyrizky.core.data.source.local.entity.tvshow.TvShowDetailEntity
import com.ekyrizky.core.data.source.local.entity.tvshow.TvShowEntity
import com.ekyrizky.core.data.source.local.room.MovieDao
import com.ekyrizky.core.data.source.local.room.TvShowDao
import com.ekyrizky.core.utils.SortUtils.MOVIE
import com.ekyrizky.core.utils.SortUtils.TV_SHOW
import com.ekyrizky.core.utils.SortUtils.getSortedQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val mMovieDao: MovieDao,
    private val mTvShowDao: TvShowDao,
) {

    fun getMovies(sort: String): Flow<List<MovieEntity>> = mMovieDao.getMovies(getSortedQuery(sort, MOVIE))

    fun getPopularMovies(): Flow<List<PopularMovieEntity>> = mMovieDao.getPopularMovies()

    fun getMovieById(id: Int): Flow<MovieDetailEntity> = mMovieDao.getMovieById(id)

    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>> = mMovieDao.getFavoriteMovies()

    suspend fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovies(movies)

    suspend fun insertPopularMovies(movies: List<PopularMovieEntity>) = mMovieDao.insertPopularMovies(movies)

    suspend fun insertMovieDetail(movies: MovieDetailEntity) = mMovieDao.insertMovieDetail(movies)

    suspend fun insertFavoriteMovie(movies: FavoriteMovieEntity) = mMovieDao.insertFavoriteMovie(movies)

    suspend fun checkFavoriteMovie(id: Int): Boolean = mMovieDao.checkFavoriteMovie(id)

    suspend fun deleteFavoriteMovieById(id: Int) = mMovieDao.deleteFavoriteMovieById(id)

    suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovieEntity) = mMovieDao.deleteFavoriteMovie(favoriteMovie)

    fun getTvShows(sort: String): Flow<List<TvShowEntity>> = mTvShowDao.getTvShows(getSortedQuery(sort, TV_SHOW))

    fun getPopularTvShows(): Flow<List<PopularTvShowEntity>> = mTvShowDao.getPopularTvShows()

    fun getTvShowById(id: Int): Flow<TvShowDetailEntity> = mTvShowDao.getTvShowById(id)

    fun getFavoriteTvShows(): Flow<List<FavoriteTvShowEntity>> = mTvShowDao.getFavoriteTvShows()

    suspend fun insertTvShows(tvShows: List<TvShowEntity>) = mTvShowDao.insertTvShows(tvShows)

    suspend fun insertPopularTvShows(tvShows: List<PopularTvShowEntity>) = mTvShowDao.insertPopularTvShows(tvShows)

    suspend fun insertTvShowDetail(tvShows: TvShowDetailEntity) = mTvShowDao.insertTvShowDetail(tvShows)

    suspend fun insertFavoriteTvShow(tvShows: FavoriteTvShowEntity) = mTvShowDao.insertFavoriteTvShow(tvShows)

    suspend fun checkFavoriteTvShow(id: Int): Boolean = mTvShowDao.checkFavoriteTvShow(id)

    suspend fun deleteFavoriteTvShowById(id: Int) = mTvShowDao.deleteFavoriteTvShowById(id)

    suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) = mTvShowDao.deleteFavoriteTvShow(favoriteTvShow)
}