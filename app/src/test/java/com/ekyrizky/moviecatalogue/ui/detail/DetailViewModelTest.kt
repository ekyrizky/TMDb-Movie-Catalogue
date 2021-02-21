package com.ekyrizky.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.ekyrizky.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_TVSHOW
import com.ekyrizky.moviecatalogue.utils.DataDummy
import com.ekyrizky.moviecatalogue.vo.Resource
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
    private lateinit var contentRepository: ContentRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<TvShowEntity>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(contentRepository)
    }

    @Test
    fun getDetailMovie() {
        val dummyMovie = Resource.success(DataDummy.generateDummyMovieDetail())
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyMovie

        `when`(movieId?.let { contentRepository.getMovieDetail(it) }).thenReturn(movie)

        viewModel.setContent(movieId.toString(), EXTRA_MOVIE)
        viewModel.getMovieDetail().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun setFavoriteMovie() {
        val dummyMovie = Resource.success(DataDummy.generateDummyMovieDetail())
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyMovie

        `when`(movieId?.let { contentRepository.getMovieDetail(it) }).thenReturn(movie)

        viewModel.setContent(movieId.toString(), EXTRA_MOVIE)
        viewModel.setFavoriteMovie()
        movie.value!!.data?.let { verify(contentRepository).setFavoriteMovie(it, true) }
        verifyNoMoreInteractions(movieObserver)
    }

    @Test
    fun getDetailTvShow() {
        val dummyTvShow = Resource.success(DataDummy.generateDummyTvShowDetail())
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dummyTvShow

        `when`(tvShowId?.let { contentRepository.getTvShowDetail(it) }).thenReturn(tvShow)

        viewModel.setContent(tvShowId.toString(), EXTRA_TVSHOW)
        viewModel.getTvShowDetail().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShow)
    }

    @Test
    fun setFavoriteTvShow() {
        val dummyTvShow = Resource.success(DataDummy.generateDummyTvShowDetail())
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dummyTvShow

        `when`(tvShowId?.let { contentRepository.getTvShowDetail(it) }).thenReturn(tvShow)

        viewModel.setContent(tvShowId.toString(), EXTRA_TVSHOW)
        viewModel.setFavoriteTvShow()
        tvShow.value!!.data?.let { verify(contentRepository).setFavoriteTvShow(it, true) }
        verifyNoMoreInteractions(tvShowObserver)
    }
}