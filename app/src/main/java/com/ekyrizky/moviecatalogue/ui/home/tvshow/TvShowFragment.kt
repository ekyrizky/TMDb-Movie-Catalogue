package com.ekyrizky.moviecatalogue.ui.home.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.databinding.FragmentTvShowBinding
import com.ekyrizky.moviecatalogue.ui.detail.DetailActivity
import com.ekyrizky.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_TVSHOW
import com.ekyrizky.moviecatalogue.ui.home.ContentCallback
import com.ekyrizky.moviecatalogue.utils.SortUtils.HIGHEST_VOTE
import com.ekyrizky.moviecatalogue.utils.SortUtils.LONGEST_DURATION
import com.ekyrizky.moviecatalogue.utils.SortUtils.NEWEST
import com.ekyrizky.moviecatalogue.viewmodel.ViewModelFactory
import com.ekyrizky.moviecatalogue.vo.Resource
import com.ekyrizky.moviecatalogue.vo.Status

class TvShowFragment : Fragment(), ContentCallback {
    private lateinit var tvShowBinding: FragmentTvShowBinding
    private lateinit var viewModel: TvShowViewModel
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tvShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return tvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            showLoading(true)
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

            tvShowAdapter = TvShowAdapter()
            viewModel.getTvShows(NEWEST).observe(viewLifecycleOwner, tvShowObserver)

            initRecyclerView()
        }
    }

    private val tvShowObserver = Observer<Resource<PagedList<TvShowEntity>>> { tvShow ->
        if (tvShow != null) {
            when (tvShow.status) {
                Status.LOADING -> showLoading(true)
                Status.SUCCESS -> {
                    showLoading(false)
                    tvShowAdapter.submitList(tvShow.data)
                    tvShowAdapter.setOnItemClickCallback(this)
                    tvShowAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showLoading(false)
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, id)
        intent.putExtra(DetailActivity.EXTRA_CONTENT, EXTRA_TVSHOW)

        context?.startActivity(intent)
    }

    private fun initRecyclerView() {
        with(tvShowBinding.rvTvShow) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_sort, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_newest -> {
                sort = NEWEST
                Toast.makeText(context, "Sorted by newest", Toast.LENGTH_SHORT).show()
            }
            R.id.action_highest_vote -> {
                sort = HIGHEST_VOTE
                Toast.makeText(context, "Sorted by highest vote", Toast.LENGTH_SHORT).show()
            }
            R.id.action_longest_duration -> {
                sort = LONGEST_DURATION
                Toast.makeText(context, "Sorted by longest duration", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getTvShows(sort).observe(viewLifecycleOwner, tvShowObserver)
        item.isChecked = true

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        tvShowBinding.progressBar.isVisible = state
        tvShowBinding.rvTvShow.isVisible = !state
    }
}