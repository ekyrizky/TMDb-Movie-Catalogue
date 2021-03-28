package com.ekyrizky.moviecatalogue.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.ui.search.SearchMovieAdapter
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

    private var _fragmentSearchBinding: FragmentSearchBinding? = null
    private val binding get() = _fragmentSearchBinding

    private val viewModel: SearchViewModel by viewModels()

    private var _movieAdapter: SearchMovieAdapter? = null
    private val movieAdapter get() = _movieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _movieAdapter = SearchMovieAdapter()

        val actionMovie = SearchFragmentDirections.actionNavigationSearchToMovieDetailFragment()

        movieAdapter?.setOnItemClickListener {
            actionMovie.movieId = it
            findNavController().navigate(actionMovie)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    private fun observeMovie() {
        with(binding) {
            viewModel.searchMovieResult.observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        this?.shimmerMovie?.visibility = View.GONE
                        this?.rvMovieSearch?.visibility = View.VISIBLE
                        val movieList = DataMapper.mapMovieToMovieDomain(it.data)
                        movieAdapter?.setData(movieList)
                    }
                    is Resource.Error -> {
                        this?.shimmerMovie?.visibility = View.INVISIBLE
                        this?.rvMovieSearch?.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }


    private fun showLoading() {
        binding?.apply {
            shimmerMovie.visibility = View.VISIBLE
            rvMovieSearch.visibility = View.GONE
        }
    }

    private fun initRecycler() {
        with(binding?.rvMovieSearch) {
            this?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        with(binding?.rvMovieSearch) {
            if (this?.adapter != null) {
                this.adapter = null
            }
        }
        _fragmentSearchBinding = null
    }
}