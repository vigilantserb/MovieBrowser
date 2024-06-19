package com.stameni.com.moviebrowser.screens.discover.genre.moviegridlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.baseClasses.BaseAdapter
import com.stameni.com.moviebrowser.common.baseClasses.BaseViewHolder
import com.stameni.com.moviebrowser.common.listen
import com.stameni.com.moviebrowser.data.models.movie.Movie
import com.stameni.com.moviebrowser.databinding.MovieGridItemBinding
import com.stameni.com.moviebrowser.screens.singleMovie.SingleMovieActivity

class MovieGridAdapter(
    private val imageLoader: ImageLoader
) : BaseAdapter<Movie, MovieGridItemBinding>(MovieGridItemBinding::inflate) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MovieGridItemBinding> {
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

    override fun bind(binding: MovieGridItemBinding, item: Movie, position: Int) {
        val url = item.moviePosterUrl
        binding.movieName.text = item.movieTitle

        url?.let {
            imageLoader.loadImageFromTmdb(
                it,
                binding.myImageView,
                binding.gifProgressBar,
                "w500"
            )
        }
        setFadeAnimation(binding.root) // todo - see if it works
    }

    override fun add(item: Movie) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    override fun addAll(items: List<Movie>) {
        for (response in items) {
            add(response)
        }
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }
}

