package com.ekyrizky.moviecatalogue.ui.home.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.databinding.FragmentFavoriteBinding
import com.ekyrizky.moviecatalogue.ui.home.favorite.movie.FavoriteMovieFragment
import com.ekyrizky.moviecatalogue.ui.home.favorite.tvshow.FavoriteTvShowFragment
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {
    private var _favoriteFragmentBinding: FragmentFavoriteBinding? = null
    private val binding get() = _favoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _favoriteFragmentBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            initViewPager()
        }
    }

    private fun  initViewPager() {
        val fragmentList = listOf(FavoriteMovieFragment(), FavoriteTvShowFragment())
        val tabTitle = listOf(resources.getString(R.string.movie), resources.getString(R.string.tv_show))

        binding?.viewPager?.adapter = FavoritePageAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        TabLayoutMediator(binding!!.tabs, binding!!.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _favoriteFragmentBinding = null
    }
}