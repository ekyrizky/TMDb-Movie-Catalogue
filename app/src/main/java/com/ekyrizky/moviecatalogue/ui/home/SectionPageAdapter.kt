package com.ekyrizky.moviecatalogue.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.ui.content.movie.MovieFragment
import com.ekyrizky.moviecatalogue.ui.content.tvshow.TvShowFragment

class SectionPageAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie, R.string.tv_show)
    }

    private val pages = listOf(
        MovieFragment(),
        TvShowFragment()
    )

    override fun getPageTitle(position: Int): CharSequence =
        mContext.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = pages.size

    override fun getItem(position: Int): Fragment = pages[position]
}