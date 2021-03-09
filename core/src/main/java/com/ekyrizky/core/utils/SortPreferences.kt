package com.ekyrizky.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.ekyrizky.core.utils.SortUtils.TITLE_ASC

class SortPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "sort_pref"
        private const val MENU_MOVIE = "menu_movie"
        private const val SORT_MOVIE = "sort_movie"
        private const val MENU_TVSHOW = "menu_tvshow"
        private const val SORT_TVSHOW = "sort_tvshow"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private var editor: SharedPreferences.Editor = preferences.edit()

    fun setPrefMovie(menuId: Int, sort: String) {
        editor.putInt(MENU_MOVIE, menuId)
            .putString(SORT_MOVIE, sort)
            .apply()
    }

    fun setPrefTvShow(menuId: Int, sort: String) {
        editor.putInt(MENU_TVSHOW, menuId)
            .putString(SORT_TVSHOW, sort)
            .apply()
    }

    fun getMenuMovie(): Int = preferences.getInt(MENU_MOVIE, 0)
    fun getMenuTvShow(): Int = preferences.getInt(MENU_TVSHOW, 0)

    fun getSortMovie(): String? = preferences.getString(SORT_MOVIE, TITLE_ASC)
    fun getSortTvShow(): String? = preferences.getString(SORT_TVSHOW, TITLE_ASC)
}