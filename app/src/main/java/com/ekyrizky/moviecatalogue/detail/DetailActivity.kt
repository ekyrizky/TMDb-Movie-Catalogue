package com.ekyrizky.moviecatalogue.detail

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig.BASE_IMG
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.ui.ViewModelFactory
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils.getDateConverted
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils.getRuntimeConverted
import com.ekyrizky.moviecatalogue.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CONTENT = "extra_content"
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        showLoading(true)

        initActionBar()

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val dataId = extras.getString(EXTRA_ID)
            val dataCategory = extras.getString(EXTRA_CONTENT)

            if (dataId != null && dataCategory != null) {
                viewModel.setContent(dataId, dataCategory.toString())

                when (dataCategory) {
                    EXTRA_MOVIE  -> {
                        observeMovie()
                    }
                    EXTRA_TVSHOW -> {
                        observeTvShow()
                    }
                }
            }
        }
    }

    private fun initActionBar() {
        setSupportActionBar(detailBinding.toolbar)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        detailBinding.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
    }

    private fun observeMovie() {
        viewModel.getMovieDetail().observe(this, { detail ->
            when (detail) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    if (detail.data != null) {
                        showLoading(false)
                        loadMovie(detail.data)
                        setFavoriteState(detail.data.isFavorite)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun observeTvShow() {
        viewModel.getTvShowDetail().observe(this, { detail ->
            when (detail) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    if (detail.data != null) {
                        showLoading(false)
                        loadTvShow(detail.data)
                        setFavoriteState(detail.data.isFavorite)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loadMovie(movie: MovieDomain) {
        detailBinding.collapsingToolbar.title = movie.title
        detailBinding.tvTitle.text = movie.title
        detailBinding.tvReleaseYear.text = getDateConverted(movie.releaseYear)
        detailBinding.tvRuntime.text = getRuntimeConverted(movie.runtime)
        detailBinding.tvTagline.text = movie.tagline
        detailBinding.tvVoteAverage.text = movie.voteAverage.toString()
        detailBinding.tvDescription.text = movie.description
        Glide.with(this)
                .load("$BASE_IMG${movie.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailBinding.imgPoster)
        Glide.with(this)
                .load("$BASE_IMG${movie.backdropPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailBinding.imgBackdrop)

        showLoading(false)
        detailBinding.fabFavorite.setOnClickListener {
            setFavoriteMovie(movie)
        }
    }

    private fun loadTvShow(tvShow: TvShowDomain) {
        detailBinding.collapsingToolbar.title = tvShow.title
        detailBinding.tvTitle.text = tvShow.title
        detailBinding.tvReleaseYear.text = getDateConverted(tvShow.releaseYear)
        detailBinding.tvRuntime.text = getRuntimeConverted(tvShow.runtime)
        detailBinding.tvTagline.text = tvShow.tagline
        detailBinding.tvVoteAverage.text = tvShow.voteAverage.toString()
        detailBinding.tvDescription.text = tvShow.description
        Glide.with(this)
                .load("$BASE_IMG${tvShow.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailBinding.imgPoster)
        Glide.with(this)
                .load("$BASE_IMG${tvShow.backdropPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailBinding.imgBackdrop)

        showLoading(false)
        detailBinding.fabFavorite.setOnClickListener {
            setFavoriteTvShow(tvShow)
        }
    }

    private fun setFavoriteState(state: Boolean) {
        val fab = detailBinding.fabFavorite
        if (state) {
            fab.setImageResource(R.drawable.ic_favorite)
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun setFavoriteMovie(movie: MovieDomain?) {
        if (movie != null) {
            if (movie.isFavorite) {
                showSnackBar("${movie.title} Removed from favorite")
            } else {
                showSnackBar("${movie.title} Added to favorite")
            }
            viewModel.setFavoriteMovie()
        }
    }

    private fun setFavoriteTvShow(tvShow: TvShowDomain?) {
        if (tvShow != null) {
            if (tvShow.isFavorite){
                showSnackBar("${tvShow.title} Removed from favorite")
            } else {
                showSnackBar("${tvShow.title} Added to favorite")
            }
            viewModel.setFavoriteTvShow()
        }
    }

    private fun showLoading(state: Boolean) {
        detailBinding.progresBar.isVisible = state
        detailBinding.scrollLayout.isVisible = !state
        detailBinding.appbar.isVisible = !state
        detailBinding.fabFavorite.isVisible = !state
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

    private fun showSnackBar(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }
}

