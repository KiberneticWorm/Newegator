package ru.rubt.newegator.data.converters

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import ru.rubt.newegator.data.db.NewsEntity
import ru.rubt.newegator.data.model.News
import ru.rubt.newegator.data.model.Source

class NewsConverterTest {

    val newsConverter = NewsConverter()

    @Test
    fun testConvertToDb() {

        val lstNewsModel = arrayListOf(
            News(
                Source("lenta", "Lenta"), null,
            "Россиянин поджег квартиру после ссоры с женой",
            "Пожар произошел в многоэтажном доме на улице Металлистов в Пскове. Спасателям пришлось эвакуировать из здания 30 человек. Попав в горящую квартиру, они не обнаружили там хозяев. Впоследствии выяснилось, что жилое помещение намеренно поджег хозяин — мужчина 19…",
            "https://lenta.ru/news/2020/12/19/burn/",
            "https://icdn.lenta.ru/images/2020/12/18/09/20201218091827452/share_73dafe041e577024a935b2936daff653.jpg",
            "2020-12-19T04:05:00Z",
            "Пожар произошел в многоэтажном доме на улице Металлистов в Пскове. Спасателям пришлось эвакуировать из здания 30 человек. Попав в горящую квартиру, они не обнаружили там хозяев. Впоследствии выяснилось, что жилое помещение намеренно поджег хозяин — мужчина 19 [+2487chars]"
        )
        )
        val newsModel = lstNewsModel[0]

        val lstNewsDb = newsConverter.convertToDb(lstNewsModel)

        Assert.assertEquals(lstNewsDb.size, 1)

        val emptyStr = ""

        val newsDb = lstNewsDb[0]
        Assert.assertEquals(newsDb.sourceId, newsModel.source.id)
        Assert.assertEquals(newsDb.sourceName, newsModel.source.name)
        Assert.assertEquals(newsDb.author, emptyStr)
        Assert.assertEquals(newsDb.title, newsModel.title)
        Assert.assertEquals(newsDb.url, newsModel.url)
        Assert.assertEquals(newsDb.urlToImage, newsModel.urlToImage)
        Assert.assertEquals(newsDb.description, newsModel.description)
        Assert.assertEquals(newsDb.content, newsModel.content)

    }

    @Test
    fun testConvertToModel() {
        val lstNewsDb = arrayListOf(
            NewsEntity(
            "lenta", "Lenta", "",
            "Россиянин поджег квартиру после ссоры с женой",
            "Пожар произошел в многоэтажном доме на улице Металлистов в Пскове. Спасателям пришлось эвакуировать из здания 30 человек. Попав в горящую квартиру, они не обнаружили там хозяев. Впоследствии выяснилось, что жилое помещение намеренно поджег хозяин — мужчина 19…",
            "https://lenta.ru/news/2020/12/19/burn/",
            "https://icdn.lenta.ru/images/2020/12/18/09/20201218091827452/share_73dafe041e577024a935b2936daff653.jpg",
            "2020-12-19T04:05:00Z",
            "Пожар произошел в многоэтажном доме на улице Металлистов в Пскове. Спасателям пришлось эвакуировать из здания 30 человек. Попав в горящую квартиру, они не обнаружили там хозяев. Впоследствии выяснилось, что жилое помещение намеренно поджег хозяин — мужчина 19 [+2487chars]"
        )
        )
        val newsDb = lstNewsDb[0]

        val lstNewsModel = newsConverter.convertToModel(lstNewsDb)

        Assert.assertEquals(lstNewsModel.size, 1)

        val newsModel = lstNewsModel[0]

        Assert.assertEquals(newsModel.source.id, newsDb.sourceId)
        Assert.assertEquals(newsModel.source.name, newsDb.sourceName)
        Assert.assertEquals(newsModel.author, newsDb.author)
        Assert.assertEquals(newsModel.description, newsDb.description)
        Assert.assertEquals(newsModel.url, newsDb.url)
        Assert.assertEquals(newsModel.urlToImage, newsDb.urlToImage)
        Assert.assertEquals(newsModel.content, newsDb.content)
        Assert.assertEquals(newsModel.publishedAt, newsDb.publishedAt)
    }

}