package com.ekyrizky.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ekyrizky.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.core.data.source.local.entity.movie.MovieDetailEntity
import com.ekyrizky.core.data.source.local.entity.movie.MovieEntity
import com.ekyrizky.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.core.data.source.local.entity.tvshow.TvShowDetailEntity
import com.ekyrizky.core.data.source.local.entity.tvshow.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class,
        MovieDetailEntity::class, TvShowDetailEntity::class,
        FavoriteMovieEntity::class, FavoriteTvShowEntity::class],
        version = 1,
        exportSchema = false)
abstract class ContentDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TvShowDao
}