package com.kashyapkpatel.sampleapp.network.api

import com.kashyapkpatel.sampleapp.network.models.response.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET(value = "movie/popular")
    suspend fun getMovieList(
        @Query(value = "language") language: String = "en-US"
    ): Response<MovieListResponse>

}