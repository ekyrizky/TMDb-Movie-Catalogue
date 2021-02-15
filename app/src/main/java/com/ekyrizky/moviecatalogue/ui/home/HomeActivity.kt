package com.ekyrizky.moviecatalogue.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer
import com.ekyrizky.moviecatalogue.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        supportActionBar?.elevation = 0f

        initViewPager()
    }

    private fun  initViewPager() {
        val sectionPageAdapter = SectionPageAdapter(this, supportFragmentManager)
        homeBinding.viewPager.adapter = sectionPageAdapter
        homeBinding.tabs.setupWithViewPager(homeBinding.viewPager)
        homeBinding.viewPager.setPageTransformer(true, CubeOutTransformer())
    }
}