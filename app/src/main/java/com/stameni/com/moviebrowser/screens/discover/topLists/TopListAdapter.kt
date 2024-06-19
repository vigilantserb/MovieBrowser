package com.stameni.com.moviebrowser.screens.discover.topLists

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.baseClasses.BaseAdapter
import com.stameni.com.moviebrowser.common.baseClasses.BaseViewHolder
import com.stameni.com.moviebrowser.common.listen
import com.stameni.com.moviebrowser.data.models.Genre
import com.stameni.com.moviebrowser.data.models.ListItem
import com.stameni.com.moviebrowser.databinding.ListItemBinding
import com.stameni.com.moviebrowser.screens.discover.topLists.movielist.MovieListActivity

class TopListAdapter(
    private val imageLoader: ImageLoader
) : BaseAdapter<ListItem, ListItemBinding>(ListItemBinding::inflate) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListItemBinding> {
        return super.onCreateViewHolder(parent, viewType).listen { position, type ->
            val item = items[position]
            val intent = Intent(parent.context, MovieListActivity::class.java)
            intent.putExtra("url", item.url)
            intent.putExtra("title", item.title)
            intent.putExtra("id", item.id)
            parent.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun add(response: ListItem) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    override fun addAll(postItems: List<ListItem>) {
        for (response in postItems) {
            add(response)
        }
    }

    override fun bind(binding: ListItemBinding, item: ListItem, position: Int) {
        binding.listTitle.text = item.title
        val url = item.url

        imageLoader.loadListImageCenterCrop(url, binding.listPoster, "w500")
    }
}