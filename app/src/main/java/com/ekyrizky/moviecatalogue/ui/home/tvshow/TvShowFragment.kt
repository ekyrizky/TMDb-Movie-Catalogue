package com.ekyrizky.moviecatalogue.ui.home.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.databinding.FragmentTvShowBinding
import com.ekyrizky.moviecatalogue.ui.detail.DetailActivity
import com.ekyrizky.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_TVSHOW
import com.ekyrizky.moviecatalogue.ui.home.ContentCallback
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
            viewModel.getTvShows().observe(viewLifecycleOwner, tvShowObserver)

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

    private fun showLoading(state: Boolean) {
        tvShowBinding.progressBar.isVisible = state
        tvShowBinding.rvTvShow.isVisible = !state
    }
}