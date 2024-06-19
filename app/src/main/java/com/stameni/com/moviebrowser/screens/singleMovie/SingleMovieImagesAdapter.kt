package com.stameni.com.moviebrowser.screens.singleMovie

import android.view.ViewGroup
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.baseClasses.BaseAdapter
import com.stameni.com.moviebrowser.common.baseClasses.BaseViewHolder
import com.stameni.com.moviebrowser.common.listen
import com.stameni.com.moviebrowser.data.models.movie.MovieImage
import com.stameni.com.moviebrowser.databinding.SingleMovieImageItemBinding

class SingleMovieImagesAdapter(
    private val imageLoader: ImageLoader
) : BaseAdapter<MovieImage, SingleMovieImageItemBinding>(SingleMovieImageItemBinding::inflate) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SingleMovieImageItemBinding> =
        super.onCreateViewHolder(parent, viewType).listen { position, type ->
//            val item = items[position]
//            val intent = Intent(parent.context, NewsWebViewActivity::class.java)
//            intent.putExtra("source", item.source)
//            parent.context.startActivity(intent)
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun bind(binding: SingleMovieImageItemBinding, item: MovieImage, position: Int) {
        val url = item.imageUrl

        if (url != null) imageLoader.loadImageNoFormat(url, binding.movieImage, "w500")
    }

    override fun add(response: MovieImage) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    override fun addAll(postItems: List<MovieImage>) {
        for (response in postItems) {
            add(response)
        }
    }
}