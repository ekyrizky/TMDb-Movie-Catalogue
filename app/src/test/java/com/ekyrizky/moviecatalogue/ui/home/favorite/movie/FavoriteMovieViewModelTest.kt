package com.ekyrizky.moviecatalogue.ui.home.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.utils.DataDummy
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
    private lateinit var contentRepository: ContentRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteMovieViewModel(contentRepository)
    }

    @Test
    fun getFavoriteMovies() {
        val dummyMovie = pagedList
        Mockito.`when`(dummyMovie.size).thenReturn(dummySize)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
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
        viewModel.setFavoriteMovie(DataDummy.generateDummyMovieDetail())
        verify(contentRepository).setFavoriteMovie(DataDummy.generateDummyMovieDetail(), true)
        verifyNoMoreInteractions(contentRepository)
    }
}