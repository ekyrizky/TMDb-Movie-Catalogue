package com.ekyrizky.moviecatalogue.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val NEWEST = "newest"
    const val HIGHEST_VOTE = "highest_vote"
    const val MOVIE = "movies"
    const val TV_SHOW = "tv_shows"

    fun getSortedQuery(filter: String, table_name: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table_name ")
        when (filter) {
            NEWEST -> simpleQuery.append("ORDER BY release_year DESC")
            HIGHEST_VOTE -> simpleQuery.append("ORDER BY vote_average DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}