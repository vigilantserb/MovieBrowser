package com.stameni.com.moviebrowser.screens.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.baseClasses.BaseAdapter
import com.stameni.com.moviebrowser.common.baseClasses.BaseViewHolder
import com.stameni.com.moviebrowser.common.listen
import com.stameni.com.moviebrowser.data.models.SearchItem
import com.stameni.com.moviebrowser.databinding.ListMovieItemBinding
import com.stameni.com.moviebrowser.screens.singleMovie.SingleMovieActivity
import com.stameni.com.moviebrowser.screens.singlePerson.SinglePersonActivity

class SearchAdapter(
    private val imageLoader: ImageLoader
) : BaseAdapter<SearchItem, ListMovieItemBinding>(ListMovieItemBinding::inflate) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ListMovieItemBinding> =
        super.onCreateViewHolder(parent, viewType).listen { position, type ->
            val item = items[position]
            if (item.type == Constants.MOVIE_TYPE) {
                val intent = Intent(parent.context, SingleMovieActivity::class.java)
                intent.putExtra(Constants.POSTER_URL, item.url)
                intent.putExtra(Constants.MOVIE_ID, item.id)
                intent.putExtra(Constants.MOVIE_NAME, item.title)
                parent.context.startActivity(intent)
            } else if (item.type == Constants.PEOPLE_TYPE) {
                val intent = Intent(parent.context, SinglePersonActivity::class.java)
                intent.putExtra(Constants.PERSON_TYPE, item.type)
                intent.putExtra(Constants.PERSON_NAME, item.title)
                intent.putExtra(Constants.PERSON_ID, item.id)
                intent.putExtra(Constants.PERSON_IMAGE_URL, item.url)
                parent.context.startActivity(intent)
            }
        }

    override fun getItemCount(): Int = items.size
    override fun bind(binding: ListMovieItemBinding, item: SearchItem, position: Int) {
        binding.movieTitle.text = item.title
        binding.genresTextView.text = item.type
        binding.yearTextView.text = item.year
        val url = item.url

        if (binding.yearTextView.text.isEmpty()) binding.yearTextView.visibility = View.GONE

        if (url != null) imageLoader.loadPosterImageCenterCrop(
            url,
            binding.moviePoster,
            Constants.LARGE_IMAGE_SIZE
        )
        setFadeAnimation(binding.root)
    }

    override fun add(response: SearchItem) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    override fun addAll(postItems: List<SearchItem>) {
        for (response in postItems) {
            add(response)
        }
    }

    fun removeAll() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }
}