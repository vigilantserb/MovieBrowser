package com.stameni.com.whatshouldiwatch.screens.mylist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.listen
import com.stameni.com.whatshouldiwatch.data.room.models.Movie
import com.stameni.com.whatshouldiwatch.screens.singleMovie.SingleMovieActivity
import kotlinx.android.synthetic.main.my_list_movie_item.view.*

class LocalMovieListAdapter(
    private val items: ArrayList<Movie>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<LocalMovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.my_list_movie_item, parent, false)
        return ViewHolder(v, parent).listen { position, type ->
            val item = items[position]
            val intent = Intent(parent.context, SingleMovieActivity::class.java)
            intent.putExtra("posterUrl", item.movieImageUrl)
            intent.putExtra("movieId", item.movieId)
            intent.putExtra("movieName", item.movieName)
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
        var listItem = items[position]
        holder.movieTitle.text = listItem.movieName
        holder.movieGenres.text = listItem.movieGenres
        holder.movieYear.text = "1989"
        holder.movieTitle.text = listItem.movieName
        val url = listItem.movieImageUrl
        holder.infoButton.setOnClickListener { v ->
            Toast.makeText(
                v.context,
                "INFO CLICKED",
                Toast.LENGTH_SHORT
            ).show()
        }
        holder.editButton.setOnClickListener { v ->
            Toast.makeText(
                v.context,
                "EDIT CLICKED",
                Toast.LENGTH_SHORT
            ).show()
        }
        if(url != null) imageLoader.loadPosterImageCenterCrop(url, holder.moviePoster, "w92")
        holder.setFadeAnimation(holder.itemView)
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var movieTitle = itemView.movie_title
        var moviePoster = itemView.movie_poster
        var movieYear = itemView.year_text_view
        var movieGenres = itemView.genres_text_view
        var infoButton = itemView.info_button
        var editButton = itemView.edit_button

        fun setFadeAnimation(view: View) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 500
            view.startAnimation(anim)
        }
    }
}