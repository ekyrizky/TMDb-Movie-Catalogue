package com.ekyrizky.moviecatalogue

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ekyrizky.moviecatalogue.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.title = null

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_movie_detail
                || destination.id == R.id.navigation_tvshow_detail
            ) {
                binding?.navView?.visibility = View.GONE
                binding?.toolbar?.visibility = View.GONE
            } else {
                binding?.navView?.visibility = View.VISIBLE
                binding?.toolbar?.visibility = View.VISIBLE
            }
        }

        binding?.navView?.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}