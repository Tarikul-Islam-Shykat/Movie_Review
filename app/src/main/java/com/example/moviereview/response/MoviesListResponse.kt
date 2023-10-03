package com.example.moviereview.response

data class MoviesListResponse( // popular movie
    val page: Int  = 1,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)