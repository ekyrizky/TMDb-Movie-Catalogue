package com.ekyrizky.moviecatalogue.ui.home.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.DataDummy
import com.ekyrizky.moviecatalogue.favorite.movie.FavoriteMovieViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieViewModelTest {
    private val dummySize = 10
    private lateinit var viewModel: FavoriteMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var contentRepository: ContentUseCase

    @Mock
    private lateinit var observer: Observer<List<MovieDomain>>

    @Mock
    private lateinit var pagedList: List<MovieDomain>

    @Before
    fun setUp() {
        viewModel = FavoriteMovieViewModel(contentRepository)
    }

    @Test
    fun getFavoriteMovies() {
        val dummyMovie = pagedList
        Mockito.`when`(dummyMovie.size).thenReturn(dummySize)
        val movies = MutableLiveData<List<MovieDomain>>()
        movies.value = dummyMovie

        Mockito.`when`(contentRepository.getFavoriteMovies()).thenReturn(movies)
        val movie = viewModel.getFavoriteMovies().value
        verify(contentRepository).getFavoriteMovies()
        assertNotNull(movie)
        assertEquals(dummySize, movie?.size)

        viewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun setFavoriteMovie() {
        viewModel.setFavoriteMovie(DataDummy.generateDummyMovieDomain())
        verify(contentRepository).setFavoriteMovie(DataDummy.generateDummyMovieDomain(), true)
        verifyNoMoreInteractions(contentRepository)
    }
}