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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig.BASE_IMG
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.moviecatalogue.core.ui.ViewModelFactory
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils.getDateConverted
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils.getRuntimeConverted
import com.ekyrizky.moviecatalogue.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CONTENT = "extra_content"
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding

    private lateinit var viewModel: DetailViewModel
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

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
                        lifecycleScope.launch {
                            val checkFavorite = viewModel.checkFavoriteMovie(dataId.toInt())
                            statusFavorite = checkFavorite
                        }
                        observeMovie()
                    }
                    EXTRA_TVSHOW -> {
                        lifecycleScope.launch {
                            val checkFavorite = viewModel.checkFavoriteTvShow(dataId.toInt())
                            statusFavorite = checkFavorite
                        }
                        observeTvShow()
                    }
                }
            }
        }
    }

    private fun initActionBar() {
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        binding?.collapsingToolbar?.setExpandedTitleColor(Color.TRANSPARENT)
    }

    private fun observeMovie() {
        viewModel.getMovieDetail().observe(this, { detail ->
            when (detail) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    if (detail.data != null) {
                        showLoading(false)
                        loadMovie(detail.data)
                        setFavoriteState(statusFavorite)
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
                        setFavoriteState(statusFavorite)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loadMovie(movie: MovieDetailDomain) {
        binding?.collapsingToolbar?.title = movie.title
        binding?.tvTitle?.text = movie.title
        binding?.tvReleaseYear?.text = movie.releaseYear?.let { getDateConverted(it) }
        binding?.tvRuntime?.text = movie.runtime?.let { getRuntimeConverted(it) }
        binding?.tvTagline?.text = movie.tagline
        binding?.tvVoteAverage?.text = movie.voteAverage.toString()
        binding?.tvDescription?.text = movie.description
        binding?.imgPoster?.let {
            Glide.with(this)
                .load("$BASE_IMG${movie.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(it)
        }
        binding?.imgBackdrop?.let {
            Glide.with(this)
                .load("$BASE_IMG${movie.backdropPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(it)
        }

        showLoading(false)
        binding?.fabFavorite?.setOnClickListener {
            setFavoriteMovie(movie)
        }
    }

    private fun loadTvShow(tvShow: TvShowDetailDomain) {
        binding?.collapsingToolbar?.title = tvShow.title
        binding?.tvTitle?.text = tvShow.title
        binding?.tvReleaseYear?.text = tvShow.releaseYear?.let { getDateConverted(it) }
        binding?.tvRuntime?.text = tvShow.runtime?.let { getRuntimeConverted(it) }
        binding?.tvTagline?.text = tvShow.tagline
        binding?.tvVoteAverage?.text = tvShow.voteAverage.toString()
        binding?.tvDescription?.text = tvShow.description
        binding?.imgPoster?.let {
            Glide.with(this)
                .load("$BASE_IMG${tvShow.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(it)
        }
        binding?.imgBackdrop?.let {
            Glide.with(this)
                .load("$BASE_IMG${tvShow.backdropPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(it)
        }

        showLoading(false)
        binding?.fabFavorite?.setOnClickListener {
            setFavoriteTvShow(tvShow)
        }
    }

    private fun setFavoriteState(state: Boolean) {
        val fab = binding?.fabFavorite
        if (state) {
            fab?.setImageResource(R.drawable.ic_favorite)
        } else {
            fab?.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun setFavoriteMovie(movie: MovieDetailDomain?) {
        if (movie != null){
            if (statusFavorite) {
                movie.id?.let { viewModel.deleteFavoriteMovieById(it) }
                showSnackBar("${movie.title} Removed from favorite")
                setFavoriteState(!statusFavorite)
            } else {
                viewModel.insertFavoriteMovie(movie)
                showSnackBar("${movie.title} Added to favorite")
                setFavoriteState(!statusFavorite)
            }
        }
        statusFavorite = !statusFavorite
    }

    private fun setFavoriteTvShow(tvShow: TvShowDetailDomain?) {
        if (tvShow != null) {
            if (statusFavorite) {
                tvShow.id?.let { viewModel.deleteFavoriteTvShowById(it) }
                showSnackBar("${tvShow.title} Removed from favorite")
                setFavoriteState(!statusFavorite)
            } else {
                viewModel.insertFavoriteTvShow(tvShow)
                showSnackBar("${tvShow.title} Added to favorite")
                setFavoriteState(!statusFavorite)
            }
        }
        statusFavorite = !statusFavorite
    }

    private fun showLoading(state: Boolean) {
        binding?.progresBar?.isVisible = state
        binding?.scrollLayout?.isVisible = !state
        binding?.appbar?.isVisible = !state
        binding?.fabFavorite?.isVisible = !state
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share) {
            val mimeType = "text/plain"
            val shareObject = binding?.tvTitle?.text
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

