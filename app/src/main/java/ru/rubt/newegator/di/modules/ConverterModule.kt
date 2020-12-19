package ru.rubt.newegator.di.modules

import dagger.Module
import dagger.Provides
import ru.rubt.newegator.data.converters.DateConverter
import ru.rubt.newegator.data.converters.NewsConverter

@Module
class ConverterModule {

    @Provides
    fun providesNewsConverter() : NewsConverter = NewsConverter()

    @Provides
    fun providesDateConverter() : DateConverter = DateConverter()

}