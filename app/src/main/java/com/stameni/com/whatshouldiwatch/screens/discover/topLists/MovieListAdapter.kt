package com.stameni.com.whatshouldiwatch.screens.discover.topLists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stameni.com.whatshouldiwatch.R
import kotlinx.android.synthetic.main.list_item.view.*

class MovieListAdapter(
    private val items: List<MovieListItem>
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listItem = items[position]
        holder.movieListTitle.text = listItem.title
        val url = listItem.url

        Glide.with(holder.context)
            .load("https://image.tmdb.org/t/p/w500/$url")
            .centerCrop()
            .into(holder.movieListPoster)
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var movieListTitle = itemView.list_title
        var movieListPoster = itemView.list_poster
        var context = itemView.context
    }
}