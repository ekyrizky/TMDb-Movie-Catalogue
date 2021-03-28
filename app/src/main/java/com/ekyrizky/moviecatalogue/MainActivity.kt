package com.ekyrizky.moviecatalogue

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ekyrizky.moviecatalogue.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_favorite -> {
                    val uriNav = Uri.parse("mviecatalogue://favorite")
                    navController.navigate(uriNav)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_movie_detail)
            {
                binding.navView.visibility = View.GONE
                binding.tvTitle.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
                binding.tvTitle.visibility = View.VISIBLE
            }
        }

        binding.navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (isTaskRoot
                && navHostFragment.childFragmentManager.backStackEntryCount == 0
                && supportFragmentManager.backStackEntryCount == 0
        ) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

}