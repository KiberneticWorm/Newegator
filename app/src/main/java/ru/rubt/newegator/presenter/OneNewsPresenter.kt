package ru.rubt.newegator.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.rubt.newegator.data.NewsRepository
import ru.rubt.newegator.data.db.NewsEntity
import ru.rubt.newegator.view.OneNewsView

@InjectViewState
class OneNewsPresenter(
        private val newsRepository: NewsRepository
) : MvpPresenter<OneNewsView>() {

    fun showOneNews(newsId: Int) {
        CoroutineScope(Dispatchers.IO).async {
            val news = newsRepository.getNewsById(newsId)
            withContext(Dispatchers.Main) {
                viewState.showSuccess(news)
            }
        }
    }

    fun updateNewsIsRead(oneNews: NewsEntity) {
        CoroutineScope(Dispatchers.IO).async {
            newsRepository.updateNewsIsRead(oneNews)
        }
    }

}