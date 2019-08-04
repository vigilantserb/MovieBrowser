package com.stameni.com.whatshouldiwatch.screens.singleMovie

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.listen
import com.stameni.com.whatshouldiwatch.data.models.Actor
import com.stameni.com.whatshouldiwatch.screens.singleActor.SingleActorActivity
import kotlinx.android.synthetic.main.single_movie_actor_item.view.*

class SingleMovieActorsAdapter(
    private val items: ArrayList<Actor>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<SingleMovieActorsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_movie_actor_item, parent, false)
        return ViewHolder(v, parent).listen { position, type ->
            val item = items[position]
            val intent = Intent(parent.context, SingleActorActivity::class.java)
            intent.putExtra("actorName", item.actorName)
            intent.putExtra("actorId", item.actorId)
            intent.putExtra("actorUrl", item.profileImageUrl)
            parent.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(response: Actor) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(postItems: List<Actor>) {
        for (response in postItems) {
            add(response)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = items[position]
        val url = listItem.profileImageUrl
        holder.actorName.text = listItem.actorName
        holder.castName.text = listItem.characterName

        if (url != null) imageLoader.loadListImageCenterCrop(url, holder.actorImageView, "w500")
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var actorImageView = itemView.actor_image
        var actorName = itemView.actor_name
        var castName = itemView.cast_name
    }
}