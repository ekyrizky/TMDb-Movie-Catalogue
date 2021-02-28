package com.ekyrizky.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.data.source.local.LocalDataSource
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.ekyrizky.moviecatalogue.core.utils.AppExecutors
import com.ekyrizky.moviecatalogue.core.utils.DataDummy
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.NEWEST
import com.ekyrizky.moviecatalogue.utils.LiveDataTestUtil
import com.ekyrizky.moviecatalogue.utils.PagedListUtil
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ContentRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remoteDataSource = mock(RemoteDataSource::class.java)
    private val localDataSource = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val contentRepository = FakeContentRepository(remoteDataSource, localDataSource, appExecutors)

    private val moviesResponse = DataDummy.generateRemoteDummyMovies()
    private val movieId = moviesResponse[0].id
    private val movieDetailResponse = DataDummy.generateRemoteDummyMovieDetail()


    private val tvShowsResponse = DataDummy.generateRemoteDummyTvShows()
    private val tvShowId = tvShowsResponse[0].id
    private val tvShowDetailResponse = DataDummy.generateRemoteDummyTvShowDetail()

    @Test
    fun getMovies() {
        val dummyMovies = MutableLiveData<List<MovieEntity>>()
        dummyMovies.value = DataDummy.generateDummyMovies()
        `when`(localDataSource.getMovies(NEWEST)).thenReturn(dummyMovies)
        contentRepository.getMovies(NEWEST)

        val movieEntities = Resource.Success(PagedListUtil.mockPagedList((DataDummy.generateDummyMovies())))
        verify(localDataSource).getMovies(NEWEST)
        assertNotNull(movieEntities)
        assertEquals(moviesResponse.size, movieEntities.data?.size)
    }

    @Test
    fun getMovieDetail() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = DataDummy.generateDummyMovieDetail()
        `when`(localDataSource.getMovieById(movieId)).thenReturn(dummyMovie)

        val movieDetailEntity = LiveDataTestUtil.getValue(contentRepository.getMovieDetail(movieId))
        verify(localDataSource).getMovieById(movieId)
        assertNotNull(movieDetailEntity)
        assertEquals(movieDetailResponse.id, movieDetailEntity.data?.id)
    }

    @Test
    fun getFavoriteMovie() {
        val dummyMovies = MutableLiveData<List<MovieEntity>>()
        dummyMovies.value = DataDummy.generateDummyMovies()
        `when`(localDataSource.getFavoriteMovies()).thenReturn(dummyMovies)
        contentRepository.getFavoriteMovies()

        val movieEntities = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(localDataSource).getFavoriteMovies()
        assertNotNull(movieEntities)
        assertEquals(moviesResponse.size, movieEntities.data?.size)
    }

    @Test
    fun getTvShows() {
        val dummyTvShows = MutableLiveData<List<TvShowEntity>>()
        dummyTvShows.value = DataDummy.generateDummyTvShows()
        `when`(localDataSource.getTvShows(NEWEST)).thenReturn(dummyTvShows)
        contentRepository.getTvShows(NEWEST)

        val tvShowEntities = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(localDataSource).getTvShows(NEWEST)
        assertNotNull(tvShowEntities)
        assertEquals(tvShowsResponse.size, tvShowEntities.data?.size)
    }

    @Test
    fun getTvShowDetail() {
        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = DataDummy.generateDummyTvShowDetail()
        `when`(localDataSource.getTvShowById(tvShowId)).thenReturn(dummyTvShow)

        val tvShowDetailEntity = LiveDataTestUtil.getValue(contentRepository.getTvShowDetail(tvShowId))
        verify(localDataSource).getTvShowById(tvShowId)
        assertNotNull(tvShowDetailEntity)
        assertEquals(tvShowDetailResponse.id, tvShowDetailEntity.data?.id)
    }

    @Test
    fun getFavoriteTvShows() {
        val dummyTvShows = MutableLiveData<List<TvShowEntity>>()
        dummyTvShows.value = DataDummy.generateDummyTvShows()
        `when`(localDataSource.getFavoriteTvShows()).thenReturn(dummyTvShows)
        contentRepository.getFavoriteTvShows()

        val tvShowEntities = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(localDataSource).getFavoriteTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowsResponse.size, tvShowEntities.data?.size)
    }
}