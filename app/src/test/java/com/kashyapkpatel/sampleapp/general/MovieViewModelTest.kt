package com.kashyapkpatel.sampleapp.general

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.kashyapkpatel.sampleapp.TestApplication
import com.kashyapkpatel.sampleapp.util.getOrAwaitValues
import com.kashyapkpatel.sampleapp.base.BaseTest
import com.kashyapkpatel.sampleapp.util.Status
import com.kashyapkpatel.sampleapp.viewmodel.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.HttpURLConnection

/**
 * Unit test for [MovieViewModel]
 */
@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class MovieViewModelTest : BaseTest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check and verify that the 'getMoviesList' succeeds with 200`() = runBlockingTest {

        mockWebServer.enqueue(MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getJson("getMoviesList.json")))

        val movieViewModel = MovieViewModel(movieRepository)

        val results = movieViewModel.getMoviesList().getOrAwaitValues()
        val initialResult = results[0]
        val result = results[1]

        Truth.assertThat(initialResult.status).isEqualTo(Status.LOADING)
        Truth.assertThat(result.status).isEqualTo(Status.SUCCESS)
        val movieListResponse = result.data
        Truth.assertThat(movieListResponse).isNotNull()
        Truth.assertThat(movieListResponse?.results).isNotNull()
        Truth.assertThat(movieListResponse?.results?.get(0)?.id).isEqualTo(297761)
        Truth.assertThat(movieListResponse?.results?.get(0)?.title).isEqualTo("Suicide Squad")
        Truth.assertThat(movieListResponse?.results?.get(0)?.poster_path).isEqualTo("/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check and verify that the 'getMoviesList' fails with 502 bad gateway`() = runBlockingTest {

        mockWebServer.enqueue(MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_GATEWAY)
            .setBody(""))

        val movieViewModel = MovieViewModel(movieRepository)

        val results = movieViewModel.getMoviesList().getOrAwaitValues()
        val initialResult = results[0]
        val result = results[1]

        Truth.assertThat(initialResult.status).isEqualTo(Status.LOADING)
        Truth.assertThat(result.status).isEqualTo(Status.ERROR)
        Truth.assertThat(result.data).isNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check and verify that the 'getMoviesList' fails with 503`() = runBlockingTest {

        mockWebServer.enqueue(MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
            .setBody(getJson("internalServerFailureWith503.json")))

        val movieViewModel = MovieViewModel(movieRepository)

        val results = movieViewModel.getMoviesList().getOrAwaitValues()
        val initialResult = results[0]
        val result = results[1]

        Truth.assertThat(initialResult.status).isEqualTo(Status.LOADING)
        Truth.assertThat(result.status).isEqualTo(Status.ERROR)
        Truth.assertThat(result.data).isNull()
        Truth.assertThat(result.msg).isEqualTo("Internal server error.")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check and verify that the 'getMoviesList' fails with 401`() = runBlockingTest {

        mockWebServer.enqueue(MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            .setBody(getJson("invalidApiKey.json")))

        val movieViewModel = MovieViewModel(movieRepository)

        val results = movieViewModel.getMoviesList().getOrAwaitValues()
        val initialResult = results[0]
        val result = results[1]

        Truth.assertThat(initialResult.status).isEqualTo(Status.LOADING)
        Truth.assertThat(result.status).isEqualTo(Status.ERROR)
        Truth.assertThat(result.data).isNull()
        Truth.assertThat(result.msg).isEqualTo("Invalid API key: You must be granted a valid key.")
    }
}