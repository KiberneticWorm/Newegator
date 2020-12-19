package ru.rubt.newegator

import android.app.Application
import ru.rubt.newegator.di.AppComponent
import ru.rubt.newegator.di.DaggerAppComponent
import ru.rubt.newegator.di.modules.NewsModule
import ru.rubt.newegator.di.modules.PreferencesModule
import ru.rubt.newegator.di.modules.RetrofitModule
import ru.rubt.newegator.di.modules.RoomModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .roomModule(RoomModule(applicationContext))
                .preferencesModule(PreferencesModule(applicationContext))
                .newsModule(NewsModule())
                .retrofitModule(RetrofitModule())
                .build()
    }

}