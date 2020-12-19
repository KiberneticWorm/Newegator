package ru.rubt.newegator.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.rubt.newegator.data.enums.NewsSortType
import ru.rubt.newegator.data.model.News
import ru.rubt.newegator.data.model.NewsResponse

interface NewsService {

    @GET("everything")
    fun getNews(
        @Query("sources") sources: String,
        @Query("sortBy") sortType: NewsSortType,
        @Query("apiKey") apiKey: String) : Call<NewsResponse>

}