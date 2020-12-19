package ru.rubt.newegator.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.rubt.newegator.data.db.NewsEntity

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface OneNewsView : MvpView {
    fun showSuccess(oneNews: NewsEntity)
}