package com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieDetailEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "tagline")
    var tagline: String?,

    @ColumnInfo(name = "release_year")
    var releaseYear: String?,

    @ColumnInfo(name = "runtime")
    var runtime: Int?,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double?,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "poster_path")
    var posterPath: String?,

    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String?,
)