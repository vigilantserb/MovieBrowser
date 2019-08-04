package com.stameni.com.whatshouldiwatch.screens.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.listen
import com.stameni.com.whatshouldiwatch.data.models.SearchItem
import com.stameni.com.whatshouldiwatch.screens.singleActor.SingleActorActivity
import com.stameni.com.whatshouldiwatch.screens.singleMovie.SingleMovieActivity
import kotlinx.android.synthetic.main.list_movie_item.view.*

class SearchAdapter(
    private val items: ArrayList<SearchItem>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_movie_item, parent, false)
        return ViewHolder(v, parent).listen { position, type ->
            val item = items[position]
            if (item.type == "Movie"){
                val intent = Intent(parent.context, SingleMovieActivity::class.java)
                intent.putExtra("posterUrl", item.url)
                intent.putExtra("movieId", item.id)
                intent.putExtra("movieName", item.title)
                parent.context.startActivity(intent)
            } else if (item.type == "Person"){
                val intent = Intent(parent.context, SingleActorActivity::class.java)
                intent.putExtra("actorName", item.title)
                intent.putExtra("actorId", item.id)
                intent.putExtra("actorUrl", item.url)
                parent.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(response: SearchItem) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(postItems: List<SearchItem>) {
        for (response in postItems) {
            add(response)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listItem = items[position]
        holder.movieTitle.text = listItem.title
        holder.movieGenres.text = listItem.type
        holder.year.text = listItem.year
        val url = listItem.url

        if (holder.year.text.isEmpty()) holder.year.visibility = View.GONE

        if (url != null) imageLoader.loadPosterImageCenterCrop(url, holder.moviePoster, "w500")

        holder.setFadeAnimation(holder.itemView)
    }

    fun removeAll() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var movieTitle = itemView.movie_title
        var moviePoster = itemView.movie_poster
        var movieGenres = itemView.genres_text_view
        var year = itemView.year_text_view
        var context = parent.context

        fun setFadeAnimation(view: View) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 500
            view.startAnimation(anim)
        }
    }
}