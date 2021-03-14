package com.ekyrizky.moviecatalogue.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.ui.search.SearchMovieAdapter
import com.ekyrizky.core.ui.search.SearchTvShowAdapter
import com.ekyrizky.moviecatalogue.databinding.FragmentSearchBinding
import com.ekyrizky.moviecatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    private var _fragmentSearchBinding: FragmentSearchBinding? = null
    private val binding get() = _fragmentSearchBinding

    private lateinit var movieAdapter: SearchMovieAdapter
    private lateinit var tvShowAdapter: SearchTvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = SearchMovieAdapter()
        tvShowAdapter = SearchTvShowAdapter()

        val actionMovie = SearchFragmentDirections.actionNavigationSearchToMovieDetailFragment()
        val actionTv = SearchFragmentDirections.actionNavigationSearchToTvShowDetailFragment()

        movieAdapter.onItemClick = {
            actionMovie.movieId = it.toString()
            view.findNavController().navigate(actionMovie)
        }

        tvShowAdapter.onItemClick = {
            actionTv.tvShowId = it.toString()
            view.findNavController().navigate(actionTv)
        }

        initRecycler()

        binding?.searchView?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    showLoading()
                    lifecycleScope.launch {
                        if (query != null) {
                            viewModel.queryChannel.send(query)
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    showLoading()
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
                        this?.shimmerMovie?.visibility = View.GONE
                        this?.rvMovieSearch?.visibility = View.VISIBLE
                        this?.tvMovie?.visibility = View.VISIBLE
                        val movieList = DataMapper.mapMovieToMovieDomain(it.data)
                        movieAdapter.setData(movieList)
                    }
                    is Resource.Error -> {
                        this?.shimmerMovie?.visibility = View.INVISIBLE
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
                        this?.shimmerTv?.visibility = View.GONE
                        this?.rvTvshowSearch?.visibility = View.VISIBLE
                        this?.tvTvshow?.visibility = View.VISIBLE
                        val tvShowList = DataMapper.mapTvShowToTvShowDomain(it.data)
                        tvShowAdapter.setData(tvShowList)
                    }
                    is Resource.Error -> {
                        this?.shimmerTv?.visibility = View.INVISIBLE
                        this?.rvTvshowSearch?.visibility = View.INVISIBLE
                        this?.tvTvshow?.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }

    private fun showLoading() {
        binding?.apply {
            shimmerMovie.visibility = View.VISIBLE
            shimmerTv.visibility = View.VISIBLE
            rvMovieSearch.visibility = View.GONE
            rvTvshowSearch.visibility = View.GONE
            tvMovie.visibility = View.GONE
            tvTvshow.visibility = View.GONE
        }
    }

    private fun initRecycler() {
        with(binding?.rvMovieSearch) {
            this?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }

        with(binding?.rvTvshowSearch) {
            this?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentSearchBinding = null
    }
}