package com.stameni.com.moviebrowser.screens.mylist.toWatch

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.data.room.roomModels.Movie
import com.stameni.com.moviebrowser.screens.singleMovie.SingleMovieActivity
import kotlinx.android.synthetic.main.my_list_movie_item.view.*

class ToWatchListAdapter(
    private val items: ArrayList<Movie>,
    private val imageLoader: ImageLoader,
    val viewModel: ToWatchViewModel
) : RecyclerView.Adapter<ToWatchListAdapter.ViewHolder>() {

    private val oldItems = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.my_list_movie_item, parent, false)
        return ViewHolder(v, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(response: Movie) {
        items.add(response)
        oldItems.add(response)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(postItems: List<Movie>) {
        for (response in postItems) {
            add(response)
        }
    }

    fun searchListWithTerm(term: String) {
        val searchedItems = ArrayList<Movie>()
        items.clear()
        items.addAll(oldItems)
        if (term.isNotBlank()) {
            items.forEach {
                if (it.movieTitle!!.toLowerCase().contains(term.toLowerCase())) {
                    searchedItems.add(it)
                }
            }
            items.clear()
            items.addAll(searchedItems)
        } else {
            items.clear()
            items.addAll(oldItems)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = items[position]
        holder.movieTitle.text = listItem.movieTitle
        holder.movieGenres.text = listItem.movieGenres
        holder.movieYear.text = listItem.releaseDate
        holder.movieTitle.text = listItem.movieTitle
        val url = listItem.movieImageUrl

        holder.infoButton.setOnClickListener { v ->
            Toast.makeText(v.context, "Movie watched", Toast.LENGTH_SHORT).show()
            updateSingleMovie(listItem, position)
        }
        holder.editButton.setOnClickListener { v ->
            Toast.makeText(v.context, "Movie deleted", Toast.LENGTH_SHORT).show()
            removeSingleMovie(listItem, position)
        }

        holder.movieView.setOnClickListener {
            val item = items[position]
            val intent = Intent(it.context, SingleMovieActivity::class.java)
            intent.putExtra(Constants.POSTER_URL, item.movieImageUrl)
            intent.putExtra(Constants.MOVIE_ID, item.movieId)
            intent.putExtra(Constants.MOVIE_NAME, item.movieTitle)
            it.context.startActivity(intent)
        }

        if (url != null) imageLoader.loadPosterImageCenterCrop(url, holder.moviePoster, "w92")
        holder.setFadeAnimation(holder.itemView)
    }

    private fun removeSingleMovie(listItem: Movie, position: Int) {
        popElementFromList(listItem, position)
        viewModel.deleteSingleMovie(listItem)
    }

    private fun updateSingleMovie(listItem: Movie, position: Int) {
        popElementFromList(listItem, position)
        viewModel.updateMovie(listItem, "watched")
    }

    private fun popElementFromList(listItem: Movie, position: Int) {
        items.remove(listItem)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var movieTitle = itemView.movie_title
        var moviePoster = itemView.movie_poster
        var movieYear = itemView.year_text_view
        var movieGenres = itemView.genres_text_view
        var infoButton = itemView.watched_button
        var editButton = itemView.delete_button
        var movieView = itemView.movie_root

        fun setFadeAnimation(view: View) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 500
            view.startAnimation(anim)
        }
    }
}