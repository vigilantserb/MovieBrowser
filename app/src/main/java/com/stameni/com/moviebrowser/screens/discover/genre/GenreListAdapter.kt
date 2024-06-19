package com.stameni.com.moviebrowser.screens.discover.genre

import android.content.Intent
import android.view.ViewGroup
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.baseClasses.BaseAdapter
import com.stameni.com.moviebrowser.common.baseClasses.BaseViewHolder
import com.stameni.com.moviebrowser.common.listen
import com.stameni.com.moviebrowser.data.models.Genre
import com.stameni.com.moviebrowser.databinding.ListItemBinding
import com.stameni.com.moviebrowser.screens.discover.genre.moviegridlist.MovieGridActivity

class GenreListAdapter(
    private val imageLoader: ImageLoader
) : BaseAdapter<Genre, ListItemBinding>(ListItemBinding::inflate) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListItemBinding> {
        return super.onCreateViewHolder(parent, viewType).listen { position, type ->
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

    override fun bind(binding: ListItemBinding, item: Genre, position: Int) {
        binding.listTitle.text = item.genreName
        val url = item.url

        imageLoader.loadListImageCenterCrop(url, binding.listPoster, "w500")
    }

    override fun add(response: Genre) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    override fun addAll(postItems: List<Genre>) {
        for (response in postItems) {
            add(response)
        }
    }
}
