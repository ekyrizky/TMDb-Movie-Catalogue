package com.ekyrizky.moviecatalogue.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val TITLE_ASC = "title_asc"
    const val TITLE_DESC = "title_desc"
    const val HIGHEST_VOTE = "highest_vote"
    const val LOWEST_VOTE = "lowest_vote"
    const val MOVIE = "movies"
    const val TV_SHOW = "tv_shows"

    fun getSortedQuery(filter: String, table_name: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table_name ")
        when (filter) {
            TITLE_ASC -> simpleQuery.append("ORDER BY title ASC")
            TITLE_DESC -> simpleQuery.append("ORDER BY title DESC")
            HIGHEST_VOTE -> simpleQuery.append("ORDER BY vote_average DESC")
            LOWEST_VOTE -> simpleQuery.append("ORDER BY vote_average ASC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}