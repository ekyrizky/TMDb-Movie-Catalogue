package com.ekyrizky.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ekyrizky.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.core.data.source.local.entity.movie.MovieDetailEntity
import com.ekyrizky.core.data.source.local.entity.movie.MovieEntity
import com.ekyrizky.core.data.source.local.entity.movie.PopularMovieEntity

@Database(entities = [MovieEntity::class, PopularMovieEntity::class,
    MovieDetailEntity::class, FavoriteMovieEntity::class,], version = 1,
    exportSchema = false)
abstract class ContentDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}