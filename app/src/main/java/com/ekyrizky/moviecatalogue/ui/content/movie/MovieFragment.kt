package com.ekyrizky.moviecatalogue.ui.content.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.databinding.FragmentMovieBinding
import com.ekyrizky.moviecatalogue.viewmodel.ViewModelFactory

class MovieFragment : Fragment() {
    private lateinit var movieBinding: FragmentMovieBinding
    private lateinit var viewModel: MovieViewModel
    private val movieAdapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return movieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance()
            activity?.let {
                viewModel = ViewModelProvider(it, factory)[MovieViewModel::class.java]
            }
        }

        getMovieList()
        initRecyclerView()
    }

    private fun getMovieList() {
        viewModel.getListMovies().observe(viewLifecycleOwner, { movieItems ->
            movieAdapter.setItems(movieItems as MutableList<MovieResponse>)
        })
    }

    private fun initRecyclerView() {
        with(movieBinding.rvMovie) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }
}