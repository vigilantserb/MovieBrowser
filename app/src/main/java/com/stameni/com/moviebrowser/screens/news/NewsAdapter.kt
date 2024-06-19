package com.stameni.com.moviebrowser.screens.news

import android.content.Intent
import android.view.ViewGroup
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.common.ImageLoader
import com.stameni.com.moviebrowser.common.baseClasses.BaseAdapter
import com.stameni.com.moviebrowser.common.baseClasses.BaseViewHolder
import com.stameni.com.moviebrowser.common.listen
import com.stameni.com.moviebrowser.data.models.NewsItem
import com.stameni.com.moviebrowser.databinding.NewsItemBinding
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId

class NewsAdapter(
    private val imageLoader: ImageLoader
) : BaseAdapter<NewsItem, NewsItemBinding>(NewsItemBinding::inflate) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<NewsItemBinding> {
        return super.onCreateViewHolder(parent, viewType).listen { position, type ->
            val item = items[position]
            val intent = Intent(parent.context, NewsWebViewActivity::class.java)
            intent.putExtra(Constants.SOURCE_LINK, item.source)
            parent.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun bind(binding: NewsItemBinding, item: NewsItem, position: Int) {
        val instant = Instant.parse(item.publishedAt)
        val date = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        binding.newsTitle.text = item.newsTitle
        binding.newsDescription.text = item.newsDescription
        binding.newsSource.text = item.publishedBy
        binding.publishedAt.text = date.toString()
        val url = item.urlToImage

        if (url != null) imageLoader.loadNewsImageCenterCrop(url, binding.imageView)
    }

    override fun add(response: NewsItem) {
        items.add(response)
        notifyItemInserted(items.size - 1)
    }

    override fun addAll(postItems: List<NewsItem>) {
        for (response in postItems) {
            add(response)
        }
    }
}