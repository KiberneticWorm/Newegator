package ru.rubt.newegator.adapters

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.rubt.newegator.R
import ru.rubt.newegator.data.converters.DateConverter
import ru.rubt.newegator.data.db.NewsEntity

class StandardNewsAdapter(
        private val ctx: Context,
        private val lstNews: List<NewsEntity>,
        private val dateConverter: DateConverter
) : BaseNewsAdapter(ctx, lstNews, dateConverter) {

    companion object {
        const val ONE_SPACE = " "
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.setDescVisibility(View.GONE)

    }

}