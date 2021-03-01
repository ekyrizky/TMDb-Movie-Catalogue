package com.ekyrizky.moviecatalogue.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.MovieDetailEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.TvShowDetailEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {

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

    @Query("DELETE from favorite_movies")
    suspend fun deleteAllFavoriteMovies()

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShows(query: SimpleSQLiteQuery): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tv_show_detail WHERE id = :id")
    fun getTvShowById(id: Int): Flow<TvShowDetailEntity>

    @Query("SELECT * FROM favorite_tv_show")
    fun getFavoriteTvShows(): Flow<List<FavoriteTvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(shows: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShowDetail(detailShows: TvShowDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteTvShow(favoriteShows: FavoriteTvShowEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite_tv_show WHERE id =:id)")
    suspend fun checkFavoriteTvShow(id: Int): Boolean

    @Query("DELETE from favorite_tv_show WHERE id =:id")
    suspend fun deleteFavoriteTvShowById(id: Int)

    @Delete
    suspend fun deleteFavoriteTvShow(favoriteShows: FavoriteTvShowEntity)

    @Query("DELETE from favorite_tv_show")
    suspend fun deleteAllFavoriteTvShows()
}