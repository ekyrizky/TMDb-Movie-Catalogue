package com.ekyrizky.moviecatalogue.detail.movie

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.moviecatalogue.core.ui.ViewModelFactory
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils
import com.ekyrizky.moviecatalogue.databinding.FragmentMovieDetailBinding
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment() {

    private var _fragmentMovieDetailBinding: FragmentMovieDetailBinding? = null
    private val binding get() = _fragmentMovieDetailBinding

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var id: String
    private var statusFavorite = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragmentMovieDetailBinding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        initActionBar()
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

        id = MovieDetailFragmentArgs.fromBundle(arguments as Bundle).movieId

        lifecycleScope.launch {
            val checkFavorite = viewModel.checkFavoriteMovie(id.toInt())
            statusFavorite = checkFavorite
        }

        observeMovie()
    }

    private fun observeMovie() {
        viewModel.getMovieDetail(id).observe(viewLifecycleOwner, { detail ->
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
                    Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loadMovie(movie: MovieDetailDomain) {
        binding?.collapsingToolbar?.title = movie.title
        binding?.tvTitle?.text = movie.title
        binding?.tvReleaseYear?.text = movie.releaseYear?.let { ConvertUtils.getDateConverted(it) }
        binding?.tvRuntime?.text = movie.runtime?.let { ConvertUtils.getRuntimeConverted(it) }
        binding?.tvTagline?.text = movie.tagline
        binding?.tvVoteAverage?.text = movie.voteAverage.toString()
        binding?.tvDescription?.text = movie.description
        binding?.imgPoster?.let {
            Glide.with(this)
                    .load("${BuildConfig.BASE_IMG}${movie.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(it)
        }
        binding?.imgBackdrop?.let {
            Glide.with(this)
                    .load("${BuildConfig.BASE_IMG}${movie.backdropPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(it)
        }
        showLoading(false)
        binding?.fabFavorite?.setOnClickListener {
            setFavoriteMovie(movie)
        }
    }

    private fun setFavoriteMovie(movie: MovieDetailDomain?) {
        if (movie != null){
            if (statusFavorite) {
                movie.id?.let { viewModel.deleteFavoriteMovieById(it) }
                Toast.makeText(context, R.string.remove_favorite, Toast.LENGTH_SHORT).show()
                setFavoriteState(!statusFavorite)
            } else {
                viewModel.insertFavoriteMovie(movie)
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
        (activity as AppCompatActivity?)?.setSupportActionBar(binding?.toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.elevation = 0f
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbar?.setNavigationOnClickListener{ this.activity?.onBackPressed() }
        binding?.collapsingToolbar?.setExpandedTitleColor(Color.TRANSPARENT)
    }

    private fun showLoading(state: Boolean) {
        binding?.progresBar?.isVisible = state
        binding?.scrollLayout?.isVisible = !state
        binding?.appbar?.isVisible = !state
        binding?.fabFavorite?.isVisible = !state
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
                .setText("Segera tonton $shareObject di bioskiop kesayangan anda!")
                .startChooser()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMovieDetailBinding = null
    }
}