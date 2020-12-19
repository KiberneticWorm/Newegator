package ru.rubt.newegator.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert
    fun insertNews(news: NewsEntity)

    @Query("select * from news ")
    fun getNews() : List<NewsEntity>

    @Query("select * from news where newsId = :newsId " +
            "and title in (select distinct title from news) " +
            "and description in (select distinct description from news)")
    fun getNewsById(newsId: Int) : NewsEntity

    @Query("select * from news where sourceId in (:sourceId)" +
            "and title in (select distinct title from news) " +
            "and description in (select distinct description from news)")
    fun getNewsBySources(sourceId: Array<String>) : List<NewsEntity>


    @Query("update news set isRead = :isRead where newsId = :newsId")
    fun updateNewsIsRead(newsId: Int, isRead: Boolean)

}