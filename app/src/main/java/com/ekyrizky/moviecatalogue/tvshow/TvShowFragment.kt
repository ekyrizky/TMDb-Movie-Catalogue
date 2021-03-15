package com.ekyrizky.moviecatalogue.tvshow

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.ui.tvshow.PopularTvShowAdapter
import com.ekyrizky.core.ui.tvshow.TvShowAdapter
import com.ekyrizky.core.utils.SortPreferences
import com.ekyrizky.core.utils.SortUtils.HIGHEST_VOTE
import com.ekyrizky.core.utils.SortUtils.LOWEST_VOTE
import com.ekyrizky.core.utils.SortUtils.TITLE_ASC
import com.ekyrizky.core.utils.SortUtils.TITLE_DESC
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.databinding.FragmentTvShowBinding
import com.ekyrizky.moviecatalogue.model.tvshow.TvShow
import com.ekyrizky.moviecatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private val viewModel: TvShowViewModel by viewModels()

    private var _fragmentTvShowFavoriteBinding: FragmentTvShowBinding? = null
    private val binding get() = _fragmentTvShowFavoriteBinding

    private lateinit var popularTvShowAdapter: PopularTvShowAdapter
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var sortPreferences: SortPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTvShowFavoriteBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            showLoading(true)
            popularTvShowAdapter = PopularTvShowAdapter()
            tvShowAdapter = TvShowAdapter()

            popularTvShowAdapter.onItemClick = {
                val action = TvShowFragmentDirections.actionNavigationTvshowToTvShowDetailFragment()
                action.tvShowId = it.toString()
                view.findNavController().navigate(action)
            }
            tvShowAdapter.onItemClick = {
                val action = TvShowFragmentDirections.actionNavigationTvshowToTvShowDetailFragment()
                action.tvShowId = it.toString()
                view.findNavController().navigate(action)
            }

            viewModel.getPopularTvShows().observe(viewLifecycleOwner, popularTvShowObserver)

            sortPreferences = SortPreferences(requireContext())
            sortPreferences.getSortTvShow()?.let { viewModel.getTvShows(it).observe(viewLifecycleOwner, tvShowObserver) }

            initRecyclerView()
        }
    }

    private val popularTvShowObserver = Observer<Resource<List<TvShow>>> { tvShow ->
        if (tvShow != null) {
            when (tvShow) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    val tvShowList = DataMapper.mapTvShowToTvShowDomain(tvShow.data)
                    popularTvShowAdapter.setData(tvShowList)
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val tvShowObserver = Observer<Resource<List<TvShow>>> { tvShow ->
        if (tvShow != null) {
            when (tvShow) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    val tvShowList = DataMapper.mapTvShowToTvShowDomain(tvShow.data)
                    tvShowAdapter.submitList(tvShowList)
                    tvShowAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecyclerView() {
        with(binding?.rvPopularTvshow) {
            this?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this?.setHasFixedSize(true)
            this?.adapter = popularTvShowAdapter
        }

        with(binding?.rvTvShow) {
            this?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort, menu)
        val activeMenu = menu.getItem(sortPreferences.getMenuTvShow())
        activeMenu.isChecked = true
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        var index = 0
        when (item.itemId) {
            R.id.action_title_asc -> {
                sort = TITLE_ASC
                index = 0
                Toast.makeText(context, R.string.sort_title_asc, Toast.LENGTH_SHORT).show()
            }
            R.id.action_title_desc -> {
                sort = TITLE_DESC
                index = 1
                Toast.makeText(context, R.string.sort_title_desc, Toast.LENGTH_SHORT).show()
            }
            R.id.action_highest_vote -> {
                sort = HIGHEST_VOTE
                index = 2
                Toast.makeText(context, R.string.sort_highest_vote, Toast.LENGTH_SHORT).show()
            }
            R.id.action_lowest_vote -> {
                sort = LOWEST_VOTE
                index = 3
                Toast.makeText(context, R.string.sort_lowest_vote, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getTvShows(sort).observe(viewLifecycleOwner, tvShowObserver)
        item.isChecked = true
        sortPreferences.setPrefTvShow(index, sort)

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        binding?.apply {
            progressBar.isVisible = state
            rvPopularTvshow.isVisible = !state
            rvTvShow.isVisible = !state
            tvPopularTvshow.isVisible = !state
            tvPlayingTvshow.isVisible = !state
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTvShowFavoriteBinding = null
    }
}