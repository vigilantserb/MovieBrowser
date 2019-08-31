package com.stameni.com.moviebrowser.screens.singleMovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.listen
import com.stameni.com.moviebrowser.data.models.movie.MovieImage
import kotlinx.android.synthetic.main.single_movie_image_item.view.*

class SingleMovieImagesAdapter(
    private val items: ArrayList<MovieImage>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<SingleMovieImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_movie_image_item, parent, false)
        return ViewHolder(v, parent).listen { position, type ->
//            val item = items[position]
//            val intent = Intent(parent.context, NewsWebViewActivity::class.java)
//            intent.putExtra("source", item.source)
//            parent.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(response: MovieImage) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(postItems: List<MovieImage>) {
        for (response in postItems) {
            add(response)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listItem = items[position]
        val url = listItem.imageUrl

        if(url != null) imageLoader.loadImageNoFormat(url, holder.movieImageView, "w500")
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var movieImageView = itemView.movieImage
    }
}