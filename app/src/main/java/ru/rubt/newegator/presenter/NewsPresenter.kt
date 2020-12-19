package ru.rubt.newegator.presenter

import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.rubt.newegator.R
import ru.rubt.newegator.data.NewsRepository
import ru.rubt.newegator.data.enums.NewsSortType
import ru.rubt.newegator.data.local.NewsSharedPreferences
import ru.rubt.newegator.data.states.NewsLoadState
import ru.rubt.newegator.view.NewsView

@InjectViewState
class NewsPresenter(
    private val newsRepository: NewsRepository,
    private val newsSharedPreferences: NewsSharedPreferences
) : MvpPresenter<NewsView>() {

    private val accessKey = newsSharedPreferences.getAccessKey()

    fun updateNews() {
        CoroutineScope(Dispatchers.IO).launch {

            val sourcesId = newsSharedPreferences.getNewsSources()

            val newsRemoteState = newsRepository
                    .updateNews(sourcesId,
                            NewsSortType.PUBLISHED_AT,
                            accessKey)

            withContext(Dispatchers.Main) {
                viewState.endLoaded()
            }

            if (newsRemoteState is NewsLoadState.NewsFailedLoadedState) {
                withContext(Dispatchers.Main) {
                    viewState.showFailed(R.string.show_failed)
                }
            }
        }
    }

    fun showNews(showMode: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val sourcesId = newsSharedPreferences.getNewsSources()

            val newsFromDb = newsRepository.getNewsFromDB(sourcesId)

            withContext(Dispatchers.Main) {
                viewState.showSuccess(newsFromDb, showMode)
            }
        }
    }

}
