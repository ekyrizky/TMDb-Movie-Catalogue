package com.ekyrizky.moviecatalogue.favorite.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekyrizky.core.ui.favorite.movie.FavoriteMovieAdapter
import com.ekyrizky.moviecatalogue.MyApplication
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.databinding.FragmentFavoriteMovieBinding
import com.ekyrizky.moviecatalogue.di.ViewModelFactory
import com.ekyrizky.moviecatalogue.favorite.FavoriteFragmentDirections
import com.ekyrizky.moviecatalogue.utils.DataMapper
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class FavoriteMovieFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteMovieViewModel by viewModels { factory }

    private var _fragmentMovieFavoriteBinding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _fragmentMovieFavoriteBinding

    private lateinit var movieFavAdapter: FavoriteMovieAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieFavoriteBinding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteMovie)

        if (activity != null) {
            movieFavAdapter = FavoriteMovieAdapter()

            val action = FavoriteFragmentDirections.actionNavigationFavoriteToNavigationMovieDetail()
            movieFavAdapter.onItemClick = {
                action.movieId = it.toString()
                view.findNavController().navigate(action)
            }

            viewModel.getFavoriteMovies().observe(viewLifecycleOwner) { favMovies ->
                if (favMovies != null) {
                    binding?.rvFavoriteMovie?.adapter.let { adapter ->
                        when (adapter) {
                            is FavoriteMovieAdapter -> {
                                if (favMovies.isNullOrEmpty()) {
                                    binding?.rvFavoriteMovie?.isVisible = false
                                    binding?.imgEmpty?.isVisible = true
                                    binding?.tvEmpty?.isVisible = true
                                } else {
                                    binding?.rvFavoriteMovie?.isVisible = true
                                    binding?.imgEmpty?.isVisible = false
                                    binding?.tvEmpty?.isVisible = false
                                    val favoriteMovie = DataMapper.mapFavoriteMovieToFavoriteMovieDomain(favMovies)
                                    adapter.submitList(favoriteMovie)
                                    adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                }
            }

            initRecyclerView()
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val favoriteMovieDomain = movieFavAdapter.getSwipedData(swipedPosition)
                favoriteMovieDomain?.let { viewModel.deleteFavoriteMovie(it) }

                val snackBar = Snackbar.make(requireView(), getString(R.string.undo, favoriteMovieDomain?.title), Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.nav_view)
                    .setAction(R.string.ok) { _ -> favoriteMovieDomain?.let { viewModel.insertFavoriteMovie(it) } }
                snackBar.show()
            }
        }
    })

    private fun initRecyclerView() {
        with(binding?.rvFavoriteMovie) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = movieFavAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMovieFavoriteBinding = null
    }
}