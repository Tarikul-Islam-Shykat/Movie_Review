package com.example.moviereview.repo

import com.example.moviereview.api.ApiServices
import com.example.moviereview.utils.Constants
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped // this scoped object live as long as its components can be seen.
class ApiRepository @Inject constructor( private val apiServices: ApiServices) { // this wil provide the information about the response.

    fun getPopularMoviesList(page: Int) = apiServices.getPopularMovie(page)
    //fun getPopularMoviesList() = apiServices.getPopularMovie(Constants.API_KEY)
    fun getMovieDetails(id : Int) = apiServices.getMoviesDetails(id)

}