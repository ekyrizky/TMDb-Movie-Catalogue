package com.ekyrizky.moviecatalogue.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekyrizky.moviecatalogue.ContentCallback
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.ui.ViewModelFactory
import com.ekyrizky.moviecatalogue.core.ui.movie.MovieAdapter
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.HIGHEST_VOTE
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.NEWEST
import com.ekyrizky.moviecatalogue.databinding.FragmentMovieBinding
import com.ekyrizky.moviecatalogue.detail.DetailActivity
import com.ekyrizky.moviecatalogue.detail.DetailActivity.Companion.EXTRA_MOVIE


class MovieFragment : Fragment(), ContentCallback {

    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val binding get() = _fragmentMovieBinding

    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

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
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            movieAdapter = MovieAdapter()
            viewModel.getMovies(NEWEST).observe(viewLifecycleOwner, movieObserver)

            initRecyclerView()
        }
    }

    private val movieObserver = Observer<Resource<List<MovieDomain>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    movieAdapter.submitList(movies.data)
                    movieAdapter.setOnItemClickCallback(this)
                    movieAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, id)
        intent.putExtra(DetailActivity.EXTRA_CONTENT, EXTRA_MOVIE)

        context?.startActivity(intent)
    }

    private fun initRecyclerView() {
        with(binding?.rvMovie) {
            this?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
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
        }

        viewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)
        item.isChecked = true

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        binding?.progressBar?.isVisible = state
        binding?.rvMovie?.isVisible = !state
    }
}