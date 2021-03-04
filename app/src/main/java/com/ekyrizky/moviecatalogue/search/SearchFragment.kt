package com.ekyrizky.moviecatalogue.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.ui.ViewModelFactory
import com.ekyrizky.moviecatalogue.core.ui.search.SearchMovieAdapter
import com.ekyrizky.moviecatalogue.core.ui.search.SearchTvShowAdapter
import com.ekyrizky.moviecatalogue.databinding.FragmentSearchBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {

    private var _fragmentSearchBinding: FragmentSearchBinding? = null
    private val binding get() = _fragmentSearchBinding

    private lateinit var viewModel: SearchViewModel
    private lateinit var movieAdapter: SearchMovieAdapter
    private lateinit var tvShowAdapter: SearchTvShowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        movieAdapter = SearchMovieAdapter()
        tvShowAdapter = SearchTvShowAdapter()

        initRecycler()

        binding?.searchView?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    lifecycleScope.launch {
                        if (query != null) {
                            viewModel.queryChannel.send(query)
                        }
                    }

                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    lifecycleScope.launch {
                        if (query != null) {
                            viewModel.queryChannel.send(query)
                        }
                    }
                    return true
                }
            })
        }

        observeMovie()
        observeTvShow()
    }

    private fun observeMovie() {
        with(binding) {
            viewModel.searchMovieResult.observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        this?.pbMovie?.visibility = View.GONE
                        this?.rvMovieSearch?.visibility = View.VISIBLE
                        this?.tvMovie?.visibility = View.VISIBLE
                        movieAdapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        this?.pbMovie?.visibility = View.INVISIBLE
                        this?.rvMovieSearch?.visibility = View.INVISIBLE
                        this?.tvMovie?.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }

    private fun observeTvShow() {
        with(binding) {
            viewModel.searchTvShowResult.observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        this?.pbMovie?.visibility = View.GONE
                        this?.rvTvshowSearch?.visibility = View.VISIBLE
                        this?.tvTvshow?.visibility = View.VISIBLE
                        tvShowAdapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        this?.pbMovie?.visibility = View.INVISIBLE
                        this?.rvTvshowSearch?.visibility = View.INVISIBLE
                        this?.tvTvshow?.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }

    private fun showLoading() {
        binding?.pbMovie?.visibility = View.VISIBLE
        binding?.rvMovieSearch?.visibility = View.GONE
        binding?.rvTvshowSearch?.visibility = View.GONE
        binding?.tvMovie?.visibility = View.GONE
        binding?.tvTvshow?.visibility = View.GONE
    }

    private fun initRecycler() {
        with(binding?.rvMovieSearch) {
            this?.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }

        with(binding?.rvTvshowSearch) {
            this?.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowAdapter
        }
    }
}