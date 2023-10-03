package com.example.moviereview.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import coil.size.Scale
import com.example.moviereview.R
import com.example.moviereview.databinding.FragmentMovieDetailsBinding
import com.example.moviereview.repo.ApiRepository
import com.example.moviereview.response.MovieDeatilsResponse
import com.example.moviereview.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private  lateinit var  binding: FragmentMovieDetailsBinding

    @Inject
    lateinit var  apiRepository: ApiRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = Bundle()
        val id  = bundle.getInt("Id")
        binding.apply {
            apiRepository.getMovieDetails(id).enqueue(object : Callback<MovieDeatilsResponse>{
                override fun onResponse(
                    call: Call<MovieDeatilsResponse>,
                    response: Response<MovieDeatilsResponse>
                ) {
                    when(response.code()){
                        200 -> {
                            response.body().let { itBody ->
                                val moviePosterURL = Constants.POSTER_BASE_URL + itBody?.poster_path
                                imgMovie.load(moviePosterURL) {
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                                imgMovieBack.load(moviePosterURL) {
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                                tvMovieTitle.text = itBody?.title
                                tvMovieTagLine.text = itBody?.tagline
                                tvMovieDateRelease.text = itBody?.release_date
                                tvMovieRating.text = itBody?.vote_average.toString()
                                tvMovieRuntime.text = itBody?.runtime.toString()
                                tvMovieBudget.text = itBody?.budget.toString()
                                tvMovieRevenue.text = itBody?.revenue.toString()
                                tvMovieOverview.text = itBody?.overview
                            }
                        }
                        400 -> { Toast.makeText(requireContext(), "The Resource you request could not be found", Toast.LENGTH_SHORT).show() }

                        401 -> { Toast.makeText(requireContext(), "Invalid API key: You must be granted a valid key" , Toast.LENGTH_SHORT).show() }
                    }
                }

                override fun onFailure(call: Call<MovieDeatilsResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }


}