package ru.rubt.newegator.data.converters

import ru.rubt.newegator.data.model.News
import ru.rubt.newegator.data.model.Source
import ru.rubt.newegator.data.db.NewsEntity

class NewsConverter {

    fun convertToDb(news: List<News>) : List<NewsEntity> {
        val newsDb = arrayListOf<NewsEntity>()
        news.forEach {
            if (it.source.id != null) {
                newsDb.add(
                        NewsEntity(
                                it.source.id, it.source.name, it.author ?: "",
                                it.title, it.description ?: "",
                                it.url, it.urlToImage ?: "",
                                it.publishedAt, it.content ?: ""
                        )
                )
            }
        }
        return newsDb
    }

    fun convertToModel(newsEntities: List<NewsEntity>) : List<News> {
        val newsModel = arrayListOf<News>();

        newsEntities.forEach {
            newsModel.add(
                News(
                    Source(it.sourceId, it.sourceName), it.author,
                    it.title, it.description,
                    it.url, it.urlToImage,
                    it.publishedAt, it.content
                )
            )
        }
        return newsModel
    }

}