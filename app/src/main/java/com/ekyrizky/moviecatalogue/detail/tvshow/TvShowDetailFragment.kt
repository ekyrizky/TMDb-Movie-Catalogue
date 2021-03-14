package com.ekyrizky.moviecatalogue.detail.tvshow

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
import com.ekyrizky.moviecatalogue.databinding.FragmentTvShowDetailBinding
import com.ekyrizky.moviecatalogue.model.tvshow.TvShowDetail
import com.ekyrizky.moviecatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowDetailFragment : Fragment() {

    private val viewModel: TvShowDetailViewModel by viewModels()

    private var _fragmentTvShowDetailBinding: FragmentTvShowDetailBinding? = null
    private val binding get() = _fragmentTvShowDetailBinding

    private lateinit var id: String
    private var statusFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                    showLoading(false)
                    loadTvShow(detail.data)
                    setFavoriteState(statusFavorite)
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loadTvShow(tvShow: TvShowDetail?) {
        binding?.apply {
            collapsingToolbar.title = tvShow?.title
            tvTitle.text = tvShow?.title
            tvReleaseYear.text = tvShow?.releaseYear?.let { ConvertUtils.getDateConverted(it) }
            tvRuntime.text = tvShow?.runtime?.let { ConvertUtils.getRuntimeConverted(it) }
            tvTagline.text = tvShow?.tagline
            tvVoteAverage.text = tvShow?.voteAverage.toString()
            tvDescription.text = tvShow?.description
        }
        binding?.imgPoster?.let {
            Glide.with(this)
                .load("${BASE_IMG}${tvShow?.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(it)
        }
        binding?.imgBackdrop?.let {
            Glide.with(this)
                .load("${BASE_IMG}${tvShow?.backdropPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(it)
        }
        showLoading(false)
        binding?.fabFavorite?.setOnClickListener {
            setFavoriteTvShow(tvShow)
        }
    }

    private fun setFavoriteTvShow(tvshow: TvShowDetail?) {
        if (tvshow != null){
            if (statusFavorite) {
                tvshow.id?.let { viewModel.deleteFavoriteTvShowById(it) }
                Toast.makeText(context, R.string.remove_favorite, Toast.LENGTH_SHORT).show()
                setFavoriteState(!statusFavorite)
            } else {
                val tvShowDomain = DataMapper.mapTvShowDetailToTvShowDetailDomain(tvshow)
                viewModel.insertFavoriteTvShow(tvShowDomain)
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
        _fragmentTvShowDetailBinding = null
    }
}