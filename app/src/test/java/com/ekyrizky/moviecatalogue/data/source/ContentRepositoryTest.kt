package com.ekyrizky.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ekyrizky.moviecatalogue.data.source.local.LocalDataSource
import com.ekyrizky.moviecatalogue.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.data.source.remote.RemoteDataSource
import com.ekyrizky.moviecatalogue.utils.AppExecutors
import com.ekyrizky.moviecatalogue.utils.DataDummy
import com.ekyrizky.moviecatalogue.utils.LiveDataTestUtil
import com.ekyrizky.moviecatalogue.utils.PagedListUtil
import com.ekyrizky.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
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
        val dataSource = mock(DataSource.Factory::class.java)as DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getMovies()).thenReturn(dataSource)
        contentRepository.getMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList((DataDummy.generateDummyMovies())))
        verify(localDataSource).getMovies()
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
    fun setFavoriteMovie() {
        contentRepository.setFavoriteMovie(DataDummy.generateDummyMovieDetail(), true)
        verify(localDataSource).updateMovie(DataDummy.generateDummyMovieDetail(), true)
        verifyNoMoreInteractions(localDataSource)
    }

    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getFavoriteMovies()).thenReturn(dataSourceFactory)
        contentRepository.getFavoriteMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(localDataSource).getFavoriteMovies()
        assertNotNull(movieEntities)
        assertEquals(moviesResponse.size, movieEntities.data?.size)
    }

    @Test
    fun getTvShows() {
        val dataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(localDataSource.getTvShows()).thenReturn(dataSource)
        contentRepository.getTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(localDataSource).getTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowsResponse.size, tvShowEntities.data?.size)
    }

    @Test
    fun getTvShowDetail() {
        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = DataDummy.generateDummyTvShowDetail()
        if (tvShowId != null) {
            `when`(localDataSource.getTvShowById(tvShowId)).thenReturn(dummyTvShow)

            val tvShowDetailEntity = LiveDataTestUtil.getValue(contentRepository.getTvShowDetail(tvShowId))
            verify(localDataSource).getTvShowById(tvShowId)
            assertNotNull(tvShowDetailEntity)
            assertEquals(tvShowDetailResponse.id, tvShowDetailEntity.data?.id)
        }
    }

    @Test
    fun setFavoriteTvShow() {
        contentRepository.setFavoriteTvShow(DataDummy.generateDummyTvShowDetail(), true)
        verify(localDataSource).updateTvShow(DataDummy.generateDummyTvShowDetail(), true)
        verifyNoMoreInteractions(localDataSource)
    }

    @Test
    fun getFavoriteTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(localDataSource.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        contentRepository.getFavoriteTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(localDataSource).getFavoriteTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowsResponse.size, tvShowEntities.data?.size)
    }
}