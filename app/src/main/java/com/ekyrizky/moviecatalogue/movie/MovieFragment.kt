package com.ekyrizky.moviecatalogue.movie

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.ui.movie.MovieAdapter
import com.ekyrizky.core.ui.movie.PopularMovieAdapter
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.databinding.FragmentMovieBinding
import com.ekyrizky.moviecatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val binding get() = _fragmentMovieBinding

    private val viewModel: MovieViewModel by viewModels()

    private var _popularMovieAdapter: PopularMovieAdapter? = null
    private val popularMovieAdapter get() = _popularMovieAdapter
    private var _movieAdapter: MovieAdapter? = null
    private val movieAdapter get() = _movieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _popularMovieAdapter = PopularMovieAdapter()
        _movieAdapter = MovieAdapter()

        popularMovieAdapter?.setOnItemClickListener {
            val action = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("moviecatalogue://detail/$it"))
                    .build()

            findNavController().navigate(action)
        }

        movieAdapter?.setOnItemClickListener {
            val action = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("moviecatalogue://detail/$it"))
                    .build()

            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        observePopularMoves()
        observeMovies()
    }

    private fun observeMovies() {
        viewModel.getMovies().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    val movieList = DataMapper.mapMovieToMovieDomain(it.data)
                    movieAdapter?.setData(movieList)
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun observePopularMoves() {
        viewModel.getPopularMovies().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    val movieList = DataMapper.mapMovieToMovieDomain(it.data)
                    popularMovieAdapter?.setData(movieList)
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRecyclerView() {
        with(binding?.rvPopularMovie) {
            this?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this?.setHasFixedSize(true)
            this?.adapter = popularMovieAdapter
        }

        with(binding?.rvMovie) {
            this?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        binding?.apply {
            progressBar.isVisible = state
            tvPopularMovie.isVisible = !state
            tvPlayingMovie.isVisible = !state
            rvPopularMovie.isVisible = !state
            rvMovie.isVisible = !state
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        with(binding?.rvMovie) {
            if (this?.adapter != null) {
                this.adapter = null
            }
        }

        with(binding?.rvPopularMovie) {
            if (this?.adapter != null) {
                this.adapter = null
            }
        }
        _fragmentMovieBinding = null
    }
}