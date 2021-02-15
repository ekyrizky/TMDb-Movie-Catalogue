package com.ekyrizky.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.local.entity.ContentEntity
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse
import com.ekyrizky.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyMovie = DataDummy.generateRemoteDummyMovies()[0]
    private val dummyTvShow = DataDummy.generateRemoteDummyTvShows()[0]
    private val movieId = dummyMovie.id
    private val tvShowId = dummyTvShow.id

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var contentRepository: ContentRepository

    @Mock
    private lateinit var movieObserver: Observer<MovieResponse>

    @Mock
    private lateinit var tvShowObserver: Observer<TvShowResponse>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(contentRepository)
    }

    @Test
    fun getDetailMovie() {
        val movie = MutableLiveData<MovieResponse>()
        movie.value = dummyMovie

        `when`(contentRepository.getMovieDetail(movieId)).thenReturn(movie)
        val movieEntity = viewModel.getDetailMovie(movieId).value
        assertNotNull(movieEntity)
        assertEquals(dummyMovie.id, movieEntity?.id)
        assertEquals(dummyMovie.originalTitle, movieEntity?.originalTitle)
        assertEquals(dummyMovie.tagline, movieEntity?.tagline)
        assertEquals(dummyMovie.releaseDate, movieEntity?.releaseDate)
        assertEquals(dummyMovie.runtime, movieEntity?.runtime)
        assertEquals(dummyMovie.voteAverage, movieEntity?.voteAverage)
        assertEquals(dummyMovie.overview, movieEntity?.overview)
        assertEquals(dummyMovie.posterPath, movieEntity?.posterPath)
        assertEquals(dummyMovie.backdropPath, movieEntity?.backdropPath)

        viewModel.getDetailMovie(movieId).observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getDetailTvShow() {
        val tvShow = MutableLiveData<TvShowResponse>()
        tvShow.value = dummyTvShow

        `when`(contentRepository.getTvShowDetail(tvShowId)).thenReturn(tvShow)
        val tvShowEntity = viewModel.getDetailTvShow(tvShowId).value
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShow.id, tvShowEntity?.id)
        assertEquals(dummyTvShow.originalName, tvShowEntity?.originalName)
        assertEquals(dummyTvShow.tagline, tvShowEntity?.tagline)
        assertEquals(dummyTvShow.firstAirDate, tvShowEntity?.firstAirDate)
        assertEquals(dummyTvShow.episodeRunTime, tvShowEntity?.episodeRunTime)
        assertEquals(dummyTvShow.voteAverage, tvShowEntity?.voteAverage)
        assertEquals(dummyTvShow.overview, tvShowEntity?.overview)
        assertEquals(dummyTvShow.posterPath, tvShowEntity?.posterPath)
        assertEquals(dummyTvShow.backdropPath, tvShowEntity?.backdropPath)

        viewModel.getDetailTvShow(tvShowId).observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShow)
    }
}