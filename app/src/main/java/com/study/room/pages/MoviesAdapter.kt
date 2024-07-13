package com.study.room.pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.study.room.R
import com.study.room.data.AppEvent
import com.study.room.data.AppViewModel
import com.study.room.data.Movie

class MoviesAdapter(private val viewModel: AppViewModel, private val movies: List<Movie>): RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>(){

    inner class MoviesViewHolder(itemView: View): ViewHolder(itemView){
        val ivMoviePoster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
        val tvMovieName: TextView = itemView.findViewById(R.id.tv_movie_name)
        val tvMovieStudio: TextView = itemView.findViewById(R.id.tv_movie_studio)
        val tvCriticsRating: TextView = itemView.findViewById(R.id.tv_critics_rating)
        val cvMovieItem: CardView = itemView.findViewById(R.id.cv_movie_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MoviesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.tvMovieName.text = movie.title
        holder.tvMovieStudio.text = movie.studio
        holder.tvCriticsRating.text = movie.rating.toString()
        Glide.with(holder.ivMoviePoster)
            .load(movie.posterUrl)
            .error(R.drawable.ic_launcher_background)
            .into(holder.ivMoviePoster)
        holder.cvMovieItem.setOnClickListener{
            movie.id?.let { id ->
                viewModel.onEvent(AppEvent.GetMovieById(id))
            }
        }
    }
}