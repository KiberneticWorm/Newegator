package ru.rubt.newegator

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import ru.rubt.newegator.data.local.NewsSharedPreferences
import ru.rubt.newegator.di.AppComponent
import ru.rubt.newegator.di.DaggerAppComponent
import ru.rubt.newegator.di.modules.NewsModule
import ru.rubt.newegator.di.modules.PreferencesModule
import ru.rubt.newegator.di.modules.RetrofitModule
import ru.rubt.newegator.di.modules.RoomModule
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var newsSharedPreferences: NewsSharedPreferences
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .roomModule(RoomModule(applicationContext))
                .preferencesModule(PreferencesModule(applicationContext))
                .newsModule(NewsModule())
                .retrofitModule(RetrofitModule())
                .build()

        appComponent.inject(this)

        if (newsSharedPreferences.isDarkTheme()) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

}