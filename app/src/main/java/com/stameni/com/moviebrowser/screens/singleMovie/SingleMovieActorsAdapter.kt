package com.stameni.com.moviebrowser.screens.singleMovie

import android.content.Intent
import android.view.ViewGroup
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.baseClasses.BaseAdapter
import com.stameni.com.moviebrowser.common.baseClasses.BaseViewHolder
import com.stameni.com.moviebrowser.common.listen
import com.stameni.com.moviebrowser.data.models.movie.Actor
import com.stameni.com.moviebrowser.databinding.SingleMovieActorItemBinding
import com.stameni.com.moviebrowser.screens.singlePerson.SinglePersonActivity

class SingleMovieActorsAdapter(
    private val imageLoader: ImageLoader
) : BaseAdapter<Actor, SingleMovieActorItemBinding>(SingleMovieActorItemBinding::inflate) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SingleMovieActorItemBinding> =
        super.onCreateViewHolder(parent, viewType).listen { position, type ->
            val item = items[position]
            val intent = Intent(parent.context, SinglePersonActivity::class.java)
            intent.putExtra(Constants.PERSON_TYPE, Constants.ACTOR_TYPE)
            intent.putExtra(Constants.PERSON_NAME, item.actorName)
            intent.putExtra(Constants.PERSON_ID, item.actorId)
            intent.putExtra(Constants.PERSON_IMAGE_URL, item.profileImageUrl)
            parent.context.startActivity(intent)
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun bind(binding: SingleMovieActorItemBinding, item: Actor, position: Int) {
        val url = item.profileImageUrl
        binding.actorName.text = item.actorName
        binding.castName.text = item.characterName

        if (url != null) imageLoader.loadListImageCenterCrop(url, binding.actorImage, "w500")
    }

    override fun add(response: Actor) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    override fun addAll(postItems: List<Actor>) {
        for (response in postItems) {
            add(response)
        }
    }
}