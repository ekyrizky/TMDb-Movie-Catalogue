package com.ekyrizky.moviecatalogue.detail.tvshow

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.MyApplication
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.moviecatalogue.core.ui.ViewModelFactory
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils
import com.ekyrizky.moviecatalogue.databinding.FragmentTvShowDetailBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowDetailFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: TvShowDetailViewModel by viewModels { factory }
    private var _fragmentTvShowDetailBinding: FragmentTvShowDetailBinding? = null
    private val binding get() = _fragmentTvShowDetailBinding

    private lateinit var id: String
    private var statusFavorite = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragmentTvShowDetailBinding = FragmentTvShowDetailBinding.inflate(layoutInflater, container, false)
        initActionBar()
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = TvShowDetailFragmentArgs.fromBundle(arguments as Bundle).tvShowId

        lifecycleScope.launch {
            val checkFavorite = viewModel.checkFavoriteTvShow(id.toInt())
            statusFavorite = checkFavorite
        }

        observeTvShow()
    }

    private fun observeTvShow() {
        viewModel.getTvShowDetail(id).observe(viewLifecycleOwner, { detail ->
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
                    Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loadTvShow(tvShow: TvShowDetailDomain) {
        binding?.collapsingToolbar?.title = tvShow.title
        binding?.tvTitle?.text = tvShow.title
        binding?.tvReleaseYear?.text = tvShow.releaseYear?.let { ConvertUtils.getDateConverted(it) }
        binding?.tvRuntime?.text = tvShow.runtime?.let { ConvertUtils.getRuntimeConverted(it) }
        binding?.tvTagline?.text = tvShow.tagline
        binding?.tvVoteAverage?.text = tvShow.voteAverage.toString()
        binding?.tvDescription?.text = tvShow.description
        binding?.imgPoster?.let {
            Glide.with(this)
                .load("${BuildConfig.BASE_IMG}${tvShow.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(it)
        }
        binding?.imgBackdrop?.let {
            Glide.with(this)
                .load("${BuildConfig.BASE_IMG}${tvShow.backdropPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(it)
        }
        showLoading(false)
        binding?.fabFavorite?.setOnClickListener {
            setFavoriteMovie(tvShow)
        }
    }

    private fun setFavoriteMovie(movie: TvShowDetailDomain?) {
        if (movie != null){
            if (statusFavorite) {
                movie.id?.let { viewModel.deleteFavoriteTvShowById(it) }
                Toast.makeText(context, R.string.remove_favorite, Toast.LENGTH_SHORT).show()
                setFavoriteState(!statusFavorite)
            } else {
                viewModel.insertFavoriteTvShow(movie)
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
        _fragmentTvShowDetailBinding = null
    }
}