package com.ekyrizky.core.data.source.local

import com.ekyrizky.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.core.data.source.local.entity.movie.MovieDetailEntity
import com.ekyrizky.core.data.source.local.entity.movie.MovieEntity
import com.ekyrizky.core.data.source.local.entity.movie.PopularMovieEntity
import com.ekyrizky.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val mMovieDao: MovieDao,
) {

    fun getMovies(): Flow<List<MovieEntity>> = mMovieDao.getMovies()

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
}