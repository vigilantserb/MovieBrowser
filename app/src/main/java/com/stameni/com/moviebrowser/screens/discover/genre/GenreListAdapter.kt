package com.stameni.com.moviebrowser.screens.discover.genre

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.listen
import com.stameni.com.moviebrowser.data.models.Genre
import com.stameni.com.moviebrowser.screens.discover.genre.moviegridlist.MovieGridActivity
import kotlinx.android.synthetic.main.list_item.view.*

class GenreListAdapter(
    private val items: ArrayList<Genre>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<GenreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v, parent).listen { position, type ->
            val item = items[position]
            val intent = Intent(parent.context, MovieGridActivity::class.java)
            intent.putExtra("genreId", item.genreId)
            intent.putExtra("genreName", item.genreName)
            parent.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(response: Genre) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(postItems: List<Genre>) {
        for (response in postItems) {
            add(response)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listItem = items[position]
        holder.movieListTitle.text = listItem.genreName
        val url = listItem.url

        imageLoader.loadListImageCenterCrop(url, holder.movieListPoster, "w500")
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var movieListTitle = itemView.list_title
        var movieListPoster = itemView.list_poster
        var context = parent.context
    }
}
