package com.ekyrizky.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.ekyrizky.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.core.data.source.local.entity.tvshow.PopularTvShowEntity
import com.ekyrizky.core.data.source.local.entity.tvshow.TvShowDetailEntity
import com.ekyrizky.core.data.source.local.entity.tvshow.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShows(query: SimpleSQLiteQuery): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM popular_tv_shows")
    fun getPopularTvShows(): Flow<List<PopularTvShowEntity>>

    @Query("SELECT * FROM tv_show_detail WHERE id = :id")
    fun getTvShowById(id: Int): Flow<TvShowDetailEntity>

    @Query("SELECT * FROM favorite_tv_show")
    fun getFavoriteTvShows(): Flow<List<FavoriteTvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(shows: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularTvShows(shows: List<PopularTvShowEntity>)

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
}