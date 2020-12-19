package ru.rubt.newegator.adapters

import android.content.Context
import android.util.Log
import android.view.View
import ru.rubt.newegator.R
import ru.rubt.newegator.data.converters.DateConverter
import ru.rubt.newegator.data.db.NewsEntity

class ExtendedNewsAdapter(
        private val ctx: Context,
        private val lstNews: List<NewsEntity>,
        private val dateConverter: DateConverter
) : BaseNewsAdapter(ctx, lstNews, dateConverter) {


}