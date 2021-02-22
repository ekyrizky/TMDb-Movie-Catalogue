package com.ekyrizky.moviecatalogue.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val NEWEST = "newest"
    const val HIGHEST_VOTE = "highest_vote"
    const val LONGEST_DURATION = "longest_duration"
    const val MOVIE = "movie_entities"
    const val TV_SHOW = "tv_show_entities"

    fun getSortedQuery(filter: String, table_name: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table_name ")
        when (filter) {
            NEWEST -> simpleQuery.append("ORDER BY release_year DESC")
            HIGHEST_VOTE -> simpleQuery.append("ORDER BY vote_average DESC")
            LONGEST_DURATION -> simpleQuery.append(("ORDER BY runtime DESC"))
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}