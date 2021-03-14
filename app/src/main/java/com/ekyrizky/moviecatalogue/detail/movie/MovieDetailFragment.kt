package com.ekyrizky.moviecatalogue.detail.movie

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.core.BuildConfig.BASE_IMG
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.utils.ConvertUtils
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.databinding.FragmentMovieDetailBinding
import com.ekyrizky.moviecatalogue.model.movie.MovieDetail
import com.ekyrizky.moviecatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()

    private var _fragmentMovieDetailBinding: FragmentMovieDetailBinding? = null
    private val binding get() = _fragmentMovieDetailBinding

    private lateinit var id: String
    private var statusFavorite = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieDetailBinding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        initActionBar()
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = MovieDetailFragmentArgs.fromBundle(arguments as Bundle).movieId

        lifecycleScope.launch {
            val checkFavorite = viewModel.checkFavoriteMovie(id.toInt())
            statusFavorite = checkFavorite
        }

        observeMovie()
    }

    private fun observeMovie() {
        viewModel.getMovieDetail(id).observe(viewLifecycleOwner) { detail ->
            when (detail) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    loadMovie(detail.data)
                    setFavoriteState(statusFavorite)
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadMovie(movie: MovieDetail?) {
        binding?.apply {
            collapsingToolbar.title = movie?.title
            tvTitle.text = movie?.title
            tvReleaseYear.text = movie?.releaseYear?.let { ConvertUtils.getDateConverted(it) }
            tvRuntime.text = movie?.runtime?.let { ConvertUtils.getRuntimeConverted(it) }
            tvTagline.text = movie?.tagline
            tvVoteAverage.text = movie?.voteAverage.toString()
            tvDescription.text = movie?.description
        }
        binding?.imgPoster?.let {
            Glide.with(this)
                .load("${BASE_IMG}${movie?.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(it)
        }
        binding?.imgBackdrop?.let {
            Glide.with(this)
                .load("${BASE_IMG}${movie?.backdropPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(it)
        }
        showLoading(false)
        binding?.fabFavorite?.setOnClickListener {
            setFavoriteMovie(movie)
        }
    }

    private fun setFavoriteMovie(movie: MovieDetail?) {
        if (movie != null){
            if (statusFavorite) {
                movie.id?.let { viewModel.deleteFavoriteMovieById(it) }
                Toast.makeText(context, R.string.remove_favorite, Toast.LENGTH_SHORT).show()
                setFavoriteState(!statusFavorite)
            } else {
                val movieDomain = DataMapper.mapMovieDetailToMovieDetailDomain(movie)
                viewModel.insertFavoriteMovie(movieDomain)
                Toast.makeText(context, R.string.add_favorite, Toast.LENGTH_SHORT).show()
                setFavoriteState(!statusFavorite)
            }
        }
        statusFavorite = !statusFavorite
    }

    private fun setFavoriteState(state: Boolean) {
        val fab = binding?.fabFavorite
        if (state) {
            fab?.setImageResource(R.drawable.ic_favorite)
        } else {
            fab?.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun initActionBar() {
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.elevation = 0f
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbar?.setNavigationOnClickListener{ this.activity?.onBackPressed() }
        binding?.collapsingToolbar?.setExpandedTitleColor(Color.TRANSPARENT)
    }

    private fun showLoading(state: Boolean) {
        binding?.apply {
            progresBar.isVisible = state
            scrollLayout.isVisible = !state
            appbar.isVisible = !state
            fabFavorite.isVisible = !state
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share) {
            val mimeType = "text/plain"
            val shareObject = binding?.tvTitle?.text
            ShareCompat.IntentBuilder.from(requireActivity())
                .setType(mimeType)
                .setChooserTitle(R.string.share_title)
                .setText("Watch $shareObject in your favorite cinema!")
                .startChooser()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMovieDetailBinding = null
    }
}