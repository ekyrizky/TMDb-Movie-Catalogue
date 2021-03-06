package com.ekyrizky.favorite.movie

import android.content.Context
import android.graphics.Color
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
import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.core.ui.favorite.movie.FavoriteMovieAdapter
import com.ekyrizky.favorite.FavoriteFragmentDirections
import com.ekyrizky.favorite.R
import com.ekyrizky.favorite.databinding.FragmentFavoriteMovieBinding
import com.ekyrizky.favorite.di.DaggerFavoriteComponent
import com.ekyrizky.favorite.utils.DataMapper
import com.ekyrizky.favorite.utils.ViewModelFactory
import com.ekyrizky.moviecatalogue.di.FavoriteModuleDependencies
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteMovieFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteMovieViewModel by viewModels { factory }

    private var _fragmentMovieFavoriteBinding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _fragmentMovieFavoriteBinding

    private lateinit var movieFavAdapter: FavoriteMovieAdapter

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onAttach(context)
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
                showSnackbar(favoriteMovieDomain)
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

    private fun showSnackbar(favMovie: FavoriteMovieDomain?) {
        val snackBar = Snackbar.make(requireView(), getString(R.string.undo, favMovie?.title), Snackbar.LENGTH_LONG)

        snackBar.apply {
            setAnchorView(com.ekyrizky.moviecatalogue.R.id.nav_view)
            setAction(R.string.ok) { _ -> favMovie?.let { viewModel.insertFavoriteMovie(it) } }
            setActionTextColor(Color.parseColor("#FFFFFF"))
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMovieFavoriteBinding = null
    }
}