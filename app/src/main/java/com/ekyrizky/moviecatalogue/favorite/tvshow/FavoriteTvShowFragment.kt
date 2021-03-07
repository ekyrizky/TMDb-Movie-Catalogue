package com.ekyrizky.moviecatalogue.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.ui.ViewModelFactory
import com.ekyrizky.moviecatalogue.core.ui.favorite.tvshow.FavoriteTvShowAdapter
import com.ekyrizky.moviecatalogue.databinding.FragmentFavoriteTvShowBinding
import com.ekyrizky.moviecatalogue.favorite.FavoriteFragmentDirections
import com.google.android.material.snackbar.Snackbar

class FavoriteTvShowFragment : Fragment() {
    private var _fragmentTvShowFavoriteBinding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _fragmentTvShowFavoriteBinding

    private lateinit var viewModel: FavoriteTvShowViewModel
    private lateinit var tvShowFavAdapter: FavoriteTvShowAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _fragmentTvShowFavoriteBinding = FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteTvshow)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteTvShowViewModel::class.java]

            tvShowFavAdapter = FavoriteTvShowAdapter()
            val action = FavoriteFragmentDirections.actionNavigationFavoriteToNavigationTvshowDetail()
            tvShowFavAdapter.onItemClick = {
                action.tvShowId = it.toString()
                view.findNavController().navigate(action)
            }
            viewModel.getFavoriteTvShow().observe(viewLifecycleOwner, { favTvShow ->
                if (favTvShow != null) {
                    binding?.rvFavoriteTvshow?.adapter.let { adapter ->
                        when (adapter) {
                            is FavoriteTvShowAdapter -> {
                                if (favTvShow.isNullOrEmpty()) {
                                    binding?.rvFavoriteTvshow?.isVisible = false
                                    binding?.imgEmpty?.isVisible = true
                                    binding?.tvEmpty?.isVisible = true
                                } else {
                                    binding?.rvFavoriteTvshow?.isVisible = true
                                    binding?.imgEmpty?.isVisible = false
                                    binding?.tvEmpty?.isVisible = false
                                    adapter.submitList(favTvShow)
                                    adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                }
            })

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
                val tvShowDomain = tvShowFavAdapter.getSwipedData(swipedPosition)
                tvShowDomain?.let { viewModel.deleteFavoriteTvShow(it) }

                val snackBar = Snackbar.make(requireView(), getString(R.string.undo, tvShowDomain?.title), Snackbar.LENGTH_LONG)
                snackBar.setAnchorView(R.id.nav_view)
                snackBar.setAction(R.string.ok) { _ ->
                    tvShowDomain?.let { viewModel.insertFavoriteTvShow(it) }
                }
                snackBar.show()
            }
        }
    })

    private fun initRecyclerView() {
        with(binding?.rvFavoriteTvshow) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowFavAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTvShowFavoriteBinding = null
    }
}