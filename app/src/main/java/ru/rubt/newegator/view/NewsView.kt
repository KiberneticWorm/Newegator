package ru.rubt.newegator.view

import moxy.MvpView
import moxy.viewstate.strategy.*
import ru.rubt.newegator.data.db.NewsEntity

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface NewsView : MvpView {
    fun showSuccess(news: List<NewsEntity>, showMode: String)
    fun endLoaded()
    fun showFailed(message: Int)
}