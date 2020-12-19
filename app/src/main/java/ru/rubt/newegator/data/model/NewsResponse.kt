package ru.rubt.newegator.data.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<News>
)