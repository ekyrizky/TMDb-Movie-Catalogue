package com.ekyrizky.moviecatalogue.ui.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse
import com.ekyrizky.moviecatalogue.databinding.ActivityDetailBinding
import com.ekyrizky.moviecatalogue.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CONTENT = "extra_content"
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    private lateinit var detailBinding: ActivityDetailBinding
    private val imgSize = "w500"
    private val imgUrl = BuildConfig.BASE_IMG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        initActionBar()
        showLoading(true)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this@DetailActivity, factory)[DetailViewModel::class.java]

        val id = intent.getIntExtra(EXTRA_CONTENT, 0)
        val type = intent.getStringExtra(EXTRA_TYPE)
        when  (type.equals(EXTRA_MOVIE, ignoreCase = true)) {
            true -> {
                viewModel.getDetailMovie(id).observe(this, {
                    it?.let {
                        loadMovie(it)
                        showLoading(false)
                    }
                })
            }
            false -> {
                viewModel.getDetailTvShow(id).observe(this, {
                    it?.let {
                        loadTvShow(it)
                        showLoading(false)
                    }
                })
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            detailBinding.progresBar.visibility = View.VISIBLE
            detailBinding.scrollLayout.visibility = View.GONE
            detailBinding.appbar.visibility = View.GONE
        } else {
            detailBinding.progresBar.visibility = View.GONE
            detailBinding.scrollLayout.visibility = View.VISIBLE
            detailBinding.appbar.visibility = View.VISIBLE
        }
    }

    private fun initActionBar() {
        setSupportActionBar(detailBinding.toolbar)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        detailBinding.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
    }

    private fun dateConverter(date: String): String{
        val apiDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val formatDate = SimpleDateFormat("yyyy", Locale.getDefault())
        val year = apiDate.parse(date)
        return formatDate.format(year!!)
    }

    private fun loadMovie(movieResponse: MovieResponse) {
        detailBinding.toolbar.title = movieResponse.originalTitle
        detailBinding.tvTitle.text = movieResponse.originalTitle
        detailBinding.tvReleaseYear.text = dateConverter(movieResponse.releaseDate)
        detailBinding.tvRuntime.text = getString(R.string.tv_runtime, movieResponse.runtime)
        detailBinding.tvTagline.text = movieResponse.tagline
        detailBinding.tvVoteAverage.text = movieResponse.voteAverage.toString()
        detailBinding.tvDescription.text = movieResponse.overview
        Glide.with(this)
                .load("$imgUrl$imgSize${movieResponse.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailBinding.imgPoster)
        Glide.with(this)
                .load("$imgUrl$imgSize${movieResponse.backdropPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailBinding.imgBackdrop)
    }

    private fun loadTvShow(tvShowResponse: TvShowResponse) {
        detailBinding.toolbar.title = tvShowResponse.originalName
        detailBinding.tvTitle.text = tvShowResponse.originalName
        detailBinding.tvReleaseYear.text = dateConverter(tvShowResponse.firstAirDate.toString())
        detailBinding.tvRuntime.text = getString(R.string.tv_runtime, tvShowResponse.episodeRunTime?.get(0))
        detailBinding.tvTagline.text = tvShowResponse.tagline
        detailBinding.tvVoteAverage.text = tvShowResponse.voteAverage.toString()
        detailBinding.tvDescription.text = tvShowResponse.overview
        Glide.with(this)
                .load("$imgUrl$imgSize${tvShowResponse.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailBinding.imgPoster)
        Glide.with(this)
                .load("$imgUrl$imgSize${tvShowResponse.backdropPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailBinding.imgBackdrop)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share) {
            val mimeType = "text/plain"
            val shareObject = detailBinding.tvTitle.text
            ShareCompat.IntentBuilder.from(this)
                    .setType(mimeType)
                    .setChooserTitle(R.string.share_title)
                    .setText("Segera tonton $shareObject di bioskiop kesayangan anda!")
                    .startChooser()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}