package ru.rubt.newegator.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.rubt.newegator.R
import ru.rubt.newegator.adapters.interfaces.ItemClickListener
import ru.rubt.newegator.data.converters.DateConverter
import ru.rubt.newegator.data.db.NewsEntity

abstract class BaseNewsAdapter(
        private val ctx: Context,
        protected val news: List<NewsEntity>,
        private val dateConverter: DateConverter
) : RecyclerView.Adapter<BaseNewsAdapter.NewsViewHolder>() {

    private var listener: ItemClickListener? = null

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemLayout = inflater.inflate(R.layout.layout_news_list_item, parent, false)
        return NewsViewHolder(listItemLayout as CardView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = news[position]

        holder.loadImage(newsItem.urlToImage)
        holder.setTitle(newsItem.title)
        holder.setDesc(newsItem.description)

        holder.setPublishedAt(
                ctx.getString(R.string.published_at_title)
                        .plus(StandardNewsAdapter.ONE_SPACE)
                        .plus(dateConverter.getDate(newsItem.publishedAt)))

        holder.setSource(
                ctx.getString(R.string.source_title)
                        .plus(StandardNewsAdapter.ONE_SPACE)
                        .plus(newsItem.sourceName))

        if (newsItem.isRead) {
            holder.checkHaveRead()
        }

        holder.setOnNewsClick(news[position].newsId)

    }

    inner class NewsViewHolder(
            private val root: CardView
    ) : RecyclerView.ViewHolder(root) {

        private val ivImage = root.findViewById<ImageView>(R.id.iv_image)
        private val tvTitle = root.findViewById<TextView>(R.id.tv_title)
        private val tvDesc = root.findViewById<TextView>(R.id.tv_desc)
        private val tvPublishedAt = root.findViewById<TextView>(R.id.tv_published_date)
        private val tvSource = root.findViewById<TextView>(R.id.tv_source)
        private val tvReadStatus = root.findViewById<TextView>(R.id.tv_read_status)

        fun loadImage(url: String) {

            Glide.with(ctx)
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .fallback(R.drawable.placeholder)
                    .into(ivImage)

        }

        fun setOnNewsClick(newsId: Int) {
            root.setOnClickListener {
                listener?.onNewsClick(newsId)
            }
        }

        fun setTitle(title: String) {
            tvTitle.text = title
        }

        fun setDescVisibility(visibility: Int) {
            tvDesc.visibility = visibility
        }

        fun setDesc(desc: String) {
            tvDesc.text = desc
        }

        fun setPublishedAt(date: String) {
            tvPublishedAt.text = date
        }

        fun setSource(source: String) {
            tvSource.text = source
        }

        fun checkHaveRead() {
            tvReadStatus.text = ctx.getString(R.string.status_have_read)
        }

    }

}