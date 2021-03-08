package com.ekyrizky.moviecatalogue.tvshow

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekyrizky.moviecatalogue.MyApplication
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.ui.ViewModelFactory
import com.ekyrizky.moviecatalogue.core.ui.tvshow.TvShowAdapter
import com.ekyrizky.moviecatalogue.core.utils.SortPreferences
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.HIGHEST_VOTE
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.LOWEST_VOTE
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.TITLE_ASC
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.TITLE_DESC
import com.ekyrizky.moviecatalogue.databinding.FragmentTvShowBinding
import javax.inject.Inject

class TvShowFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: TvShowViewModel by viewModels { factory }

    private var _fragmentTvShowFavoriteBinding: FragmentTvShowBinding? = null
    private val binding get() = _fragmentTvShowFavoriteBinding

    private lateinit var tvShowAdapter: TvShowAdapter

    lateinit var sortPreferences: SortPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

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
            tvShowAdapter = TvShowAdapter()

            val action = TvShowFragmentDirections.actionNavigationTvshowToTvShowDetailFragment()
            tvShowAdapter.onItemClick = {
                action.tvShowId = it.toString()
                view.findNavController().navigate(action)
            }

            sortPreferences = SortPreferences(requireContext())
            sortPreferences.getSortTvShow()?.let { viewModel.getTvShows(it).observe(viewLifecycleOwner, tvShowObserver) }

            initRecyclerView()
        }
    }

    private val tvShowObserver = Observer<Resource<List<TvShowDomain>>> { tvShow ->
        if (tvShow != null) {
            when (tvShow) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    tvShowAdapter.submitList(tvShow.data)
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
        binding?.progressBar?.isVisible = state
        binding?.rvTvShow?.isVisible = !state
    }


}