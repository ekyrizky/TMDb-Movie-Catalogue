package com.ekyrizky.moviecatalogue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ekyrizky.moviecatalogue.databinding.ActivityMainBinding
import com.ekyrizky.moviecatalogue.favorite.FavoriteFragment
import com.ekyrizky.moviecatalogue.movie.MovieFragment
import com.ekyrizky.moviecatalogue.tvshow.TvShowFragment

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.title = null

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
                R.id.nav_movie -> {
                    fragment = MovieFragment()
                    setToolbarTitle(getString(R.string.popular_movies))
                }
                R.id.nav_tvshow -> {
                    fragment = TvShowFragment()
                    setToolbarTitle(getString(R.string.popular_tvshows))
                }
                R.id.nav_favorite -> {
                    fragment = FavoriteFragment()
                    setToolbarTitle(getString(R.string.my_favorite))
                }
            }

            fragment.let {
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment)
                        .commit()
            }
        }
    }

    private fun setToolbarTitle(title: String) {
        binding?.titleToolbar?.text = title
    }
}