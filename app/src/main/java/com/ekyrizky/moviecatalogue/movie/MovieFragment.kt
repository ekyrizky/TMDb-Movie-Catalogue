package com.ekyrizky.moviecatalogue.movie

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
import com.ekyrizky.core.ui.movie.MovieAdapter
import com.ekyrizky.core.ui.movie.PopularMovieAdapter
import com.ekyrizky.core.utils.SortPreferences
import com.ekyrizky.core.utils.SortUtils.HIGHEST_VOTE
import com.ekyrizky.core.utils.SortUtils.LOWEST_VOTE
import com.ekyrizky.core.utils.SortUtils.TITLE_ASC
import com.ekyrizky.core.utils.SortUtils.TITLE_DESC
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.databinding.FragmentMovieBinding
import com.ekyrizky.moviecatalogue.model.movie.Movie
import com.ekyrizky.moviecatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()

    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val binding get() = _fragmentMovieBinding

    private lateinit var popularMovieAdapter: PopularMovieAdapter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var sortPreferences: SortPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            showLoading(true)
            popularMovieAdapter = PopularMovieAdapter()
            movieAdapter = MovieAdapter()

            popularMovieAdapter.onItemClick = {
                val action = MovieFragmentDirections.actionNavigationMovieToMovieDetailFragment()
                action.movieId = it.toString()
                view.findNavController().navigate(action)
            }
            movieAdapter.onItemClick = {
                val action = MovieFragmentDirections.actionNavigationMovieToMovieDetailFragment()
                action.movieId = it.toString()
                view.findNavController().navigate(action)
            }

            viewModel.getPopularMovies().observe(viewLifecycleOwner, popularMovieObserver)

            sortPreferences = SortPreferences(requireContext())
            sortPreferences.getSortMovie()?.let { viewModel.getMovies(it).observe(viewLifecycleOwner, movieObserver) }

            initRecyclerView()
        }
    }

    private val popularMovieObserver = Observer<Resource<List<Movie>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    val movieList = DataMapper.mapMovieToMovieDomain(movies.data)
                    popularMovieAdapter.setData(movieList)
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val movieObserver = Observer<Resource<List<Movie>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    val movieList = DataMapper.mapMovieToMovieDomain(movies.data)
                    movieAdapter.submitList(movieList)
                    movieAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort, menu)
        val activeMenu = menu.getItem(sortPreferences.getMenuMovie())
        activeMenu.isChecked = true
        super.onCreateOptionsMenu(menu, inflater)
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

        viewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)
        item.isChecked = true
        sortPreferences.setPrefMovie(index, sort)

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        binding?.apply {
            progressBar.isVisible = state
            rvPopularMovie.isVisible = !state
            rvMovie.isVisible = !state
            tvPopularMovie.isVisible = !state
            tvPlayingMovie.isVisible = !state
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMovieBinding = null
    }
}