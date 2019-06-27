package com.stameni.com.whatshouldiwatch.screens.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.listen
import com.stameni.com.whatshouldiwatch.data.models.NewsItem
import kotlinx.android.synthetic.main.news_item.view.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import java.util.*

class NewsAdapter(
    private val items: ArrayList<NewsItem>
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(v, parent).listen { position, type ->
            //            val item = items[position]
//            val intent = Intent(parent.context, MovieListActivity::class.java)
//            intent.putExtra("url", item.url)
//            intent.putExtra("title", item.title)
//            intent.putExtra("id", item.id)
//            parent.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(response: NewsItem) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(postItems: List<NewsItem>) {
        for (response in postItems) {
            add(response)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listItem = items[position]
        val instant = Instant.parse(listItem.publishedAt)
        val date = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        holder.newsTitle.text = listItem.newsTitle
        holder.newsDescription.text = listItem.newsDescription
        holder.newsSource.text = listItem.publishedBy
        holder.newsDate.text = date.toString()
        val url = listItem.urlToImage

        if(url != null) holder.addImageFromUrl(url)
    }

    class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        var newsTitle = itemView.newsTitle
        var newsDescription = itemView.newsDescription
        var newsSource = itemView.newsSource
        var newsDate = itemView.publishedAt
        var newsImage = itemView.imageView

        fun addImageFromUrl(url: String) {
            Glide.with(itemView.context)
                .load(url)
                .centerCrop()
                .into(newsImage)
        }
    }
}