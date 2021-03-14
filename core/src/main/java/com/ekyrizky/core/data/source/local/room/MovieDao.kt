package com.ekyrizky.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.ekyrizky.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.core.data.source.local.entity.movie.MovieDetailEntity
import com.ekyrizky.core.data.source.local.entity.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SimpleSQLiteQuery): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_detail WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieDetailEntity>

    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movies: MovieDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movies: FavoriteMovieEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite_movies WHERE id =:id)")
    suspend fun checkFavoriteMovie(id: Int): Boolean

    @Query("DELETE FROM favorite_movies WHERE id =:id")
    suspend fun deleteFavoriteMovieById(id: Int)

    @Delete
    suspend fun deleteFavoriteMovie(favoriteMoviesEntity: FavoriteMovieEntity)
}