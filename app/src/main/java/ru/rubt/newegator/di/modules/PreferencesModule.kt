package ru.rubt.newegator.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.rubt.newegator.data.local.NewsSharedPreferences

@Module
class PreferencesModule(private val ctx: Context) {

    @Provides
    fun providesNewsSharedPreferences() : NewsSharedPreferences =
            NewsSharedPreferences(ctx)

}