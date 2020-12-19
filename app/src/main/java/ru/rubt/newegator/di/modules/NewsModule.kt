package ru.rubt.newegator.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.rubt.newegator.data.converters.NewsConverter
import ru.rubt.newegator.data.db.AppDatabase
import ru.rubt.newegator.data.db.NewsDao
import ru.rubt.newegator.data.remote.NewsService

@Module
class NewsModule {

    @Provides
    fun providesNewsDao(appDatabase: AppDatabase) : NewsDao =
        appDatabase.newsDao()

    @Provides
    fun providesNewsService(retrofit: Retrofit) : NewsService {
        return retrofit.create(NewsService::class.java)
    }

}