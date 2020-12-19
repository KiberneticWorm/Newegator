package ru.rubt.newegator.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    var sourceId: String,
    var sourceName: String,
    var author: String,
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String,
    var content: String,
    var isRead: Boolean = false
) {
    @PrimaryKey(autoGenerate = true) var newsId: Int = 0
}