package com.ekyrizky.moviecatalogue.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.MovieDetailEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.TvShowDetailEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class,
        MovieDetailEntity::class, TvShowDetailEntity::class,
        FavoriteMovieEntity::class, FavoriteTvShowEntity::class],
        version = 1,
        exportSchema = false)
abstract class ContentDatabase: RoomDatabase() {
    abstract fun contentDao(): ContentDao

    companion object {
        @Volatile
        private var INSTANCE: ContentDatabase? = null

        fun getInstance(context: Context): ContentDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    ContentDatabase::class.java,
                    "Content.db")
                    .build()
            }
    }
}