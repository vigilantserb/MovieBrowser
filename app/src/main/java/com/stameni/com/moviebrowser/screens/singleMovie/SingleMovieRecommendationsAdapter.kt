package com.stameni.com.moviebrowser.screens.singleMovie

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
import com.stameni.com.moviebrowser.databinding.SingleMovieRecommendationItemBinding

class SingleMovieRecommendationsAdapter(
    private val imageLoader: ImageLoader
) : BaseAdapter<Movie, SingleMovieRecommendationItemBinding>(SingleMovieRecommendationItemBinding::inflate) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SingleMovieRecommendationItemBinding> =
        super.onCreateViewHolder(parent, viewType).listen { position, type ->
            val item = items[position]
            val intent = Intent(parent.context, SingleMovieActivity::class.java)
            intent.putExtra("posterUrl", item.moviePosterUrl)
            intent.putExtra("movieId", item.movieId)
            intent.putExtra("movieTitle", item.movieTitle)
            parent.context.startActivity(intent)
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun bind(binding: SingleMovieRecommendationItemBinding, item: Movie, position: Int) {
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
