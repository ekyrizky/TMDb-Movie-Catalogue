package com.ekyrizky.moviecatalogue.ui.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.NEWEST
import com.ekyrizky.moviecatalogue.movie.MovieViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    private val dummySize = 10
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var contentRepository: ContentUseCase

    @Mock
    private lateinit var observer: Observer<Resource<List<MovieDomain>>>

    @Mock
    private lateinit var pagedList: List<MovieDomain>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(contentRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.Success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(dummySize)
        val movies = MutableLiveData<Resource<List<MovieDomain>>>()
        movies.value = dummyMovies

        `when`(contentRepository.getMovies(NEWEST)).thenReturn(movies)
        val movie = viewModel.getMovies(NEWEST).value?.data
        verify(contentRepository).getMovies(NEWEST)
        assertNotNull(movie)
        assertEquals(dummySize, movie?.size)

        viewModel.getMovies(NEWEST).observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}