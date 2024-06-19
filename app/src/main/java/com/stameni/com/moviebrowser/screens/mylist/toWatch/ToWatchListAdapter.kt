package com.stameni.com.moviebrowser.screens.mylist.toWatch

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.baseClasses.BaseAdapter
import com.stameni.com.moviebrowser.common.baseClasses.BaseViewHolder
import com.stameni.com.moviebrowser.data.room.roomModels.Movie
import com.stameni.com.moviebrowser.databinding.MyListMovieItemBinding
import com.stameni.com.moviebrowser.screens.singleMovie.SingleMovieActivity

class ToWatchListAdapter(
    private val imageLoader: ImageLoader,
    val viewModel: ToWatchViewModel
) : BaseAdapter<Movie, MyListMovieItemBinding>(MyListMovieItemBinding::inflate) {

    private val oldItems = ArrayList<Movie>()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun bind(binding: MyListMovieItemBinding, item: Movie, position: Int) {
        binding.movieTitle.text = item.movieTitle
        binding.genresTextView.text = item.movieGenres
        binding.yearTextView.text = item.releaseDate
        binding.movieTitle.text = item.movieTitle
        val url = item.movieImageUrl

        binding.watchedButton.setOnClickListener { v ->
            Toast.makeText(v.context, "Movie watched", Toast.LENGTH_SHORT).show()
            updateSingleMovie(item, position)
        }
        binding.deleteButton.setOnClickListener { v ->
            Toast.makeText(v.context, "Movie deleted", Toast.LENGTH_SHORT).show()
            removeSingleMovie(item, position)
        }

        binding.movieRoot.setOnClickListener {
            val intent = Intent(it.context, SingleMovieActivity::class.java)
            intent.putExtra(Constants.POSTER_URL, item.movieImageUrl)
            intent.putExtra(Constants.MOVIE_ID, item.movieId)
            intent.putExtra(Constants.MOVIE_NAME, item.movieTitle)
            it.context.startActivity(intent)
        }

        if (url != null) imageLoader.loadPosterImageCenterCrop(url, binding.moviePoster, "w92")
        setFadeAnimation(binding.root)
    }

    override fun add(response: Movie) {
        items.add(response)
        oldItems.add(response)
        notifyItemInserted(items.size - 1)
    }

    override fun addAll(postItems: List<Movie>) {
        for (response in postItems) {
            add(response)
        }
    }

    fun searchListWithTerm(term: String) {
        val searchedItems = ArrayList<Movie>()
        items.clear()
        items.addAll(oldItems)
        if (term.isNotBlank()) {
            items.forEach {
                if (it.movieTitle!!.toLowerCase().contains(term.toLowerCase())) {
                    searchedItems.add(it)
                }
            }
            items.clear()
            items.addAll(searchedItems)
        } else {
            items.clear()
            items.addAll(oldItems)
        }
        notifyDataSetChanged()
    }

    private fun removeSingleMovie(listItem: Movie, position: Int) {
        popElementFromList(listItem, position)
        viewModel.deleteSingleMovie(listItem)
    }

    private fun updateSingleMovie(listItem: Movie, position: Int) {
        popElementFromList(listItem, position)
        viewModel.updateMovie(listItem, "watched")
    }

    private fun popElementFromList(listItem: Movie, position: Int) {
        items.remove(listItem)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }
}