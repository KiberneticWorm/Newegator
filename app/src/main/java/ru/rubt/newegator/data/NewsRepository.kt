package ru.rubt.newegator.data

import ru.rubt.newegator.data.converters.NewsConverter
import ru.rubt.newegator.data.enums.NewsSortType
import ru.rubt.newegator.data.remote.NewsService
import ru.rubt.newegator.data.db.NewsDao
import ru.rubt.newegator.data.db.NewsEntity
import ru.rubt.newegator.data.model.News
import ru.rubt.newegator.data.states.NewsLoadState
import java.lang.Exception
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val newsService: NewsService,
    private val newsConverter: NewsConverter
) {

    fun getNewsById(newsId: Int) : NewsEntity =
            newsDao.getNewsById(newsId)

    fun updateNewsIsRead(oneNews: NewsEntity) =
            newsDao.updateNewsIsRead(oneNews.newsId, oneNews.isRead)

    fun getNewsFromDB(sourcesId: Array<String>) : List<NewsEntity> =
            newsDao.getNewsBySources(sourcesId)

    private fun getNewsFromNetwork(sources: Array<String>, sortType: NewsSortType, apiKey: String): List<News> {
        val newsRequest = newsService.getNews(
                sources.joinToString(","), sortType, apiKey)

        val newsResponse = newsRequest.execute()

        if (!newsResponse.isSuccessful) {
            throw Exception("Network problems or Internet access denied")
        }

        val newsResponseBody = newsResponse.body() ?: throw Exception("News is not exists!")
        return newsResponseBody.articles ?: throw Exception("News is not exists!")
    }

    private fun saveNewsIntoDb(news: List<News>) {
        val newsDb = newsConverter.convertToDb(news)

        newsDb.forEach {
            newsDao.insertNews(it)
        }
    }

    fun updateNews(sources: Array<String>, sortType: NewsSortType, apiKey: String) : NewsLoadState {

        return try {

            val newsFromNetwork = getNewsFromNetwork(sources, sortType, apiKey)

            saveNewsIntoDb(newsFromNetwork)

            NewsLoadState.NewsSuccessLoadedState

        } catch (exc: Exception) {

            NewsLoadState.NewsFailedLoadedState

        }

    }

}