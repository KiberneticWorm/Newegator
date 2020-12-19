package ru.rubt.newegator.data.enums

enum class NewsSortType(private val sortNameType: String) {

    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt");

    override fun toString(): String = this.sortNameType

}