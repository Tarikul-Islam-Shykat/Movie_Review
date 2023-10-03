package com.example.moviereview.api

import com.example.moviereview.Module.ApiModule
import com.example.moviereview.response.MovieDeatilsResponse
import com.example.moviereview.response.MoviesListResponse
import dagger.Binds
import dagger.Component
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiServices {

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("page") page : Int
    ): Call<MoviesListResponse>

    @GET("movie/{movie_id}")
    fun getMoviesDetails(
        @Path("movie_id") id: Int
    ) : Call<MovieDeatilsResponse>

}