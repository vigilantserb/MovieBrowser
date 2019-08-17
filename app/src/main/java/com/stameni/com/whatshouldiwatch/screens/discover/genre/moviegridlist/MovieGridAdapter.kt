package com.stameni.com.whatshouldiwatch.screens.discover.genre.moviegridlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.listen
import com.stameni.com.whatshouldiwatch.data.models.movie.Movie
import com.stameni.com.whatshouldiwatch.screens.singleMovie.SingleMovieActivity
import kotlinx.android.synthetic.main.movie_grid_item.view.*

class MovieGridAdapter(
    private val items: ArrayList<Movie>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<MovieGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_grid_item, parent, false)
        return ViewHolder(v, parent).listen { position, type ->
            val item = items[position]
            val intent = Intent(parent.context, SingleMovieActivity::class.java)
            intent.putExtra("posterUrl", item.moviePosterUrl)
            intent.putExtra("movieId", item.movieId)
            intent.putExtra("movieTitle", item.movieTitle)
            parent.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(response: Movie) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(postItems: List<Movie>) {
        for (response in postItems) {
            add(response)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = items[position]
        val url = listItem.moviePosterUrl
        holder.movieNameTxtView.text = listItem.movieTitle

        imageLoader.loadImageFromTmdb(url, holder.moviePoster, holder.progressBar, "w500")
        holder.setFadeAnimation(holder.itemView)
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var moviePoster = itemView.myImageView
        var progressBar = itemView.gif_progress_bar
        var movieNameTxtView = itemView.movie_name

        fun setFadeAnimation(view: View) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 500
            view.startAnimation(anim)
        }
    }
}

