package com.example.moviereview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.moviereview.R
import com.example.moviereview.databinding.ItemMovieBinding
import com.example.moviereview.response.MoviesListResponse
import com.example.moviereview.response.Result
import com.example.moviereview.utils.Constants
import javax.inject.Inject

class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var binding: ItemMovieBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding= ItemMovieBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int  = differ.currentList.size


    inner  class ViewHolder(): RecyclerView.ViewHolder(binding.root){

        fun set(item: Result){
            binding.apply {
                tvMovieName.text = item.original_title
                tvLang.text = item.original_title
                tvRate.text = item.vote_average.toString()
                tvMovieDateRelease.text = item.release_date
                val moviePoster = Constants.POSTER_BASE_URL + item.poster_path
                imgMovie.load(moviePoster){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }

        }

    }

    private  val differCallBack = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return  oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    private var onItemClickListener: ((Result) -> Unit) ? = null

    fun setOnItemClickListener(listener:(Result) -> Unit){
        onItemClickListener = listener
    }


}