package com.stameni.com.whatshouldiwatch.screens.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.Constants
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.listen
import com.stameni.com.whatshouldiwatch.data.models.SearchItem
import com.stameni.com.whatshouldiwatch.screens.singleMovie.SingleMovieActivity
import com.stameni.com.whatshouldiwatch.screens.singlePerson.SinglePersonActivity
import kotlinx.android.synthetic.main.list_movie_item.view.*

class SearchAdapter(
    private val items: ArrayList<SearchItem>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_movie_item, parent, false)
        return ViewHolder(v, parent).listen { position, type ->
            val item = items[position]
            if (item.type == Constants.MOVIE_TYPE){
                val intent = Intent(parent.context, SingleMovieActivity::class.java)
                intent.putExtra(Constants.POSTER_URL, item.url)
                intent.putExtra(Constants.MOVIE_ID, item.id)
                intent.putExtra(Constants.MOVIE_NAME, item.title)
                parent.context.startActivity(intent)
            } else if (item.type == Constants.PEOPLE_TYPE){
                val intent = Intent(parent.context, SinglePersonActivity::class.java)
                intent.putExtra(Constants.ACTOR_NAME, item.title)
                intent.putExtra(Constants.ACTOR_ID, item.id)
                intent.putExtra(Constants.ACTOR_IMAGE_URL, item.url)
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

        if (url != null) imageLoader.loadPosterImageCenterCrop(url, holder.moviePoster, Constants.LARGE_IMAGE_SIZE)

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