package com.ekyrizky.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.DataDummy
import com.ekyrizky.moviecatalogue.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.ekyrizky.moviecatalogue.detail.DetailActivity.Companion.EXTRA_TVSHOW
import com.ekyrizky.moviecatalogue.detail.DetailViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyMovie = DataDummy.generateDummyMovieDetail()
    private val dummyTvShow = DataDummy.generateDummyTvShowDetail()
    private val movieId = dummyMovie.id
    private val tvShowId = dummyTvShow.id

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var contentRepository: ContentUseCase

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieDomain>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<TvShowDomain>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(contentRepository)
    }

    @Test
    fun getDetailMovie() {
        val dummyMovie = Resource.Success(DataDummy.generateDummyMovieDomain())
        val movie = MutableLiveData<Resource<MovieDomain>>()
        movie.value = dummyMovie

        `when`(contentRepository.getMovieDetail(movieId)).thenReturn(movie)

        viewModel.setContent(movieId.toString(), EXTRA_MOVIE)
        viewModel.getMovieDetail().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun setFavoriteMovie() {
        val dummyMovie = Resource.Success(DataDummy.generateDummyMovieDomain())
        val movie = MutableLiveData<Resource<MovieDomain>>()
        movie.value = dummyMovie

        `when`(contentRepository.getMovieDetail(movieId)).thenReturn(movie)

        viewModel.setContent(movieId.toString(), EXTRA_MOVIE)
        viewModel.setFavoriteMovie()
        movie.value!!.data?.let { verify(contentRepository).setFavoriteMovie(it, true) }
        verifyNoMoreInteractions(movieObserver)
    }

    @Test
    fun getDetailTvShow() {
        val dummyTvShow = Resource.Success(DataDummy.generateDummyTvShowDomain())
        val tvShow = MutableLiveData<Resource<TvShowDomain>>()
        tvShow.value = dummyTvShow

        `when`(contentRepository.getTvShowDetail(tvShowId)).thenReturn(tvShow)

        viewModel.setContent(tvShowId.toString(), EXTRA_TVSHOW)
        viewModel.getTvShowDetail().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShow)
    }

    @Test
    fun setFavoriteTvShow() {
        val dummyTvShow = Resource.Success(DataDummy.generateDummyTvShowDomain())
        val tvShow = MutableLiveData<Resource<TvShowDomain>>()
        tvShow.value = dummyTvShow

        `when`(contentRepository.getTvShowDetail(tvShowId)).thenReturn(tvShow)

        viewModel.setContent(tvShowId.toString(), EXTRA_TVSHOW)
        viewModel.setFavoriteTvShow()
        tvShow.value!!.data?.let { verify(contentRepository).setFavoriteTvShow(it, true) }
        verifyNoMoreInteractions(tvShowObserver)
    }
}