package com.stameni.com.moviebrowser.screens.discover.topLists.movielist

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.baseClasses.BaseAdapter
import com.stameni.com.moviebrowser.common.baseClasses.BaseViewHolder
import com.stameni.com.moviebrowser.common.listen
import com.stameni.com.moviebrowser.data.models.movie.Movie
import com.stameni.com.moviebrowser.databinding.ListMovieItemBinding
import com.stameni.com.moviebrowser.screens.singleMovie.SingleMovieActivity

class ListMoviesAdapter(
    private val imageLoader: ImageLoader
) : BaseAdapter<Movie, ListMovieItemBinding>(ListMovieItemBinding::inflate) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListMovieItemBinding> {
        return super.onCreateViewHolder(parent, viewType).listen { position, type ->
            val item = items[position]
            val intent = Intent(parent.context, SingleMovieActivity::class.java)
            intent.putExtra("posterUrl", item.moviePosterUrl)
            intent.putExtra("movieId", item.movieId)
            intent.putExtra("movieTitle", item.movieTitle)
            parent.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun bind(binding: ListMovieItemBinding, item: Movie, position: Int) {
        binding.movieTitle.text = item.movieTitle
        binding.genresTextView.text = item.movieGenres
        binding.yearTextView.text = item.movieYear
        binding.movieTitle.text = item.movieTitle
        val url = item.moviePosterUrl

        url?.let { imageLoader.loadPosterImageCenterCrop(it, binding.moviePoster, "w92") }
        setFadeAnimation(binding.root)
    }

    override fun add(response: Movie) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    override fun addAll(postItems: List<Movie>) {
        for (response in postItems) {
            add(response)
        }
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }
}