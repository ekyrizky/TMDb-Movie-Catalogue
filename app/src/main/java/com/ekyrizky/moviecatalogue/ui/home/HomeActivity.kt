package com.ekyrizky.moviecatalogue.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.databinding.ActivityHomeBinding
import com.ekyrizky.moviecatalogue.ui.home.favorite.FavoriteFragment
import com.ekyrizky.moviecatalogue.ui.home.movie.MovieFragment
import com.ekyrizky.moviecatalogue.ui.home.tvshow.TvShowFragment


class HomeActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityHomeBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, MovieFragment())
                    .commit()
            binding?.bottomChip?.setItemSelected(R.id.nav_movie)
        }

        setBottomNav()
    }

    private fun setBottomNav() {


        binding?.bottomChip?.setOnItemSelectedListener { id ->
            when (id) {
                R.id.nav_movie -> fragment = MovieFragment()
                R.id.nav_tvshow -> fragment = TvShowFragment()
                R.id.nav_favorite -> fragment = FavoriteFragment()
            }

            fragment.let {
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment)
                        .commit()
            }
        }
    }
}