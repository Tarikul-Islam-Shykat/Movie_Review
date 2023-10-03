package com.example.moviereview.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviereview.R
import com.example.moviereview.adapter.MovieAdapter
import com.example.moviereview.databinding.FragmentMoviesBinding
import com.example.moviereview.repo.ApiRepository
import com.example.moviereview.response.MoviesListResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    @Inject
    lateinit var  api_Reository : ApiRepository

    @Inject
    lateinit var moviesAdapter : MovieAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            prgBarMovies.visibility = View.VISIBLE

            api_Reository.getPopularMoviesList(1).enqueue(object : Callback<MoviesListResponse>{

                override fun onResponse(call: Call<MoviesListResponse>, response: Response<MoviesListResponse>) {
                    prgBarMovies.visibility = View.GONE

                    when(response.code()){

                        200 ->{
                            Toast.makeText(requireContext(), "Yup", Toast.LENGTH_SHORT).show()
                            response.body().let { itBody ->
                                if(itBody?.results!!.isNotEmpty()){
                                    moviesAdapter.differ.submitList(itBody.results)
                                }
                                rlMovies.apply {  // setting recyclerMovie
                                    layoutManager = LinearLayoutManager(requireContext())
                                    adapter = moviesAdapter
                                }
                            }

                            moviesAdapter.setOnItemClickListener {
                                val intent = Intent(requireContext(), MovieDetailsFragment::class.java)
                                intent.putExtra("Id", it.id)
                                startActivity(intent)
                            }
                        }

                        400 -> { Toast.makeText(requireContext(), "The Resource you request could not be found", Toast.LENGTH_SHORT).show() }

                        401 -> { Toast.makeText(requireContext(), "Invalid API key: You must be granted a valid key" , Toast.LENGTH_SHORT).show() }
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Toast.makeText(requireContext(), "onFailure" , Toast.LENGTH_SHORT).show()
                }

            })
        }
    }


}