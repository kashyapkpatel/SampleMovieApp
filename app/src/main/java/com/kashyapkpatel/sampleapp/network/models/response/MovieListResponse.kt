package com.kashyapkpatel.sampleapp.network.models.response

data class MovieListResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)