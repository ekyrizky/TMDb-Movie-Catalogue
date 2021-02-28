package com.ekyrizky.moviecatalogue.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class],
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