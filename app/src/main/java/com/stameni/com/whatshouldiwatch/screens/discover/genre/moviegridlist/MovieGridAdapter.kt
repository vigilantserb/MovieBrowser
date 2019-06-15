package com.stameni.com.whatshouldiwatch.screens.discover.genre.moviegridlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.listen
import com.stameni.com.whatshouldiwatch.data.models.Movie
import kotlinx.android.synthetic.main.movie_grid_item.view.*

class MovieGridAdapter(
    private val items: ArrayList<Movie>
) : RecyclerView.Adapter<MovieGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_grid_item, parent, false)
        return ViewHolder(v, parent)
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

        holder.addImageFromUrl(url)
        holder.setFadeAnimation(holder.itemView)
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var moviePoster = itemView.myImageView

        fun addImageFromUrl(url: String) {
            moviePoster.visibility = View.VISIBLE
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500/$url")
                .centerCrop()
                .into(moviePoster)
        }

        fun setFadeAnimation(view: View) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 500
            view.startAnimation(anim)
        }
    }
}

