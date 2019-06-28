package com.stameni.com.whatshouldiwatch.screens.discover.topLists.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.data.models.Movie
import kotlinx.android.synthetic.main.list_movie_item.view.*

class ListMoviesAdapter(
    private val items: ArrayList<Movie>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<ListMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_movie_item, parent, false)
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
        var listItem = items[position]
        holder.movieTitle.text = listItem.movieTitle
        holder.movieGenres.text = listItem.movieGenres
        holder.movieYear.text = listItem.movieYear
        holder.movieTitle.text = listItem.movieTitle
        val url = listItem.moviePosterUrl

        imageLoader.loadImageFromTmdb(url, holder.moviePoster, null, "w92")
        holder.setFadeAnimation(holder.itemView)
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var movieTitle = itemView.movie_title
        var moviePoster = itemView.movie_poster
        var movieYear = itemView.year_text_view
        var movieGenres = itemView.genres_text_view

        fun setFadeAnimation(view: View) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 500
            view.startAnimation(anim)
        }
    }
}